package com.example.mayikhstyle.Components.Login;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.Administrador.AdminHome;
import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.R;

public class Login extends AppCompatActivity {

    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.input_phone);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        LinearLayout animar = findViewById(R.id.linearLayout2);
        animar.setAnimation(animationNavegacion);
    }

    public void IniciaSesion(View view) {
        String phone = etPhone.getText().toString();

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        if(phone.length() != 0) {
            int phoneI = Integer.parseInt(phone);
            if(phoneI == 2829){
                Intent intent = new Intent(Login.this, AdminHome.class);
                startActivity(intent);
            }else {
                //CONSULTAR DATOS A LA BD
                if(!phone.isEmpty()){
                    Cursor fila = DataBase.rawQuery
                            ("SELECT * FROM usuario WHERE phone = ?", new String[] {phone});
                    //REVISAR SI LA CUNSULTA TIENE VALORES
                    if (fila.moveToFirst()){

                        @SuppressLint("Range")
                        String NameUser = fila.getString(fila.getColumnIndex("nameU"));
                        @SuppressLint("Range")
                        int idUser = Integer.parseInt(fila.getString(fila.getColumnIndex("idUser")));

                        //GUARDAR NOMBRE EN PREFERENCIAS
                        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("NameUser", NameUser);
                        editor.putInt("IdUser", idUser);
                        editor.apply();

                        //REDIRECCIÓN A HOME
                        Intent intent = new Intent(this, Home.class);
                        startActivity(intent);
                        DataBase.close();
                    }else{
                        DataBase.close();
                        //NOTIFICACION
                        Toast.makeText(this, "¡Número no registrado!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }else {
            Toast.makeText(this, "Ingresa un teléfono",Toast.LENGTH_LONG).show();
        }

    }
    //Metodo (Botón Atras)
    public void Anterior(View view) {
        //REDIRECCIÓN A HOME
        Intent anterior = new Intent (this, HomeMain.class);
        startActivity(anterior);
    }
    //Metodo (Botón Registrar)
    public void Registrar(View view) {
        //REDIRECCIÓN A REGISTER
        Intent anterior = new Intent (this, Register.class);
        startActivity(anterior);
    }
}