package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.List;

public class AdminEditCategory extends AppCompatActivity {

    private EditText etNameC, etUrlC;
    private TextView NameC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_category);

        NameC = (TextView)findViewById(R.id.nameAdminCategory);
        etNameC = (EditText)findViewById(R.id.input_nameCategory);
        etUrlC = (EditText)findViewById(R.id.input_UrlCategory);

        Content();
    }

    private void Content() {

        Intent intent = getIntent();
        int IdCategory = intent.getIntExtra("idCategory", 0);

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        Cursor cursor = DataBase.rawQuery
                ("SELECT * FROM category WHERE idCategory = "+IdCategory,null);

        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range")
            String NameC = cursor.getString(cursor.getColumnIndex("nameC"));
            @SuppressLint("Range")
            String UrlC = cursor.getString(cursor.getColumnIndex("urlC"));

            // MOSTRAR DATOS EN AL INTERFACE
            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            TextView NameCategory = findViewById(R.id.input_nameCategory);
            NameCategory.setText(String.valueOf(NameC));

            TextView UrlCategory = findViewById(R.id.input_UrlCategory);
            UrlCategory.setText(UrlC);

            TextView TitleCategory = findViewById(R.id.nameAdminCategory);
            TitleCategory.setText(NameC);

            ImageView imageView = findViewById(R.id.imageCategory);
            if (UrlC != null){
                Glide.with(this)
                        .load(UrlC)
                        .centerCrop()
                        .into(imageView);
            }

            cursor.close();
            DataBase.close();
            dataBase.close();

            //Metodo OnClick
            Button btnUpdate = findViewById(R.id.btn_Actualizar);
            btnUpdate.setOnClickListener(v -> Actualizar(IdCategory,NameC,UrlC));

            Button btnDelete = findViewById(R.id.btn_Eliminar);
            btnDelete.setOnClickListener(v -> Eliminar(IdCategory));
        }
    }

    public void Actualizar(int idCategory, String NameC,String UrlC){

        String NameCategory = etNameC.getText().toString();
        String UrlCategory = etUrlC.getText().toString();

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if(!NameCategory.isEmpty() & !UrlCategory.isEmpty()){
            if (NameCategory.length() > 3 || UrlCategory.length() > 80){
                //POR SI NO MODIFICA NINGUN INPUT
                if(NameCategory.equals(NameC)){
                    if (UrlCategory.equals(UrlC)){
                        //NOTIFICACION
                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                        //REDIRECCIÓN
                        Intent intent = new Intent(this, AdminCategory.class);
                        startActivity(intent);
                    }else {
                        //INSERTAR LOS DATOS A LA BASE DE DATOS
                        Category NewCategory = new Category(NameC, UrlCategory);
                        dataBase.updateCategory(NewCategory, idCategory);
                        DataBase.close();
                        //NOTIFICACION
                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                        //REDIRECCIÓN
                        Intent intent = new Intent(this, AdminCategory.class);
                        startActivity(intent);
                    }
                }else {
                    Cursor filaN = DataBase.rawQuery("SELECT nameC FROM category WHERE nameC = ?", new String[]{NameCategory});
                    if (filaN.moveToFirst()) {
                        DataBase.close();
                        filaN.close();
                        //NOTIFICACION
                        Toast.makeText(this, "¡Nombre de Categoría existente!", Toast.LENGTH_LONG).show();
                    }else {
                        filaN.close();
                        //INSERTAR LOS DATOS A LA BASE DE DATOS
                        Category NewCategory = new Category(NameCategory, UrlCategory);
                        dataBase.updateCategory(NewCategory, idCategory);
                        DataBase.close();
                        //NOTIFICACION
                        Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                        //REDIRECCIÓN
                        Intent intent = new Intent(this, AdminCategory.class);
                        startActivity(intent);
                    }
                }
            }else{
                Toast.makeText(this, "Ingresa los campos correctamente",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
        dataBase.close();
        DataBase.close();
    }
    public  void Eliminar(int IdCategory){
        AdminSQLopenHelper DataBase = new AdminSQLopenHelper(this, "administracion", null, 1);

/*
        //List<Product> idProduct = DataBase.listIdProducto(IdCategory);
        //ELIMINAR DETALLE ORDER
        for (Product item : idProduct) {
            DataBase.deleteProduct(item.getIdProduct2());
        }
        DataBase.deleteCategory(IdCategory);

        Intent GestionarProducto = new Intent (this, AdminCategory.class);
        startActivity(GestionarProducto);*/
    }
    public  void Atras(View view){
        Intent GestionarProducto = new Intent (this, AdminCategory.class);
        startActivity(GestionarProducto);
    }
}