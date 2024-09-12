package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mayikhstyle.Adapters.ProductAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfertaProduct extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_product);

        //Metodo para retroceder un activity
        ImageButton btnAtras = findViewById(R.id.button_Atras);
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
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        int idOffers = intent.getIntExtra("IdOffers", 0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);
        //List<Product> product = DataBase.listOffersProduct(idOffers);
/*
        if (product.size() > 0) {
            productAdapter = new ProductAdapter(product);
            DataBase.close();
        } else {
            ArrayList<Product> productEmpty = new ArrayList<>();
            productAdapter.addItems(productEmpty);
            DataBase.close();
        }*/
        ProductRecyclerView.setAdapter(productAdapter);

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery ("SELECT * FROM offers WHERE offers.idOffers = "+ idOffers, null);

        // Recorrer el cursor para obtener los datos del SELECT
        if (cursor.moveToFirst()) {
            @SuppressLint("Range")
            int Discount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("discount")));
            @SuppressLint("Range")
            String Description = cursor.getString(cursor.getColumnIndex("descriptionO"));

            TextView DiscountO = findViewById(R.id.txt_Discount);
            DiscountO.setText(Discount+"%");

            TextView DescriptionO = findViewById(R.id.txt_DescriptionO);
            DescriptionO.setText(Description);
        }else {
        }
        DataBase.close();
        dataBase.close();
        cursor.close();
    }
}