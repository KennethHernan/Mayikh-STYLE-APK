package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.Models.Order;
import com.example.mayikhstyle.R;

import java.util.List;

public class AdminHome extends AppCompatActivity {

    private Button CantidadPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        CantidadPedido = findViewById(R.id.btn_Cantidad);

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);

        // MOSTRAR CANTIDAD PEDIDO
        List<Order> order = DataBase.listOrderAdmin(1);

        if (order.size() > 0){
            //VISIBLE
            CantidadPedido.setVisibility(View.VISIBLE);

            Button btnCantidadPedido = findViewById(R.id.btn_Cantidad);
            btnCantidadPedido.setText(""+order.size());
        }else {
            //OCULTAR
            CantidadPedido.setVisibility(View.GONE);
        }
    }
    public  void Home(View view){
        Intent intent = new Intent (this, HomeMain.class);
        startActivity(intent);
    }
    public  void GestionarProducto(View view){
        Intent intent = new Intent (this, AdminCategory.class);
        startActivity(intent);
    }
    public  void GestionarVenta(View view){
        Intent intent = new Intent (this, AdminVenta.class);
        startActivity(intent);
    }
    public  void GestionarOferta(View view){
        Intent intent = new Intent (this, AdminOferta.class);
        startActivity(intent);
    }

}