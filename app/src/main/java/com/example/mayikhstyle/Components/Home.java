package com.example.mayikhstyle.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mayikhstyle.Adapters.CategoryImgHorizontalAdapter;
import com.example.mayikhstyle.Adapters.OfertaHorizontalAdapter;
import com.example.mayikhstyle.Adapters.ProductAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Order;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Home extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @BindView(R.id.list_content_category)
    RecyclerView CategoryRecyclerImgView;
    CategoryImgHorizontalAdapter categoryAdapter;
    GridLayoutManager CategoryLayoutManager;
    @BindView(R.id.list_oferta_horizontal)
    RecyclerView OffertRecyclerView;
    OfertaHorizontalAdapter offertAdapter;
    GridLayoutManager OffertLayoutManager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;

    private Button CantidadCarrito,CantidadOrder;

    private List<ProductDetails> allProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Recuperamos dato de Prerefencia
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String NameUser = preferences.getString("NameUser", "");

        // Mostrar el dato en la interface
        TextView textView = findViewById(R.id.label_nameUP);
        textView.setText(NameUser);

        CantidadCarrito = findViewById(R.id.btn_CantidadCarrito);
        CantidadOrder = findViewById(R.id.btn_CantidadOrder);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        ImageButton animar = findViewById(R.id.button_Home);
        animar.setAnimation(animationNavegacion);

        inicializarFirebase();
        ButterKnife.bind(this);
        Recycler();
    }

    private void Recycler() {

        /*=================== Product ======================*/
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ProductLayoutManager = new GridLayoutManager(this, 2);
        } else {
            ProductLayoutManager = new GridLayoutManager(this, 1);
        }
        ProductRecyclerView.setLayoutManager(ProductLayoutManager);
        productAdapter = new ProductAdapter(new ArrayList<>());

        /*=================== Oferta ======================*/
        int orientation2 = getResources().getConfiguration().orientation;
        if (orientation2 == Configuration.ORIENTATION_LANDSCAPE) {
            OffertLayoutManager = new GridLayoutManager(this, 2);

        } else {
            OffertLayoutManager = new GridLayoutManager(this,1);
        }

        OffertRecyclerView.setLayoutManager(OffertLayoutManager);
        offertAdapter = new OfertaHorizontalAdapter(new ArrayList<>());

        /*=================== Categoy ======================*/
        int orientation3 = getResources().getConfiguration().orientation;
        if (orientation3 == Configuration.ORIENTATION_LANDSCAPE) {
            CategoryLayoutManager = new GridLayoutManager(this, 2);
        } else {
            CategoryLayoutManager = new GridLayoutManager(this,1);
        }

        CategoryRecyclerImgView.setLayoutManager(CategoryLayoutManager);
        categoryAdapter = new CategoryImgHorizontalAdapter(new ArrayList<>());
        Content();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void Content() {
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String idUser = preferences.getString("IdUser",toString());

        DatabaseReference databaseCarrito = FirebaseDatabase.getInstance().getReference("carrito");
        DatabaseReference databaseOrder = FirebaseDatabase.getInstance().getReference("orders");
        DatabaseReference databaseOferta = FirebaseDatabase.getInstance().getReference("offers");
        DatabaseReference databaseCategory = FirebaseDatabase.getInstance().getReference("category");
        DatabaseReference databaseProduct = FirebaseDatabase.getInstance().getReference("product");

        //=================== CARRITO ======================
        databaseCarrito.orderByChild("idUser").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Carrito> listCarrito = new ArrayList<>();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Carrito carrito = offerSnapshot.getValue(Carrito.class);
                    listCarrito.add(carrito);
                }

                if (listCarrito.size() > 0) {
                    //Mostrar Notificacion Carrito
                    CantidadCarrito.setVisibility(View.VISIBLE);
                    Button btnCantidadCar = findViewById(R.id.btn_CantidadCarrito);
                    btnCantidadCar.setText(""+listCarrito.size());
                } else {
                    //OCULTAR
                    CantidadCarrito.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("Firebase", "Error al leer datos: " + error.getMessage());
            }
        });

        //=================== ORDER ======================
        databaseOrder.orderByChild("idUser").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> listOrder = new ArrayList<>();
                String idState = "e70aa771-470f-476e-800e-da6e477b1b0b";

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Order order = offerSnapshot.getValue(Order.class);
                    if (order != null && order.getIdState() == idState) {
                        listOrder.add(order);
                    }
                }
                if (listOrder.size() > 0){
                    //VISIBLE
                    CantidadOrder.setVisibility(View.VISIBLE);
                    Button btnCantidadOrd = findViewById(R.id.btn_CantidadOrder);
                    btnCantidadOrd.setText(""+listOrder.size());
                }else {
                    //OCULTAR
                    CantidadOrder.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseError", "Error al leer datos: " + error.getMessage());
            }
        });

        //=================== OFFERS ======================
        databaseOferta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Offers> allOffers = new ArrayList<>();
                for (DataSnapshot offersSnapshot : dataSnapshot.getChildren()) {
                    Offers offers = offersSnapshot.getValue(Offers.class);
                    allOffers.add(offers);
                }
                Collections.shuffle(allOffers);
                if (allOffers.size() > 0) {
                    offertAdapter = new OfertaHorizontalAdapter(allOffers);
                } else {
                    ArrayList<Offers> offersEmpty = new ArrayList<>();
                    offertAdapter.addItems(offersEmpty);
                }
                //=================== Oferta HORIZONTAL ======================
                RecyclerView recyclerViewOfetas = findViewById(R.id.list_oferta_horizontal);
                LinearLayoutManager layoutManagerOffert = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewOfetas.setLayoutManager(layoutManagerOffert);

                OfertaHorizontalAdapter ofertaAdapter = new OfertaHorizontalAdapter(allOffers);
                recyclerViewOfetas.setAdapter(ofertaAdapter);

                OffertRecyclerView.setAdapter(ofertaAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //=================== CATEGORY ======================
        databaseCategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategory = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    listCategory.add(category);
                }
                Collections.shuffle(listCategory);
                if (listCategory.size() > 0) {
                    categoryAdapter = new CategoryImgHorizontalAdapter(listCategory);
                } else {
                    ArrayList<Category> categoryEmpty = new ArrayList<>();
                    categoryAdapter.addItems(categoryEmpty);
                }

                //=================== Category Img HORIZONTAL ======================
                RecyclerView recyclerViewCategoriasImg = findViewById(R.id.list_content_category);
                LinearLayoutManager layoutManagerCategoryImg = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewCategoriasImg.setLayoutManager(layoutManagerCategoryImg);

                CategoryImgHorizontalAdapter categoryImgAdapter = new CategoryImgHorizontalAdapter(listCategory);
                recyclerViewCategoriasImg.setAdapter(categoryImgAdapter);

                CategoryRecyclerImgView.setAdapter(categoryImgAdapter);
                CategoryRecyclerImgView.setAdapter(categoryAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //=================== PRODUCT ======================
        databaseProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        Product product = productSnapshot.getValue(Product.class);

                        if (product != null && product.getStock() > 0) {
                            ProductDetails productDetails = new ProductDetails();
                            productDetails.setProduct(product);

                            loadCategoryAndOffers(productDetails);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) { }
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
        }
    }
    public void VerProducto(View view) {
        Intent intent = new Intent(Home.this, HomeProduct.class);
        startActivity(intent);
    }
    public void VerOferta(View view) {
        Intent intent = new Intent(this, Oferta.class);
        startActivity(intent);
    }
    public void Home(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void Pedidos(View view) {
        Intent intent = new Intent(this, Pedidos.class);
        startActivity(intent);
    }
    public void Carrito(View view) {
        Intent intent = new Intent(this, CarritoUser.class);
        startActivity(intent);
    }
    public void Profile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}