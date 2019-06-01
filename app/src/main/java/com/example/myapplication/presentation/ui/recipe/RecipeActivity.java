package com.example.myapplication.presentation.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenterImpl;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.example.myapplication.presentation.ui.GridSpacingItemDecoration;
import com.example.myapplication.presentation.ui.fragments.RecipeAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends BaseToolbarActivity
        implements RecipePresenter.RecipeContractView, RecipeAdapter.RecipeClickListener {
    public static final String TITLE = "toolbar_title";

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;

    @Inject
    RecipePresenterImpl presenter;

    private RecipeAdapter adapter;
    private String currentCategory;

    public static Intent getInstance(Context packageContext, String categoryName) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(RecipeActivity.TITLE, categoryName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        currentCategory = getIntent().getStringExtra(TITLE);
        setTitle(currentCategory);

        doInject();
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        adapter = new RecipeAdapter(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(this, 8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void doInject() {
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);
        presenter.bind(this);

        presenter.init(currentCategory);
    }

    @Override
    public void setRecipeList(List<Recipe> recipeList) {
        if (adapter != null)
            adapter.setList(recipeList);
    }

    @Override
    public void updateRecipes(List<Recipe> recipeList) {
        if (adapter != null)
            adapter.update(recipeList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind();
    }

    @Override
    public void onRecipeClick(int id,String recipeName) {
        startActivity(MainRecipeActivity.getInstance(this,id));
    }
}
