package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.R;

public class AdminAddOferta extends AppCompatActivity {

    private EditText etDescription, etDescuento;
    private TextView txt_Discount,txt_DescriptionO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_oferta);

        etDescription = findViewById(R.id.input_descriptionOferta);
        etDescuento = findViewById(R.id.input_porcentajeOferta);
        txt_Discount = findViewById(R.id.txt_Discount);
        txt_DescriptionO = findViewById(R.id.txt_DescriptionO);

        // MOSTRAR DATOS EN AL INTERFACE
        txt_Discount.setText("00%");
        txt_DescriptionO.setText("¡Ingrese una descripción!");
    }
    public void Actualizar(View view){
        String DescriptionOferta = etDescription.getText().toString();
        String DiscountOferta = etDescuento.getText().toString();

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if(!DescriptionOferta.isEmpty() & !DiscountOferta.isEmpty()){
            int DiscountO = Integer.parseInt(DiscountOferta);
            if (DescriptionOferta.length() > 3 && DiscountO >= 0 && DiscountO < 100){
                //INSERTAR LOS DATOS A LA BASE DE DATOS
                Offers NewOffers = new Offers(DiscountO,DescriptionOferta);
                dataBase.newOffer(NewOffers);
                DataBase.close();
                //NOTIFICACION
                Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                //REDIRECCIÓN
                Intent intent = new Intent(this, AdminOferta.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Ingresa los campos correctamente",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
        dataBase.close();
        DataBase.close();
    }
    public  void Atras(View view){
        Intent intent = new Intent (this, AdminOferta.class);
        startActivity(intent);
    }
}