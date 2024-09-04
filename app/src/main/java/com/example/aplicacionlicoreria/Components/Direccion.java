package com.example.aplicacionlicoreria.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.aplicacionlicoreria.Adapters.DireccionAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.ComponentsVacios.DireccionVacio;
import com.example.aplicacionlicoreria.Models.Address;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Direccion extends AppCompatActivity {

    @BindView(R.id.list_direccion)
    RecyclerView DireccionRecyclerView;
    DireccionAdapter direccionAdapter;
    GridLayoutManager DireccionLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DireccionLayoutManager = new GridLayoutManager(this, 2);
        } else {
            DireccionLayoutManager = new GridLayoutManager(this,1);
        }

        DireccionRecyclerView.setLayoutManager(DireccionLayoutManager);
        direccionAdapter = new DireccionAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        Cursor filaAddresss = dataBase.rawQuery
                ("SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser}, null);
        if (filaAddresss.moveToFirst()) {
            @SuppressLint("Range")
            int idUser = Integer.parseInt(filaAddresss.getString(filaAddresss.getColumnIndex("idUser")));

            List<Address> direccion = DataBase.listAddress(idUser);

            if (direccion.size() > 0) {
                direccionAdapter = new DireccionAdapter(direccion);
                DataBase.close();
            } else {
                ArrayList<Address> direccionEmpty = new ArrayList<>();
                direccionAdapter.addItems(direccionEmpty);
                DataBase.close();

                //Si la lista está vacia se Redirecciona a "DireccionVacio"
                Intent intent = new Intent(this, DireccionVacio.class);
                startActivity(intent);
            }

            DireccionRecyclerView.setAdapter(direccionAdapter);
            DataBase.close();
            filaAddresss.close();
        } else {
            filaAddresss.close();
        }
    }

    public void agregarNewDireccion(View view) {
        Intent intent = new Intent(this, RegistrarDireccion.class);
        startActivity(intent);
    }
    public void Atras(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

}