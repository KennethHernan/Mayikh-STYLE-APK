package com.example.aplicacionlicoreria.Components.ComponentsVacios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.aplicacionlicoreria.Components.Home;
import com.example.aplicacionlicoreria.Components.Pedidos;
import com.example.aplicacionlicoreria.Components.Profile;
import com.example.aplicacionlicoreria.R;

public class PedidoVacioComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_vacio_complete);

        //METODO ONCLICK
        LinearLayout btnActivo = findViewById(R.id.btn_Activos);
        btnActivo.setOnClickListener(v -> PedidosActivos());
    }
    public void Home(View view) {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentH = new Intent(PedidoVacioComplete.this, Home.class);
        intentH.putExtra("NameUser", NameUser);
        startActivity(intentH);
    }
    public void Pedidos(View view) {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentC = new Intent(PedidoVacioComplete.this, PedidoVacioComplete.class);
        intentC.putExtra("NameUser", NameUser);
        startActivity(intentC);
    }
    public void PedidosActivos() {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentC = new Intent(PedidoVacioComplete.this, Pedidos.class);
        intentC.putExtra("NameUser", NameUser);
        startActivity(intentC);
    }
    public void Profile(View view) {
        //Recuperamos los datos del Login
        Intent intent = getIntent();
        String NameUser = intent.getStringExtra("NameUser");
        Intent intentP = new Intent(PedidoVacioComplete.this, Profile.class);
        intentP.putExtra("NameUser", NameUser);
        startActivity(intentP);
    }
}