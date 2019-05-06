package com.example.myapplication.presentation.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.CategoryServiceImpl;
import com.example.myapplication.framework.retrofit.model.Categories;
import com.example.myapplication.framework.retrofit.model.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CategoryFeedFragment extends Fragment {
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerView;

    @Inject
    CategoryServiceImpl categoryService;

    private List<Category> categoryList = new ArrayList<>();
    private CategoriesAdapter adapter;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public CategoryFeedFragment() {
        Timber.d("fragment constructor");
        BaseApp.getComponent().inject(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                feetch();
            }
        }).start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity_recepies, container, false);
        ButterKnife.bind(this, view);

        adapter = new CategoriesAdapter(categoryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void feetch() {
        Timber.e("categories download started!");
        this.categoryList.addAll(categoryService.getCategories().getCategories());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            } // This is your code
        };
        mainHandler.post(myRunnable);
        Timber.e("categories download completed!");
    }

    public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
        private List<Category> list;

        public CategoriesAdapter(List<Category> categories) {
            if (categories != null)
                this.list = categories;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freagment_recycler_category_row, viewGroup, false);
            return new CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
            Category category = list.get(i);
            byte[] decodedString = Base64.decode(category.getData(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            categoryViewHolder.image.setImageBitmap(decodedByte);
            categoryViewHolder.name.setText(category.getName());
        }

        @Override
        public int getItemCount() {
            if (list != null)
                return list.size();
            return 0;
        }

        public class CategoryViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name;

            public CategoryViewHolder(View view) {
                super(view);
                image = view.findViewById(R.id.category_element_image);
                name = view.findViewById(R.id.category_element_name);
            }
        }
    }
}
