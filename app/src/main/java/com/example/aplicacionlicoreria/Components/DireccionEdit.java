package com.example.aplicacionlicoreria.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Address;
import com.example.aplicacionlicoreria.R;

public class DireccionEdit extends AppCompatActivity {
    private TextView etNameD,etDistrito,etDireccion;
    private ImageButton btnAtras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_edit);
        etNameD = findViewById(R.id.input_nameD);
        etDistrito = findViewById(R.id.input_distrito);
        etDireccion = findViewById(R.id.input_direccion);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasDireccion);
        btnAtras.setOnClickListener(v -> onBackPressed());

        Content();
    }
    private void Content() {
        //Recibimos el IDAddress
        Intent intent = getIntent();
        int IdAddres = intent.getIntExtra("idAddress",0);
        String distrito = "Punta Hermosa";

        // Mostrar el dato en la interface
        TextView Distrito = findViewById(R.id.input_distrito);
        Distrito.setText(distrito);

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT * FROM address WHERE idAddress = "+IdAddres,null);

        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            String nameA = cursor.getString(cursor.getColumnIndex("nameA"));
            @SuppressLint("Range")
            String address = cursor.getString(cursor.getColumnIndex("address"));

            // Mostrar el dato en la interface
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView phoneU = findViewById(R.id.input_nameD);
            phoneU.setText(String.valueOf(nameA));

            TextView emailU = findViewById(R.id.input_direccion);
            emailU.setText(address);

            cursor.close();
            DataBase.close();
            dataBase.close();

            //Metodo OnClick
            Button btnUpdate = findViewById(R.id.btnUpdateDireccion);
            btnUpdate.setOnClickListener(v -> ActualizarDireccion(nameA,address));
        }
    }

    public void ActualizarDireccion(String NameAO,String AddressO) {

        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        String nameA = etNameD.getText().toString();
        String Address = etDireccion.getText().toString();


        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if (!nameA.isEmpty()) {
            if (!Address.isEmpty()) {
                if (nameA.length() >= 4) {
                    if (Address.length() >= 13) {
                        //POR SI NO SE MODIFICA NADA
                        if (nameA.equals(NameAO)){
                            if (Address.equals(AddressO)){
                                super.onBackPressed();
                            }else {
                                Cursor filaA = DataBase.rawQuery(
                                        "SELECT address FROM address WHERE address = ? ", new String[]{Address});
                                if (filaA.moveToFirst()) {
                                    DataBase.close();
                                    filaA.close();
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Dirección Existente!", Toast.LENGTH_LONG).show();
                                    dataBase.close();
                                    DataBase.close();
                                } else {
                                    filaA.close();

                                    Cursor filaIdUser = DataBase.rawQuery(
                                            "SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser});
                                    if (filaIdUser.moveToFirst()) {
                                        @SuppressLint("Range")
                                        int idUser = Integer.parseInt(filaIdUser.getString(filaIdUser.getColumnIndex("idUser")));

                                        Address NewAddress = new Address(NameAO, Address, idUser);
                                        dataBase.updateDireccion(NewAddress, idUser);
                                        filaIdUser.close();
                                    } else {
                                        filaIdUser.close();
                                    }
                                    dataBase.close();
                                    DataBase.close();

                                    super.onBackPressed();
                                }
                            }
                        }else {
                            if (Address.equals(AddressO)){
                                Cursor filaN = DataBase.rawQuery(
                                        "SELECT nameA FROM address WHERE nameA = ? ", new String[]{nameA});
                                if (filaN.moveToFirst()) {
                                    DataBase.close();
                                    filaN.close();
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Nombre de la Ubicación Existente!", Toast.LENGTH_LONG).show();
                                    dataBase.close();
                                    DataBase.close();
                                } else {
                                    filaN.close();
                                    Cursor filaIdUser = DataBase.rawQuery(
                                            "SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser});
                                    if (filaIdUser.moveToFirst()) {
                                        @SuppressLint("Range")
                                        int idUser = Integer.parseInt(filaIdUser.getString(filaIdUser.getColumnIndex("idUser")));
                                        Address NewAddress = new Address(nameA, AddressO, idUser);
                                        dataBase.updateDireccion(NewAddress, idUser);
                                        filaIdUser.close();
                                    } else {
                                        filaIdUser.close();
                                    }
                                    dataBase.close();
                                    DataBase.close();

                                    super.onBackPressed();
                                }
                            }else {
                                //CONSULTAR DATOS A LA BD
                                Cursor filaN = DataBase.rawQuery(
                                        "SELECT nameA FROM address WHERE nameA = ? ", new String[]{nameA});
                                if (filaN.moveToFirst()) {
                                    DataBase.close();
                                    filaN.close();
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Nombre de la Ubicación Existente!", Toast.LENGTH_LONG).show();
                                    dataBase.close();
                                    DataBase.close();
                                } else {
                                    filaN.close();
                                    Cursor filaA = DataBase.rawQuery(
                                            "SELECT address FROM address WHERE address = ? ", new String[]{Address});
                                    if (filaA.moveToFirst()) {
                                        DataBase.close();
                                        filaA.close();
                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Dirección Existente!", Toast.LENGTH_LONG).show();
                                        dataBase.close();
                                        DataBase.close();
                                    } else {
                                        filaA.close();

                                        Cursor filaIdUser = DataBase.rawQuery(
                                                "SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser});
                                        if (filaIdUser.moveToFirst()) {
                                            @SuppressLint("Range")
                                            int idUser = Integer.parseInt(filaIdUser.getString(filaIdUser.getColumnIndex("idUser")));

                                            Address NewAddress = new Address(nameA, Address, idUser);
                                            dataBase.updateDireccion(NewAddress, idUser);
                                            filaIdUser.close();
                                        } else {
                                            filaIdUser.close();
                                        }

                                        dataBase.close();
                                        DataBase.close();

                                        super.onBackPressed();
                                    }
                                }
                            }
                        }

                    } else {
                        Toast.makeText(this, "¡Ingresa una Dirección!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this, "¡Minimo 4 letras!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "¡Ingresa un Distrito!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "¡Ingresa un Nombre de la Ubicación!", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}