package com.example.myapplication.presentation.ui.recipe;

import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.framework.retrofit.model.recipe.Recipe;
import com.example.myapplication.framework.retrofit.model.recipe.Recipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.framework.retrofit.services.recipe.RecipeServices;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class RecipeActivity extends BaseToolbarActivity {
    public static final String TITLE = "toolbar_title";

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerView;

    @Inject
    RecipeServices recipeServices;
    @Inject
    ImageServices imageServices;

    private RecipeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        doInject();

        fetch();

        setTitle(getIntent().getStringExtra(TITLE));

        adapter = new RecipeAdapter();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void doInject(){
        ButterKnife.bind(this);
        BaseApp.getComponent().inject(this);
    }


    public void fetch() {
        Timber.d("recipe download started!");
        recipeServices.getRecipeByCategoryName("Супы",CACHE, new NetworkCallback<Recipes>() {
            @Override
            public void onResponse(Recipes recipes) {
                Timber.d("download %s category", recipes.getRecipes().size());
                adapter.setRecipeList(recipes.getRecipes());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed get categories");
            }
        });
    }

    public static Intent getInstance(Context packageContext, String categoryName){
        Intent intent = new Intent(packageContext, RecipeActivity.class);
        intent.putExtra(RecipeActivity.TITLE, categoryName);
        return intent;
    }



    public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

        private List<Recipe> recipeList;

        public void setRecipeList(List<Recipe> recipeList) {
            if (!this.recipeList.isEmpty())
                this.recipeList.clear();

            this.recipeList.addAll(recipeList);
            notifyDataSetChanged();
        }

        public RecipeAdapter() {
            this.recipeList = new ArrayList<>();
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_by_category_recycler_row, viewGroup, false);
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
            private ImageView image;
            private TextView name;

            public RecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.recipe_image_view);
                name =  itemView.findViewById(R.id.recipe_name_text_view);
            }
        }

    }
}
