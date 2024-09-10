package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.ArrayList;
import java.util.List;

public class AdminAddProduct extends AppCompatActivity {

    private EditText etNameP, etDescription,etPrice,etStock,etUrlP;
    private Spinner spinnerOferta, spinnerCategory;

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

        AdminSQLopenHelper database = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = database.getWritableDatabase();

        List<String> offersList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();

        //AGREGAR LOS DATOS AL ARRAY direccionList y targetaList
        Cursor cursorDireccion = DataBase.rawQuery("SELECT discount FROM offers",null);
        if (cursorDireccion.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String DiscountO = cursorDireccion.getString(cursorDireccion.getColumnIndex("discount"));
                offersList.add(DiscountO);
            } while (cursorDireccion.moveToNext());
        }
        cursorDireccion.close();
        Cursor cursorCategoy = DataBase.rawQuery("SELECT nameC FROM category",null);
        if (cursorCategoy.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String NameC = cursorCategoy.getString(cursorCategoy.getColumnIndex("nameC"));
                categoryList.add(NameC);
            } while (cursorCategoy.moveToNext());
        }
        cursorCategoy.close();

        //PREPARAMOS LOS DATOS EN EL SPINNER
        String[] ofertaSpinner = offersList.toArray(new String[0]);
        String[] categoriaSpinner = categoryList.toArray(new String[0]);

        //MOSTRAMOS LOS DATOS EN EL SPINNER
        ArrayAdapter<String> adapterOferta = new ArrayAdapter<>(this, R.layout.spinner, ofertaSpinner);
        adapterOferta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOferta.setAdapter(adapterOferta);

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, R.layout.spinner, categoriaSpinner);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

        //Metodo OnClick
        Intent intent = getIntent();
        int idCategory = intent.getIntExtra("idCategory", 0);
        ImageButton btnAtras = findViewById(R.id.btn_Atras);
        btnAtras.setOnClickListener(v -> Atras(idCategory));
    }

    public void Actualizar(View view){

        String nameP = etNameP.getText().toString();
        String descriptionP = etDescription.getText().toString();
        String SpriceP = etPrice.getText().toString();
        String SstockP = etStock.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String ofertaS = spinnerOferta.getSelectedItem().toString();
        String urlP = etUrlP.getText().toString();

        AdminSQLopenHelper database = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = database.getWritableDatabase();

        if(!nameP.isEmpty() & !descriptionP.isEmpty() & !SpriceP.isEmpty() & !ofertaS.isEmpty() & !category.isEmpty() & !urlP.isEmpty()) {
            int priceP = Integer.parseInt(SpriceP);
            int ofertaP = Integer.parseInt(ofertaS);
            int Stock = Integer.parseInt(SstockP);
            if (priceP >= 7 ) {
                if (Stock >= 1 ) {
                    Cursor filaO = DataBase.rawQuery("SELECT idOffers FROM offers WHERE discount = "+ofertaP,null);
                    if (filaO.moveToFirst()) {
                        @SuppressLint("Range")
                        Integer IdOffer = filaO.getInt(filaO.getColumnIndex("idOffers"));
                        Cursor filaC = DataBase.rawQuery("SELECT idCategory FROM category WHERE nameC = ?", new String[]{category});

                        if (filaC.moveToFirst()) {
                            filaO.close();
                            @SuppressLint("Range")
                            Integer IdCategory = filaC.getInt(filaC.getColumnIndex("idCategory"));

                            //INSERTAR LOS DATOS A LA BASE DE DATOS
                            Product NewProduct = new Product(nameP, descriptionP, priceP,IdCategory,urlP,Stock,IdOffer);
                            database.newProduct(NewProduct);
                            database.close();

                            //REDIRECCIÓN
                            Intent intentP = new Intent(AdminAddProduct.this, AdminProduct.class);
                            intentP.putExtra("idCategory",IdCategory);
                            startActivity(intentP);
                            filaC.close();
                        }else {
                            filaC.close();
                        }
                    }else {
                        filaO.close();
                    }
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