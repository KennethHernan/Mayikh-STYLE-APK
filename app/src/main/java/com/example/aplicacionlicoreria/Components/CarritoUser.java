package com.example.aplicacionlicoreria.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.aplicacionlicoreria.Adapters.CarritoAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.ComponentsVacios.CarritoVacio;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarritoUser extends AppCompatActivity {

    @BindView(R.id.list_carrito)
    RecyclerView CarritoRecyclerView;
   CarritoAdapter carritoAdapter;
    GridLayoutManager CarritoLayoutManager;

    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        ButterKnife.bind(this);

        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CarritoLayoutManager = new GridLayoutManager(this, 2);
        } else {
            CarritoLayoutManager = new GridLayoutManager(this,1);
        }

        CarritoRecyclerView.setLayoutManager(CarritoLayoutManager);
        carritoAdapter = new CarritoAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {
        int TotalPrice = 0;

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        int IdUser = preferences.getInt("IdUser",0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        List<Carrito> carrito = DataBase.listCarrito(IdUser);

        if (carrito.size() > 0) {
            carritoAdapter = new CarritoAdapter(carrito);
            DataBase.close();
        } else {
            ArrayList<Carrito> carritoEmpty = new ArrayList<>();
            carritoAdapter.addItems(carritoEmpty);
            DataBase.close();
            //Si la lista está vacia se Redirecciona a "CarritoVacio"
            Intent intent = new Intent(CarritoUser.this, CarritoVacio.class);
            startActivity(intent);
        }

        CarritoRecyclerView.setAdapter(carritoAdapter);

        //MOSTAR SUBTOTAL EN BOTÓN
        for (Carrito item : carrito) {
            TotalPrice += item.getPrice();
        }
        Button miBoton = findViewById(R.id.btn_Carrito);
        miBoton.setText(getString(R.string.btn_Comprar) + " S/." + TotalPrice);

        DataBase.close();
    }

    public void ComprarAhora(View view) {
        Intent intent = new Intent(CarritoUser.this, OrdenarPedido.class);
        startActivity(intent);
    }

    public  void Atras(View view){
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }
}