package com.example.aplicacionlicoreria.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.DetalleOrd;
import com.example.aplicacionlicoreria.Models.Offers;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.R;

import java.util.ArrayList;
import java.util.List;

public class AdminEditProduct extends AppCompatActivity {

    private EditText etNameP, etDescription,etPrice,etStock,etUrlP;
    private Spinner spinnerOferta, spinnerCategory;

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

        spinnerOferta= (Spinner) findViewById(R.id.spinnerOferta);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);

        AdminSQLopenHelper database = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = database.getWritableDatabase();

        List<String> offersList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();

        List<Offers> offers = database.listOffers();

        if (offers.size() > 0 ) {
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

            Intent intent = getIntent();
            int IdProduct = intent.getIntExtra("idProduct", 0);
            Cursor cursor = DataBase.rawQuery
                    ("SELECT * FROM product " +
                            "INNER JOIN offers ON offers.idOffers = product.idOffers " +
                            "INNER JOIN category ON category.idCategory = product.idCategory " +
                            "WHERE product.idProduct ="+IdProduct,null);
            if (cursor != null) {
                cursor.moveToFirst();
                @SuppressLint("Range")
                String NameP = cursor.getString(cursor.getColumnIndex("nameP"));
                @SuppressLint("Range")
                String Description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range")
                Integer Price = cursor.getInt(cursor.getColumnIndex("price"));
                @SuppressLint("Range")
                Integer Stock = cursor.getInt(cursor.getColumnIndex("stock"));
                @SuppressLint("Range")
                Integer IdCategory = cursor.getInt(cursor.getColumnIndex("idCategory"));
                @SuppressLint("Range")
                String NameC = cursor.getString(cursor.getColumnIndex("nameC"));
                @SuppressLint("Range")
                Integer Discount = cursor.getInt(cursor.getColumnIndex("discount"));
                @SuppressLint("Range")
                String UrlP = cursor.getString(cursor.getColumnIndex("urlP"));


                //PREPARAMOS LOS DATOS EN EL SPINNER
                String[] ofertaSpinner = offersList.toArray(new String[0]);
                String[] categoriaSpinner = categoryList.toArray(new String[0]);

                //MOSTRAMOS LOS DATOS EN EL XML
                ArrayAdapter<String> adapterOferta = new ArrayAdapter<>(this, R.layout.spinner, ofertaSpinner);
                adapterOferta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOferta.setAdapter(adapterOferta);

                ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, R.layout.spinner, categoriaSpinner);
                adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(adapterCategory);

                // MOSTRAR DATOS EN LA INTERFACE
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView NameProduct = findViewById(R.id.input_nameP);
                NameProduct.setText(NameP);

                TextView DescriptionP = findViewById(R.id.input_descriptionP);
                DescriptionP.setText(Description);

                TextView PriceP = findViewById(R.id.input_priceP);
                PriceP.setText(String.valueOf(Price));

                TextView StockP = findViewById(R.id.input_stockProduct);
                StockP.setText(String.valueOf(Stock));

                int seccionarCategory = adapterCategory.getPosition(NameC);
                spinnerCategory.setSelection(seccionarCategory);

                int seccionarOferta = adapterOferta.getPosition(String.valueOf(Discount));
                spinnerOferta.setSelection(seccionarOferta);

                TextView UrlProduct = findViewById(R.id.input_urlP);
                UrlProduct.setText(UrlP);

                cursor.close();
                DataBase.close();

                //Metodo OnClick
                Button btnUpdate = findViewById(R.id.btn_Actualizar);
                btnUpdate.setOnClickListener(v -> Actualizar(IdProduct,NameP));

                Button btnDelete = findViewById(R.id.btn_Eliminar);
                btnDelete.setOnClickListener(v -> Eliminar(IdProduct,IdCategory));

                ImageButton btnAtras = findViewById(R.id.btn_Atras);
                btnAtras.setOnClickListener(v -> Atras(IdCategory));
            }
        } else {
            DataBase.close();
        }
    }

    public void Actualizar(int IdProduct, String NameP){

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
                    if (nameP.equals(NameP)) {
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
                                Product NewProduct = new Product(NameP, descriptionP, priceP,IdCategory,urlP,Stock,IdOffer);
                                database.updateProduct(NewProduct,IdProduct);
                                database.close();

                                //REDIRECCIÓN
                                Intent intentP = new Intent(AdminEditProduct.this, AdminProduct.class);
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
                        Cursor filaP = DataBase.rawQuery("SELECT nameP FROM product WHERE nameP = ?", new String[]{nameP});
                        if (filaP.moveToFirst()) {
                            DataBase.close();
                            filaP.close();
                            //NOTIFICACION
                            Toast.makeText(this, "¡Nombre de Producto existente!", Toast.LENGTH_LONG).show();
                        }else {
                            filaP.close();
                            Cursor filaO = DataBase.rawQuery("SELECT idOffers FROM offers WHERE discount = "+ofertaP,null);
                            if (filaO.moveToFirst()) {
                                @SuppressLint("Range")
                                Integer IdOffer = filaO.getInt(filaO.getColumnIndex("idOffers"));

                                Cursor filaC = DataBase.rawQuery("SELECT idCategory FROM category WHERE nameC = ?", new String[]{category});
                                if (filaC.moveToFirst()) {
                                    @SuppressLint("Range")
                                    Integer IdCategory = filaC.getInt(filaC.getColumnIndex("idCategory"));

                                    //INSERTAR LOS DATOS A LA BASE DE DATOS
                                    Product NewProduct = new Product(nameP, descriptionP, priceP,IdCategory,urlP,Stock,IdOffer);
                                    database.updateProduct(NewProduct,IdProduct);
                                    database.close();

                                    //REDIRECCIÓN
                                    Intent intentP = new Intent(AdminEditProduct.this, AdminProduct.class);
                                    intentP.putExtra("idCategory",IdCategory);
                                    startActivity(intentP);
                                    filaC.close();
                                }else {
                                    filaC.close();
                                }
                            }else {
                                filaO.close();
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Stock invalido",Toast.LENGTH_LONG).show();}
            } else {
                Toast.makeText(this, "Precio S/."+priceP+" Invalido", Toast.LENGTH_LONG).show();}
        } else {
            Toast.makeText(this, "Ingresa todo los campos",Toast.LENGTH_LONG).show();}
    }

    public  void Eliminar(int IdProduct,int IdCategory){
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

        DataBase.deleteProduct(IdProduct);

        Intent AdminProduct = new Intent (this, AdminProduct.class);
        AdminProduct.putExtra("idCategory", IdCategory);
        startActivity(AdminProduct);
    }
    public  void Atras(int IdCategory){
        Intent AdminProduct = new Intent (this, AdminProduct.class);
        AdminProduct.putExtra("idCategory", IdCategory);
        startActivity(AdminProduct);
    }
}