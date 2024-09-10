package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Payment;
import com.example.mayikhstyle.R;

public class RegisterMetodoPago extends AppCompatActivity {

    private TextView etTarget,etMonth,etYear,etCvv;
    public ImageButton btnAtras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_metodo_pago);

        etTarget = findViewById(R.id.input_target);
        etMonth = findViewById(R.id.input_month);
        etYear = findViewById(R.id.input_year);
        etCvv = findViewById(R.id.input_cvv);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasMetodoPago);
        btnAtras.setOnClickListener(v -> onBackPressed());
    }

    public void AddTargeta(View view){
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        int IdUser = preferences.getInt("IdUser",0);

        String targetS = etTarget.getText().toString();
        String monthS = etMonth.getText().toString();
        String yearS = etYear.getText().toString();
        String cvvS = etCvv.getText().toString();

        if (!targetS.isEmpty() & !monthS.isEmpty() & !yearS.isEmpty() & !cvvS.isEmpty()){
            if (targetS.length() == 16){
                int month = Integer.parseInt(monthS);
                if (month <= 12){
                    int year = Integer.parseInt(yearS);
                    if (year <= 27){
                        int cvv = Integer.parseInt(cvvS);
                        if (cvv >= 010){
                            AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
                            SQLiteDatabase DataBase = dataBase.getWritableDatabase();

                            //CONSULTAR DATOS A LA BD
                            Cursor filaT = DataBase.rawQuery(
                                    "SELECT cardNumber FROM payment WHERE cardNumber = "+ targetS+" AND idUser = "+IdUser,null);
                            if (filaT.moveToFirst()) {
                                DataBase.close();
                                filaT.close();
                                //NOTIFICACION
                                Toast.makeText(this, "¡Numero de Targeta existente!", Toast.LENGTH_LONG).show();
                                dataBase.close();
                                DataBase.close();
                            } else {
                                filaT.close();

                                Payment NewPayment = new Payment(IdUser,targetS,month,year, cvv);
                                dataBase.newTarget(NewPayment);
                                dataBase.close();
                                DataBase.close();

                                //REDIRECCIÓN
                                Intent intentR = new Intent(this, MetodoPago.class);
                                startActivity(intentR);
                            }
                        }else {
                            Toast.makeText(this, "¡Ingresa el Código CVV!", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(this, "¡Ingresa el Año de Caducidad!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, "¡Ingresa el Mes de Caducidad!", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, "¡Ingresa una Targeta, 16 digitos!", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "¡Completa los Campos!", Toast.LENGTH_LONG).show();
        }
    }
}