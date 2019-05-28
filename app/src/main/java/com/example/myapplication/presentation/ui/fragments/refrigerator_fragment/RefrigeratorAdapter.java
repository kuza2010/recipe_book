package com.example.myapplication.presentation.ui.fragments.refrigerator_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.framework.retrofit.model.product.Product;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class RefrigeratorAdapter extends RecyclerView.Adapter<RefrigeratorAdapter.RefrigeratorViewHolder>
        implements SwipeTouchHelper.ItemTouchHelperAdapter{

    private List<Product> products;
    private RefrigeratorListener listener;

    public RefrigeratorAdapter(RefrigeratorListener listener) {
        this.products = new ArrayList<>();
        this.listener = listener;
    }

    public void updateProduct(List<Product> newList){
       products.clear();
       products.addAll(newList);

       notifyDataSetChanged();
    }

    public void updateAmountProduct(Product product, int totalAmount){
        if(products.contains(product)) {
            for (Product prod : products) {
                if (prod.getIngredientName().equals(product.getIngredientName()))
                    prod.setIngredientCount(totalAmount);
            }

            notifyItemChanged(products.indexOf(product));
            return;
        }
        Timber.e("updateAmountProduct: adapter not contains %s",product.getIngredientName());
    }

    public void removeProduct(Product product) {
        if (products.contains(product)) {
            int index = products.indexOf(product);
            products.remove(product);
            notifyItemRemoved(index);
            return;
        }
        Timber.e("removeProduct: adapter not contains %s", product.getIngredientName());
    }

    public int getIndex(Product product) {
        if (products.contains(product))
            return products.indexOf(product);
        return 0;
    }

    @NonNull
    @Override
    public RefrigeratorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_product_recycler_row, viewGroup, false);
        return new RefrigeratorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefrigeratorViewHolder refrigeratorViewHolder, int i) {
        refrigeratorViewHolder.units.setText(products.get(i).getIngredientCount()+ " " + products.get(i).getUnits());
        refrigeratorViewHolder.name.setText(products.get(i).getIngredientName());
    }

    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();

        return 0;
    }

    @Override
    public void onItemAttemptToDelete(int position) {
        listener.onAttemptToDelete(products.get(position));
    }

    public class RefrigeratorViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView name;
        AppCompatTextView units;

        public RefrigeratorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            units = itemView.findViewById(R.id.product_unit);
        }
    }


    public interface RefrigeratorListener{
        void onAttemptToDelete(Product product);
    }
}
