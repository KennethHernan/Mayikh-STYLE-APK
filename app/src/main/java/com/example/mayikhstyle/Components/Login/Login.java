package com.example.mayikhstyle.Components.Login;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.telephony.SmsManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mayikhstyle.Components.Administrador.AdminHome;
import com.example.mayikhstyle.Components.Home;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class Login extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText etPhone;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.input_phone);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        LinearLayout animar = findViewById(R.id.linearLayout2);
        animar.setAnimation(animationNavegacion);

        etPhone.setText("999999999");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            Toast.makeText(getApplicationContext(), "sendSMS()", Toast.LENGTH_LONG).show();
        }

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void IniciaSesion(View view) {
        String phone = etPhone.getText().toString();

        if(phone.length() != 0) {
            int phoneI = Integer.parseInt(phone);
            if(phoneI == 2829){
                Intent intent = new Intent(Login.this, AdminHome.class);
                startActivity(intent);
            }else {
                databaseReference.child("user").orderByChild("phone").equalTo(phoneI)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Log.d("Firebase", "Snapshot: " + snapshot.toString());
                                if (snapshot.exists()) {
                                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                        String idUser = userSnapshot.child("idUser").getValue(String.class);
                                        String NameUser = userSnapshot.child("nameU").getValue(String.class);
                                        Random random = new Random();
                                        int min = 1000;
                                        int max = 9999;
                                        int Token = random.nextInt(max - min + 1) + min;
                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(phone, null, "El código de validación de Mayikh STYLE es: "+Token, null, null);
                                            //Validar SMS

                                            //GUARDAR NOMBRE EN PREFERENCIAS
                                            SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("NameUser", NameUser);
                                            editor.putString("IdUser", idUser);
                                            editor.apply();
                                            //REDIRECCIÓN A HOME
                                            Intent intent = new Intent(Login.this, Home.class);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Teléfono no registrado: "+phone, Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.d("Firebase", "Error en la consulta: " + error.getMessage());
                            }
                        });
            }
        }else {
            Toast.makeText(this, "Ingresa un teléfono",Toast.LENGTH_LONG).show();
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "sendSMS()", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }
    //Metodo (Botón Atras)
    public void Anterior(View view) {
        //REDIRECCIÓN A HOME
        Intent anterior = new Intent (this, HomeMain.class);
        startActivity(anterior);
    }
    //Metodo (Botón Registrar)
    public void Registrar(View view) {
        //REDIRECCIÓN A REGISTER
        Intent anterior = new Intent (this, Register.class);
        startActivity(anterior);
    }
}