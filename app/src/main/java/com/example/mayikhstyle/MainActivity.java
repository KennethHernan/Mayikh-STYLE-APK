package com.example.mayikhstyle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.WindowManager;

import com.example.mayikhstyle.Components.Administrador.AdminAddCategory;
import com.example.mayikhstyle.Components.Administrador.AdminAddOferta;
import com.example.mayikhstyle.Components.Administrador.AdminAddProduct;
import com.example.mayikhstyle.Components.Login.Register;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Pasar al siguiente activity con tiempo
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, AdminAddProduct.class);
            startActivity(intent);
            finish();
        }, 4000);
    }
}