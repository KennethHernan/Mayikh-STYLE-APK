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
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.aplicacionlicoreria.Adapters.PedidoCompleteAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.ComponentsVacios.PedidoVacioComplete;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PedidosComplete extends AppCompatActivity {
    @BindView(R.id.list_pedidoComplete)
    RecyclerView OrderRecyclerView;
    PedidoCompleteAdapter orderdapter;
    GridLayoutManager OrderLayoutManager;

    private Button CantidadOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_complete);

        //METODO ONCLICK
        LinearLayout btnActivo = findViewById(R.id.btn_Activos);
        btnActivo.setOnClickListener(v -> Pedidos());

        CantidadOrder = (Button)findViewById(R.id.btn_CantidadOrder);

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OrderLayoutManager = new GridLayoutManager(this, 2);
        } else {
            OrderLayoutManager = new GridLayoutManager(this,1);
        }

        OrderRecyclerView.setLayoutManager(OrderLayoutManager);
        orderdapter = new PedidoCompleteAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser", 0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

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
        Cursor cursor = dataBase.rawQuery(
                "SELECT * FROM usuario WHERE idUser = "+IdUser, null);
        if (cursor.moveToFirst()) {

            int IdState = 2;
            @SuppressLint("Range")
            int idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            @SuppressLint("Range")
            String NameU = cursor.getString(cursor.getColumnIndex("nameU"));
            List<Order> orders = DataBase.listOrder(idUser,IdState);

            if (orders.size() > 0) {
                orderdapter = new PedidoCompleteAdapter(orders);
                DataBase.close();
            } else {
                ArrayList<Order> orderEmpty = new ArrayList<>();
                orderdapter.addItems(orderEmpty);
                DataBase.close();

                //Si la lista está vacia se Redirecciona a "PedidoVacioComplete"
                Intent intentCar = new Intent(PedidosComplete.this, PedidoVacioComplete.class);
                intentCar.putExtra("NameUser", NameU);
                startActivity(intentCar);
            }
        }

        OrderRecyclerView.setAdapter(orderdapter);
        DataBase.close();
        cursor.close();
    }

    public void Home(View view) {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentH = new Intent(PedidosComplete.this, Home.class);
        intentH.putExtra("NameUser", NameUser);
        startActivity(intentH);
    }
    public void Pedidos() {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentC = new Intent(PedidosComplete.this, Pedidos.class);
        intentC.putExtra("NameUser", NameUser);
        startActivity(intentC);
    }
    public void Profile(View view) {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentP = new Intent(PedidosComplete.this, Profile.class);
        intentP.putExtra("NameUser", NameUser);
        startActivity(intentP);
    }
}