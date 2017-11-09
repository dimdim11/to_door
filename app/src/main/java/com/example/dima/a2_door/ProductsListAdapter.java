package com.example.dima.a2_door;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductsListAdapter extends ArrayAdapter<Products> {
    private Activity mContext;
    private List<Products> productsList;

    public ProductsListAdapter(Activity mContext, List<Products> productsList) {
        super(mContext, R.layout.item_product_list, productsList);
        this.mContext = mContext;
        this.productsList = productsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater infla = mContext.getLayoutInflater();
        View v = infla.inflate(R.layout.item_product_list, null, true);
        TextView tvDate = (TextView)v.findViewById(R.id.tv_date);
        TextView tvaddress = (TextView)v.findViewById(R.id.tv_address);
        TextView tvDeliver = (TextView)v.findViewById(R.id.tv_deliver);
        Products prod = productsList.get(position);
        if (prod == null)
            return v;
        tvDate.setText(prod.getDate());
        tvaddress.setText(prod.getAddress());
        tvDeliver.setText(prod.getDeliver());
        return v;
    }
}
