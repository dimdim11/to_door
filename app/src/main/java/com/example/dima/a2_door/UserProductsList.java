package com.example.dima.a2_door;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserProductsList extends AppCompatActivity {
    public ProductsListAdapter adapter;
    private ListView lvProducts;
    private List<Products> mProductsList;
    DatabaseReference db;
    private String date;
    private String customer;
    private String address;
    private String phone;
    private String deliver;
    private String prod;
    private String gov;
    private String more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_products_list);
        lvProducts = (ListView)findViewById(R.id.listview_product);
        mProductsList = new ArrayList<>();
        this.setTitle("user1");
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseDatabase.getInstance().getReference("workers").child("products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date = (String)dataSnapshot.child("4").child("date").getValue();
                customer = (String)dataSnapshot.child("4").child("customer").getValue();
                address = (String)dataSnapshot.child("4").child("address").getValue();
                phone = (String)dataSnapshot.child("4").child("phone").getValue();
                deliver = (String)dataSnapshot.child("4").child("deliver").getValue();
                prod = (String)dataSnapshot.child("4").child("prod").getValue();
                gov = (String)dataSnapshot.child("4").child("gov").getValue();
                more = (String)dataSnapshot.child("4").child("more").getValue();
                mProductsList.clear();
                for(DataSnapshot pSnapshot : dataSnapshot.getChildren()) {
                    Products p = pSnapshot.getValue(Products.class);
                    mProductsList.add(p);

                }
                adapter = new ProductsListAdapter(UserProductsList.this, mProductsList);
                lvProducts.setAdapter(adapter);
                lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent inte = new Intent(view.getContext(), TheProduct.class);
                        inte.putExtra("date", date);
                        inte.putExtra("customer", customer);
                        inte.putExtra("address", address);
                        inte.putExtra("phone", phone);
                        inte.putExtra("deliver", deliver);
                        inte.putExtra("prod", prod);
                        inte.putExtra("gov", gov);
                        inte.putExtra("more", more);
                        startActivityForResult(inte, 0);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
