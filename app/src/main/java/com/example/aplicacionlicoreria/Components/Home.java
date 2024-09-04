package com.example.aplicacionlicoreria.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionlicoreria.Adapters.CategoryAdapter;
import com.example.aplicacionlicoreria.Adapters.CategoryHorizontalAdapter;
import com.example.aplicacionlicoreria.Adapters.CategoryImgHorizontalAdapter;
import com.example.aplicacionlicoreria.Adapters.OfertaAdapter;
import com.example.aplicacionlicoreria.Adapters.OfertaHorizontalAdapter;
import com.example.aplicacionlicoreria.Adapters.ProductAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.Category;
import com.example.aplicacionlicoreria.Models.Offers;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.jvm.internal.PropertyReference0Impl;


public class Home extends AppCompatActivity {
    @BindView(R.id.list_content_category)
    RecyclerView CategoryRecyclerImgView;
    @BindView(R.id.list_content_category_button)
    RecyclerView CategoryRecyclerView;
    CategoryHorizontalAdapter categoryAdapter;
    GridLayoutManager CategoryLayoutManager;
    @BindView(R.id.list_oferta_horizontal)
    RecyclerView OffertRecyclerView;
    OfertaHorizontalAdapter offertAdapter;
    GridLayoutManager OffertLayoutManager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;

    private Button CantidadCarrito,CantidadOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        // Mostrar el dato en la interface
        TextView textView = findViewById(R.id.label_nameUP);
        textView.setText(NameUser);

        CantidadCarrito = findViewById(R.id.btn_CantidadCarrito);
        CantidadOrder = findViewById(R.id.btn_CantidadOrder);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        ImageButton animar = findViewById(R.id.button_Home);
        animar.setAnimation(animationNavegacion);

        ButterKnife.bind(this);
        Recycler();
    }

    private void Recycler() {

        /*=================== Product ======================*/
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ProductLayoutManager = new GridLayoutManager(this, 2);
        } else {
            ProductLayoutManager = new GridLayoutManager(this, 1);
        }
        ProductRecyclerView.setLayoutManager(ProductLayoutManager);
        productAdapter = new ProductAdapter(new ArrayList<>());

        /*=================== Oferta ======================*/
        int orientation2 = getResources().getConfiguration().orientation;
        if (orientation2 == Configuration.ORIENTATION_LANDSCAPE) {
            OffertLayoutManager = new GridLayoutManager(this, 2);

        } else {
            OffertLayoutManager = new GridLayoutManager(this,1);
        }

        OffertRecyclerView.setLayoutManager(OffertLayoutManager);
        offertAdapter = new OfertaHorizontalAdapter(new ArrayList<>());

        /*=================== Categoy ======================*/
        int orientation3 = getResources().getConfiguration().orientation;
        if (orientation3 == Configuration.ORIENTATION_LANDSCAPE) {
            CategoryLayoutManager = new GridLayoutManager(this, 2);
        } else {
            CategoryLayoutManager = new GridLayoutManager(this,1);
        }

        CategoryRecyclerView.setLayoutManager(CategoryLayoutManager);
        categoryAdapter = new CategoryHorizontalAdapter(new ArrayList<>());
        Content();
    }

    private void Content() {
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int idUser = preferences.getInt("IdUser", 0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);
        List<Product> product = DataBase.listProductRecomendado();
        List<Offers> offers = DataBase.listOffers();
        List<Category> category = DataBase.listCategory();

        // MOSTRAR CANTIDAD CARRITO Y PEDIDO
        List<Carrito> carrito = DataBase.listCarrito(idUser);
        List<Order> order = DataBase.listOrder(idUser,1);

        if (carrito.size() > 0){
            //VISIBLE
            CantidadCarrito.setVisibility(View.VISIBLE);

            Button btnCantidadCar = findViewById(R.id.btn_CantidadCarrito);
            btnCantidadCar.setText(""+carrito.size());
        }else {
            //OCULTAR
            CantidadCarrito.setVisibility(View.GONE);
        }
        if (order.size() > 0){
            //VISIBLE
            CantidadOrder.setVisibility(View.VISIBLE);

            Button btnCantidadOrd = findViewById(R.id.btn_CantidadOrder);
            btnCantidadOrd.setText(""+order.size());
        }else {
            //OCULTAR
            CantidadOrder.setVisibility(View.GONE);
        }

        /*=================== Product ======================*/
        if (product.size() > 0) {
            productAdapter = new ProductAdapter(product);
            DataBase.close();

        } else {
            ArrayList<Product> productEmpty = new ArrayList<>();
            productAdapter.addItems(productEmpty);
            DataBase.close();
        }

        /*=================== Oferta ======================*/
        if (offers.size() > 0) {
            offertAdapter = new OfertaHorizontalAdapter(offers);
            DataBase.close();
        } else {
            ArrayList<Offers> offersEmpty = new ArrayList<>();
            offertAdapter.addItems(offersEmpty);
            DataBase.close();
        }

        /*=================== Category ======================*/
        if (category.size() > 0) {
            categoryAdapter = new CategoryHorizontalAdapter(category);
            DataBase.close();
        } else {
            ArrayList<Category> categoryEmpty = new ArrayList<>();
            categoryAdapter.addItems(categoryEmpty);
            DataBase.close();
        }

        /*=================== Oferta HORIZONTAL ======================*/
        RecyclerView recyclerViewOfetas = findViewById(R.id.list_oferta_horizontal);
        LinearLayoutManager layoutManagerOffert = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOfetas.setLayoutManager(layoutManagerOffert);

        OfertaHorizontalAdapter ofertaAdapter = new OfertaHorizontalAdapter(offers);
        recyclerViewOfetas.setAdapter(ofertaAdapter);

        /*=================== Category Button HORIZONTAL ======================*/
        RecyclerView recyclerViewCategorias = findViewById(R.id.list_content_category_button);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategorias.setLayoutManager(layoutManagerCategory);

        CategoryHorizontalAdapter categoryAdapter = new CategoryHorizontalAdapter(category);
        recyclerViewCategorias.setAdapter(categoryAdapter);

        /*=================== Category Img HORIZONTAL ======================*/
        RecyclerView recyclerViewCategoriasImg = findViewById(R.id.list_content_category);
        LinearLayoutManager layoutManagerCategoryImg = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoriasImg.setLayoutManager(layoutManagerCategoryImg);

        CategoryImgHorizontalAdapter categoryImgAdapter = new CategoryImgHorizontalAdapter(category);
        recyclerViewCategoriasImg.setAdapter(categoryImgAdapter);

        CategoryRecyclerImgView.setAdapter(categoryImgAdapter);
        CategoryRecyclerView.setAdapter(categoryAdapter);
        OffertRecyclerView.setAdapter(ofertaAdapter);
        ProductRecyclerView.setAdapter(productAdapter);
        DataBase.close();

    }
    public void VerProducto(View view) {
        Intent intent = new Intent(Home.this, HomeProduct.class);
        startActivity(intent);
    }
    public void VerOferta(View view) {
        Intent intent = new Intent(this, Oferta.class);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void Pedidos(View view) {
        Intent intent = new Intent(this, Pedidos.class);
        startActivity(intent);
    }
    public void Carrito(View view) {
        Intent intent = new Intent(this, CarritoUser.class);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}