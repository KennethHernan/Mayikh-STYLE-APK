package com.example.aplicacionlicoreria.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.Adapters.AdminAdapter.AdminPedidoCompleteAdapter;
import com.example.aplicacionlicoreria.Adapters.PedidoCompleteAdapter;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.Models.User;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminVentaCliente extends AppCompatActivity {

    @BindView(R.id.list_pedidoComplete)
    RecyclerView OrderRecyclerView;
    AdminPedidoCompleteAdapter orderdapter;
    GridLayoutManager OrderLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_venta_cliente);

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            OrderLayoutManager = new GridLayoutManager(this, 2);
        } else {
            OrderLayoutManager = new GridLayoutManager(this,1);
        }

        OrderRecyclerView.setLayoutManager(OrderLayoutManager);
        Content();
    }
    private void Content() {
        Intent intent = getIntent();
        int IdUser = intent.getIntExtra("IdUser",0);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase dataBase = DataBase.getWritableDatabase();

        Cursor cursorUser = dataBase.rawQuery
                ("SELECT * FROM orders WHERE idUser = "+ IdUser, null);
        if (cursorUser.moveToFirst()) {
            List<Order> orders = DataBase.listOrder(IdUser,2);
            if (orders.size() > 0) {
                orderdapter = new AdminPedidoCompleteAdapter(orders);
            } else {
//                ArrayList<Order> orderEmpty = new ArrayList<>();
//                orderdapter.addItems(orderEmpty);
//                DataBase.close();

                Toast.makeText(this, "Cliente no tiene pedidos completados", Toast.LENGTH_LONG).show();
            }
            OrderRecyclerView.setAdapter(orderdapter);
        }
        cursorUser.close();

        Cursor cursor = dataBase.rawQuery
                ("SELECT * FROM usuario WHERE idUser = "+ IdUser, null);

        // Recorrer el cursor para obtener los datos del usuario
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            String NameU = cursor.getString(cursor.getColumnIndex("nameU"));
            @SuppressLint("Range")
            int Phone = Integer.parseInt(cursor.getString(cursor.getColumnIndex("phone")));
            @SuppressLint("Range")
            String Email = cursor.getString(cursor.getColumnIndex("email"));

            TextView nameU = findViewById(R.id.txtNamU);
            nameU.setText(NameU);

            TextView phone = findViewById(R.id.txtPhone);
            phone.setText(""+Phone);

            TextView email = findViewById(R.id.txtEmail);
            email.setText(Email);

        }
        cursor.close();
        DataBase.close();
    }

    public void Atras(View view) {
        Intent intentH = new Intent(this, AdminVenta.class);
        startActivity(intentH);
    }
}