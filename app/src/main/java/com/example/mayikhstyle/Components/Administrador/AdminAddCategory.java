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
import com.example.mayikhstyle.BaseDeDatos.DataBaseFireBase;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AdminAddCategory extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText etNameC, etUrlC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_category);

        etNameC = (EditText)findViewById(R.id.input_nameCategory);
        etUrlC = (EditText)findViewById(R.id.input_UrlCategory);

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void Agregar(View view) {
        String NameCategory = etNameC.getText().toString();
        String UrlCategory = etUrlC.getText().toString();

        if(!NameCategory.isEmpty() & !UrlCategory.isEmpty()){
            //INSERTAR LOS DATOS A LA BASE DE DATOS
            String IdC = UUID.randomUUID().toString();

            Category NewCategory = new Category(IdC,NameCategory, UrlCategory);

            DataBaseFireBase DataFire = new DataBaseFireBase();
            DataFire.newCategoy(NewCategory);
            //NOTIFICACION
            Toast.makeText(this, "¡Hecho!", Toast.LENGTH_LONG).show();
            //REDIRECCIÓN
            Intent intent = new Intent(this, AdminAddCategory.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
    }
    public void Atras (View view) {
        Intent intent = new Intent(this, AdminCategory.class);
        startActivity(intent);
    }
}