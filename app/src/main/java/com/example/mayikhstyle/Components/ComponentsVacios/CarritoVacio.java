package com.example.mayikhstyle.Components.ComponentsVacios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayikhstyle.Components.DetalleP;
import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.R;

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
    public void Atras(View view){
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String IdUser = preferences.getString("IdUser", "");

        //REDIRECCIÓN A HOME
        Intent intentH = new Intent(this, Home.class);
        intentH.putExtra("IdUser", IdUser);
        startActivity(intentH);

    }
}