package com.example.aplicacionlicoreria.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aplicacionlicoreria.Adapters.CarritoAdapter;
import com.example.aplicacionlicoreria.Adapters.DetallePedidoAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.ComponentsVacios.CarritoVacio;
import com.example.aplicacionlicoreria.Models.CancelOrder;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.DetalleOrd;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetallePedidoActivo extends AppCompatActivity {

    @BindView(R.id.list_detalle_pedido_activo)
    RecyclerView DetalleOrdRecyclerView;
    DetallePedidoAdapter detalleOrdAdapter;
    GridLayoutManager DetalleOrdLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido_activo);
        ButterKnife.bind(this);

        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DetalleOrdLayoutManager = new GridLayoutManager(this, 2);
        } else {
            DetalleOrdLayoutManager = new GridLayoutManager(this,1);
        }

        DetalleOrdRecyclerView.setLayoutManager(DetalleOrdLayoutManager);
        detalleOrdAdapter = new DetallePedidoAdapter(new ArrayList<>());

        Content();
    }

    public void Content() {
        Intent intent = getIntent();
        int IdOrder = intent.getIntExtra("idOrders",0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        Cursor cursor = dataBase.rawQuery
                ("SELECT * FROM orders WHERE idOrder = "+ IdOrder, null);

        // RECORRER EL CURSOR PARA OBTENER EL NOMBRE DEL ESTADO
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            int idOrder = Integer.parseInt(cursor.getString(cursor.getColumnIndex("idOrder")));
            @SuppressLint("Range")
            int amountProduct = Integer.parseInt(cursor.getString(cursor.getColumnIndex("amountProduct")));
            @SuppressLint("Range")
            int priceTotal = Integer.parseInt(cursor.getString(cursor.getColumnIndex("precioTotal")));

            TextView nameP = findViewById(R.id.titlePedido);
            nameP.setText("Código de Pedido N° "+idOrder);

            TextView descritionP = findViewById(R.id.cantidadProductos);
            descritionP.setText(amountProduct+" Productos");

            TextView priceP = findViewById(R.id.priceProduct);
            priceP.setText("S/." + priceTotal);

            List<DetalleOrd> detalleOrds = DataBase.listDetalleOrds(idOrder);

            if (detalleOrds.size() > 0) {
                detalleOrdAdapter = new DetallePedidoAdapter(detalleOrds);
                DataBase.close();
            } else {
                ArrayList<DetalleOrd> detalleOrdsEmpty = new ArrayList<>();
                detalleOrdAdapter.addItems(detalleOrdsEmpty);
                DataBase.close();
            }

            DetalleOrdRecyclerView.setAdapter(detalleOrdAdapter);
            DataBase.close();
        }

    }

    public void Atras(View view) {
        Intent intent = new Intent(DetallePedidoActivo.this, Pedidos.class);
        startActivity(intent);
    }
    public void CanlelarPedido(View view) {
        Intent intent = getIntent();
        int IdOrder = intent.getIntExtra("idOrders",0);
        Intent intentC = new Intent(DetallePedidoActivo.this, CancelOrder.class);
        intentC.putExtra("idOrders", IdOrder);
        startActivity(intentC);
    }
}