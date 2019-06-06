package com.example.myapplication.presentation.ui.fragments.preferences;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class PreferencesAdapter extends RecyclerView.Adapter<PreferencesAdapter.PreferencesViewHolder>{

    private List<Preferences> content;
    private MenuListener listener;

    public PreferencesAdapter(List<Preferences> content, MenuListener listener) {
        this.content = content;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PreferencesAdapter.PreferencesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.preferences_recycler_view_row, viewGroup, false);
        return new PreferencesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferencesAdapter.PreferencesViewHolder preferencesViewHolder, int i) {
        final Preferences current = content.get(i);
        preferencesViewHolder.textView.setText(current.title);
        preferencesViewHolder.imageView.setImageResource(current.resId);

        preferencesViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onVariantClick(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (content != null)
            return content.size();

        return 0;
    }

    class PreferencesViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout layout;


        public PreferencesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.icon);
            layout = itemView.findViewById(R.id.layout);
        }
    }


    public enum Preferences{
        MY_RECIPE("My recipe",R.drawable.cake),
        FAVORITE ("Favorite",R.drawable.favorites),
        ADD_RECIPE("Add recipe", R.drawable.add_recipe),
        LOGOUT("Log out",R.drawable.logout);

        public String title;
        public int resId;

        Preferences(String title,int resId) {
            this.title = title;
            this.resId= resId;
        }
    }

    interface MenuListener{
        void onVariantClick(Preferences preferences);
    }
}