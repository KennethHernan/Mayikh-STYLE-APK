package com.example.mayikhstyle.Components.Administrador;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.Adapters.ProductAdapter;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.Components.Login.Login;
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
import java.util.Objects;

public class AdminEditProduct extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Offers> offersList = new ArrayList<Offers>();
    ArrayAdapter<Offers> offersArrayAdapter;
    Offers offersSelect;
    private List<Category> categoryList = new ArrayList<Category>();
    ArrayAdapter<Category> categoryArrayAdapter;
    Category categorySelect;
    private EditText etNameP, etDescription, etPrice, etStock, etUrlP;
    private Spinner spinnerOferta, spinnerCategory;

    public String IDCATEGORY, IDOFFERS;

    private List<ProductDetails> allProducts = new ArrayList<>();

    DataBaseFireBase DataFire = new DataBaseFireBase();

    int positionC = -1;
    int positionO = -1;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_product);

        etNameP = findViewById(R.id.input_nameP);
        etDescription = findViewById(R.id.input_descriptionP);
        etPrice = findViewById(R.id.input_priceP);
        etStock = findViewById(R.id.input_stockProduct);
        etUrlP = findViewById(R.id.input_urlP);

        spinnerOferta = findViewById(R.id.spinnerOferta);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        inicializarFirebase();
        listarOfertaCategoria();

        spinnerOferta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                offersSelect = (Offers) adapterView.getItemAtPosition(position);
                IDOFFERS = offersSelect.getIdOffers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                categorySelect = (Category) adapterView.getItemAtPosition(position);
                IDCATEGORY = categorySelect.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Intent intent = getIntent();
        String idProduct = intent.getStringExtra("idProduct");

        //LISTAR PRODUCTO POR ID
        databaseReference.child("product").orderByChild("idProduct").equalTo(idProduct).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    if (product != null) {
                        ProductDetails productDetails = new ProductDetails();
                        productDetails.setProduct(product);

                        //AÑADIR CATEGORY
                        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("category").child(productDetails.getProduct().getIdCategory());
                        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Category category = dataSnapshot.getValue(Category.class);
                                if (category != null) {
                                    productDetails.setCategory(category);
                                }

                                //AÑADIR OFFERS
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
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarOfertaCategoria() {
        databaseReference.child("offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offersList.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()) {
                    Offers o = objSnaptshot.getValue(Offers.class);
                    offersList.add(o);

                    //MOSTRAMOS LOS DATOS EN EL SPINNER
                    offersArrayAdapter = new ArrayAdapter<>(AdminEditProduct.this, R.layout.spinner, offersList);
                    offersArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerOferta.setAdapter(offersArrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()) {
                    Category c = objSnaptshot.getValue(Category.class);
                    categoryList.add(c);

                    //MOSTRAMOS LOS DATOS EN EL SPINNER
                    categoryArrayAdapter = new ArrayAdapter<>(AdminEditProduct.this, R.layout.spinner, categoryList);
                    categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(categoryArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void addProductToList(ProductDetails productDetails) {
        allProducts.add(productDetails);
        allProducts.size();

        ProductDetails productList = allProducts.get(0);

        // MOSTRAR DATOS EN LA INTERFACE
        TextView NameProduct = findViewById(R.id.input_nameP);
        NameProduct.setText(productList.getProduct().getNameP());
        TextView DescriptionP = findViewById(R.id.input_descriptionP);
        DescriptionP.setText(productList.getProduct().getDescription());
        TextView PriceP = findViewById(R.id.input_priceP);
        PriceP.setText(String.valueOf(productList.getProduct().getPrice()));
        TextView StockP = findViewById(R.id.input_stockProduct);
        StockP.setText(String.valueOf(productList.getProduct().getStock()));
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategory().equals(productDetails.getCategory().getCategory())) {
                positionC = i;
                break;
            }
        }
        spinnerCategory.setSelection(positionC);

        for (int i = 0; i < offersList.size(); i++) {
            if (Objects.equals(offersList.get(i).getDiscount(), productDetails.getOffer().getDiscount())) {
                positionO = i;
                break;
            }
        }
        spinnerOferta.setSelection(positionO);

        TextView UrlProduct = findViewById(R.id.input_urlP);
        UrlProduct.setText(productList.getProduct().getUrlP());
        //Metodo OnClick
        Button btnUpdate = findViewById(R.id.btn_Actualizar);
        btnUpdate.setOnClickListener(v -> Actualizar(productList.getProduct().getIdProduct(), productList.getProduct().getNameP()));
        Button btnDelete = findViewById(R.id.btn_Eliminar);
        btnDelete.setOnClickListener(v -> Eliminar(productList.getProduct().getNameP(),productList.getProduct().getIdProduct(), productList.getProduct().getIdCategory()));
    }

    public void Actualizar(String IdProduct, String NameP) {
        String nameP = etNameP.getText().toString();
        String descriptionP = etDescription.getText().toString();
        String SpriceP = etPrice.getText().toString();
        String SstockP = etStock.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String ofertaS = spinnerOferta.getSelectedItem().toString();
        String urlP = etUrlP.getText().toString();

        if (!nameP.isEmpty() & !descriptionP.isEmpty() & !SpriceP.isEmpty() & !ofertaS.isEmpty() & !category.isEmpty() & !urlP.isEmpty()) {
            int priceP = Integer.parseInt(SpriceP);
            int Stock = Integer.parseInt(SstockP);
            if (priceP >= 20) {
                if (Stock > 0) {
                    if (nameP.equals(NameP)) {
                        Toast.makeText(this, "Nombre existente", Toast.LENGTH_LONG).show();
                    } else {
                        databaseReference.child("product").orderByChild("nameP").equalTo(nameP).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                        String NameUser = userSnapshot.child("nameP").getValue(String.class);
                                        Toast.makeText(getApplicationContext(), "Nombre Registrado: " + NameUser, Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    //INSERTAR LOS DATOS A LA BASE DE DATOS
                                    Product updateProduct = new Product(IdProduct, nameP, descriptionP, priceP, urlP, Stock, IDCATEGORY, IDOFFERS);
                                    DataBaseFireBase DataFire = new DataBaseFireBase();
                                    DataFire.updateProduct(updateProduct);
                                    Toast.makeText(AdminEditProduct.this, nameP+" - Actualizado",Toast.LENGTH_LONG).show();

                                    //REDIRECCIÓN
                                    Intent intentP = new Intent(AdminEditProduct.this, AdminProduct.class);
                                    intentP.putExtra("idCategory", IDCATEGORY);
                                    startActivity(intentP);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.d("Firebase", "Error en la consulta: " + error.getMessage());
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Stock invalido", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Precio Invalido", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingresa todo los campos", Toast.LENGTH_LONG).show();
        }
    }

    public  void Eliminar(String name,String IdProduct,String IdCategory){
        DataFire.deleteProduct(IdProduct);
        Toast.makeText(this, name+" - Eliminado",Toast.LENGTH_LONG).show();
        Intent AdminProduct = new Intent (this, AdminProduct.class);
        AdminProduct.putExtra("idCategory", IdCategory);
        startActivity(AdminProduct);
    }
    public  void Atras(View view){
        Intent intent = getIntent();
        String idCategory = intent.getStringExtra("idCategory");
        Intent intentAddP = new Intent(AdminEditProduct.this, AdminProduct.class);
        intentAddP.putExtra("idCategory",idCategory);
        startActivity(intentAddP);
    }
}