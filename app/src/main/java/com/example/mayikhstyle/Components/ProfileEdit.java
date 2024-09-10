package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.User;
import com.example.mayikhstyle.R;

public class ProfileEdit extends AppCompatActivity {

    private EditText etPhone, etEmail, etName;
    private ImageButton btnAtras;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasProfile);
        btnAtras.setOnClickListener(v -> onBackPressed());

        etPhone = findViewById(R.id.input_phone);
        etEmail = findViewById(R.id.input_email);
        etName = findViewById(R.id.input_name);

        Content();
    }

    private void Content() {
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT * FROM usuario WHERE nameU = ? ",  new String[]{NameUser});

        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            int idUser = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idUser")));
            @SuppressLint("Range")
            int PhoneU = Integer.parseInt(cursor.getString(cursor.getColumnIndex("phone")));
            @SuppressLint("Range")
            String EmailU = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range")
            String NameU = cursor.getString(cursor.getColumnIndex("nameU"));

            // Mostrar el dato en la interface
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView phoneU = findViewById(R.id.input_phone);
            phoneU.setText(String.valueOf(PhoneU));

            TextView emailU = findViewById(R.id.input_email);
            emailU.setText(EmailU);

            TextView nameU = findViewById(R.id.input_name);
            nameU.setText(NameU);

            cursor.close();
            DataBase.close();
            dataBase.close();

            //Metodo OnClick
            btnUpdate = findViewById(R.id.btnUpdate);
            btnUpdate.setOnClickListener(v -> ActualizarProfile(idUser,PhoneU,EmailU,NameU));
        }
    }

    public void ActualizarProfile(int idUser, int PhoneU,String EmailU,String NameU){
        String Sphone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if(!Sphone.isEmpty()) {
            int phone = Integer.parseInt(Sphone);
            if (phone > 901000000){
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (name.length() > 8) {
                        //Por si no modifican nada
                        if(phone == PhoneU){
                            if (email.equals(EmailU)){
                                if(name.equals(NameU)){
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                    //REDIRECCIÓN
                                    Intent intent = new Intent(this, Profile.class);
                                    startActivity(intent);
                                }else {
                                    Cursor filaN = DataBase.rawQuery(
                                            "SELECT nameU FROM usuario WHERE nameU = ?", new String[]{name});
                                    if (filaN.moveToFirst()) {
                                        etName.setText(filaN.getString(0));
                                        DataBase.close();
                                        filaN.close();
                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Nombre existente!", Toast.LENGTH_LONG).show();
                                    } else {
                                        filaN.close();
                                        //INSERTAR LOS DATOS A LA BASE DE DATOS
                                        User NewUser = new User(PhoneU, EmailU, name);
                                        dataBase.updateUser(NewUser, idUser);
                                        DataBase.close();

                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                        //REDIRECCIÓN
                                        Intent intent = new Intent(this, Profile.class);
                                        startActivity(intent);
                                    }
                                }
                            }else {
                                Cursor filaE = DataBase.rawQuery(
                                        "SELECT email FROM usuario WHERE email = ?", new String[]{email});
                                if (filaE.moveToFirst()) {
                                    etEmail.setText(filaE.getString(0));
                                    DataBase.close();
                                    filaE.close();
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Email existente!", Toast.LENGTH_LONG).show();
                                }else {
                                    filaE.close();
                                    if(name.equals(NameU)){
                                        //INSERTAR LOS DATOS A LA BASE DE DATOS
                                        User NewUser = new User(PhoneU, email, NameU);
                                        dataBase.updateUser(NewUser, idUser);
                                        dataBase.close();

                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                        //REDIRECCIÓN
                                        Intent intent = new Intent(this, Profile.class);
                                        startActivity(intent);
                                    }else {
                                        Cursor filaN = DataBase.rawQuery(
                                                "SELECT nameU FROM usuario WHERE nameU = ?", new String[]{name});
                                        if (filaN.moveToFirst()) {
                                            etName.setText(filaN.getString(0));
                                            DataBase.close();
                                            filaN.close();
                                            //NOTIFICACION
                                            Toast.makeText(this, "¡Nombre existente!", Toast.LENGTH_LONG).show();
                                        } else {
                                            filaN.close();
                                            //INSERTAR LOS DATOS A LA BASE DE DATOS
                                            User NewUser = new User(PhoneU, email, name);
                                            dataBase.updateUser(NewUser, idUser);
                                            dataBase.close();

                                            //NOTIFICACION
                                            Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                            //REDIRECCIÓN
                                            Intent intent = new Intent(this, Profile.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        }else {
                            //CONSULTAR DATOS A LA BD
                            Cursor filaP = DataBase.rawQuery(
                                    "SELECT phone FROM usuario WHERE phone = " + phone, null);
                            if (filaP.moveToFirst()) {
                                etPhone.setText(filaP.getString(0));
                                DataBase.close();
                                filaP.close();
                                //NOTIFICACION
                                Toast.makeText(this, "¡Numero Existente!", Toast.LENGTH_LONG).show();
                            }else {
                                filaP.close();
                                if (email.equals(EmailU)){
                                    if(name.equals(NameU)){
                                        //INSERTAR LOS DATOS A LA BASE DE DATOS
                                        User NewUser = new User(phone, EmailU, NameU);
                                        dataBase.updateUser(NewUser, idUser);
                                        dataBase.close();

                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                        //REDIRECCIÓN
                                        Intent intent = new Intent(this, Profile.class);
                                        startActivity(intent);
                                    }else {
                                        Cursor filaN = DataBase.rawQuery(
                                                "SELECT nameU FROM usuario WHERE nameU = ?", new String[]{name});
                                        if (filaN.moveToFirst()) {
                                            etName.setText(filaN.getString(0));
                                            DataBase.close();
                                            filaN.close();
                                            //NOTIFICACION
                                            Toast.makeText(this, "¡Nombre existente!", Toast.LENGTH_LONG).show();
                                        } else {
                                            filaN.close();
                                            //INSERTAR LOS DATOS A LA BASE DE DATOS
                                            User NewUser = new User(phone, EmailU, name);
                                            dataBase.updateUser(NewUser, idUser);
                                            dataBase.close();
                                            DataBase.close();

                                            //NOTIFICACION
                                            Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                            //REDIRECCIÓN
                                            Intent intent = new Intent(this, Profile.class);
                                            startActivity(intent);
                                        }
                                    }
                                }else {
                                    Cursor filaE = DataBase.rawQuery(
                                            "SELECT email FROM usuario WHERE email = ?", new String[]{email});
                                    if (filaE.moveToFirst()) {
                                        etEmail.setText(filaE.getString(0));
                                        DataBase.close();
                                        filaE.close();
                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Email existente!", Toast.LENGTH_LONG).show();
                                    }else {
                                        filaE.close();
                                        if(name.equals(NameU)){
                                            //INSERTAR LOS DATOS A LA BASE DE DATOS
                                            User NewUser = new User(phone, email, NameU);
                                            dataBase.updateUser(NewUser, idUser);
                                            dataBase.close();
                                            DataBase.close();

                                            //NOTIFICACION
                                            Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                            //REDIRECCIÓN
                                            Intent intent = new Intent(this, Profile.class);
                                            startActivity(intent);
                                        }else {
                                            Cursor filaN = DataBase.rawQuery(
                                                    "SELECT nameU FROM usuario WHERE nameU = ?", new String[]{name});
                                            if (filaN.moveToFirst()) {
                                                etName.setText(filaN.getString(0));
                                                DataBase.close();
                                                filaN.close();
                                                //NOTIFICACION
                                                Toast.makeText(this, "¡Nombre existente!", Toast.LENGTH_LONG).show();
                                            } else {
                                                filaN.close();
                                                //INSERTAR LOS DATOS A LA BASE DE DATOS
                                                User NewUser = new User(phone, email, name);
                                                dataBase.updateUser(NewUser, idUser);
                                                dataBase.close();
                                                DataBase.close();

                                                //NOTIFICACION
                                                Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();

                                                //REDIRECCIÓN
                                                Intent intent = new Intent(this, Profile.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else {
                        Toast.makeText(this, "Ingresa un Nombre y Apellido", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, "Correo Electrónico Invalido", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Teléfono +51 "+phone+" inválido",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa un teléfono",Toast.LENGTH_LONG).show();
        }
        dataBase.close();
        DataBase.close();
    }
}