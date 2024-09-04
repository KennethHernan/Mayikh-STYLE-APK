package com.example.aplicacionlicoreria.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.R;

public class DetalleP extends AppCompatActivity {

    private ImageButton btnAtras;
    private int cantidadActual = 0;
    private int DescuentoTotal = 0;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_p);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.buttonAtrasDescription);
        btnAtras.setOnClickListener(v -> onBackPressed());

        Content();
    }

    private void Content() {
        //Recuperamos el ID del Producto
        Intent intent = getIntent();
        int idProducto = intent.getIntExtra("id", 0);

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT * FROM product " +
                        "INNER JOIN offers " +
                        "ON product.idOffers = offers.idOffers " +
                        "WHERE idProduct = "+ idProducto, null);

        // Recorrer el cursor para obtener los datos del usuario
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            String NameP = cursor.getString(cursor.getColumnIndex("nameP"));
            @SuppressLint("Range")
            String DescriptionP = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range")
            int PriceP = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
            @SuppressLint("Range")
            String Url = cursor.getString(cursor.getColumnIndex("urlP"));
            @SuppressLint("Range")
            int Discount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("discount")));

            // Mostrar el dato en la interface
            if (Discount > 0){
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView discount = findViewById(R.id.txt_Discount);
                discount.setText(""+Discount+"% de Descuento - ¡Oferta limitada!");
            }
            TextView nameP = findViewById(R.id.NameP);
            nameP.setText(NameP);

            TextView descritionP = findViewById(R.id.label_description);
            descritionP.setText(DescriptionP);

            TextView priceP = findViewById(R.id.label_PriceP);
            priceP.setText(String.valueOf(PriceP));

            ImageView imageView = findViewById(R.id.imageViewDetalleP);
            if (Url != null){
                Glide.with(this)
                        .load(Url)
                        .centerCrop()
                        .into(imageView);
            }
        }
        cursor.close();
        DataBase.close();

        aumentarCantidad(findViewById(R.id.btn_Suma));
    }

    public void aumentarCantidad(View view) {
        //Recuperamos el ID del Producto
        Intent intent = getIntent();
        int idProducto = intent.getIntExtra("id", 0);

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT * FROM product WHERE idProduct = "+ idProducto, null);
        // Recorrer el cursor para obtener los datos del usuario
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            int Stock = Integer.parseInt(cursor.getString(cursor.getColumnIndex("stock")));
            if (cantidadActual < Stock) {
                cantidadActual++;
                mostrarCantidad();
            }
        }
        cursor.close();
        DataBase.close();
    }

    public void disminuirCantidad(View view) {
        if (cantidadActual > 0) {
            cantidadActual--;
            mostrarCantidad();
        }
    }
    private void mostrarCantidad() {
        TextView labelCantidad = findViewById(R.id.label_Cantidad);
        labelCantidad.setText(getString(R.string.input_cantidad) + " " + cantidadActual);
        mostrarTotalPagar();
    }
    private void mostrarTotalPagar() {
        //Recuperamos el ID del Producto
        Intent intent = getIntent();
        int idProducto = intent.getIntExtra("id", 0);

        AdminSQLopenHelper admin = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT product.price,offers.discount " +
                        "FROM product " +
                        "INNER JOIN offers " +
                        "ON product.idOffers = offers.idOffers " +
                        "WHERE idProduct = "+ idProducto, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            int PriceP = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
            @SuppressLint("Range")
            int Discount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("discount")));

            //APLICAR DESCUENTO DE PRODUCTO
            Double Descuento = PriceP * (((double) Discount / 100));
            DescuentoTotal = (int) (PriceP - Descuento);

            //Mostrar precio en el botón
            Button miBoton = findViewById(R.id.btn_AgregarC);
            miBoton.setText(getString(R.string.btn_Agregar) + " S/." + DescuentoTotal * cantidadActual);
        }
        cursor.close();
        DataBase.close();
    }

    public void agregarCarrito(View view) {

        //Recuperamos el ID del Producto
        Intent intent = getIntent();
        int idProducto = intent.getIntExtra("id", 0);
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");


        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if (cantidadActual >= 1){
            Cursor cursor = DataBase.rawQuery
                    ("SELECT * FROM product WHERE idProduct = "+ idProducto, null);
            Cursor cursorUser = DataBase.rawQuery
                    ("SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                @SuppressLint("Range")
                String NameP = cursor.getString(cursor.getColumnIndex("nameP"));
                @SuppressLint("Range")
                String Description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range")
                String UrlP = cursor.getString(cursor.getColumnIndex("urlP"));

                if (cursorUser != null) {
                    cursorUser.moveToFirst();
                    @SuppressLint("Range")
                    int IdUser = Integer.parseInt(cursorUser.getString(cursorUser.getColumnIndex("idUser")));
                    int PriceTotal = (DescuentoTotal * cantidadActual);

                    Carrito newCarrito = new Carrito(idProducto,cantidadActual,PriceTotal,NameP,Description,UrlP,IdUser);
                    dataBase.newCarrito(newCarrito);
                }
                cursorUser.close();
            }
            cursor.close();
            //REDIRECCIÓN A HOME
            Intent intentC = new Intent(DetalleP.this, Home.class);
            intentC.putExtra("NameUser", NameUser);
            startActivity(intentC);
        }else{
        }
        DataBase.close();
    }

}