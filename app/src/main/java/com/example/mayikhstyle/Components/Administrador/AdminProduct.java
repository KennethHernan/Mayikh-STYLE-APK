package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.Adapters.AdminAdapter.AdminProductoAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminProduct extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_admin_product)
    RecyclerView ProductRecyclerView;
    AdminProductoAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

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
        productAdapter = new AdminProductoAdapter(new ArrayList<>());

        Content();
    }

    private void Content() {

        Intent intent = getIntent();
        int idCategory = intent.getIntExtra("idCategory", 0);

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        //List<Product> product = dataBase.listCategoryProduct(idCategory);
/*
        // VER SI HAY PRODUCTOS EN CATEGORY
        Cursor cursoCategory = DataBase.rawQuery ("" +
                "SELECT * FROM category " +
                "INNER JOIN product " +
                "ON category.idCategory = product.idCategory " +
                "WHERE category.idCategory ="+ idCategory, null);

        if (cursoCategory.moveToFirst()) {

            if (product.size() > 0) {
                productAdapter = new AdminProductoAdapter(product);
            } else {
                ArrayList<Product> productEmpty = new ArrayList<>();
                productAdapter.addItems(productEmpty);
            }
            ProductRecyclerView.setAdapter(productAdapter);

            Cursor cursorDatos = DataBase.rawQuery ("" +
                    "SELECT category.nameC,category.urlC " +
                    "FROM category " +
                    "INNER JOIN product " +
                    "ON category.IdCategory = product.IdCategory " +
                    "WHERE category.idCategory = "+ idCategory, null);

            // Recorrer el cursor para obtener los datos del SELECT
            if (cursorDatos != null) {
                cursorDatos.moveToFirst();
                @SuppressLint("Range")
                String urlC = cursorDatos.getString(cursorDatos.getColumnIndex("urlC"));

                ImageView imageView = findViewById(R.id.imageViewCateP);
                if (urlC != null){
                    Glide.with(this)
                            .load(urlC)
                            .centerCrop()
                            .into(imageView);
                }
            }
            cursorDatos.close();
            DataBase.close();
            dataBase.close();
        } else {
            //CATEGORIA VACIO
            ArrayList<Product> productEmpty = new ArrayList<>();
            productAdapter.addItems(productEmpty);

            ProductRecyclerView.setAdapter(productAdapter);

            Cursor cursorDatos = DataBase.rawQuery ("" +
                    "SELECT urlC " +
                    "FROM category " +
                    "WHERE idCategory = "+ idCategory, null);

            // Recorrer el cursor para obtener los datos del SELECT
            if (cursorDatos != null) {
                cursorDatos.moveToFirst();
                @SuppressLint("Range")
                String urlC = cursorDatos.getString(cursorDatos.getColumnIndex("urlC"));

                ImageView imageView = findViewById(R.id.imageViewCateP);
                if (urlC != null){
                    Glide.with(this)
                            .load(urlC)
                            .centerCrop()
                            .into(imageView);
                }
            }
            cursorDatos.close();
            DataBase.close();
            dataBase.close();
        }*/
    }
    public  void Atras(View view){
        Intent GestionarProducto = new Intent (this, AdminCategory.class);
        startActivity(GestionarProducto);
    }
    public void AddProduct(View view) {
        Intent intent = getIntent();
        int idCategory = intent.getIntExtra("idCategory", 0);
        Intent intentAddP = new Intent(AdminProduct.this, AdminAddProduct.class);
        intentAddP.putExtra("idCategory",idCategory);
        startActivity(intentAddP);
    }
}