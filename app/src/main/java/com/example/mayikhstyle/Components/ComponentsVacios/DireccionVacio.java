package com.example.mayikhstyle.Components.ComponentsVacios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mayikhstyle.Components.Profile;
import com.example.mayikhstyle.Components.RegistrarDireccion;
import com.example.mayikhstyle.R;

public class DireccionVacio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_vacio);

    }
    public void Atras(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void agregarNewDireccion(View view) {
        Intent intent = new Intent(this, RegistrarDireccion.class);
        startActivity(intent);
    }
}