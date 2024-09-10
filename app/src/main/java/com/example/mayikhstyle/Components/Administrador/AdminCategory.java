package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.mayikhstyle.Adapters.AdminAdapter.AdminCategoryAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminCategory extends AppCompatActivity {
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

        Content();
    }
    private void Content() {
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper( this, "administracion", null, 1);
        List<Category> category = DataBase.listCategory();

        if (category.size() > 0) {
            categoryAdapter = new AdminCategoryAdapter(category);
            DataBase.close();
        } else {
            ArrayList<Category> categoryEmpty = new ArrayList<>();
            categoryAdapter.addItems(categoryEmpty);
            DataBase.close();
        }

        CategoryRecyclerView.setAdapter(categoryAdapter);
        DataBase.close();
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