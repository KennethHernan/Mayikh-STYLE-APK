package com.example.mayikhstyle.Components.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.HomeMain;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.Models.State;
import com.example.mayikhstyle.Models.User;
import com.example.mayikhstyle.R;

public class Register extends AppCompatActivity {

    private EditText etPhone, etEmail, etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPhone = findViewById(R.id.input_phone);
        etEmail = findViewById(R.id.input_email);
        etName = findViewById(R.id.input_name);

        //ANIMACION
        Animation animationNavegacion = AnimationUtils.loadAnimation(this,R.anim.animation_navegacion);
        LinearLayout animar = findViewById(R.id.linearLayout2);
        animar.setAnimation(animationNavegacion);
    }
    //Metodo (Botón Siguiente)
    public void Registrar(View view) {
        String Sphone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();

        if(!Sphone.isEmpty()) {
            int phone = Integer.parseInt(Sphone);
            if (phone > 901000000){
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length() >= 14) {
                    if (name.length() > 8) {

                        AdminSQLopenHelper dataBase = new AdminSQLopenHelper(this, "administracion", null, 1);
                        SQLiteDatabase DataBase = dataBase.getWritableDatabase();

                        //CONSULTAR DATOS A LA BD
                        Cursor filaP = DataBase.rawQuery(
                                "SELECT phone FROM usuario WHERE phone = " + phone, null);
                        if (filaP.moveToFirst()) {
                            DataBase.close();
                            filaP.close();
                            //NOTIFICACION
                            Toast.makeText(this, "¡Numero Existente!", Toast.LENGTH_LONG).show();
                        } else {
                            filaP.close();
                            Cursor filaE = DataBase.rawQuery(
                                    "SELECT email FROM usuario WHERE email = ?", new String[]{email});
                            if (filaE.moveToFirst()) {
                                DataBase.close();
                                filaE.close();
                                //NOTIFICACION
                                Toast.makeText(this, "¡Email existente!", Toast.LENGTH_LONG).show();
                            } else {
                                filaE.close();
                                Cursor filaN = DataBase.rawQuery(
                                        "SELECT nameU FROM usuario WHERE nameU = ?", new String[]{name});
                                if (filaN.moveToFirst()) {
                                    DataBase.close();
                                    filaN.close();
                                    //NOTIFICACION
                                    Toast.makeText(this, "¡Nombre existente!", Toast.LENGTH_LONG).show();
                                } else {
                                    filaN.close();

                                    Cursor filaUser = DataBase.rawQuery("SELECT * FROM usuario",null);
                                    if (filaUser.moveToFirst()){
                                        filaUser.close();
                                        //INSERTAR USUARIO A LA BASE DE DATOS
                                        String urlU ="https://res.cloudinary.com/dr2c4aovp/image/upload/v1682541322/Pilsen_dq3aki.png";
                                        int IdAddressU = 0;
                                        int IdPaymentU = 0;
                                        User NewUser = new User(phone, email, name, urlU, IdAddressU,IdPaymentU);
                                        dataBase.newUser(NewUser);
                                        dataBase.close();

                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Registrado!", Toast.LENGTH_LONG).show();
                                        //REDIRECCIÓN AL LOGIN
                                        Intent anterior = new Intent(this, Login.class);
                                        startActivity(anterior);

                                    }else {
                                        filaUser.close();
                                        //DATOS UTILIZADOS PARA COMPROBAR SI ESTAN EN LA BD
                                        String c1 = ("VINOS SEMISECOS");
                                        String c2 = ("VINOS SEMISECOS (VIÑA D LOS CAMPOS)");
                                        String c3 = ("PISCOS");
                                        String c4 = ("CERVESA (PILSEN)");

                                        String urlC1 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1682541322/Vinos_Semisecos_Don_Miguel_rapwpy.png";
                                        String urlC2 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1682541322/Vino_Semiseco_Vi%C3%B1a_d_Campo_mmn0mz.png";
                                        String urlC3 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1682541322/Piscos_dda9ot.png";
                                        String urlC4 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1682541322/Pilsen_dq3aki.png";
                                        Category NewCategory1 = new Category(c1, urlC1);
                                        Category NewCategory2 = new Category(c2, urlC2);
                                        Category NewCategory3 = new Category(c3, urlC3);
                                        Category NewCategory4 = new Category(c4, urlC4);
                                        dataBase.newCategoy(NewCategory1);
                                        dataBase.newCategoy(NewCategory2);
                                        dataBase.newCategoy(NewCategory3);
                                        dataBase.newCategoy(NewCategory4);
                                        dataBase.close();

                                        //INSERTAR OFERTA
                                        String DescriptionO = ("¡Descuento solo válido para hoy!");
                                        int Discount = 0;
                                        String DescriptionO2 = ("¡Descuento solo válido para hoy!");
                                        int Discount2 = 30;
                                        String DescriptionO3 = ("¡Descuento solo válido para hoy!");
                                        int Discount3 = 12;

                                        Offers NewOffer = new Offers(Discount, DescriptionO);
                                        Offers NewOffer2 = new Offers(Discount2, DescriptionO2);
                                        Offers NewOffer3 = new Offers(Discount3, DescriptionO3);
                                        dataBase.newOffer(NewOffer);
                                        dataBase.newOffer(NewOffer2);
                                        dataBase.newOffer(NewOffer3);
                                        dataBase.close();

                                        //INSERTAR STATE
                                        String NameS = ("Pagado");
                                        String NameS1 = ("Completado");
                                        String NameS2 = ("Cancelado");

                                        State NewState = new State(NameS);
                                        State NewState2 = new State(NameS1);
                                        State NewState3 = new State(NameS2);
                                        dataBase.newState(NewState);
                                        dataBase.newState(NewState2);
                                        dataBase.newState(NewState3);
                                        dataBase.close();

                                        //DATOS UTILIZADOS PARA AÑADIR LOS PRODUCTOS A LA BD
                                        String p1 = ("VINO SEMISECO (QUEIROLO)");
                                        String p2 = ("VINO SEMISECO VINA D' LOS CAMPOS (QUEBRANTA)");
                                        String p3 = ("VINO SEMISECO VINA D' LOS CAMPOS (BORGOÑA BLANCA)");
                                        String p4 = ("VINO SEMISECO VINA D' LOS CAMPOS (ROSE)");
                                        String p5 = ("VINO SEMISECO (TABERNERO)");
                                        String p6 = ("PISCO ACHOLADO");
                                        String p7 = ("PISCO ITALIA");
                                        String p8 = ("CAJA DE VERVESA (PILSEN)");
                                        String p9 = ("UNIDAD CERVESA (PILSEN)");
                                        String p10 = ("VINO ARTESANAL (MANZANILLO QUEBRANTA)");
                                        String p11 = ("VINO ARTESANAL (GRAN MIXTURA)");
                                        String p12 = ("VINO ARTESANAL (GRAN BLANCO DE BLANCOS)");
                                        String p13 = ("VINO ARTESANAL (BORGOÑA TINTO)");
                                        String p14 = ("VINO ARTESANAL (GRAN ROSA DEL PERÚ HALA)");
                                        String p15 = ("VINO ARTESANAL (BORGOÑA BLANCO)");
                                        String p16 = ("VINO ARTESANAL (GRAN ROSE");

                                        /*===================== PRODUCTO 1 ===========================*/
                                        String nameP1 = p1;
                                        String descriptionP1 = "750 ML";
                                        int priceP1 = 20;
                                        int idcategory1 = 1;
                                        int stock = 5;
                                        int idOffers = 1;
                                        int idOffers2 = 2;
                                        int idOffers3 = 3;
                                        String urlP1 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/Publicaci%C3%B3n_Vino_Semiseco_QUEIROLO_z8pb9e.png";
                                        Product NewProduct = new Product(nameP1, descriptionP1, priceP1, idcategory1, urlP1, stock, idOffers);
                                        dataBase.newProduct(NewProduct);

                                        /*===================== PRODUCTO 2 ===========================*/
                                        String nameP2 = p2;
                                        String descriptionP2 = "750 ML";
                                        int priceP2 = 18;
                                        int idcategory2 = 2;
                                        String urlP2 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/Vino_QUEBRANTA_nuubo0.png";
                                        Product NewProduct2 = new Product(nameP2, descriptionP2, priceP2, idcategory2, urlP2, stock, idOffers2);
                                        dataBase.newProduct(NewProduct2);

                                        /*===================== PRODUCTO 3 ===========================*/
                                        String nameP3 = p3;
                                        String descriptionP3 = "750 ML";
                                        int priceP3 = 18;
                                        int idcategory3 = 2;
                                        String urlP3 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317879/Vino_borgo%C3%B1a_blanca_ybovwn.png";
                                        Product NewProduct3 = new Product(nameP3, descriptionP3, priceP3, idcategory3, urlP3, stock, idOffers3);
                                        dataBase.newProduct(NewProduct3);

                                        /*===================== PRODUCTO 4 ===========================*/
                                        String nameP4 = p4;
                                        String descriptionP4 = "750 ML";
                                        int priceP4 = 18;
                                        int idcategory4 = 2;
                                        String urlP4 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317879/Vino_ROSE_nfdspp.png";
                                        Product NewProduct4 = new Product(nameP4, descriptionP4, priceP4, idcategory4, urlP4, stock, idOffers);
                                        dataBase.newProduct(NewProduct4);

                                        /*===================== PRODUCTO 5 ===========================*/

                                        String nameP5 = p5;
                                        String descriptionP5 = "750 ML";
                                        int priceP5 = 21;
                                        int idcategory5 = 1;
                                        String urlP5 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/_Vino_Semiseco_tabernero_es9xyp.png";
                                        Product NewProduct5 = new Product(nameP5, descriptionP5, priceP5, idcategory5, urlP5, stock, idOffers2);
                                        dataBase.newProduct(NewProduct5);

                                        /*===================== PRODUCTO 6 ===========================*/
                                        String nameP6 = p6;
                                        String descriptionP6 = "530 ML";
                                        int priceP6 = 30;
                                        int idcategory6 = 3;
                                        String urlP6 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317879/Publicaci%C3%B3n_Pisco_Acholado_j3ohoo.png";
                                        Product NewProduct6 = new Product(nameP6, descriptionP6, priceP6, idcategory6, urlP6, stock, idOffers2);
                                        dataBase.newProduct(NewProduct6);

                                        /*===================== PRODUCTO 7 ===========================*/
                                        String nameP7 = p7;
                                        String descriptionP7 = "530 ML";
                                        int priceP7 = 30;
                                        int idcategory7 = 3;
                                        String urlP7 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317878/Pisco_Italia_zqv8yx.png";
                                        Product NewProduct7 = new Product(nameP7, descriptionP7, priceP7, idcategory7, urlP7, stock, idOffers);
                                        dataBase.newProduct(NewProduct7);

                                        /*===================== PRODUCTO 8 ===========================*/

                                        String nameP8 = p8;
                                        String descriptionP8 = "20 UNIDADES";
                                        int priceP8 = 70;
                                        int idcategory8 = 4;
                                        String urlP8 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/Caja_de_Pilsen_t6ttrd.png";
                                        Product NewProduct8 = new Product(nameP8, descriptionP8, priceP8, idcategory8, urlP8, stock, idOffers3);
                                        dataBase.newProduct(NewProduct8);

                                        /*===================== PRODUCTO 9 ===========================*/
                                        String nameP9 = p9;
                                        String descriptionP9 = "400 ML";
                                        int priceP9 = 6;
                                        int idcategory9 = 4;
                                        int stock9 = 5;
                                        int idOffers9 = 1;
                                        String urlP9 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/Unidad_Pilsen_omlyh6.png";
                                        Product NewProduct9 = new Product(nameP9, descriptionP9, priceP9, idcategory9, urlP9, stock, idOffers);
                                        dataBase.newProduct(NewProduct9);

                                        /*===================== PRODUCTO 10 ===========================*/
                                        String nameP10 = p10;
                                        String descriptionP10 = "720 ML";
                                        int priceP10 = 18;
                                        int idcategory10 = 1;
                                        String urlP10 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317878/Manzanillo_Quebranta_i954gy.png";
                                        Product NewProduct10 = new Product(nameP10, descriptionP10, priceP10, idcategory10, urlP10, stock, idOffers);
                                        dataBase.newProduct(NewProduct10);

                                        /*===================== PRODUCTO 11 ===========================*/

                                        String nameP11 = p11;
                                        String descriptionP11 = "720 ML";
                                        int priceP11 = 18;
                                        int idcategory11 = 1;
                                        String urlP11 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317878/GRAN_MIXTURA_piezbb.png";
                                        Product NewProduct11 = new Product(nameP11, descriptionP11, priceP11, idcategory11, urlP11, stock, idOffers2);
                                        dataBase.newProduct(NewProduct11);

                                        /*===================== PRODUCTO 12 ===========================*/

                                        String nameP12 = p12;
                                        String descriptionP12 = "720 ML";
                                        int priceP12 = 18;
                                        int idcategory12 = 1;
                                        String urlP12 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317878/Gran_Blanco_de_Blancos_ljcnd4.png";
                                        Product NewProduct12 = new Product(nameP12, descriptionP12, priceP12, idcategory12, urlP12, stock, idOffers3);
                                        dataBase.newProduct(NewProduct12);

                                        /*===================== PRODUCTO 13 ===========================*/
                                        String nameP13 = p13;
                                        String descriptionP13 = "720 ML";
                                        int priceP13 = 18;
                                        int idcategory13 = 1;
                                        String urlP13 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317878/Borgo%C3%B1a_Tinto_dprv9m.png";
                                        Product NewProduct13 = new Product(nameP13, descriptionP13, priceP13, idcategory13, urlP13, stock, idOffers);
                                        dataBase.newProduct(NewProduct13);

                                        /*===================== PRODUCTO 14 ===========================*/
                                        String nameP14 = p14;
                                        String descriptionP14 = "720 ML";
                                        int priceP14 = 18;
                                        int idcategory14 = 1;
                                        String urlP14 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317878/_Gran_Rosa_del_Per%C3%BA_Hala_fcs8l8.png";
                                        Product NewProduct14 = new Product(nameP14, descriptionP14, priceP14, idcategory14, urlP14, stock, idOffers);
                                        dataBase.newProduct(NewProduct14);

                                        /*===================== PRODUCTO 15 ===========================*/

                                        String nameP15 = p15;
                                        String descriptionP15 = "720 ML";
                                        int priceP15 = 18;
                                        int idcategory15 = 1;
                                        String urlP15 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/Borgo%C3%B1a_Blanco_ks5te0.png";
                                        Product NewProduct15 = new Product(nameP15, descriptionP15, priceP15, idcategory15, urlP15, stock, idOffers2);
                                        dataBase.newProduct(NewProduct15);

                                        /*===================== PRODUCTO 16 ===========================*/

                                        String nameP16 = p16;
                                        String descriptionP16 = "720 ML";
                                        int priceP16 = 18;
                                        int idcategory16 = 1;
                                        String urlP16 =
                                                "https://res.cloudinary.com/dr2c4aovp/image/upload/v1681317877/_Gran_Rose_r7p98h.png";
                                        Product NewProduct16 = new Product(nameP16, descriptionP16, priceP16, idcategory16, urlP16, stock, idOffers);
                                        dataBase.newProduct(NewProduct16);

                                        //INSERTAR USUARIO A LA BASE DE DATOS
                                        String urlU ="https://res.cloudinary.com/dr2c4aovp/image/upload/v1682541322/Pilsen_dq3aki.png";
                                        int IdAddressU = 0;
                                        int IdPaymentU = 0;
                                        User NewUser = new User(phone, email, name, urlU, IdAddressU,IdPaymentU);
                                        dataBase.newUser(NewUser);
                                        dataBase.close();
                                        DataBase.close();

                                        //NOTIFICACION
                                        Toast.makeText(this, "¡Registrado!", Toast.LENGTH_LONG).show();
                                        //REDIRECCIÓN AL LOGIN
                                        Intent anterior = new Intent(this, Login.class);
                                        startActivity(anterior);
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Ingresa un Nombre y Apellido", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Correo Electrónico Invalido", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Teléfono +51 "+phone+" inválido",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Ingresa los campos",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo (Botón Atras)
    public void Anterior(View view) {
        Intent anterior = new Intent (this, HomeMain.class);
        startActivity(anterior);
    }
    //Metodo (Botón Inicia Sesion)
    public void IniciaSesion(View view) {
        Intent anterior = new Intent (this, Login.class);
        startActivity(anterior);
    }
}