package com.example.myapplication.presentation.ui.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.ExpandableRecipes;
import com.example.myapplication.presentation.presenter.recipe.ExpandableRecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.ExpandableRecipePresenterImpl;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainRecipeActivity extends BaseToolbarActivity
        implements ExpandableRecipePresenter.RecipeContractView {
    @Inject
    ExpandableRecipePresenterImpl presenter;

    @BindView(R.id.expandable_description_content)
    ExpandableTextView expandableTextView;
    @BindView(R.id.instrument)
    RecyclerView instrument;
    @BindView(R.id.ingredient)
    RecyclerView ingredient;
    @BindView(R.id.direction)
    RecyclerView direction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recipe_activity);
        doInject();

        expandableTextView.setText("Даже не помню, откуда рецепт, но он у меня лет 7, наверное. Очень тонкие, нежные и вкусные блины без дрожжей. Готовятся очень быстро - по 1 мин. обжаривать каждую сторону, а то и меньше. Рецепт настолько удачен, что изменять ничего не стоит.");
    }

    private void doInject() {
        BaseApp.getComponent().inject(this);
        presenter.bind(this);
        ButterKnife.bind(this);
    }

    @Override
    public void setRecipe(ExpandableRecipes recipe) {
        Timber.d("setRecipe: set %s ",recipe);
    }

    @Override
    public void showErrorScreen(String message) {
        Timber.d("showErrorScreen:");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.unbind();
    }
}
