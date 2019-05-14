package com.example.myapplication.presentation.ui.main;

import android.os.Bundle;
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
import com.example.myapplication.presentation.ui.OnVariantClick;
import com.example.myapplication.presentation.ui.recipe.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class CategoryFeedFragment extends Fragment implements OnVariantClick {

    public static final String TAG = Fragment.class.getSimpleName();

    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerView;

    @Inject
    CategoryServices categoryService;
    @Inject
    ImageServices imageServices;

    private CategoriesAdapter adapter;

    public CategoryFeedFragment() {
        BaseApp.getComponent().inject(this);
        adapter = new CategoriesAdapter(this);
        fetch(CACHE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity_recepies, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void fetch(String cacheControll) {
        Timber.d("categories download started! Cache control: \"%s\"",cacheControll);
        categoryService.getCategories(cacheControll, new NetworkCallback<Categories>() {
            @Override
            public void onResponse(Categories categories) {
                Timber.d("download %s category", categories.getCategories().size());
                adapter.setList(categories.getCategories());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed get categories. Messages: %s",throwable.getMessage());
            }
        });
    }

    @Override
    public void onRecyclerClick(Category category) {
        Timber.d("Open RecipeActivity, id = %s", category);
        startActivity(RecipeActivity.getInstance(getActivity(), category.getName()));
    }


    private class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

        private OnVariantClick listener;
        private List<Category> list;

        public void setList(List<Category> categoryList) {
            if(!this.list.isEmpty())
                this.list.clear();

            this.list.addAll(categoryList);
            notifyDataSetChanged();
        }

        public void update(List<Category> categoryList) {
            for (Category each : categoryList) {
                if (!list.contains(each))
                    list.add(each);
            }

            notifyDataSetChanged();
        }

        public CategoriesAdapter (OnVariantClick listener) {
            this.listener = listener;
            this.list = new ArrayList<>();
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_recycler_row, viewGroup, false);
            return new CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
            final Category category = list.get(i);
            Timber.e("Start load image pos %s", i);
            imageServices
                    .getPicasso()
                    .load(ImageServices.getUrlForImage(list.get(i).getImageId()))
                    .error(R.drawable.load_image_error)
                    .into(categoryViewHolder.image);
            categoryViewHolder.name.setText(category.getName());

            if (listener != null) {
                categoryViewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Timber.d("onRecyclerClick: clicked \" %s \"",category);
                        listener.onRecyclerClick(category);
                    }
                });
            } else {
                Timber.w("Listener is null! Skipped touch in pos %s", i);

            }
        }

        @Override
        public int getItemCount() {
            if (list != null)
                return list.size();

            return 0;
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder {
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
