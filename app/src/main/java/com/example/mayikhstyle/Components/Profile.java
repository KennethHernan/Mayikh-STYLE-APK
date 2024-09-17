package com.example.mayikhstyle.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Order;
import com.example.mayikhstyle.R;

import java.util.List;

public class Profile extends AppCompatActivity {

    private Button CantidadOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        CantidadOrder = findViewById(R.id.btn_CantidadOrder);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        ImageButton animar = findViewById(R.id.button_Profile);
        animar.setAnimation(animationNavegacion);

        Content();
    }

    private void Content() {
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser", 0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        // MOSTRAR CANTIDAD PEDIDO
        /*List<Order> order = DataBase.listOrder(IdUser,1);

        if (order.size() > 0){
            //VISIBLE
            CantidadOrder.setVisibility(View.VISIBLE);

            Button btnCantidadOrd = findViewById(R.id.btn_CantidadOrder);
            btnCantidadOrd.setText(""+order.size());
        }else {
            //OCULTAR
            CantidadOrder.setVisibility(View.GONE);
        }*/

        Cursor cursor = dataBase.rawQuery
                ("SELECT * FROM usuario WHERE idUser = "+IdUser,null);

        // Recorrer el cursor para obtener los datos del usuario
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            int PhoneU = Integer.parseInt(cursor.getString(cursor.getColumnIndex("phone")));
            @SuppressLint("Range")
            String NameU = cursor.getString(cursor.getColumnIndex("nameU"));

            // Mostrar el dato en la interface
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView phoneU = findViewById(R.id.label_Phone);
            phoneU.setText("(+51) "+PhoneU);

            TextView nameU = findViewById(R.id.label_nameUP);
            nameU.setText(NameU);
        }
        cursor.close();
        DataBase.close();

    }
    public void Oferta(View view) {
        Intent intent = new Intent(this, Oferta.class);
        startActivity(intent);
    }
    public void CerraSesion(View view) {
        Intent intent = new Intent(this, CerrarSesion.class);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void Pedidos(View view) {
        Intent intent = new Intent(this, Pedidos.class);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void EditProfile(View view) {
        Intent intent = new Intent(this, ProfileEdit.class);
        startActivity(intent);
    }
    public void Direccion(View view) {
        Intent intent = new Intent(this, Direccion.class);
        startActivity(intent);
    }
    public void Payment(View view) {
        Intent intent = new Intent(Profile.this, MetodoPago.class);
        startActivity(intent);
    }
}