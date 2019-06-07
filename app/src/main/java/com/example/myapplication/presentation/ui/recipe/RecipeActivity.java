package com.example.myapplication.presentation.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import timber.log.Timber;

public class RecipeActivity extends BaseToolbarActivity
        implements RecipePresenter.RecipeContractView, RecipeAdapter.RecipeClickListener {

    public static final String TITLE = "toolbar_title";
    public static final String USER_ID = "id";
    public static final String TYPE_PAGE = "toolbar_title";

    public enum TypePage{
        FAVORITE("Favorite"),
        BY_ME("By_me"),
        COMMON("common");

        public String title;
        TypePage(String title) {
            this.title = title;
        }
    }

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.hint_layout)
    RelativeLayout hintLayout;
    @BindView(R.id.image_view_smile)
    ImageView imageHint;
    @BindView(R.id.text_view_hint)
    TextView hint;

    @Inject
    RecipePresenterImpl presenter;

    private RecipeAdapter adapter;

    public static Intent getInstance(Context packageContext, String categoryName) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(RecipeActivity.TITLE, categoryName);
        return intent;
    }

    public static Intent getInstanceById(Context packageContext, int userId, TypePage typePage) {
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(RecipeActivity.TYPE_PAGE,typePage.title);
        intent.putExtra(RecipeActivity.USER_ID,userId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

        doInject();
        configureRecyclerView();
        init();
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
    }

    private void init(){
        hintLayout.setVisibility(View.INVISIBLE);
        String currentCategory = getIntent().getStringExtra(RecipeActivity.TITLE);
        String pageType = getIntent().getStringExtra(TYPE_PAGE);
        int userId = getIntent().getIntExtra(USER_ID,-1);

        if(userId==-1){
            Timber.d("init unauthorized user ID = %s",userId);
            setTitle(currentCategory);
            presenter.init(userId,pageType,currentCategory);
        }else {
            Timber.d("init authorized user ID = %s",userId);
            presenter.init(userId,pageType,null);
            hideToolbar();
        }
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
    public void showHint() {
        //hint visible
        hintLayout.setVisibility(View.VISIBLE);
        imageHint.setImageResource(R.drawable.error);
        hint.setText(String.format("Not recipe"));
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
