package com.example.mayikhstyle.Components;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.Adapters.ProductAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryProduct extends AppCompatActivity {

    private ImageButton btnAtras;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasCatPro);
        btnAtras.setOnClickListener(v -> {
            Atras();
            onBackPressed();
        });

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
        int idCategory = intent.getIntExtra("idCategory", 0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);
        //List<Product> product = DataBase.listCategoryProduct(idCategory);
/*
        if (product.size() > 0) {
            productAdapter = new ProductAdapter(product);
            DataBase.close();
        } else {
            ArrayList<Product> productEmpty = new ArrayList<>();
            productAdapter.addItems(productEmpty);
            DataBase.close();
        }
        ProductRecyclerView.setAdapter(productAdapter);*/

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery ("" +
                "SELECT category.nameC,category.urlC " +
                "FROM category " +
                "INNER JOIN product " +
                "ON category.IdCategory = product.IdCategory " +
                "WHERE category.idCategory = "+ idCategory, null);

        // Recorrer el cursor para obtener los datos del SELECT
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            String nameC = cursor.getString(cursor.getColumnIndex("nameC"));
            @SuppressLint("Range")
            String urlC = cursor.getString(cursor.getColumnIndex("urlC"));

            TextView NameC = findViewById(R.id.title_CategoryProduct);
            NameC.setText(nameC);

            ImageView imageView = findViewById(R.id.imageViewCateP);
            if (urlC != null){
                Glide.with(this)
                        .load(urlC)
                        .centerCrop()
                        .into(imageView);
            }
        }
        DataBase.close();
        dataBase.close();
        cursor.close();
    }
    public void Atras(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}