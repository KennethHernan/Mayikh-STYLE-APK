package com.example.aplicacionlicoreria.Components.ComponentsVacios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacionlicoreria.Components.Home;
import com.example.aplicacionlicoreria.R;

public class CarritoVacio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_vacio);

    }

    public void Home(View view){
        Intent intentC = new Intent(this, Home.class);
        startActivity(intentC);
    }
}