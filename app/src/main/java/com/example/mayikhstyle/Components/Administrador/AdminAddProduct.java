package com.example.mayikhstyle.Components.Administrador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.Components.Oferta;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Product;
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

public class AdminAddProduct extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Offers> offersList = new ArrayList<Offers>();
    ArrayAdapter<Offers> offersArrayAdapter;
    Offers offersSelect;
    private List<Category> categoryList = new ArrayList<Category>();
    ArrayAdapter<Category> categoryArrayAdapter;
    Category categorySelect;
    private EditText etNameP, etDescription,etPrice,etStock,etUrlP;
    private Spinner spinnerOferta, spinnerCategory;

    public String IDCATEGORY;
    public String IDOFFERS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        etNameP = (EditText)findViewById(R.id.input_nameP);
        etDescription = (EditText)findViewById(R.id.input_descriptionP);
        etPrice = (EditText)findViewById(R.id.input_priceP);
        etStock = (EditText)findViewById(R.id.input_stockProduct);
        etUrlP = (EditText)findViewById(R.id.input_urlP);

        spinnerOferta= (Spinner) findViewById(R.id.spinnerOferta);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);

        //Metodo OnClick
        Intent intent = getIntent();
        int idCategory = intent.getIntExtra("idCategory", 0);
        ImageButton btnAtras = findViewById(R.id.btn_Atras);
        btnAtras.setOnClickListener(v -> Atras(idCategory));

        inicializarFirebase();
        listarOfertaCategoria();


        spinnerOferta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                offersSelect = (Offers) adapterView.getItemAtPosition(position);
                IDOFFERS = offersSelect.getIdOffers();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                categorySelect = (Category) adapterView.getItemAtPosition(position);
                IDCATEGORY = categorySelect.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void listarOfertaCategoria(){
        databaseReference.child("offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offersList.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Offers o = objSnaptshot.getValue(Offers.class);
                    offersList.add(o);

                    //MOSTRAMOS LOS DATOS EN EL SPINNER
                    offersArrayAdapter = new ArrayAdapter<Offers>(AdminAddProduct.this, R.layout.spinner, offersList);
                    offersArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerOferta.setAdapter(offersArrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        databaseReference.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()) {
                    Category c = objSnaptshot.getValue(Category.class);
                    categoryList.add(c);

                    //MOSTRAMOS LOS DATOS EN EL SPINNER
                    categoryArrayAdapter = new ArrayAdapter<Category>(AdminAddProduct.this, R.layout.spinner, categoryList);
                    categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(categoryArrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void Actualizar(View view){

        String nameP = etNameP.getText().toString();
        String descriptionP = etDescription.getText().toString();
        String SpriceP = etPrice.getText().toString();
        String SstockP = etStock.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String ofertaS = spinnerOferta.getSelectedItem().toString();
        String urlP = etUrlP.getText().toString();
        String idProduct = UUID.randomUUID().toString();

        if(!nameP.isEmpty() & !descriptionP.isEmpty() & !SpriceP.isEmpty() & !ofertaS.isEmpty() & !category.isEmpty() & !urlP.isEmpty()) {
            int priceP = Integer.parseInt(SpriceP);
            int Stock = Integer.parseInt(SstockP);
            if (priceP >= 20 ) {
                if (Stock > 0 ) {

                    //INSERTAR LOS DATOS A LA BASE DE DATOS
                    Product NewProduct = new Product(idProduct, nameP, descriptionP, priceP , urlP, Stock,IDCATEGORY,IDOFFERS);
                    DataBaseFireBase DataFire = new DataBaseFireBase();
                    DataFire.newProduct(NewProduct);

                    //REDIRECCIÓN
                    Intent intentP = new Intent(AdminAddProduct.this, AdminAddProduct.class);
                    //Intent intentP = new Intent(AdminAddProduct.this, AdminAddProduct.class);
                    intentP.putExtra("idCategory",IDCATEGORY);
                    startActivity(intentP);
                } else {
                    Toast.makeText(this, "Stock invalido",Toast.LENGTH_LONG).show();}
            } else {
                Toast.makeText(this, "Precio S/."+priceP+" Invalido", Toast.LENGTH_LONG).show();}
        } else {
            Toast.makeText(this, "Ingresa todo los campos",Toast.LENGTH_LONG).show();}
    }

    public  void Atras(int IdCategory){
        Intent AdminProduct = new Intent (this, AdminProduct.class);
        AdminProduct.putExtra("idCategory", IdCategory);
        startActivity(AdminProduct);
    }
}