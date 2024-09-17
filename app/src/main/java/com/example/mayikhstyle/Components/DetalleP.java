package com.example.mayikhstyle.Components;

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

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.Models.ProductDetails;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetalleP extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ImageButton btnAtras;
    private int cantidadActual = 0;
    private int DescuentoTotal = 0;
    private ProductDetails productDetails1;

    private List<ProductDetails> allProducts = new ArrayList<>();
    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_p);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.buttonAtrasDescription);
        btnAtras.setOnClickListener(v -> onBackPressed());

        inicializarFirebase();
        Content();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void Content() {
        //Recuperamos el ID del Producto
        Intent intent = getIntent();
        String idProduct = intent.getStringExtra("idProduct");
        DatabaseReference databaseProduct = FirebaseDatabase.getInstance().getReference("product");

        databaseProduct.orderByChild("idProduct").equalTo(idProduct).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    if (product != null) {
                        ProductDetails productDetails = new ProductDetails();
                        productDetails.setProduct(product);

                        loadCategoryAndOffers(productDetails);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void loadCategoryAndOffers(ProductDetails productDetails) {
        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("category").child(productDetails.getProduct().getIdCategory());
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Category category = dataSnapshot.getValue(Category.class);
                if (category != null) {
                    productDetails.setCategory(category);
                }

                loadOffers(productDetails);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private void loadOffers(ProductDetails productDetails) {
        DatabaseReference offersRef = FirebaseDatabase.getInstance().getReference("offers").child(productDetails.getProduct().getIdOffers());
        offersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Offers offers = dataSnapshot.getValue(Offers.class);
                if (offers != null) {
                    productDetails.setOffer(offers);
                }

                addProductToList(productDetails);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    private void addProductToList(ProductDetails productDetails) {
        allProducts.add(productDetails);

        if (allProducts.size() > 0) {

            productDetails1 = allProducts.get(0);
            //Mostrar datos
            if (productDetails1.getOffer().getDiscount() > 0){
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView discount = findViewById(R.id.txt_Discount);
                discount.setText(""+productDetails1.getOffer().getDiscount()+"% de Descuento - ¡Oferta limitada!");
            }
            TextView nameP = findViewById(R.id.NameP);
            nameP.setText(productDetails1.getProduct().getNameP());

            TextView descritionP = findViewById(R.id.label_description);
            descritionP.setText(productDetails1.getProduct().getDescription());

            TextView priceP = findViewById(R.id.label_PriceP);
            priceP.setText(String.valueOf(productDetails1.getProduct().getPrice()));

            ImageView imageView = findViewById(R.id.imageViewDetalleP);
            if (productDetails1.getProduct().getUrlP() != null){
                Glide.with(this)
                        .load(productDetails1.getProduct().getUrlP())
                        .centerCrop()
                        .into(imageView);
            }
            aumentarCantidad(findViewById(R.id.btn_Suma));

        }
    }
    public void aumentarCantidad(View view) {
        productDetails1 = allProducts.get(0);

        if (cantidadActual < productDetails1.getProduct().getStock()) {
            cantidadActual++;
            mostrarCantidad();
        }

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
        productDetails1 = allProducts.get(0);

        //APLICAR DESCUENTO DE PRODUCTO
        Double Descuento = productDetails1.getProduct().getPrice() * (((double) productDetails1.getOffer().getDiscount() / 100));
        DescuentoTotal = (int) (productDetails1.getProduct().getPrice() - Descuento);
        //Mostrar precio en el botón
        Button miBoton = findViewById(R.id.btn_AgregarC);
        miBoton.setText(getString(R.string.btn_Agregar) + " S/." + DescuentoTotal * cantidadActual);
    }

    public void agregarCarrito(View view) {
        productDetails1 = allProducts.get(0);
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");
        String IdUser = preferences.getString("IdUser", "");

        if (cantidadActual > 0){
            // Cambiar STOCK
            int NewStock = productDetails1.getProduct().getStock() -cantidadActual ;
            Product updateProduct = new Product(productDetails1.getProduct().getIdProduct(), productDetails1.getProduct().getNameP(), productDetails1.getProduct().getDescription(), productDetails1.getProduct().getPrice() , productDetails1.getProduct().getUrlP(), NewStock,productDetails1.getProduct().getIdCategory(),productDetails1.getProduct().getIdOffers());
            DataBaseFireBase DataFire = new DataBaseFireBase();
            DataFire.updateProduct(updateProduct);

            // AGREGAR P. A CARRITO
            String idCarrito = UUID.randomUUID().toString();
            int PriceTotal = (productDetails1.getProduct().getPrice() * cantidadActual);
            Carrito newCarrito = new Carrito(idCarrito,productDetails1.getProduct().getIdProduct(),cantidadActual,PriceTotal,productDetails1.getProduct().getNameP(),productDetails1.getProduct().getDescription(),productDetails1.getProduct().getUrlP(),IdUser);
            DataFire.newCarrito(newCarrito);
        }
        //REDIRECCIÓN A HOME
        Intent intentC = new Intent(DetalleP.this, Home.class);
        intentC.putExtra("NameUser", NameUser);
        startActivity(intentC);
    }

}