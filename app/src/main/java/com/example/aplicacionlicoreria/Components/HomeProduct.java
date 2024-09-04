package com.example.aplicacionlicoreria.Components;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionlicoreria.Adapters.ProductAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeProduct extends AppCompatActivity {

    private ImageButton btnAtras;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_product);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasProduct);
        btnAtras.setOnClickListener(v -> onBackPressed());

        ButterKnife.bind(this);
        Recycler();
    }

    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ProductLayoutManager = new GridLayoutManager(this, 2);
        } else {
            ProductLayoutManager = new GridLayoutManager(this, 1);
        }
        ProductRecyclerView.setLayoutManager(ProductLayoutManager);
        productAdapter = new ProductAdapter(new ArrayList<>());

        Content();
    }

    private void Content() {

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);
        List<Product> product = DataBase.listProduct();

        if (product.size() > 0) {
            productAdapter = new ProductAdapter(product);
            DataBase.close();
        } else {
            ArrayList<Product> productEmpty = new ArrayList<>();
            productAdapter.addItems(productEmpty);
            DataBase.close();
        }

        ProductRecyclerView.setAdapter(productAdapter);
        DataBase.close();
    }

}