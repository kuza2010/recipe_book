package com.example.myapplication.presentation.ui.product;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.search.SearchedIngredientName;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.fridge.FridgeServices;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.DELAY_CHARACTER_ENTERED;
import static com.example.myapplication.RecepiesConstant.LIMIT_POPUP_SUGGEST_ADD_PRODUCT;
import static com.example.myapplication.RecepiesConstant.SUGGEST_LENGTH_MIN;

public class AddProductActivity extends BaseToolbarActivity {
    public static final String TITLE = "Add product";

    @BindView(R.id.hint_layout)
    RelativeLayout hintLayout;
    @BindView(R.id.image_view_smile)
    ImageView imageView;
    @BindView(R.id.text_view_hint)
    TextView textView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.search_app_compat_edit_text)
    AppCompatEditText editText;
    @BindView(R.id.ingredient_list_view)
    ListView ingredientList;

    @Inject
    FridgeServices services;
    @Inject
    SearchServices searchServices;

    private AddProductAdapter adapter;
    private Handler handler = new Handler();
    private SuggestionRunnable suggestionRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        doInject();

        setTitle(TITLE);

        adapter = new AddProductAdapter(this);
        ingredientList.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() < SUGGEST_LENGTH_MIN && suggestionRunnable != null)
                    handler.removeCallbacks(suggestionRunnable);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > SUGGEST_LENGTH_MIN) {
                    if (suggestionRunnable != null)
                        handler.removeCallbacks(suggestionRunnable);
                    suggestionRunnable = new SuggestionRunnable(s.toString());
                    handler.postDelayed(suggestionRunnable, DELAY_CHARACTER_ENTERED);

                    return;
                } else if (s.length() == 0)
                    editText.setHint(R.string.add_product_hint);

                adapter.clearItem();
            }
        });
    }

    private void doInject() {
        BaseApp.getComponent().inject(this);
        ButterKnife.bind(this);
    }

    private void setProgressBarVisible(int id){
        progressBar.setVisibility(id);
    }

    private void setHintLayoutVisible(int id) {
        hintLayout.setVisibility(id);
    }

    private void replaceAndShowHintLayout(int imageId,String hintTitle) {
        imageView.setImageResource(imageId);
        textView.setText(hintTitle);
        setHintLayoutVisible(View.VISIBLE);
    }


    class SuggestionRunnable implements Runnable {

        private String query;

        SuggestionRunnable(String query) {
            this.query = query;
        }

        @Override
        public void run() {
            setProgressBarVisible(View.VISIBLE);
            searchServices.cancelOrSkip();
            searchServices.getIngredientNameByPart(CACHE, query, LIMIT_POPUP_SUGGEST_ADD_PRODUCT, new NetworkCallback<SearchedIngredientName>() {
                @Override
                public void onResponse(SearchedIngredientName body) {
                    Timber.d("get product, size %s", body.getIngredients().size());
                    if (!body.getIngredients().isEmpty()) {
                        adapter.setItem(body.getIngredients());
                        setHintLayoutVisible(View.INVISIBLE);
                    }
                    else{
                        adapter.clearItem();
                        replaceAndShowHintLayout(R.drawable.error,"We dont't have a "+query+" product yet");
                    }
                    setProgressBarVisible(View.GONE);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Timber.e("get product by part name is failure. Message: %s", throwable.getMessage());
                    setProgressBarVisible(View.GONE);
                }
            });
        }
    }
}
