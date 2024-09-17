package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mayikhstyle.Adapters.AdminAdapter.AdminPedidoAdapter;
import com.example.mayikhstyle.Adapters.AdminAdapter.AdminPedidoCancelAdapter;
import com.example.mayikhstyle.Adapters.AdminAdapter.AdminUserAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.CancelOrder;
import com.example.mayikhstyle.Models.Order;
import com.example.mayikhstyle.Models.User;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminVenta extends AppCompatActivity {

    @BindView(R.id.list_user_button)
    RecyclerView UserRecyclerView;
    AdminUserAdapter userAdapter;
    GridLayoutManager LayoutManager;
    GridLayoutManager userLayoutManager;
    @BindView(R.id.list_pedidoCancelado)
    RecyclerView OrderCancelRecyclerView;
    AdminPedidoCancelAdapter orderCancelAdapter;
    GridLayoutManager cancelLayoutManager;

    @BindView(R.id.list_pedidoActivo)
    RecyclerView OrderRecyclerView;
    AdminPedidoAdapter orderdapter;
    GridLayoutManager orderLayoutManager;

    private Button CantidadPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_venta);

        CantidadPedido = findViewById(R.id.btn_Cantidad);

        ButterKnife.bind(this);
        Recycler();
    }

    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            userLayoutManager = new GridLayoutManager(this, 2);
        } else {
            userLayoutManager = new GridLayoutManager(this, 1);
        }
        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE) {
            cancelLayoutManager = new GridLayoutManager(this, 2);
        } else {
            cancelLayoutManager = new GridLayoutManager(this, 1);
        }
        int orientation2 = getResources().getConfiguration().orientation;
        if (orientation2 == Configuration.ORIENTATION_LANDSCAPE) {
            orderLayoutManager = new GridLayoutManager(this, 2);
        } else {
            orderLayoutManager = new GridLayoutManager(this, 1);
        }

        /*=================== User ======================*/
        UserRecyclerView.setLayoutManager(userLayoutManager);
        userAdapter = new AdminUserAdapter(new ArrayList<>());

        /*=================== Order Cancel ======================*/
        OrderCancelRecyclerView.setLayoutManager(cancelLayoutManager);
        orderCancelAdapter = new AdminPedidoCancelAdapter(new ArrayList<>());

        /*=================== Order Pendientes ======================*/
        OrderRecyclerView.setLayoutManager(orderLayoutManager);
        orderdapter = new AdminPedidoAdapter(new ArrayList<>());

        Content();
    }

    private void Content() {

        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);
        List<User> user = DataBase.listUser();/*
        List<CancelOrder> cancelOrder = DataBase.listCancelOrder();
        List<Order> orders = DataBase.listOrderAdmin(1);

        // MOSTRAR CANTIDAD PEDIDO
        if (orders.size() > 0){
            //VISIBLE
            CantidadPedido.setVisibility(View.VISIBLE);

            Button btnCantidadPedido = findViewById(R.id.btn_Cantidad);
            btnCantidadPedido.setText(""+orders.size());
        }else {
            //OCULTAR
            CantidadPedido.setVisibility(View.GONE);
        }
        /*=================== Product ======================*/
        if (user.size() > 0) {
            userAdapter = new AdminUserAdapter(user);
            DataBase.close();

        } else {
            ArrayList<User> userEmpty = new ArrayList<>();
            userAdapter.addItems(userEmpty);
            DataBase.close();
        }

        /*=================== Order Cancel ======================
        if (cancelOrder.size() > 0) {
            orderCancelAdapter = new AdminPedidoCancelAdapter(cancelOrder);
            DataBase.close();
        } else {
            ArrayList<CancelOrder> orderEmpty = new ArrayList<>();
            orderCancelAdapter.addItems(orderEmpty);
            DataBase.close();
        }
        /*=================== Order Pendientes ======================
        if (orders.size() > 0) {
            orderdapter = new AdminPedidoAdapter(orders);
            DataBase.close();
        } else {
            ArrayList<Order> orderEmpty = new ArrayList<>();
            orderdapter.addItems(orderEmpty);
            DataBase.close();
        }
*/
        RecyclerView recyclerViewUser = findViewById(R.id.list_user_button);
        LinearLayoutManager layoutManagerUser = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewUser.setLayoutManager(layoutManagerUser);

        AdminUserAdapter userAdapter = new AdminUserAdapter(user);
        recyclerViewUser.setAdapter(userAdapter);



        RecyclerView recyclerViewOrderCancel = findViewById(R.id.list_pedidoCancelado);
        LinearLayoutManager layoutManagerOrderCancel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOrderCancel.setLayoutManager(layoutManagerOrderCancel);

        //AdminPedidoCancelAdapter orderCancelAdapter = new AdminPedidoCancelAdapter(cancelOrder);
        recyclerViewOrderCancel.setAdapter(orderCancelAdapter);

        OrderRecyclerView.setAdapter(orderdapter);
        UserRecyclerView.setAdapter(userAdapter);
        OrderCancelRecyclerView.setAdapter(orderCancelAdapter);
        DataBase.close();

    }
    public  void Home(View view){
        Intent intent = new Intent (this, AdminHome.class);
        startActivity(intent);
    }
}