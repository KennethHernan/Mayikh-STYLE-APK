package com.example.mayikhstyle.Components.Administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;

public class AdminAddCategory extends AppCompatActivity {

    private EditText etNameC, etUrlC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        etNameC = (EditText)findViewById(R.id.input_nameCategory);
        etUrlC = (EditText)findViewById(R.id.input_UrlCategory);

    }
    public void Agregar(View view) {
        String NameCategory = etNameC.getText().toString();
        String UrlCategory = etUrlC.getText().toString();

        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

        if(!NameCategory.isEmpty() & !UrlCategory.isEmpty()){
            if (NameCategory.length() > 3 || UrlCategory.length() > 80){
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
                    dataBase.newCategoy(NewCategory);
                    DataBase.close();
                    //NOTIFICACION
                    Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
                    //REDIRECCIÓN
                    Intent intent = new Intent(this, AdminCategory.class);
                    startActivity(intent);
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
    public void Atras (View view) {
        Intent intent = new Intent(this, AdminCategory.class);
        startActivity(intent);
    }
}