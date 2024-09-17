package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.mayikhstyle.Adapters.AdminAdapter.AdminCategoryAdapter;
import com.example.mayikhstyle.Adapters.CategoryImgHorizontalAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminCategory extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @BindView(R.id.list_admin_categoria)
    RecyclerView CategoryRecyclerView;
    AdminCategoryAdapter categoryAdapter;
    GridLayoutManager CategoryLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        ButterKnife.bind(this);
        Recycler();
    }
    private void Recycler() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CategoryLayoutManager = new GridLayoutManager(this, 2);
        } else {
            CategoryLayoutManager = new GridLayoutManager(this,1);
        }

        CategoryRecyclerView.setLayoutManager(CategoryLayoutManager);
        categoryAdapter = new AdminCategoryAdapter(new ArrayList<>());

        inicializarFirebase();
        Content();
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void Content() {
        DatabaseReference databaseCategory = FirebaseDatabase.getInstance().getReference("category");
        databaseCategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategory = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    listCategory.add(category);
                }

                Collections.sort(listCategory, (c1, c2) -> c1.getCategory().compareToIgnoreCase(c2.getCategory()));

                if (listCategory.size() > 0) {
                    categoryAdapter = new AdminCategoryAdapter(listCategory);
                } else {
                    ArrayList<Category> categoryEmpty = new ArrayList<>();
                    categoryAdapter.addItems(categoryEmpty);
                }
                CategoryRecyclerView.setAdapter(categoryAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public  void Atras(View view){
        Intent intent = new Intent (this, AdminHome.class);
        startActivity(intent);
    }
    public  void AddCategory(View view){
        Intent intent = new Intent (this, AdminAddCategory.class);
        startActivity(intent);
    }
}