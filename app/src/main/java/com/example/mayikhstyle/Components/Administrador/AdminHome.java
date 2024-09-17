package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Order;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHome extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Button CantidadPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        CantidadPedido = findViewById(R.id.btn_Cantidad);

        //=================== Firebase ======================
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //=================== ORDER ======================
        DatabaseReference databaseOrder = FirebaseDatabase.getInstance().getReference("orders");

        String idState = "e70aa771-470f-476e-800e-da6e477b1b0b";
        databaseOrder.orderByChild("idState").equalTo(idState).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> listOrder = new ArrayList<>();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Order order = offerSnapshot.getValue(Order.class);
                    listOrder.add(order);
                }
                // MOSTRAR CANTIDAD PEDIDO
                if (listOrder.size() > 0){
                    //VISIBLE
                    CantidadPedido.setVisibility(View.VISIBLE);
                    Button btnCantidadPedido = findViewById(R.id.btn_Cantidad);
                    btnCantidadPedido.setText(""+listOrder.size());
                }else {
                    //OCULTAR
                    CantidadPedido.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseError", "Error al leer datos: " + error.getMessage());
            }
        });
    }

    private void inicializarFirebase() {
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