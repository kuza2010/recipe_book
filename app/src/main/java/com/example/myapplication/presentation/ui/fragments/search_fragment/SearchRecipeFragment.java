package com.example.myapplication.presentation.ui.fragments.search_fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.RecipesPreferences;
import com.example.myapplication.Utils;
import com.example.myapplication.content_provider.SearchProvider;
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;
import com.example.myapplication.presentation.ui.GridSpacingItemDecoration;
import com.example.myapplication.presentation.ui.QueryTextListener;
import com.example.myapplication.presentation.ui.SuggestionCursorAdapter;
import com.example.myapplication.presentation.ui.fragments.RecipeAdapter;
import com.example.myapplication.presentation.ui.recipe.MainRecipeActivity;
import com.example.myapplication.presentation.ui.search.SearchByIngredientActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.LIMIT_CHARACTERS_IN_SEARCH;

public class SearchRecipeFragment extends Fragment implements QueryTextListener.Listener, RecipeAdapter.RecipeClickListener {
    @Inject
    ImageServices imageServices;
    @Inject
    SearchServices searchServices;
    @Inject
    RecipesPreferences preferences;

    @BindView(R.id.recycle_view_searched_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.image_view_smile)
    ImageView imageView;
    @BindView(R.id.text_view_hint)
    TextView hintText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.search_by_frozen_button)
    AppCompatButton byFrozen;

    private SuggestionCursorAdapter suggestionAdapter;
    private RecipeAdapter recyclerAdapter;
    private QueryTextListener queryListener;

    public SearchRecipeFragment() {
        BaseApp.getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApp.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        boolean isRegisterUser = preferences.getValue(RecipesPreferences.IS_REGISTRED_USER, false);
        if (!isRegisterUser) {
            Timber.d("hide by frozen button");
            byFrozen.setVisibility(View.GONE);
        }

        //TODO: костыль для серч вью
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        autoComplete.setDropDownWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        autoComplete.setDropDownAnchor(R.id.anchor);

        configureSearchView();
        configureRecyclerView();

        setVisibleHint(true);

        return view;
    }

    @Override
    public void onResume() {
        setVisibilityProgressBar(false);
        super.onResume();
    }

    private void configureSearchView() {
        suggestionAdapter = new SuggestionCursorAdapter(getContext(), null, searchView,SearchProvider.Type.RECIPE);
        queryListener = new QueryTextListener(this, SearchProvider.Type.RECIPE);

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(queryListener);
        searchView.setSuggestionsAdapter(suggestionAdapter);

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("onAddClick: touch search view close button");
                setVisibleHint(true);
                setVisibilityProgressBar(false);
                searchView.onActionViewCollapsed();
                recyclerAdapter.clear();
            }
        });

        TextView query = ((TextView) searchView.findViewById(R.id.search_src_text));
        query.setFilters(new InputFilter[]{new InputFilter.LengthFilter(LIMIT_CHARACTERS_IN_SEARCH)});
    }
    private void configureRecyclerView() {
        recyclerAdapter = new RecipeAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getActivity(), 8), true));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void setVisibleHint(boolean isVisible) {
        if (isVisible) {
            imageView.setVisibility(View.VISIBLE);
            hintText.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            hintText.setVisibility(View.INVISIBLE);
        }
    }

    public void setVisibilityProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void replaceHint(int resourceImageId, String hintNewText) {
        imageView.setImageResource(resourceImageId);
        hintText.setText(hintNewText);
    }

    private void clearSuggestion() {
        if (searchView != null) {
            Timber.d("clearSuggestion: clear search view");
            searchView.setIconified(false);
        }
    }

    @Override
    public void onCursorChanged(Cursor cursor) {
        if (cursor == null)
            clearSuggestion();

        setVisibilityProgressBar(false);
        suggestionAdapter.changeCursor(cursor);
    }

    @Override
    public void onSubmit(final String toSearch) {
        if (toSearch != null && !toSearch.isEmpty()) {
            searchView.onActionViewExpanded();
            searchView.clearFocus();
            setVisibilityProgressBar(true);

            searchServices.getRecipesByFullName(CACHE, toSearch, new NetworkCallback<Recipes>() {
                @Override
                public void onResponse(Recipes body) {
                    Timber.d("onResponse: get %s item", body.getRecipes().size());

                    if (body.getRecipes().size() == 0) {
                        replaceHint(R.drawable.error, String.format("Sorry, not found %s!", toSearch));
                        setVisibleHint(true);
                        setVisibilityProgressBar(false);
                        recyclerAdapter.clear();
                        return;
                    }

                    List<Recipe> recipeList = body.getRecipes();
                    recyclerAdapter.setList(recipeList);

                    setVisibleHint(false);
                    setVisibilityProgressBar(false);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    recyclerAdapter.clear();
                    setVisibleHint(true);
                    setVisibilityProgressBar(false);
                    Timber.e("onSubmit: exception %s", throwable.getMessage());
                }
            });
        }
    }

    @Override
    public void showProgressbar() {
        setVisibilityProgressBar(true);
    }

    @Override
    public void onRecipeClick(int recipeId, String recipeName) {
        startActivity(MainRecipeActivity.getInstance(getContext(),recipeId));
    }

    @OnClick(R.id.search_by_ingredient_button)
    public void onSearchByIngredientClick(Button button){
        startActivity(new Intent(getActivity(), SearchByIngredientActivity.class));
    }

    @OnClick(R.id.search_by_frozen_button)
    public void onSearchByFrozenClick(){

    }
}
