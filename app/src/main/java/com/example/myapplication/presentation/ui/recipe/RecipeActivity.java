package com.example.myapplication.presentation.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenter;
import com.example.myapplication.presentation.presenter.recipe.RecipePresenterImpl;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class RecipeActivity extends BaseToolbarActivity implements RecipePresenter.RecipeContractView {
    public static final String TITLE = "toolbar_title";

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;

    @Inject
    RecipeServices recipeServices;
    @Inject
    ImageServices imageServices;
    @Inject
    RecipePresenterImpl presenter;

    private RecipeAdapter adapter;
    private String currentCategory;

    public static Intent getInstance(Context packageContext, String categoryName){
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(RecipeActivity.TITLE, categoryName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCategory = getIntent().getStringExtra(TITLE);
        setContentView(R.layout.recipe_activity);
        doInject();

        setTitle(currentCategory);
        adapter = new RecipeAdapter();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(this,8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void doInject(){
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);
        presenter.bind(this);

        presenter.init(currentCategory);
    }

    @Override
    public void updateRecipes(List<Recipe> recipeList) {
        adapter.setRecipeList(recipeList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

        private List<Recipe> recipeList;

        public void setRecipeList(List<Recipe> recipeList) {
            this.recipeList.addAll(0, recipeList);
            notifyItemRangeChanged(0, recipeList.size());
        }

        public RecipeAdapter() {
            this.recipeList = new ArrayList<>();
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_recycler_row, viewGroup, false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
            Timber.e("Start load image pos %s", i);
            imageServices
                    .getPicasso()
                    .load(ImageServices.getUrlForImage(recipeList.get(i).getImageId()))
                    .error(R.drawable.load_image_error)
                    .into(recipeViewHolder.image);
            recipeViewHolder.name.setText(recipeList.get(i).getName());
        }

        @Override
        public int getItemCount() {
            if (recipeList == null)
                return 0;

            return recipeList.size();
        }


        public class RecipeViewHolder extends RecyclerView.ViewHolder{
            ImageView image;
            TextView name;

            public RecipeViewHolder(View view) {
                super(view);
                image = view.findViewById(R.id.recipe_image_view);
                name = view.findViewById(R.id.recipe_name_text_view);
            }
        }

    }
}
