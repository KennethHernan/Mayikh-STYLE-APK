package com.example.mayikhstyle.Components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mayikhstyle.Adapters.ProductAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfertaProduct extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private List<ProductDetails> allProducts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_product);

        //Metodo para retroceder un activity
        ImageButton btnAtras = findViewById(R.id.button_Atras);
        btnAtras.setOnClickListener(v -> onBackPressed());

        inicializarFirebase();
        ButterKnife.bind(this);
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
            ProductLayoutManager = new GridLayoutManager(this, 2);
        } else {
            ProductLayoutManager = new GridLayoutManager(this, 1);
        }

        ProductRecyclerView.setLayoutManager(ProductLayoutManager);
        productAdapter = new ProductAdapter(new ArrayList<>());

        Content();
    }

    private void Content() {
        Intent intent = getIntent();
        String idOffers = intent.getStringExtra("IdOffers");

        DatabaseReference databaseOferta = FirebaseDatabase.getInstance().getReference("offers");
        DatabaseReference databaseProduct = FirebaseDatabase.getInstance().getReference("product");

        databaseOferta.orderByChild("idOffers").equalTo(idOffers).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Offers> filteredOffers = new ArrayList<>();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Offers offer = offerSnapshot.getValue(Offers.class);
                    filteredOffers.add(offer);
                }

                if (filteredOffers.size() > 0) {
                    Offers offers = filteredOffers.get(0);
                    //Mostrar datos
                    TextView DiscountO = findViewById(R.id.txt_Discount);
                    DiscountO.setText(offers.getDiscount() + "%");

                    TextView DescriptionO = findViewById(R.id.txt_DescriptionO);
                    DescriptionO.setText(offers.getDescriptionO());
                } else {
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseProduct.orderByChild("idOffers").equalTo(idOffers).addListenerForSingleValueEvent(new ValueEventListener() {
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
            productAdapter = new ProductAdapter(allProducts);
            ProductRecyclerView.setAdapter(productAdapter);
        } else {
            ArrayList<ProductDetails> productEmpty = new ArrayList<>();
            productAdapter.addItems(productEmpty);
            ProductRecyclerView.setAdapter(productAdapter);
        }
    }
}
