package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.R;

public class CerrarSesion extends AppCompatActivity {

    private Button btnCancelar;
    private LinearLayout LayoutCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_sesion);

        //Metodo para retroceder un activity
        btnCancelar = findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(v -> onBackPressed());

        //Metodo para retroceder un activity
        LayoutCancelar = findViewById(R.id.layoutCancelar);
        LayoutCancelar.setOnClickListener(v -> onBackPressed());

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        LinearLayout animar = findViewById(R.id.linearLayout10);
        animar.setAnimation(animationNavegacion);

        Context();
    }

    public void Context() {
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser", 0);

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
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
            phoneU.setText("(+ 51) "+String.valueOf(PhoneU));

            TextView nameU = findViewById(R.id.label_nameUP);
            nameU.setText(NameU);

        }
        cursor.close();
        DataBase.close();
    }

    public void CerraSesion(View view) {
        Intent intent = new Intent(this, HomeMain.class);
        startActivity(intent);
    }
}