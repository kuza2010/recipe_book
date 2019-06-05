package com.example.myapplication.presentation.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.AllStep;
import com.example.myapplication.framework.retrofit.model.recipe.main_recipe.GeneralRecipe;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.presentation.presenter.recipe.general_recipe.GeneralRecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.general_recipe.GeneralRecipePresenterImpl;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.USER_ID;
import static com.example.myapplication.RecepiesConstant.USER_UNAUTHORIZED_ID;

public class MainRecipeActivity extends BaseToolbarActivity
        implements GeneralRecipePresenter.RecipeContractView {
    public static final String RECIPE_ID = "id";

    @Inject
    ImageServices imageServices;
    @Inject
    GeneralRecipePresenterImpl presenter;

    @BindView(R.id.expandable_description_content)
    ExpandableTextView expandableTextView;
    @BindView(R.id.instrument)
    RecyclerView tools;
    @BindView(R.id.ingredient)
    RecyclerView ingredient;
    @BindView(R.id.steps)
    RecyclerView steps;
    @BindView(R.id.main_image_view)
    ImageView mainImage;
    @BindView(R.id.recipe_name)
    AppCompatTextView recipeName;
    @BindView(R.id.nickname)
    AppCompatTextView authorName;
    @BindView(R.id.like)
    ImageView favorite;
    @BindView(R.id.cooking_time)
    TextView cockingTime;
    @BindView(R.id.recipe_rating)
    TextView rating;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private MainRecipeAdapters toolsAdapter;
    private MainRecipeAdapters ingredientAdapter;
    private MainRecipeAdapters stepsAdapter;

    public static Intent getInstance(Context packageContext, int recipeId) {
        Intent intent = new Intent(packageContext, MainRecipeActivity.class);
        intent.putExtra(RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recipe_activity);
        doInject();

        configure();
        presenter.init(CACHE,USER_ID, getId());
    }

    private void doInject() {
        BaseApp.getComponent().inject(this);
        presenter.bind(this);
        ButterKnife.bind(this);
    }

    private void configure(){
        hideToolbar();
        progressBar.setVisibility(View.VISIBLE);

        toolsAdapter = new MainRecipeAdapters(MainRecipeAdapters.Type.TOOLS);
        tools.setLayoutManager(new LinearLayoutManager(this));
        tools.setItemAnimator(new DefaultItemAnimator());
        tools.setAdapter(toolsAdapter);

        ingredientAdapter = new MainRecipeAdapters(MainRecipeAdapters.Type.INGREDIENTS);
        ingredient.setLayoutManager(new LinearLayoutManager(this));
        ingredient.setItemAnimator(new DefaultItemAnimator());
        ingredient.setAdapter(ingredientAdapter);

        stepsAdapter = new MainRecipeAdapters(MainRecipeAdapters.Type.STEPS);
        steps.setLayoutManager(new LinearLayoutManager(this));
        steps.setItemAnimator(new DefaultItemAnimator());
        steps.setAdapter(stepsAdapter);
    }

    private int getId() {
        return getIntent().getIntExtra(RECIPE_ID, USER_UNAUTHORIZED_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.unbind();
    }

    @Override
    public void setRecipeContent(GeneralRecipe recipe) {
        Timber.d("setRecipeContent: set %s ",recipe);

        imageServices.getPicasso()
                .load(ImageServices.getUrlForImage(recipe.getRecipes().getIdImage()))
                .error(R.drawable.load_image_error)
                .into(mainImage);

        toolsAdapter.setItemList(recipe.getTools());
        ingredientAdapter.setItemList(recipe.getIngredients());
        List<String> steps = new ArrayList<>();
        for (AllStep step:recipe.getAllSteps())
            steps.add(step.getDescription());
        stepsAdapter.setItemList(steps);

        expandableTextView.setText(recipe.getRecipes().getDescription());
        recipeName.setText(recipe.getRecipes().getName());
        authorName.setText(recipe.getAutorName());
        rating.setText(String.format("Rating: %s",recipe.getRecipes().getRating()));
        cockingTime.setText(String.format("Cocking time: %s",recipe.getRecipes().getTotalTime()));

        if(recipe.getIsFavorite())
            favorite.setImageResource(R.drawable.like);

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorScreen(String message) {
        Timber.d("showErrorScreen:");
        progressBar.setVisibility(View.GONE);
    }

}
