package com.example.myapplication.presentation.ui.fragments.search_fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.framework.retrofit.model.search.recipe_search.Recipe;
import com.example.myapplication.framework.retrofit.model.search.recipe_search.SerchedRecipes;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;
import com.example.myapplication.presentation.ui.fragments.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class SearchRecipeFragment extends Fragment implements QueryTextListener.Listener {
    @Inject
    ImageServices imageServices;
    @Inject
    SearchServices searchServices;

    @BindView(R.id.recycle_view_searched_recipes)
    RecyclerView recyclerView;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.image_view_smile)
    ImageView imageView;
    @BindView(R.id.text_view_hint)
    TextView hintText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private CustomCursorAdapter suggestionAdapter;
    private RecipeAdapter recyclerAdapter;
    private QueryTextListener queryListener;

    public SearchRecipeFragment() {
        BaseApp.getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);

        //TODO: костыль для серч вью
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        autoComplete.setDropDownWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        autoComplete.setDropDownAnchor(R.id.anchor);

        configureSearchView();
        configureRecyclerView();

        setVisibleHint(true);

        return view;
    }

    @Override
    public void onResume() {
        setVisibilityProgressBar(false);
        super.onResume();
    }

    private void configureSearchView() {
        suggestionAdapter = new CustomCursorAdapter(getContext(), null, searchView);
        queryListener = new QueryTextListener(this);

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(queryListener);
        searchView.setSuggestionsAdapter(suggestionAdapter);

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("onClick: touch search view close button");
                setVisibleHint(true);
                setVisibilityProgressBar(false);
                searchView.onActionViewCollapsed();
                recyclerAdapter.clear();
            }
        });

        TextView query = ((TextView) searchView.findViewById(R.id.search_src_text));
        query.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
    }

    private void configureRecyclerView() {
        recyclerAdapter = new RecipeAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dpToPx(getActivity(), 8), true));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void setVisibleHint(boolean isVisible) {
        if (isVisible) {
            imageView.setVisibility(View.VISIBLE);
            hintText.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            hintText.setVisibility(View.INVISIBLE);
        }
    }

    public void setVisibilityProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void replaceHint(int resourceImageId, String hintNewText) {
        imageView.setImageResource(resourceImageId);
        hintText.setText(hintNewText);
    }

    private void clearSuggestion() {
        if (searchView != null) {
            Timber.d("clearSuggestion: clear search view");
            searchView.setIconified(false);
        }
    }

    @Override
    public void onCursorChanged(Cursor cursor) {
        if (cursor == null)
            clearSuggestion();

        setVisibilityProgressBar(false);
        suggestionAdapter.changeCursor(cursor);
    }

    @Override
    public void onSubmit(final String toSearch) {
        if (toSearch != null && !toSearch.isEmpty()) {
            searchView.onActionViewExpanded();
            searchView.clearFocus();
            setVisibilityProgressBar(true);

            searchServices.getRecipesByFullName(CACHE, toSearch, new NetworkCallback<SerchedRecipes>() {
                @Override
                public void onResponse(SerchedRecipes body) {
                    Timber.d("onResponse: get %s item", body.getRecipes().size());

                    if (body.getRecipes().size() == 0) {
                        replaceHint(R.drawable.error, String.format("Sorry, not found %s!", toSearch));
                        setVisibleHint(true);
                        setVisibilityProgressBar(false);
                        recyclerAdapter.clear();
                        return;
                    }

                    List<Recipe> recipeList = body.getRecipes();
                    recyclerAdapter.setList(recipeList);

                    setVisibleHint(false);
                    setVisibilityProgressBar(false);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    recyclerAdapter.clear();
                    setVisibleHint(true);
                    setVisibilityProgressBar(false);
                    Timber.e("onSubmit: exception %s", throwable.getMessage());
                }
            });
        }
    }

    @Override
    public void showProgressbar() {
        setVisibilityProgressBar(true);
    }


    private class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

        private List<Recipe> list;

        public void setList(List<Recipe> categoryList) {
            if (!this.list.isEmpty())
                this.list.clear();

            this.list.addAll(categoryList);
            notifyDataSetChanged();
        }

        public void update(List<Recipe> categoryList) {
            for (Recipe each : categoryList) {
                if (!list.contains(each))
                    list.add(each);
            }

            notifyDataSetChanged();
        }

        public void clear() {
            this.list.clear();
            notifyDataSetChanged();
        }

        public RecipeAdapter() {
            this.list = new ArrayList<>();
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_recycler_row, viewGroup, false);
            return new RecipeViewHolder(view);
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
}
