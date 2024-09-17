package com.example.mayikhstyle.Components.ComponentsVacios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayikhstyle.Adapters.OrdenarPedidoAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.CarritoUser;
import com.example.mayikhstyle.Components.DireccionOrder;
import com.example.mayikhstyle.Components.MetodoPagOrder;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenarPedidoVacio extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_carrito)
    RecyclerView OrdenarPedidoRecyclerView;
    OrdenarPedidoAdapter ordenarPedidoAdapter;

    GridLayoutManager OrdenarPedidoLayoutManager;

    private TextView Subtotal, Delivery, Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_pedido_vacio);

        //METODO ONCLICK
        LinearLayout btnAregarDireccion = findViewById(R.id.btnAgregarDireccion);
        btnAregarDireccion.setOnClickListener(v -> AgregarDireccion());

        LinearLayout btnAregarTargeta = findViewById(R.id.btnAgregarTargeta);
        btnAregarTargeta.setOnClickListener(v -> AgregarTargeta());

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OrdenarPedidoLayoutManager = new GridLayoutManager(this, 2);
        } else {
            OrdenarPedidoLayoutManager = new GridLayoutManager(this,1);
        }

        OrdenarPedidoRecyclerView.setLayoutManager(OrdenarPedidoLayoutManager);
        ordenarPedidoAdapter = new OrdenarPedidoAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        int IdUser = preferences.getInt("IdUser",0);

        int TotalPrice = 0;
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        /*List<Carrito> carrito = DataBase.listCarrito(IdUser);

        if (carrito.size() > 0) {
            ordenarPedidoAdapter = new OrdenarPedidoAdapter(carrito);
            DataBase.close();
        } else {
            ArrayList<Carrito> carritoEmpty = new ArrayList<>();
            ordenarPedidoAdapter.addItems(carritoEmpty);

            Intent intentC = new Intent(this, CarritoUser.class);
            startActivity(intentC);

            DataBase.close();
        }

        OrdenarPedidoRecyclerView.setAdapter(ordenarPedidoAdapter);

        //MOSTAR SUBTOTAL Y TOTAL
        for (Carrito item : carrito) {
            TotalPrice += item.getPrice();
        }*/

        int delivery = 5;
        int totalP = (TotalPrice + delivery);
        Subtotal = findViewById(R.id.textPriceTotal);
        Subtotal.setText("S/." + TotalPrice);
        Delivery = findViewById(R.id.txtDelivery);
        Delivery.setText("S/." + delivery);
        Total = findViewById(R.id.txtTotal);
        Total.setText("S/." + totalP);

        DataBase.close();
    }

    public void Carrito(View view){
        Intent intentC = new Intent(this, CarritoUser.class);
        startActivity(intentC);
    }
    public void AgregarDireccion(){
        Intent intentC = new Intent(this, MetodoPagOrder.class);
        startActivity(intentC);
    }
    public void AgregarTargeta(){
        Intent intentC = new Intent(this, DireccionOrder.class);
        startActivity(intentC);
    }
}