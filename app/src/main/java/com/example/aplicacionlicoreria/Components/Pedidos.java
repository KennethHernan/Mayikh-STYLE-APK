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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.aplicacionlicoreria.Adapters.PedidoAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.ComponentsVacios.PedidosVacioActivo;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pedidos extends AppCompatActivity {
    @BindView(R.id.list_pedidoActivo)
    RecyclerView OrderRecyclerView;
    PedidoAdapter orderdapter;
    GridLayoutManager OrderLayoutManager;

    private Button CantidadOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        //METODO ONCLICK
        LinearLayout btnComplete = findViewById(R.id.btn_Complete);
        btnComplete.setOnClickListener(v -> PedidosComplete());

        CantidadOrder = findViewById(R.id.btn_CantidadOrder);

        ButterKnife.bind(this);
        Recycler();

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        ImageButton animar = findViewById(R.id.button_Pedido);
        animar.setAnimation(animationNavegacion);
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OrderLayoutManager = new GridLayoutManager(this, 2);
        } else {
            OrderLayoutManager = new GridLayoutManager(this,1);
        }

        OrderRecyclerView.setLayoutManager(OrderLayoutManager);
        orderdapter = new PedidoAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser", 0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        // MOSTRAR CANTIDAD PEDIDO
        List<Order> order = DataBase.listOrder(IdUser,1);

        if (order.size() > 0){
            //VISIBLE
            CantidadOrder.setVisibility(View.VISIBLE);

            Button btnCantidadOrd = findViewById(R.id.btn_CantidadOrder);
            btnCantidadOrd.setText(""+order.size());
        }else {
            //OCULTAR
            CantidadOrder.setVisibility(View.GONE);
        }

        if (order.size() > 0) {
            orderdapter = new PedidoAdapter(order);
            DataBase.close();
        } else {
            ArrayList<Order> orderEmpty = new ArrayList<>();
            orderdapter.addItems(orderEmpty);
            DataBase.close();

            Intent intentCar = new Intent(Pedidos.this, PedidosVacioActivo.class);
            startActivity(intentCar);
        }
        OrderRecyclerView.setAdapter(orderdapter);
        DataBase.close();
    }

    public void Home(View view) {
        Intent intentH = new Intent(this, Home.class);
        startActivity(intentH);
    }
    public void Pedidos(View view) {
        Intent intentC = new Intent(this, Pedidos.class);
        startActivity(intentC);
    }
    public void PedidosComplete() {
        Intent intentP = new Intent(this, PedidosComplete.class);
        startActivity(intentP);
    }
    public void Profile(View view) {
        Intent intentP = new Intent(this, Profile.class);
        startActivity(intentP);
    }
}