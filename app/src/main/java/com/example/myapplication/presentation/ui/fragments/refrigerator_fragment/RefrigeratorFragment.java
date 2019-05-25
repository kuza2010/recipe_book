package com.example.myapplication.presentation.ui.fragments.refrigerator_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BaseApp;
import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.product.Products;
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

public class RefrigeratorFragment extends Fragment {
    @BindView(R.id.recycler_view_my_products)
    RecyclerView recyclerView;
    @BindView(R.id.add_app_compat_btn)
    AppCompatButton addBtn;

    @Inject
    FridgeServices services;

    private RefrigeratorAdapter adapter;

    public RefrigeratorFragment() {
        BaseApp.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fridge,container,false);
        ButterKnife.bind(this,view);

        // configure recycler view
        adapter = new RefrigeratorAdapter();

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

    private void fetch(){
        Timber.d("Start update user product");

        services.getMyProducts(NO_CACHE, USER_ID, new NetworkCallback<Products>() {
            @Override
            public void onResponse(Products body) {
                Timber.d("onResponse: get %s product",body.getIngredients().size());
                ((MainActivity)getActivity()).popupToast(String.format("Download %s products",body.getIngredients().size()),3);
                adapter.updateProduct(body.getIngredients());
            }

            @Override
            public void onFailure(Throwable throwable) {
                ((MainActivity)getActivity()).popupToast("Не удалось загрузить продукты клиента!",3);
                Timber.e("onFailure: get myProduct failed - %s",throwable.getMessage());
            }
        });
    }

    @OnClick(R.id.add_app_compat_btn)
    public void onAddClick(){
        Timber.d("on add click");
        startActivity(new Intent(getActivity(), AddProductActivity.class));
    }
}
