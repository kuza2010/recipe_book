package com.example.myapplication.presentation.ui.fragments.category_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.category.Categories;
import com.example.myapplication.framework.retrofit.model.category.Category;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.category.CategoryServices;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.presentation.ui.OnVariantClick;
import com.example.myapplication.presentation.ui.SimpleAnimator;
import com.example.myapplication.presentation.ui.main.MainActivity;
import com.example.myapplication.presentation.ui.recipe.RecipeActivity;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.NO_CACHE;

public class CategoryFeedFragment extends Fragment
        implements OnVariantClick,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_category)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

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

        refreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void fetch(final String cacheControll) {
        Timber.d("categories download started! Cache control: \"%s\"",cacheControll);
        categoryService.getCategories(cacheControll, new NetworkCallback<Categories>() {
            @Override
            public void onResponse(Categories categories) {
                Timber.d("download %s category", categories.getCategories().size());
                if(cacheControll.equals(NO_CACHE)) {
                    refreshLayout.setRefreshing(false);
                    adapter.update(categories.getCategories());
                }
                else
                    adapter.setList(categories.getCategories());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Timber.e("Failed get categories. Messages: %s",throwable.getMessage());
                if(refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRecyclerClick(Category category) {
        Timber.d("Open RecipeActivity, id = %s", category);
        startActivity(RecipeActivity.getInstance(getActivity(), category.getName()));
    }

    @Override
    public void onRefresh() {
        Timber.d("refresh");
        fetch(NO_CACHE);
    }

    public void attemptPopup(String message,int delay){
        ((MainActivity)getActivity()).popupToast(message,delay);
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
            boolean isUpdate = false;
            for (Category each : categoryList) {
                if (!list.contains(each)) {
                    list.add(0, each);
                    isUpdate = true;
                }
            }

            if(!isUpdate)
                attemptPopup("No new category ;(",3);

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
        public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, int i) {
            final Category category = list.get(i);
            Timber.e("Start load image pos %s", i);
            SimpleAnimator.setDefaultAnimation(Animation.REVERSE,400,categoryViewHolder.image);
            imageServices
                    .getPicasso()
                    .load(ImageServices.getUrlForImage(list.get(i).getImageId()))
                    .error(R.drawable.load_image_error)
                    .into(categoryViewHolder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            categoryViewHolder.image.clearAnimation();
                        }

                        @Override
                        public void onError(Exception e) {
                            categoryViewHolder.image.clearAnimation();
                        }
                    });
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
