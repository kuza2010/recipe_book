package com.example.myapplication.presentation.ui.search;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.content_provider.SearchProvider;
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.GridSpacingItemDecoration;
import com.example.myapplication.presentation.ui.fragments.RecipeAdapter;
import com.example.myapplication.presentation.ui.CustomCursorAdapter;
import com.example.myapplication.presentation.ui.QueryTextListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.LIMIT_CHARACTERS_IN_SEARCH_INGREDIENTS;

public class SearchByIngredientActivity extends BaseToolbarActivity implements QueryTextListener.Listener{
    public static final String TITLE = "Search by Ingredient";

    @BindView(R.id.recycle_view_searched_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.text_view_hint)
    TextView hintText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.image_view_smile)
    ImageView imageView;

    @Inject
    SearchServices searchServices;

    private RecipeAdapter recyclerAdapter;
    private CustomCursorAdapter suggestionAdapter;
    private QueryTextListener queryListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ingredients_activity);
        doInject();
        setTitle(TITLE);

        //TODO: костыль для серч вью
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        autoComplete.setDropDownWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        autoComplete.setDropDownAnchor(R.id.anchor);

        configureRecyclerView();
        configureSearchView();
    }

    private void doInject(){
        BaseApp.getComponent().inject(this);
        ButterKnife.bind(this);
    }

    private void configureSearchView() {
        suggestionAdapter = new CustomCursorAdapter(this, null, searchView,SearchProvider.Type.INGREDIENT);
        queryListener = new QueryTextListener(this, SearchProvider.Type.INGREDIENT);

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(queryListener);
        searchView.setSuggestionsAdapter(suggestionAdapter);

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("onClick: touch search view close button");
                setVisibleHint(true);
                setVisibilityProgressBar(false);
                searchView.onActionViewCollapsed();
                recyclerAdapter.clear();
            }
        });

        TextView query = ((TextView) searchView.findViewById(R.id.search_src_text));
        query.setFilters(new InputFilter[]{new InputFilter.LengthFilter(LIMIT_CHARACTERS_IN_SEARCH_INGREDIENTS)});

        setVisibilityProgressBar(false);
    }
    private void configureRecyclerView() {
        recyclerAdapter = new RecipeAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(this, 8), true));
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

            //Arrays.asList(toSearch.split(",")) - is no modified
            List<String> toSearchList = new ArrayList<>(Arrays.asList(toSearch.split(",")));

            searchServices.getRecipeByIngredients(CACHE,toSearchList , new NetworkCallback<Recipes>() {
                @Override
                public void onResponse(Recipes body) {
                    Timber.d("onResponse: get %s item", body.getRecipes().size());

                    if (body.getRecipes().size() == 0) {
                        replaceHint(R.drawable.error, String.format("Sorry, not found recipes!"));
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
}
