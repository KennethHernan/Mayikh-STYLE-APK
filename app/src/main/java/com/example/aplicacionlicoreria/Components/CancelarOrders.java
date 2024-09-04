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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.CancelOrder;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.DetalleOrd;
import com.example.aplicacionlicoreria.R;

import java.util.List;

public class CancelarOrders extends AppCompatActivity {

    private RadioButton Opcion1,Opcion2,Opcion3,Opcion4,Opcion5,Opcion6,Opcion7;
    private TextView Opcion8;

    private LinearLayout ContentEMOJI;
    private ImageButton btnAtras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_orders);

        Opcion8 = findViewById(R.id.input_descriptionC);
        Opcion1 =  findViewById(R.id.Opcion1);
        Opcion2 =  findViewById(R.id.Opcion2);
        Opcion3 =  findViewById(R.id.Opcion3);
        Opcion4 =  findViewById(R.id.Opcion4);
        Opcion5 =  findViewById(R.id.Opcion5);
        Opcion6 =  findViewById(R.id.Opcion6);
        Opcion7 =  findViewById(R.id.Opcion7);

        ContentEMOJI = findViewById(R.id.LayoutEMOJI);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.btn_Atras);
        btnAtras.setOnClickListener(v -> onBackPressed());

        //Deshabilitar Layout
        ContentEMOJI.setVisibility(View.GONE);
    }
    public void Enviar(View view) {
        //Habilitar Layout
        ContentEMOJI.setVisibility(View.VISIBLE);
    }

    public void Aceptar(View view) {
        String description = Opcion8.getText().toString();

        if (Opcion1.isChecked() == true) {
            String Description = "Esperando por mucho tiempo";
            Content(Description);
        } else if (Opcion2.isChecked() == true) {
            String Description = "No se puede contactar al delivery";
            Content(Description);
        } else if (Opcion3.isChecked() == true) {
            String Description = "Conductor negado a ir a destino";
            Content(Description);
        } else if (Opcion4.isChecked() == true) {
            String Description = "Dirección incorrecta mostrada";
            Content(Description);
        } else if (Opcion5.isChecked() == true) {
            String Description = "El precio no es razonable";
            Content(Description);
        } else if (Opcion6.isChecked() == true) {
            String Description = "Quiero pedir otro restaurante";
            Content(Description);
        } else if (Opcion7.isChecked() == true) {
            String Description = "Solo quiero cancelar";
            Content(Description);
        } else if (description.length() != 0) {
            Content(description);
        }else {
        }
    }
    public void  Content(String Description) {
        Intent intent = getIntent();
        int IdOrder = intent.getIntExtra("idOrders",0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        DataBase.updateStateOrder(3,IdOrder);

        //AGREGAR CANCELACION
        CancelOrder NewCancelOrder = new CancelOrder(Description,IdOrder);
        DataBase.newCancelOrd(NewCancelOrder);
        DataBase.close();

        Intent intentP = new Intent(CancelarOrders.this, Pedidos.class);
        startActivity(intentP);
    }

    public void Atras(View view) {
        Intent intent = getIntent();
        int IdOrder = intent.getIntExtra("idOrders",0);

        Intent intentC = new Intent(CancelarOrders.this, DetallePedidoActivo.class);
        intentC.putExtra("idOrders", IdOrder);
        startActivity(intentC);
    }
}