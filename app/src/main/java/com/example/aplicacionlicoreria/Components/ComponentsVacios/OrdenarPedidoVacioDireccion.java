package com.example.aplicacionlicoreria.Components.ComponentsVacios;

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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aplicacionlicoreria.Adapters.OrdenarPedidoAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.CarritoUser;
import com.example.aplicacionlicoreria.Components.DireccionOrder;
import com.example.aplicacionlicoreria.Components.RegistrarDireccion;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenarPedidoVacioDireccion extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_carrito)
    RecyclerView OrdenarPedidoRecyclerView;
    OrdenarPedidoAdapter ordenarPedidoAdapter;

    GridLayoutManager OrdenarPedidoLayoutManager;

    private Spinner spinnerTargeta;

    private TextView Subtotal, Delivery, Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_pedido_vacio_direccion);
        ButterKnife.bind(this);

        //METODO ONCLICK
        LinearLayout btnAregarDireccion = findViewById(R.id.btnAgregarDireccion);
        btnAregarDireccion.setOnClickListener(v -> AgregarDireccion());

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser",0);

        spinnerTargeta = findViewById(R.id.spinnerTargeta);

        List<String> targetaList = new ArrayList<>();

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        //AGREGAR LOS DATOS AL ARRAY direccionList
        Cursor cursorTargeta = dataBase.rawQuery
                ("SELECT cardNumber FROM payment WHERE idUser = "+IdUser,null);
        if (cursorTargeta.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String Targeta = cursorTargeta.getString(cursorTargeta.getColumnIndex("cardNumber"));
                targetaList.add(Targeta);
            } while (cursorTargeta.moveToNext());
        }
        cursorTargeta.close();
        dataBase.close();

        //PREPARAMOS LOS DATOS EN EL SPINNER
        String[] targetaSpinner = targetaList.toArray(new String[0]);

        //MOSTRAMOS LOS DATOS EN EL XML
        ArrayAdapter<String> adapterTargeta = new ArrayAdapter<>(this, R.layout.spinner, targetaSpinner);
        adapterTargeta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTargeta.setAdapter(adapterTargeta);

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
        int IdUser = preferences.getInt("IdUser",0);

        int TotalPrice = 0;
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        List<Carrito> carrito = DataBase.listCarrito(IdUser);

        if (carrito.size() > 0) {
            ordenarPedidoAdapter = new OrdenarPedidoAdapter(carrito);
            DataBase.close();
        } else {
            ArrayList<Carrito> carritoEmpty = new ArrayList<>();
            ordenarPedidoAdapter.addItems(carritoEmpty);

            //LISTA VACIA, REDIRECCION A HOME
            Intent intentC = new Intent(this, CarritoUser.class);
            startActivity(intentC);

            DataBase.close();
        }

        OrdenarPedidoRecyclerView.setAdapter(ordenarPedidoAdapter);

        //MOSTAR SUBTOTAL Y TOTAL
        for (Carrito item : carrito) {
            TotalPrice += item.getPrice();
        }

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
        Intent intentC = new Intent(this, DireccionOrder.class);
        startActivity(intentC);
    }
}