package com.example.aplicacionlicoreria.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Category;
import com.example.aplicacionlicoreria.Models.Offers;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.R;

import java.util.List;

public class AdminEditOferta extends AppCompatActivity {

    private EditText etDescription, etDescuento;
    private TextView txt_Discount,txt_DescriptionO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_oferta);

        etDescription = findViewById(R.id.input_descriptionOferta);
        etDescuento = findViewById(R.id.input_porcentajeOferta);
        txt_Discount = findViewById(R.id.txt_Discount);
        txt_DescriptionO = findViewById(R.id.txt_DescriptionO);
        Content();
    }
    private void Content() {
        Intent intent = getIntent();
        int IdOferta = intent.getIntExtra("IdOffers", 0);

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT * FROM offers WHERE idOffers = "+IdOferta,null);

        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            String Description = cursor.getString(cursor.getColumnIndex("descriptionO"));
            @SuppressLint("Range")
            int Discount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("discount")));

            // MOSTRAR DATOS EN AL INTERFACE
            txt_Discount.setText(""+Discount);
            txt_DescriptionO.setText(Description);

            etDescription.setText(Description);
            etDescuento.setText(""+Discount);

            //Metodo OnClick
            Button btnUpdate = findViewById(R.id.btn_Actualizar);
            btnUpdate.setOnClickListener(v -> Actualizar(IdOferta,Discount,Description));

            Button btnDelete = findViewById(R.id.btn_Eliminar);
            btnDelete.setOnClickListener(v -> Eliminar(IdOferta));
        }
        cursor.close();
        DataBase.close();
        dataBase.close();
    }

    public void Actualizar(int IdOferta, int Discount,String Description){

        String DescriptionOferta = etDescription.getText().toString();
        String DiscountOferta = etDescuento.getText().toString();
        int DiscountO = Integer.parseInt(DiscountOferta);

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if(!DescriptionOferta.isEmpty() & !DiscountOferta.isEmpty()){
            if (DescriptionOferta.length() > 3 && DiscountO >= 0 && DiscountO < 100){
                //POR SI NO MODIFICA NINGUN INPUT
                if (DiscountO == Discount){
                    //INSERTAR LOS DATOS A LA BASE DE DATOS
                    Offers NewOffers = new Offers(Discount,DescriptionOferta);
                    dataBase.updateOffers(NewOffers, IdOferta);
                    DataBase.close();
                    //NOTIFICACION
                    Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                    //REDIRECCIÓN
                    Intent intent = new Intent(this, AdminOferta.class);
                    startActivity(intent);
                }else {
                    Cursor cursorOferta = DataBase.rawQuery
                            ("SELECT * FROM offers WHERE discount = "+DiscountO,null);
                    if (cursorOferta.moveToFirst()) {
                        //NOTIFICACION
                        Toast.makeText(this, "¡Descuento existente!", Toast.LENGTH_LONG).show();
                    }else {
                        //INSERTAR LOS DATOS A LA BASE DE DATOS
                        Offers NewOffers = new Offers(DiscountO,DescriptionOferta);
                        dataBase.updateOffers(NewOffers, IdOferta);
                        DataBase.close();
                        //NOTIFICACION
                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                        //REDIRECCIÓN
                        Intent intent = new Intent(this, AdminOferta.class);
                        startActivity(intent);
                    }
                }
            }else{
                Toast.makeText(this, "Ingresa los campos correctamente",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
        dataBase.close();
        DataBase.close();
    }
    public  void Eliminar(int IdOferta){
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        List<Product> product = DataBase.listIdProductoOffers(IdOferta);
        //ELIMINAR DETALLE ORDER
        for (Product item : product) {
            DataBase.updateProductOffers(1,item.getIdProduct());
        }
        DataBase.deleteOffers(IdOferta);
        DataBase.close();

        Intent intent = new Intent (this, AdminOferta.class);
        startActivity(intent);
    }
    public  void Atras(View view){
        Intent intent = new Intent (this, AdminOferta.class);
        startActivity(intent);
    }
}