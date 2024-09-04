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
import com.example.aplicacionlicoreria.Components.MetodoPagOrder;
import com.example.aplicacionlicoreria.Components.RegisterMetodoPago;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenarPedidoVacioTargeta extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_carrito)
    RecyclerView OrdenarPedidoRecyclerView;
    OrdenarPedidoAdapter ordenarPedidoAdapter;

    GridLayoutManager OrdenarPedidoLayoutManager;

    private Spinner spinnerDireccion;

    private TextView Subtotal, Delivery, Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_pedido_vacio_targeta);

        LinearLayout btnAregarTargeta = findViewById(R.id.btnAgregarTargeta);
        btnAregarTargeta.setOnClickListener(v -> AgregarTargeta());

        ButterKnife.bind(this);

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser",0);

        spinnerDireccion = findViewById(R.id.spinnerDireccion);

        List<String> direccionList = new ArrayList<>();

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        //AGREGAR LOS DATOS AL ARRAY direccionList
        Cursor cursorDireccion = dataBase.rawQuery
                ("SELECT address FROM address WHERE idUser = "+IdUser,null);
        if (cursorDireccion.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String Direccion = cursorDireccion.getString(cursorDireccion.getColumnIndex("address"));
                direccionList.add(Direccion);
            } while (cursorDireccion.moveToNext());
        }
        cursorDireccion.close();
        dataBase.close();

        //PREPARAMOS LOS DATOS EN EL SPINNER
        String[] direccionSpinner = direccionList.toArray(new String[0]);

        //MOSTRAMOS LOS DATOS EN EL XML
        ArrayAdapter<String> adapterDireccion = new ArrayAdapter<>(this, R.layout.spinner, direccionSpinner);
        adapterDireccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDireccion.setAdapter(adapterDireccion);
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
        Intent intent = new Intent(this, CarritoUser.class);
        startActivity(intent);
    }
    public void AgregarTargeta(){
        Intent intentC = new Intent(OrdenarPedidoVacioTargeta.this, MetodoPagOrder.class);
        startActivity(intentC);
    }
}