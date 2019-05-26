package com.example.myapplication.presentation.ui.product;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.search.Ingredient;
import com.example.myapplication.framework.retrofit.model.search.SearchedIngredientName;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.fridge.FridgeServices;
import com.example.myapplication.framework.retrofit.services.search.SearchServices;
import com.example.myapplication.presentation.ui.BaseToolbarActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;
import static com.example.myapplication.RecepiesConstant.DELAY_CHARACTER_ENTERED;
import static com.example.myapplication.RecepiesConstant.LIMIT_POPUP_SUGGEST_ADD_PRODUCT;
import static com.example.myapplication.RecepiesConstant.NO_CACHE;
import static com.example.myapplication.RecepiesConstant.SUGGEST_LENGTH_MIN;
import static com.example.myapplication.RecepiesConstant.USER_ID;

public class AddProductActivity extends BaseToolbarActivity implements AddProductAdapter.OnAddClickListener {
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

    private InputMethodManager inputManager;
    private AddProductAdapter adapter;
    private Handler handler = new Handler();
    private SuggestionRunnable suggestionRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        doInject();

        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setTitle(TITLE);

        adapter = new AddProductAdapter(this,this);
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

    @Override
    public void onVariantClickClick(final Ingredient ingredient) {
        inputManager.hideSoftInputFromWindow(imageView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        View view = getLayoutInflater().inflate(R.layout.alter_diallog_view, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        AppCompatButton negative = (AppCompatButton) view.findViewById(R.id.negative);
        AppCompatButton positive = (AppCompatButton) view.findViewById(R.id.positive);
        final AppCompatTextView count = (AppCompatTextView) view.findViewById(R.id.count_ingredient);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();

        title.setText("Added " + ingredient.getName());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("onVariantClickClick: click positive button, ingredient count %s", seekBar.getProgress());
                services.addIngredient(NO_CACHE, USER_ID, ingredient.getIdIngredient(), LIMIT_POPUP_SUGGEST_ADD_PRODUCT, new NetworkCallback<Response>() {
                    @Override
                    public void onResponse(Response body) {
                        Timber.d("onResponse: code: %s",body.code());
                        AddProductActivity.this.popupToast("code = "+body.code(),3);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Timber.e("onFailure: add ingredient %s failure, message %s",ingredient.getName(),throwable.getMessage());
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
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
