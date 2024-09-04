package com.example.aplicacionlicoreria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.aplicacionlicoreria.Components.Login.Login;
import com.example.aplicacionlicoreria.Components.Login.Register;

public class HomeMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        LinearLayout animar = findViewById(R.id.linearLayout2);
        animar.setAnimation(animationNavegacion);
    }
    public void IniciaSesion(View view) {
        Intent anterior = new Intent (this, Login.class);
        startActivity(anterior);
    }

    public void Registrar(View view) {
        Intent anterior = new Intent (this, Register.class);
        startActivity(anterior);
    }
}