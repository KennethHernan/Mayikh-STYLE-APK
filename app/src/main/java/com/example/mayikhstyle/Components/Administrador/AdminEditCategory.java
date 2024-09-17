package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.Components.CategoryProduct;
import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.Components.Login.Login;
import com.example.mayikhstyle.Models.Category;
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
import java.util.List;

public class AdminEditCategory extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText etNameC, etUrlC;
    private TextView NameC;

    DataBaseFireBase DataFire = new DataBaseFireBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_category);

        NameC = (TextView)findViewById(R.id.nameAdminCategory);
        etNameC = (EditText)findViewById(R.id.input_nameCategory);
        etUrlC = (EditText)findViewById(R.id.input_UrlCategory);

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
        databaseReference.child("category").orderByChild("id").equalTo(idCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> filteredCategory = new ArrayList<>();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    Category category = offerSnapshot.getValue(Category.class);
                    filteredCategory.add(category);
                }

                ImageView imageView = findViewById(R.id.imageCategory);
                if (filteredCategory.size() > 0) {
                    Category categorys = filteredCategory.get(0);
                    //Mostrar datos
                    NameC.setText(categorys.getCategory());
                    etNameC.setText(categorys.getCategory());
                    etUrlC.setText(categorys.getUrl());

                    if (categorys.getUrl() != null){
                        Glide.with(AdminEditCategory.this)
                                .load(categorys.getUrl())
                                .centerCrop()
                                .into(imageView);
                    }

                    //Metodo OnClick
                    Button btnUpdate = findViewById(R.id.btn_Actualizar);
                    btnUpdate.setOnClickListener(v -> Actualizar(categorys.getId(),categorys.getCategory(),categorys.getUrl()));

                    Button btnDelete = findViewById(R.id.btn_Eliminar);
                    btnDelete.setOnClickListener(v -> Eliminar(idCategory));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void Actualizar(String idCategory, String NameC,String UrlC){
        String NameCategory = etNameC.getText().toString();
        String UrlCategory = etUrlC.getText().toString();

        if(!NameCategory.isEmpty() & !UrlCategory.isEmpty()){
            if (NameCategory.length() > 4){
                //POR SI NO MODIFICA NINGUN INPUT
                if(NameCategory.equals(NameC)){
                    Toast.makeText(this, "¡Nombre de Categoría existente!", Toast.LENGTH_LONG).show();
                }else {

                    databaseReference.child("caregory").orderByChild("nameC").equalTo(NameCategory)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    Log.d("Firebase", "Snapshot: " + snapshot.toString());
                                    if (snapshot.exists()) {
                                        Toast.makeText(getApplicationContext(), "¡Nombre de Categoría existente!", Toast.LENGTH_LONG).show();
                                    } else {
                                        // INSERTAR LOS DATOS A LA BASE DE DATOS
                                        Category updateCategory = new Category(idCategory, NameCategory, UrlCategory);
                                        DataFire.updateCategory(updateCategory);

                                        // NOTIFICACION
                                        Toast.makeText(AdminEditCategory.this, NameCategory+" - Actualizado", Toast.LENGTH_LONG).show();
                                        // REDIRECCIÓN
                                        Intent intent = new Intent(AdminEditCategory.this, AdminCategory.class);
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.d("Firebase", "Error en la consulta: " + error.getMessage());
                                }
                            });
                }
            }else{
                Toast.makeText(this, "Ingresa los campos correctamente",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
    }
    public  void Eliminar(String idCategory){

        //=================== PRODUCT ======================
        databaseReference.child("product").orderByChild("idCategory").equalTo(idCategory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> listProduct = new ArrayList<>();
                Toast.makeText(AdminEditCategory.this, "elimi",Toast.LENGTH_LONG).show();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    listProduct.add(product);
                    //ELIMINAR producto
                    for (Product item : listProduct) {
                        DataFire.deleteProduct(item.getIdProduct());
                    }
                }
                DataFire.deleteCategory(idCategory);
                Toast.makeText(AdminEditCategory.this, "Categoria Eliminada",Toast.LENGTH_LONG).show();
                Intent GestionarProducto = new Intent (AdminEditCategory.this, AdminCategory.class);
                startActivity(GestionarProducto);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public  void Atras(View view){
        Intent GestionarProducto = new Intent (this, AdminCategory.class);
        startActivity(GestionarProducto);
    }
}