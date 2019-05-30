package com.example.myapplication.presentation.ui.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;
import com.ms.square.android.expandabletextview.ExpandableTextView;

public class MainRecipeActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recipe_activity);

        ExpandableTextView expandableTextView =(ExpandableTextView)findViewById(R.id.expandable_description_content);

        expandableTextView.setText("Даже не помню, откуда рецепт, но он у меня лет 7, наверное. Очень тонкие, нежные и вкусные блины без дрожжей. Готовятся очень быстро - по 1 мин. обжаривать каждую сторону, а то и меньше. Рецепт настолько удачен, что изменять ничего не стоит.");
    }
}
