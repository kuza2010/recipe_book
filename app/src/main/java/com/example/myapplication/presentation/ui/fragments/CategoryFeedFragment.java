package com.example.myapplication.presentation.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.Categories;
import com.example.myapplication.framework.retrofit.model.Category;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.category.CategoryServices;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class CategoryFeedFragment extends Fragment {

    public static final String TAG = Fragment.class.getSimpleName();

    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerView;

    @Inject
    CategoryServices categoryService;
    @Inject
    ImageServices imageServices;


    private List<Category> categoryList = new ArrayList<>();
    private CategoriesAdapter adapter;


    public CategoryFeedFragment() {
        BaseApp.getComponent().inject(this);
        feetch();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        Timber.d("categories download started!");
        categoryService.getCategories(CACHE, new NetworkCallback<Categories>() {
            @Override
            public void onResponse(Categories categories) {
                Timber.d("download %s category", categories.getCategories().size());
                CategoryFeedFragment.this.categoryList.addAll(categories.getCategories());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed get categories");
            }
        });
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
            Timber.e("Start load image pos %s", i);
            imageServices
                    .getPicasso()
                    .load(ImageServices.getUrlForImage(i == 0 || i >= 5 ? 1 : i + 1))
                    .error(R.drawable.load_image_error)
                    .into(categoryViewHolder.image);
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
