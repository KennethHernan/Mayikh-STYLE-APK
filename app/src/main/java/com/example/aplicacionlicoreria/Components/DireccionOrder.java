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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Address;
import com.example.aplicacionlicoreria.R;

public class DireccionOrder extends AppCompatActivity {

    private TextView etNameD,etDistrito,etDireccion;
    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_order);

        etNameD = findViewById(R.id.input_nameD);
        etDistrito = findViewById(R.id.input_distrito);
        etDireccion =findViewById(R.id.input_direccion);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasDireccion);
        btnAtras.setOnClickListener(v -> onBackPressed());

        Content();
    }
    private void Content() {
        String distrito = "Punta Hermosa";

        // Mostrar el dato en la interface
        TextView Distrito = findViewById(R.id.input_distrito);
        Distrito.setText(distrito);

    }

    public void AddDireccion(View view) {
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        int IdUser = preferences.getInt("IdUser",0);

        String nameA = etNameD.getText().toString();
        String Address = etDireccion.getText().toString();

        if (!nameA.isEmpty()) {
            if (!Address.isEmpty()) {
                if (nameA.length() >= 4) {
                    if (Address.length() >= 13) {

                        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
                        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

                        //CONSULTAR DATOS A LA BD
                        Cursor filaN = DataBase.rawQuery(
                                "SELECT nameA FROM address WHERE nameA = ? AND idUser = "+IdUser+"", new String[]{nameA});
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
                                    "SELECT address FROM address WHERE address = ? AND idUser = "+IdUser+"", new String[]{Address});
                            if (filaA.moveToFirst()) {
                                DataBase.close();
                                filaA.close();
                                //NOTIFICACION
                                Toast.makeText(this, "¡Dirección Existente!", Toast.LENGTH_LONG).show();
                                dataBase.close();
                                DataBase.close();
                            } else {
                                filaA.close();

                                Cursor filaAddresss = DataBase.rawQuery(
                                        "SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser});
                                if (filaAddresss.moveToFirst()) {
                                    @SuppressLint("Range")
                                    int idUser = Integer.parseInt(filaAddresss.getString(filaAddresss.getColumnIndex("idUser")));

                                    Address NewAddress = new Address(nameA, Address, idUser);
                                    dataBase.newDireccion(NewAddress);
                                    filaAddresss.close();
                                } else {
                                    filaAddresss.close();
                                }

                                dataBase.close();
                                DataBase.close();

                                Intent intentD = new Intent(this, OrdenarPedido.class);
                                startActivity(intentD);
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
}