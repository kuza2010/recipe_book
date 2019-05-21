package com.example.myapplication.presentation.ui.fragments;

import android.support.annotation.NonNull;
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
import com.example.myapplication.framework.retrofit.services.image.ImageServices;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    @Inject
    ImageServices imageServices;

    private List<Recipe> list;

    public void setList(List<Recipe> categoryList) {
        if (!this.list.isEmpty())
            this.list.clear();

        this.list.addAll(categoryList);
        notifyDataSetChanged();
    }

    public void update(List<Recipe> categoryList) {
        List<Recipe> different = Utils.getNewRecipes(list, categoryList);
        if (different.size() > 0) {
            for (Recipe recipe : different)
                list.add(0, recipe);
            notifyItemRangeChanged(0, different.size());
        }
        Timber.d("update: nothing update");
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public RecipeAdapter() {
        BaseApp.getComponent().inject(this);
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_recycler_row, viewGroup, false);
        return new RecipeAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        final Recipe category = list.get(i);
        Timber.e("Start load image pos %s", i);
        imageServices
                .getPicasso()
                .load(ImageServices.getUrlForImage(list.get(i).getImageId()))
                .error(R.drawable.load_image_error)
                .into(recipeViewHolder.image);
        recipeViewHolder.name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();

        return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public RecipeViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.recipe_image_view);
            name = view.findViewById(R.id.recipe_name_text_view);
        }
    }
}