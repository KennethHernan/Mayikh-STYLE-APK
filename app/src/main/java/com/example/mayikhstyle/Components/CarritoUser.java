package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mayikhstyle.Adapters.CarritoAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.ComponentsVacios.CarritoVacio;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarritoUser extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @BindView(R.id.list_carrito)
    RecyclerView CarritoRecyclerView;
   CarritoAdapter carritoAdapter;
    GridLayoutManager CarritoLayoutManager;

    private ImageButton btnAtras;

    Double TotalPrice = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        ButterKnife.bind(this);
        inicializarFirebase();
        Recycler();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CarritoLayoutManager = new GridLayoutManager(this, 2);
        } else {
            CarritoLayoutManager = new GridLayoutManager(this,1);
        }

        CarritoRecyclerView.setLayoutManager(CarritoLayoutManager);
        carritoAdapter = new CarritoAdapter(new ArrayList<>());

        Content();
    }
    private void Content() {

        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        String IdUser = preferences.getString("IdUser", "");


        //=================== CARRITO ======================
        DatabaseReference databaseCarrito = FirebaseDatabase.getInstance().getReference("carrito");
        databaseCarrito.orderByChild("idUser").equalTo(IdUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Carrito> listCarrito = new ArrayList<>();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Carrito carrito = offerSnapshot.getValue(Carrito.class);
                    listCarrito.add(carrito);
                }

                if (listCarrito.size() > 0) {
                    carritoAdapter = new CarritoAdapter(listCarrito);
                } else {
                    ArrayList<Carrito> carritoEmpty = new ArrayList<>();
                    carritoAdapter.addItems(carritoEmpty);

                    //Si la lista está vacia se Redirecciona a "CarritoVacio"
                    Intent intent = new Intent(CarritoUser.this, CarritoVacio.class);
                    startActivity(intent);
                }
                CarritoRecyclerView.setAdapter(carritoAdapter);
                //MOSTAR SUBTOTAL EN BOTÓN
                for (Carrito item : listCarrito) {
                    TotalPrice += item.getPrice();
                }
                Button miBoton = findViewById(R.id.btn_Carrito);
                miBoton.setText(getString(R.string.btn_Comprar) + " S/." + TotalPrice);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("Firebase", "Error al leer datos: " + error.getMessage());
            }
        });
    }

    public void ComprarAhora(View view) {
        Intent intent = new Intent(CarritoUser.this, OrdenarPedido.class);
        startActivity(intent);
    }

    public  void Atras(View view){
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }
}