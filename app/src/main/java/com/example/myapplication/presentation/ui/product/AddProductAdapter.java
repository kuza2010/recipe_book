package com.example.myapplication.presentation.ui.product;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AddProductAdapter extends BaseAdapter {

    private OnAddClickListener listener;
    private LayoutInflater inflater;
    private List<String> item;


    public AddProductAdapter(Context context,OnAddClickListener listener) {
        item = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
    }

    public void setItem(List<String>itemsToSet) {
        if (item != null) {
            Timber.d("setItem: clear items");
            item.clear();

            Timber.d("setItem: add %s items",itemsToSet.size());
            item.addAll(itemsToSet);
            notifyDataSetChanged();
            return;
        }
        Timber.e("setItem: item is null!");
    }

    public void clearItem(){
        if(item!=null){
            item.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if(item!=null)
            return item.size();

        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(item!=null)
            return item.get(position);

        Timber.e("getItem: adapters item is null!");
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.add_product_list_view_item, parent, false);
        }

        final String name = (String)getItem(position);
        ((AppCompatTextView) view.findViewById(R.id.product_name_app_compat_text_view)).setText(name);

        ((ImageButton) view.findViewById(R.id.add_product_image_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("onClick button");
                listener.onClick(name);
            }
        });

        return view;
    }



    public interface OnAddClickListener{
        void onClick(String ingredientName);
    }
}
