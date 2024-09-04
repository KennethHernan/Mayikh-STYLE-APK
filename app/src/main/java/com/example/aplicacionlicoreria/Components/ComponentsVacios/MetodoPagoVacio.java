package com.example.aplicacionlicoreria.Components.ComponentsVacios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aplicacionlicoreria.Components.Profile;
import com.example.aplicacionlicoreria.Components.RegisterMetodoPago;
import com.example.aplicacionlicoreria.R;

public class MetodoPagoVacio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pago_vacio);

    }
    public void Atras(View view){
        Intent intentP = new Intent(this, Profile.class);
        startActivity(intentP);
    }
    public void NewTarget(View view){
        Intent intentT = new Intent(this, RegisterMetodoPago.class);
        startActivity(intentT);
    }
}