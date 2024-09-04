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

import com.example.aplicacionlicoreria.Adapters.MetodoPagoAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.ComponentsVacios.MetodoPagoVacio;
import com.example.aplicacionlicoreria.Models.Payment;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MetodoPago extends AppCompatActivity {

    @BindView(R.id.list_metodopay)
    RecyclerView PaymentRecyclerView;
    MetodoPagoAdapter paymentAdapter;
    GridLayoutManager PaymentLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pago);

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            PaymentLayoutManager = new GridLayoutManager(this, 2);
        } else {
            PaymentLayoutManager = new GridLayoutManager(this,1);
        }

        PaymentRecyclerView.setLayoutManager(PaymentLayoutManager);
        paymentAdapter = new MetodoPagoAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        Cursor filaPayment = dataBase.rawQuery(
                "SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser}, null);
        if (filaPayment.moveToFirst()) {
            @SuppressLint("Range")
            int idUser = Integer.parseInt(filaPayment.getString(filaPayment.getColumnIndex("idUser")));
            List<Payment> payment = DataBase.listPayment(idUser);

            if (payment.size() > 0) {
                paymentAdapter = new MetodoPagoAdapter(payment);
                DataBase.close();
            } else {
                ArrayList<Payment> paymentEmpty = new ArrayList<>();
                paymentAdapter.addItems(paymentEmpty);
                DataBase.close();

                //Si la lista está vacia se Redirecciona a "MetodoPagoVacio"
                Intent intent = new Intent(this, MetodoPagoVacio.class);
                startActivity(intent);
            }

            PaymentRecyclerView.setAdapter(paymentAdapter);
            DataBase.close();
        } else {
            filaPayment.close();
        }
    }

    public void Atras(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void NewTarget(View view){
        Intent intent = new Intent(this, RegisterMetodoPago.class);
        startActivity(intent);
    }
}