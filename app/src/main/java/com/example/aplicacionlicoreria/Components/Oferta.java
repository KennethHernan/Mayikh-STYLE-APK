package com.example.aplicacionlicoreria.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.aplicacionlicoreria.Adapters.CarritoAdapter;
import com.example.aplicacionlicoreria.Adapters.OfertaAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.Offers;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Oferta extends AppCompatActivity {
    @BindView(R.id.list_oferta)
    RecyclerView OffertRecyclerView;
    OfertaAdapter offertAdapter;
    GridLayoutManager OffertLayoutManager;

    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.btn_Atras);
        btnAtras.setOnClickListener(v -> onBackPressed());

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OffertLayoutManager = new GridLayoutManager(this, 2);
        } else {
            OffertLayoutManager = new GridLayoutManager(this,1);
        }

        OffertRecyclerView.setLayoutManager(OffertLayoutManager);
        offertAdapter = new OfertaAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        List<Offers> offers = DataBase.listOffers();

        if (offers.size() > 0) {
            offertAdapter = new OfertaAdapter(offers);
            DataBase.close();
        } else {
            ArrayList<Offers> offersEmpty = new ArrayList<>();
            offertAdapter.addItems(offersEmpty);
            DataBase.close();
        }

        OffertRecyclerView.setAdapter(offertAdapter);
        DataBase.close();
    }
}