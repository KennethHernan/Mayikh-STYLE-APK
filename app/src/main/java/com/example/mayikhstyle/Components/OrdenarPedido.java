package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mayikhstyle.Adapters.OrdenarPedidoAdapter;
import com.example.mayikhstyle.Adapters.OrdenarPedidoNuevoAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.ComponentsVacios.OrdenarPedidoVacio;
import com.example.mayikhstyle.Components.ComponentsVacios.OrdenarPedidoVacioDireccion;
import com.example.mayikhstyle.Components.ComponentsVacios.OrdenarPedidoVacioTargeta;
import com.example.mayikhstyle.Models.Address;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.Models.DetalleOrd;
import com.example.mayikhstyle.Models.Order;
import com.example.mayikhstyle.Models.Payment;
import com.example.mayikhstyle.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenarPedido extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_carrito)
    RecyclerView OrdenarPedidoRecyclerView;
    OrdenarPedidoAdapter ordenarPedidoAdapter;
    GridLayoutManager OrdenarPedidoLayoutManager;
    OrdenarPedidoNuevoAdapter ordenarPedidoNuevoAdapter;
    private Spinner spinnerTargeta, spinnerDireccion;
    private TextView Subtotal, Delivery, Total;
    private ImageButton btnAtras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_pedido);


        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.btn_AtrasOrdenar);
        btnAtras.setOnClickListener(v -> onBackPressed());

        ButterKnife.bind(this);

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        spinnerDireccion = findViewById(R.id.spinnerDireccion);
        spinnerTargeta = findViewById(R.id.spinnerTargeta);

        List<String> targetaList = new ArrayList<>();
        List<String> direccionList = new ArrayList<>();


        AdminSQLopenHelper database = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = database.getWritableDatabase();

        //COMPROBAR SI EL USUARIO TIENE DIRECCIONES Y TARGETAS
        Cursor cursorUser = DataBase.rawQuery
                ("SELECT idUser FROM usuario WHERE nameU = ? ", new String[]{NameUser},null);

        if (cursorUser != null) {
            cursorUser.moveToFirst();

            @SuppressLint("Range")
            int IdUser = Integer.parseInt(cursorUser.getString(cursorUser.getColumnIndex("idUser")));

            List<Address> direccion = database.listAddress(IdUser);
            List<Payment> payment = database.listPayment(IdUser);

            if (direccion.size() > 0 ) {
                if (payment.size() > 0) {
                    //AGREGAR LOS DATOS AL ARRAY direccionList y targetaList
                    Cursor cursorDireccion = DataBase.rawQuery
                            ("SELECT address FROM address WHERE idUser = ?", new String[] {String.valueOf(IdUser)});
                    if (cursorDireccion.moveToFirst()) {
                        do {
                            @SuppressLint("Range")
                            String Direccion = cursorDireccion.getString(cursorDireccion.getColumnIndex("address"));
                            direccionList.add(Direccion);
                        } while (cursorDireccion.moveToNext());
                    }
                    cursorDireccion.close();

                    Cursor cursorTargeta = DataBase.rawQuery
                            ("SELECT cardNumber FROM payment WHERE idUser = ?", new String[] {String.valueOf(IdUser)});
                    if (cursorTargeta.moveToFirst()) {
                        do {
                            @SuppressLint("Range")
                            String Targeta = cursorTargeta.getString(cursorTargeta.getColumnIndex("cardNumber"));
                            targetaList.add(Targeta);
                        } while (cursorTargeta.moveToNext());
                    }
                    cursorTargeta.close();
                    DataBase.close();

                    //PREPARAMOS LOS DATOS EN EL SPINNER
                    String[] direccionSpinner = direccionList.toArray(new String[0]);
                    String[] targetaSpinner = targetaList.toArray(new String[0]);

                    //MOSTRAMOS LOS DATOS EN EL XML
                    ArrayAdapter<String> adapterDireccion = new ArrayAdapter<>(this, R.layout.spinner, direccionSpinner);
                    adapterDireccion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDireccion.setAdapter(adapterDireccion);

                    ArrayAdapter<String> adapterTargeta = new ArrayAdapter<>(this, R.layout.spinner, targetaSpinner);
                    adapterTargeta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTargeta.setAdapter(adapterTargeta);

                    Recycler();
                } else {
                    DataBase.close();
                    //Targetas Vacias
                    Intent intentD = new Intent(this, OrdenarPedidoVacioTargeta.class);
                    startActivity(intentD);
                }
            } else {
                if (payment.size() > 0) {
                    DataBase.close();
                    //Direccion Vacio
                    Intent intentD = new Intent(this, OrdenarPedidoVacioDireccion.class);
                    startActivity(intentD);
                } else {
                    DataBase.close();
                    //Direccion y Targetas Vacias
                    Intent intentD = new Intent(this, OrdenarPedidoVacio.class);
                    startActivity(intentD);
                }
            }
        }
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OrdenarPedidoLayoutManager = new GridLayoutManager(this, 2);
        } else {
            OrdenarPedidoLayoutManager = new GridLayoutManager(this,1);
        }

        OrdenarPedidoRecyclerView.setLayoutManager(OrdenarPedidoLayoutManager);
        ordenarPedidoAdapter = new OrdenarPedidoAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int IdUser = preferences.getInt("IdUser",0);

        int TotalPrice = 0;
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        /*List<Carrito> carrito = DataBase.listCarrito(IdUser);

        if (carrito.size() > 0) {
            ordenarPedidoAdapter = new OrdenarPedidoAdapter(carrito);
            DataBase.close();

            OrdenarPedidoRecyclerView.setAdapter(ordenarPedidoAdapter);
        } else {
            // PEDIR DE NUEVO
            Intent intent = getIntent();
            int IdOrder = intent.getIntExtra("idOrders",0);
            List<DetalleOrd> detaleOrd = DataBase.listDetalelOrd(IdOrder);
            ordenarPedidoNuevoAdapter = new OrdenarPedidoNuevoAdapter(detaleOrd);
            DataBase.close();

            OrdenarPedidoRecyclerView.setAdapter(ordenarPedidoNuevoAdapter);
        }


        //MOSTAR SUBTOTAL Y TOTAL
        for (Carrito item : carrito) {
            TotalPrice += item.getPrice();
        }*/

        int delivery = 5;
        int totalP = (TotalPrice + delivery);
        Subtotal = findViewById(R.id.textPriceTotal);
        Subtotal.setText("S/." + TotalPrice);
        Delivery = findViewById(R.id.txtDelivery);
        Delivery.setText("S/." + delivery);
        Total = findViewById(R.id.txtTotal);
        Total.setText("S/." + totalP);

        //Mostrar precio en el botón
        Button miBoton = findViewById(R.id.btnPagarAhora);
        miBoton.setText(getString(R.string.btn_Pagar) + " S/." + totalP);

        DataBase.close();
    }

    public void OrdenarPedidos(View view) {

        String Direccion = spinnerDireccion.getSelectedItem().toString();
        String Targeta = spinnerTargeta.getSelectedItem().toString();

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        int IdUser = preferences.getInt("IdUser",0);

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT idUser FROM usuario WHERE nameU = ?", new String[]{NameUser}, null);
        // Recorrer el cursor para obtener los datos del usuario
        if (cursor != null) {
            cursor.moveToFirst();

            //DATOS PARA TABLA ORDER
            Date fechaActual = new Date();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String Fecha = formatoFecha.format(fechaActual);
            int PriceTotal = 0;
            int AmountProduct = 0;
            String Address = "";
            int IdState = 1;

            //CREAR TABLA ORDER
            //Order NewOrder = new Order(Fecha, PriceTotal,Address,AmountProduct,IdUser,IdState);
            //dataBase.newOrder(NewOrder);

            // CAPTURAR EL ORDER CREADO RECIENTEMENTE
            Cursor cursorOrder = DataBase.rawQuery
                    ("SELECT * FROM orders ORDER BY idOrder DESC LIMIT 1",null);
            // Recorrer el cursor para obtener los datos del usuario
            if (cursorOrder != null) {
                cursorOrder.moveToFirst();

                //DATOS PARA TABLA DETALLEORDER
                @SuppressLint("Range")
                int IdOrder = Integer.parseInt(cursorOrder.getString(cursorOrder.getColumnIndex("idOrder")));
                int TotalPrice = 0;
/*
                List<Carrito> carrito = dataBase.listCarrito(IdUser);
                //AGREGAMOS DATOS AL DETALLEORDER
                for (Carrito item : carrito) {
                    TotalPrice += item.getPrice();
                    ContentValues values = new ContentValues();
                    values.put("idProduct", item.getIdProducto());
                    values.put("price", item.getPrice());
                    values.put("amount", item.getAmount());
                    values.put("idOrder", IdOrder);
                    dataBase.newDetalleOrder(item.getIdProducto(),item.getPrice(),item.getAmount(),IdOrder);
                }
                AmountProduct = carrito.size();*/

                //MODIFICAR TABLA ORDER
                //Order UpOrder = new Order(Fecha, TotalPrice,Direccion,AmountProduct,IdUser,IdState);
                //dataBase.updateOrder(UpOrder, IdOrder);
            }
            cursorOrder.close();
        }

        Intent intentC = new Intent(this, Pedidos.class);
        startActivity(intentC);
    }

}