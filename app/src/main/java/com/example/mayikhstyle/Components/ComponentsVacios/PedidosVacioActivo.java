package com.example.mayikhstyle.Components.ComponentsVacios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.Components.PedidosComplete;
import com.example.mayikhstyle.Components.Profile;
import com.example.mayikhstyle.R;

public class PedidosVacioActivo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_vacio_activo);

        //METODO ONCLICK
        LinearLayout btnComplete = findViewById(R.id.btn_Complete);
        btnComplete.setOnClickListener(v -> PedidosComplete());

    }
    public void Home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void Pedidos(View view) {
        Intent intent = new Intent(this, PedidosVacioActivo.class);
        startActivity(intent);
    }
    public void PedidosComplete() {
        Intent intent = new Intent(this, PedidosComplete.class);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}