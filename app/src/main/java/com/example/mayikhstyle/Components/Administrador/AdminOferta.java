package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.mayikhstyle.Adapters.AdminAdapter.AdminOfertaAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminOferta extends AppCompatActivity {
    @BindView(R.id.list_oferta)
    RecyclerView OffertRecyclerView;
    AdminOfertaAdapter offertAdapter;
    GridLayoutManager OffertLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_oferta);

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
        offertAdapter = new AdminOfertaAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        //List<Offers> offers = DataBase.listOffers();
/*
        if (offers.size() > 0) {
            offertAdapter = new AdminOfertaAdapter(offers);
            DataBase.close();
        } else {
            ArrayList<Offers> offersEmpty = new ArrayList<>();
            offertAdapter.addItems(offersEmpty);
            DataBase.close();
        }*/

        OffertRecyclerView.setAdapter(offertAdapter);
        DataBase.close();
    }
    public  void Home(View view){
        Intent intent = new Intent (this, AdminHome.class);
        startActivity(intent);
    }
    public  void AgregarOferta(View view){
        Intent intent = new Intent (this, AdminAddOferta.class);
        startActivity(intent);
    }
}