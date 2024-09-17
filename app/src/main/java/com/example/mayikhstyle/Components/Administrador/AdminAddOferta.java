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
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.State;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AdminAddOferta extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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

        inicializarFirebase();
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void Actualizar(View view) {
        String DescriptionOferta = etDescription.getText().toString();
        String DiscountOferta = etDescuento.getText().toString();

        if (!DescriptionOferta.isEmpty() & !DiscountOferta.isEmpty()) {
            int DiscountO = Integer.parseInt(DiscountOferta);
            if (DescriptionOferta.length() > 3 && DiscountO >= 0 && DiscountO < 100) {
                //INSERTAR LOS DATOS A LA BASE DE DATOS
                String IdO = UUID.randomUUID().toString();
                Offers NewOffers = new Offers(IdO, DiscountO, DescriptionOferta);

                DataBaseFireBase DataFire = new DataBaseFireBase();
                DataFire.newOffer(NewOffers);
                //NOTIFICACION
                Toast.makeText(this, DiscountO+"% Agregado", Toast.LENGTH_LONG).show();
                //REDIRECCIÓN
                Intent intent = new Intent(this, AdminOferta.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Ingresa los campos correctamente", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingresa los campos", Toast.LENGTH_LONG).show();
        }
    }
    public  void Atras(View view){
        Intent intent = new Intent (this, AdminOferta.class);
        startActivity(intent);
    }
}