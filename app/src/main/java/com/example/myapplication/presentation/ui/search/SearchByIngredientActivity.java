package com.example.myapplication.presentation.ui.search;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.GridSpacingItemDecoration;
import com.example.myapplication.presentation.ui.fragments.RecipeAdapter;
import com.example.myapplication.presentation.ui.fragments.search_fragment.QueryTextListener;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ingredients_activity);
        BaseApp.getComponent().inject(this);
        ButterKnife.bind(this);
        setTitle("Search by Ingredient");

        configureRecyclerView();
        configureSearchView();
    }

    private void configureSearchView() {
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new ListenerSample(this));

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

    @Override
    public void onCursorChanged(Cursor cursor) {
        Timber.d("onCursorChanged: nothing");
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

    class ListenerSample extends QueryTextListener{

        public ListenerSample(QueryTextListener.Listener listener) {
            super(listener);
        }

        @Override
        public boolean onQueryTextChange(String suggestionPartText) {
            return false;
        }
    }
}
