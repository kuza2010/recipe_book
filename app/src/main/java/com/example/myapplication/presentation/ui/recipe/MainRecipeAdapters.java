package com.example.myapplication.presentation.ui.recipe;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecipeAdapters extends RecyclerView.Adapter<MainRecipeAdapters.MainRecipeIngredientsViewHolder> {

    private List<String> itemList;
    private Type type;

    public MainRecipeAdapters(Type type) {
        this.itemList = new ArrayList<>();
        this.type = type;
    }

    public void setItemList(List<String> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainRecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recipe_ingredient_or_tools_row, viewGroup, false);
        return new MainRecipeIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecipeIngredientsViewHolder generalRecipeIngredientsViewHolder, int i) {
        generalRecipeIngredientsViewHolder.name.setText(itemList.get(i));

        if (type.equals(Type.STEPS)) {
            generalRecipeIngredientsViewHolder.image.setImageResource(R.drawable.number_background);
            generalRecipeIngredientsViewHolder.number.setText(String.valueOf(i + 1));
            generalRecipeIngredientsViewHolder.name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    @Override
    public int getItemCount() {
        if (null != itemList)
            return itemList.size();

        return 0;
    }

    class MainRecipeIngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        AppCompatTextView name;
        @BindView(R.id.number)
        AppCompatTextView number;
        @BindView(R.id.item_image)
        AppCompatImageView image;

        public MainRecipeIngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public enum Type{
        INGREDIENTS, TOOLS, STEPS
    }
}
