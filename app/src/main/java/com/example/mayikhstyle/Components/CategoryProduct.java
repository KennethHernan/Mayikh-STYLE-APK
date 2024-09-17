package com.example.mayikhstyle.Components;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mayikhstyle.Adapters.ProductAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
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

public class CategoryProduct extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ImageButton btnAtras;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_product)
    RecyclerView ProductRecyclerView;
    ProductAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;
    private List<ProductDetails> allProducts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        //Metodo para retroceder un activity
        btnAtras = findViewById(R.id.button_atrasCatPro);
        btnAtras.setOnClickListener(v -> {
            Atras();
            onBackPressed();
        });

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
        String idCategory = intent.getStringExtra("idCategory");

        DatabaseReference databaseCategory = FirebaseDatabase.getInstance().getReference("category");
        DatabaseReference databaseProduct = FirebaseDatabase.getInstance().getReference("product");

        //=================== CATEGORY ======================
        databaseCategory.orderByChild("id").equalTo(idCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> filteredCategory = new ArrayList<>();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Category category = offerSnapshot.getValue(Category.class);
                    filteredCategory.add(category);
                }

                if (filteredCategory.size() > 0) {
                    Category categorys = filteredCategory.get(0);
                    //Mostrar datos
                    TextView NameC = findViewById(R.id.title_CategoryProduct);
                    NameC.setText(categorys.getCategory());

                    ImageView imageView = findViewById(R.id.imageView8);
                    if (categorys.getUrl() != null){
                        Glide.with(CategoryProduct.this)
                                .load(categorys.getUrl())
                                .apply(new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.error))
                                .into(imageView);
                    }
                } else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //=================== PRODUCT ======================
        databaseProduct.orderByChild("idCategory").equalTo(idCategory).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void Atras(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}