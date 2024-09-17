package com.example.mayikhstyle.Components.Administrador;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mayikhstyle.Adapters.AdminAdapter.AdminProductoAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.CategoryProduct;
import com.example.mayikhstyle.Models.Category;
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

public class AdminProduct extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_admin_product)
    RecyclerView ProductRecyclerView;
    AdminProductoAdapter productAdapter;
    GridLayoutManager ProductLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        ButterKnife.bind(this);
        Recycler();
    }

    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ProductLayoutManager = new GridLayoutManager(this, 2);
        } else {
            ProductLayoutManager = new GridLayoutManager(this, 1);
        }

        ProductRecyclerView.setLayoutManager(ProductLayoutManager);
        productAdapter = new AdminProductoAdapter(new ArrayList<>());

        inicializarFirebase();
        Content();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void Content() {
        Intent intent = getIntent();
        String idCategory = intent.getStringExtra("idCategory");

        //=================== CATEGORY ======================
        DatabaseReference databaseCategory = FirebaseDatabase.getInstance().getReference("category");
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
                    ImageView imageView = findViewById(R.id.imageViewCateP);
                    if (categorys.getUrl() != null){
                        Glide.with(AdminProduct.this)
                                .load(categorys.getUrl())
                                .apply(new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.error))
                                .into(imageView);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //=================== PRODUCT ======================
        DatabaseReference databaseProduct = FirebaseDatabase.getInstance().getReference("product");
        databaseProduct.orderByChild("idCategory").equalTo(idCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> listProduct = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    listProduct.add(product);
                }
                if (listProduct.size() > 0) {
                    productAdapter = new AdminProductoAdapter(listProduct);
                } else {
                    ArrayList<Product> productEmpty = new ArrayList<>();
                    productAdapter.addItems(productEmpty);
                }
                ProductRecyclerView.setAdapter(productAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public  void Atras(View view){
        Intent GestionarProducto = new Intent (this, AdminCategory.class);
        startActivity(GestionarProducto);
    }
    public void AddProduct(View view) {
        Intent intent = getIntent();
        String idCategory = intent.getStringExtra("idCategory");
        Intent intentAddP = new Intent(AdminProduct.this, AdminAddProduct.class);
        intentAddP.putExtra("idCategory",idCategory);
        startActivity(intentAddP);
    }
}