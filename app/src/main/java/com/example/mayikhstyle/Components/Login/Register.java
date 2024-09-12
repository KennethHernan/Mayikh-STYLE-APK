package com.example.mayikhstyle.Components.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.Models.State;
import com.example.mayikhstyle.Models.User;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Register extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText etPhone, etEmail, etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPhone = findViewById(R.id.input_phone);
        etEmail = findViewById(R.id.input_email);
        etName = findViewById(R.id.input_name);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        LinearLayout animar = findViewById(R.id.linearLayout2);
        animar.setAnimation(animationNavegacion);
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //Metodo (Botón Siguiente)
    public void Registrar(View view) {
        String Sphone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();

        if(!Sphone.isEmpty()) {
            int phone = Integer.parseInt(Sphone);
            if (phone > 901000000){
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length() >= 14) {
                    if (name.length() > 8) {

                        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
                        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

                        //CONSULTAR DATOS A LA BD
                        Cursor filaP = DataBase.rawQuery(
                                "SELECT phone FROM usuario WHERE phone = " + phone, null);
                        if (filaP.moveToFirst()) {
                            DataBase.close();
                            filaP.close();
                            //NOTIFICACION
                            Toast.makeText(this, "¡Numero Existente!", Toast.LENGTH_LONG).show();
                        } else {
                            filaP.close();
                            Cursor filaE = DataBase.rawQuery(
                                    "SELECT email FROM usuario WHERE email = ?", new String[]{email});
                            if (filaE.moveToFirst()) {
                                DataBase.close();
                                filaE.close();
                                //NOTIFICACION
                                Toast.makeText(this, "¡Email existente!", Toast.LENGTH_LONG).show();
                            } else {
                                filaE.close();
                                Cursor filaN = DataBase.rawQuery(
                                        "SELECT nameU FROM usuario WHERE nameU = ?", new String[]{name});
                                if (filaN.moveToFirst()) {
                                    DataBase.close();
                                    filaN.close();
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Nombre existente!", Toast.LENGTH_LONG).show();
                                } else {
                                    //INSERTAR USUARIO A LA BASE DE DATOS
                                    String urlU ="https://res.cloudinary.com/dr2c4aovp/image/upload/v1683305563/Mayikh/usuario_eohlvb.png";
                                    int IdAddressU = 0;
                                    int IdPaymentU = 0;
                                    String IdU = UUID.randomUUID().toString();
                                    User NewUser = new User(IdU, phone, email, name, urlU, IdAddressU,IdPaymentU);
                                    DataBaseFireBase DataFire = new DataBaseFireBase();
                                    DataFire.newUser(NewUser);
                                    Toast.makeText(this, "¡Usuario guardado exitosamente!", Toast.LENGTH_LONG).show();
                                    Intent Login = new Intent(this, Login.class);
                                    startActivity(Login);

                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Ingresa un Nombre y Apellido", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Correo Electrónico Invalido", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Teléfono +51 "+phone+" inválido",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo (Botón Atras)
    public void Anterior(View view) {
        Intent anterior = new Intent (this, HomeMain.class);
        startActivity(anterior);
    }
    //Metodo (Botón Inicia Sesion)
    public void IniciaSesion(View view) {
        Intent anterior = new Intent (this, Login.class);
        startActivity(anterior);
    }
}