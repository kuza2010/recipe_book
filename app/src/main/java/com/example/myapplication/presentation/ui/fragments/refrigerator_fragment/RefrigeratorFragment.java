package com.example.myapplication.presentation.ui.fragments.refrigerator_fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.product.Product;
import com.example.myapplication.framework.retrofit.model.product.Products;
import com.example.myapplication.framework.retrofit.model.product.RemoveIngredient;
import com.example.myapplication.framework.retrofit.model.product.UpdateIngredient;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.fridge.FridgeServices;
import com.example.myapplication.presentation.ui.main.MainActivity;
import com.example.myapplication.presentation.ui.product.AddProductActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.NO_CACHE;
import static com.example.myapplication.RecepiesConstant.USER_ID;

public class RefrigeratorFragment extends Fragment implements RefrigeratorAdapter.RefrigeratorListener {
    @BindView(R.id.recycler_view_my_products)
    RecyclerView recyclerView;
    @BindView(R.id.add_app_compat_btn)
    AppCompatButton addBtn;

    @Inject
    FridgeServices services;

    private RefrigeratorAdapter adapter;
    private SwipeTouchHelper callback;

    public RefrigeratorFragment() {
        BaseApp.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fridge, container, false);
        ButterKnife.bind(this, view);

        // configure recycler view
        adapter = new RefrigeratorAdapter(this);

        callback = new SwipeTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetch();
    }

    private void fetch() {
        Timber.d("Start update user product");

        services.getMyProducts(NO_CACHE, USER_ID, new NetworkCallback<Products>() {
            @Override
            public void onResponse(Products body) {
                Timber.d("onResponse: get %s product", body.getIngredients().size());
                ((MainActivity) getActivity()).popupToast(String.format("Download %s products", body.getIngredients().size()), 3);
                adapter.updateProduct(body.getIngredients());
            }

            @Override
            public void onFailure(Throwable throwable) {
                ((MainActivity) getActivity()).popupToast("Не удалось загрузить продукты клиента!", 3);
                Timber.e("onFailure: get myProduct failed - %s", throwable.getMessage());
            }
        });
    }

    @OnClick(R.id.add_app_compat_btn)
    public void onAddClick() {
        Timber.d("on add click");
        startActivity(new Intent(getActivity(), AddProductActivity.class));
    }

    @Override
    public void onAttemptToDelete(final Product product) {
        //TODO: Added LOADING!!!!!!!!
        Timber.d("onAttempToDelete: delete product %s", product);

        View view = getLayoutInflater().inflate(R.layout.alter_diallog_view, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        AppCompatButton negative = (AppCompatButton) view.findViewById(R.id.negative);
        AppCompatButton positive = (AppCompatButton) view.findViewById(R.id.positive);
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        final AppCompatTextView count = (AppCompatTextView) view.findViewById(R.id.count_ingredient);

        positive.setText("Delete");
        seekBar.setProgress(1);
        seekBar.setMax(product.getIngredientCount());
        count.setText(String.format("%s %s", seekBar.getProgress(), product.getUnits()));
        title.setText("Delete " + product.getIngredientName());

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count.setText(progress + product.getUnits());
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
                adapter.notifyItemChanged(adapter.getIndex(product));
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("onDeleteClick: click positive button, ingredient count %s", seekBar.getProgress());

                if (seekBar.getProgress() == product.getIngredientCount()) {
                    //delete
                    Timber.d("onClick: delete all product %s", product.getIngredientName());
                    deleteIngredients(product);
                } else {
                    Timber.d("onClick: delete part (%s) product %s", seekBar.getProgress(), product.getIngredientName());
                    updaterIngredients(product, seekBar.getProgress());
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void updaterIngredients(final Product productToUpdate, int takeAway) {
        int different = productToUpdate.getIngredientCount() - takeAway;

        services.updateIngredient(NO_CACHE, USER_ID,
                productToUpdate.getIngredientId(),
                different,
                new NetworkCallback<UpdateIngredient>() {
                    @Override
                    public void onResponse(UpdateIngredient body) {
                        ((MainActivity) getActivity()).popupToast("Delete successful!", 2);
                        adapter.updateAmountProduct(productToUpdate, body.getCountIngredient());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Timber.e("onFailure: delete %s failure, message %s", productToUpdate.getIngredientName(), throwable.getMessage());
                        ((MainActivity) getActivity()).popupToast("Delete failure!", 2);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public void deleteIngredients(final Product productToUpdate) {
        services.removeIngredient(NO_CACHE,
                USER_ID,
                productToUpdate.getIngredientId(),
                new NetworkCallback<RemoveIngredient>() {
                    @Override
                    public void onResponse(RemoveIngredient body) {
                        if (body.getIngrdientId() == productToUpdate.getIngredientId()) {
                            ((MainActivity) getActivity()).popupToast("Delete successful!", 2);
                            adapter.removeProduct(productToUpdate);
                        } else {
                            Timber.e("onFailure: delete %s failure. Response id %s does not match delete id %s ",
                                    productToUpdate.getIngredientName(),
                                    productToUpdate.getIngredientId(),
                                    body.getIngrdientId());
                            Timber.e("reload user product!");
                            fetch();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Timber.e("onFailure: delete %s failure, message %s", productToUpdate.getIngredientName(), throwable.getMessage());
                        ((MainActivity) getActivity()).popupToast("Delete failure!", 2);
                    }
                });
    }
}
