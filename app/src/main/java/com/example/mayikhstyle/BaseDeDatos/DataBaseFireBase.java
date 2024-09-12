package com.example.mayikhstyle.BaseDeDatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mayikhstyle.Models.Address;
import com.example.mayikhstyle.Models.CancelOrder;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.DetalleOrd;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Order;
import com.example.mayikhstyle.Models.Payment;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.Models.State;
import com.example.mayikhstyle.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataBaseFireBase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // TABLA USUARIO
    private final static String NameTableU ="usuario", IdU = "idUser", PhoneU = "phone", EmailU = "email", NameU = "nameU", UrlU = "urlU", IdAddressU = "idAddress", IdPaymentU = "idPayment";

    // TABLA PRODUCT
    private final static String NameTableP ="product", IdP = "idProduct", NameP = "nameP", DescriptionP = "description", PriceP = "price", IdCategoryP = "idCategory",UrlP = "urlP", Stock = "stock", IdOffersP = "idOffers";

    // TABLA CATEGORY
    private final static String NameTableC ="category", IdC = "idCategory", NameC = "nameC", UrlC = "urlC";
    // TABLA ADDRESS
    private final static String NameTableA ="address", IdA = "idAddress", NameA = "nameA", Address = "address", IdUserA = "idUser";

    // TABLA PAYMENT
    private final static String NameTablePay ="payment", IdPay = "idPayment", IdUserP = "idUser", CardNumber = "cardNumber", Month = "month", Year = "year", Cvv = "cvv";

    // TABLA OFFERS
    private final static String NameTableO ="offers", IdO = "idOffers", Discount = "discount", DescriptionO = "descriptionO";

    // TABLA CARRITO
    private final static String NameTableCar ="carrito", IdCar = "idCarrito", IdProductoCar = "idProducto", Amount = "amount", PriceCar = "price", NamePCar = "nombrePCar", DescriptionCar = "descriptionCar",UrlCar = "urlP",IdUserCar = "idUser";

    // TABLA DETALLE ORDER
    private final static String NameTableDetalleOrd ="detalleOrder",IdDetalleOrd = "idDetalleOrder", IdProductoDetalleOrd = "idProducto", PriceDetalleOrd = "precio", AmountDetalle = "amount", IdOrdDetalle = "idOrder";

    // TABLA ORDER
    private final static String NameTableOrd ="orders", IdOrd = "idOrder",FechaCompra = "fechaCompra", PriceTotal = "precioTotal",AddressOrd = "address", AmountOrd = "amountProduct", IdUserOrd = "idUser",IdStateOrd = "idState";

    // TABLA STATE
    private final static String NameTableEst ="state", IdEst = "idState", NameS = "nameS";

    // TABLA CANCELORDER
    private final static String NameTableCancel ="cancelOrder", IdCancel = "idCancelOrder", DescriptionCancel = "descriptionCancel", IdOrderCancel = "idOrder";

    public DataBaseFireBase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    /*public List<Product> listProduct(){
        String sql = "SELECT "+NameTableP+".*, "+NameTableO+"."+Discount+" " +
                "FROM " + NameTableP+" " +
                "INNER JOIN "+NameTableO+" " +
                "ON "+NameTableP+"."+IdO+" = "+NameTableO+"."+IdO+" ORDER BY RANDOM() ";
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Product> listProduct = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdProduct = cursor.getInt(0);
                String NameP = cursor.getString(1);
                String DescriptionP = cursor.getString(2);
                int PriceP = cursor.getInt(3);
                int IdCategory = cursor.getInt(4);
                String UrlP = cursor.getString(5);
                int Stock = cursor.getInt(6);
                int IdOffers = cursor.getInt(7);
                int Discount = cursor.getInt(8);
                listProduct.add(new Product(IdProduct,NameP,DescriptionP,PriceP,IdCategory,UrlP,Stock,IdOffers,Discount));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }
    public List<Product> listProductRecomendado(){
        String sql = "SELECT "+NameTableP+".*, "+NameTableO+"."+Discount+" " +
                "FROM " + NameTableP+" " +
                "INNER JOIN "+NameTableO+" " +
                "ON "+NameTableP+"."+IdO+" = "+NameTableO+"."+IdO+" ORDER BY RANDOM() LIMIT 4";
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Product> listProduct = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdProduct = cursor.getInt(0);
                String NameP = cursor.getString(1);
                String DescriptionP = cursor.getString(2);
                int PriceP = cursor.getInt(3);
                int IdCategory = cursor.getInt(4);
                String UrlP = cursor.getString(5);
                int Stock = cursor.getInt(6);
                int IdOffers = cursor.getInt(7);
                int Discount = cursor.getInt(8);
                listProduct.add(new Product(IdProduct,NameP,DescriptionP,PriceP,IdCategory,UrlP,Stock,IdOffers,Discount));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }
    ///////////*/
    public void listProductRecomendado() {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("product");

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            List<Product> allProducts = new ArrayList<>();

            for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                Product product = productSnapshot.getValue(Product.class);
                allProducts.add(product);
            }

            // Seleccionar 4 productos aleatorios
            Collections.shuffle(allProducts);
            List<Product> recommendedProducts = allProducts.stream().limit(4).collect(Collectors.toList());

            // Aquí puedes hacer algo con la lista de productos recomendados
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Manejar el error
        }
    });
    }
    /*
    public List<Product> listIdProducto(int idCategory){
        String sql = "SELECT product.idProduct FROM product " +
                "INNER JOIN category " +
                "ON product.idCategory = category.idCategory " +
                "WHERE category.idCategory = "+idCategory;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Product> listIdProducto = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdProduct = cursor.getInt(0);
                listIdProducto.add(new Product(IdProduct));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listIdProducto;
    }
    public List<Product> listIdProductoOffers(int idOffers){
        String sql = "SELECT product.idProduct,product.idOffers FROM product " +
                "INNER JOIN offers " +
                "ON product.idOffers=offers.idOffers " +
                "WHERE offers.idOffers = "+idOffers;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Product> listIdProducto = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdProduct = cursor.getInt(0);
                int IdOffers = cursor.getInt(1);
                listIdProducto.add(new Product(IdProduct,IdOffers));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listIdProducto;
    }
    public List<com.example.mayikhstyle.Models.Address> listAddress(int idUser){
        String sql = "SELECT * FROM "+NameTableA+" WHERE "+IdUserA+" = " + idUser;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Address> listAddress = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdAddress = cursor.getInt(0);
                String nameA = cursor.getString(1);
                String address = cursor.getString(2);
                int IdUser = cursor.getInt(3);
                listAddress.add(new Address(IdAddress,nameA,address,IdUser));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listAddress;
    }
    public List<Payment> listPayment(int idUser){
        String sql = "SELECT * FROM "+NameTablePay+" WHERE "+IdUserP+" = " + idUser;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Payment> listPayment = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdPayment = cursor.getInt(0);
                int IdUser = cursor.getInt(1);
                String CardNumber = cursor.getString(2);
                int Month = cursor.getInt(3);
                int Year = cursor.getInt(4);
                int Cvv = cursor.getInt(5);

                listPayment.add(new Payment(IdPayment,IdUser,CardNumber,Month,Year,Cvv));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listPayment;
    }
    public List<DetalleOrd> listDetalleOrds(int idOrder){
        String sql = "SELECT * FROM "+NameTableDetalleOrd+" WHERE "+IdOrdDetalle+" = " + idOrder;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<DetalleOrd> listDetalleOrds = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdDetalleOrder = cursor.getInt(0);
                int IdProduct = cursor.getInt(1);
                int Price = cursor.getInt(2);
                int Amount = cursor.getInt(3);
                int IdOrder = cursor.getInt(4);

                listDetalleOrds.add(new DetalleOrd(IdDetalleOrder,IdProduct,Price,Amount,IdOrder));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listDetalleOrds;
    }
    public List<Order> listOrder(int idUser, int idState){
        String sql = "SELECT * FROM "+NameTableOrd+" WHERE "+IdUserOrd+" = " + idUser+" AND "+IdStateOrd+" = " + idState;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Order> listOrder = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdOrder = cursor.getInt(0);
                String FechaCompra = cursor.getString(1);
                int PriceTotal = cursor.getInt(2);
                String Address = cursor.getString(3);
                int AmountProduct = cursor.getInt(4);
                int IdUser = cursor.getInt(5);
                int IdState = cursor.getInt(6);

                listOrder.add(new Order(IdOrder,FechaCompra,PriceTotal,Address,AmountProduct,IdUser,IdState));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOrder;
    }
    public List<Order> listOrderAdmin(int idState){
        String sql = "SELECT * FROM "+NameTableOrd+" WHERE "+IdStateOrd+" = " + idState;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Order> listOrder = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdOrder = cursor.getInt(0);
                String FechaCompra = cursor.getString(1);
                int PriceTotal = cursor.getInt(2);
                String Address = cursor.getString(3);
                int AmountProduct = cursor.getInt(4);
                int IdUser = cursor.getInt(5);
                int IdState = cursor.getInt(6);

                listOrder.add(new Order(IdOrder,FechaCompra,PriceTotal,Address,AmountProduct,IdUser,IdState));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOrder;
    }
    public List<CancelOrder> listCancelOrder(){
        String sql = "SELECT * FROM orders " +
                "INNER JOIN state ON orders.idState= state.idState " +
                "INNER JOIN cancelOrder ON orders.idOrder=cancelOrder.idOrder " +
                "WHERE orders.idState = 3";
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<CancelOrder> listCancelOrder = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int idOrder = cursor.getInt(0);
                int PrecioTotal = cursor.getInt(2);
                int AmountProduct = cursor.getInt(4);
                int idUser = cursor.getInt(5);
                String NameState = cursor.getString(8);
                String DescriptionCancel = cursor.getString(10);

                listCancelOrder.add(new CancelOrder(DescriptionCancel,idOrder,PrecioTotal,AmountProduct,idUser,NameState));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listCancelOrder;
    }
    public List<Product> listCategoryProduct(int idCategory){
        String sql =
                "SELECT category.nameC,category.urlC,product.idProduct,product.nameP,product.description,product.price,product.urlP " +
                        "FROM " + NameTableC +
                        " INNER JOIN "+ NameTableP +
                        " ON category.idCategory = product.idCategory"+
                        " WHERE "+NameTableC+"."+IdC+" = "+ idCategory;

        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Product> listCategoryProduct = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String nameC = cursor.getString(0);
                String urlC = cursor.getString(1);
                int idProduct = cursor.getInt(2);
                String nameP = cursor.getString(3);
                String description = cursor.getString(4);
                int price = cursor.getInt(5);
                String urlP = cursor.getString(6);
                listCategoryProduct.add(new Product(nameC,urlC,idProduct,nameP,description,price,urlP));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listCategoryProduct;
    }
    public List<Product> listOffersProduct(int idOffers){
        String sql =
                "SELECT "+NameTableP+".*, "+NameTableO+"."+Discount+","+NameTableO+"."+DescriptionO+" "+
                        "FROM " + NameTableP+" " +
                        "INNER JOIN "+NameTableO+" " +
                        "ON "+NameTableP+"."+IdO+" = "+NameTableO+"."+IdO+" WHERE "+NameTableO+"."+IdO+" = "+ idOffers;

        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Product> listOfferroduct = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdProduct = cursor.getInt(0);
                String NameP = cursor.getString(1);
                String DescriptionP = cursor.getString(2);
                int PriceP = cursor.getInt(3);
                int IdCategory = cursor.getInt(4);
                String UrlP = cursor.getString(5);
                int Stock = cursor.getInt(6);
                int IdOffers = cursor.getInt(7);
                int Discount = cursor.getInt(8);
                String Description = cursor.getString(9);
                listOfferroduct.add(new Product(IdProduct,NameP,DescriptionP,PriceP,IdCategory,UrlP,Stock,IdOffers,Discount,Description));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOfferroduct;
    }
    public List<Category> listCategory(){
        String sql = "SELECT * FROM " + NameTableC+" ORDER BY RANDOM() ";
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Category> listCategory = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdCategory = cursor.getInt(0);
                String NameC = cursor.getString(1);
                String UrlC = cursor.getString(2);
                listCategory.add(new Category(IdCategory,NameC,UrlC));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listCategory;
    }
    public List<User> listUser(){
        String sql = "SELECT * FROM " + NameTableU;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<User> listUser = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String IdUser = cursor.getString(0);
                int Phone = cursor.getInt(1);
                String Email = cursor.getString(2);
                String NameU = cursor.getString(3);
                String UrlU = cursor.getString(4);
                int IdAddress = cursor.getInt(5);
                int IdPayment = cursor.getInt(6);
                listUser.add(new User(IdUser,Phone,Email,NameU,UrlU,IdAddress,IdPayment));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listUser;
    }
    public List<User> listUserId(int id){
        String sql = "SELECT * FROM " + NameTableU+" WHERE "+IdU+" = "+id;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<User> listUser = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String IdUser = cursor.getString(0);
                int Phone = cursor.getInt(1);
                String Email = cursor.getString(2);
                String NameU = cursor.getString(3);
                String UrlU = cursor.getString(4);
                int IdAddress = cursor.getInt(5);
                int IdPayment = cursor.getInt(6);
                listUser.add(new User(IdUser,Phone,Email,NameU,UrlU,IdAddress,IdPayment));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listUser;
    }
    public List<Carrito> listCarrito(int idUser){
        String sql = "SELECT * FROM " + NameTableCar+" WHERE "+IdUserCar+" = "+ idUser;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Carrito> listCarrito = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdCarrrito = cursor.getInt(0);
                int IdProducto = cursor.getInt(1);
                int Amount = cursor.getInt(2);
                int Price = cursor.getInt(3);
                String NamePCar = cursor.getString(4);
                String DescriptionPCar = cursor.getString(5);
                String UrlPCar = cursor.getString(6);
                listCarrito.add(new Carrito(IdCarrrito,IdProducto,Amount,Price,NamePCar,DescriptionPCar,UrlPCar));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listCarrito;
    }
    public List<DetalleOrd> listDetalelOrd(int idOrder){
        String sql = "SELECT detalleOrder.*,product.nameP,product.description,product.urlP " +
                "FROM detalleOrder INNER JOIN product " +
                "ON detalleOrder.idProducto = product.idProduct " +
                "WHERE idOrder = "+ idOrder;
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<DetalleOrd> listDetalelOrd = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdDetalle = cursor.getInt(0);
                int IdProducto = cursor.getInt(1);
                int Price = cursor.getInt(2);
                int Amount = cursor.getInt(3);
                int IdOrder = cursor.getInt(4);
                String NameP = cursor.getString(5);
                String DescriptionP = cursor.getString(6);
                String UrlP = cursor.getString(7);
                listDetalelOrd.add(new DetalleOrd(IdDetalle,IdProducto,Price,Amount,NameP,DescriptionP,UrlP));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listDetalelOrd;
    }
    public List<Offers> listOffers(){
        String sql = "SELECT * FROM "+ NameTableO+" WHERE "+IdO+" != 1 ORDER BY RANDOM() ";
        SQLiteDatabase DataBase = this.getReadableDatabase();
        List<Offers> listOffers = new ArrayList<>();
        Cursor cursor = DataBase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int IdOffers = cursor.getInt(0);
                int Discount = cursor.getInt(1);
                String DescriptionO = cursor.getString(2);
                listOffers.add(new Offers(IdOffers,Discount,DescriptionO));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listOffers;
    }*/

    public void newProduct(Product product){
        databaseReference.child("product").child(product.getIdProduct()).setValue(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Producto guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar Producto", task.getException());
            }
        });
    }
    public void newCategoy(Category category){
        databaseReference.child("category").child(category.getId()).setValue(category).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Categoria guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar Categoria", task.getException());
            }
        });
    }
    public void newState(State state){
        databaseReference.child("state").child(state.getIdState2()).setValue(state).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "state guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar state", task.getException());
            }
        });
    }

    public void newCancelOrd(CancelOrder cancelOrder){
        databaseReference.child("cancelOrder").child(cancelOrder.getIdCancelOrder2()).setValue(cancelOrder).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "CancelOrder guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar CancelOrder", task.getException());
            }
        });
    }

    public void newDireccion(Address address){
        databaseReference.child("address").child(address.getIdAddress2()).setValue(address).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Address guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar address", task.getException());
            }
        });
    }
    public void newTarget(Payment payment){
        databaseReference.child("payment").child(payment.getIdPayment2()).setValue(payment).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Payment guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar payment", task.getException());
            }
        });
    }
    public void newUser(User user){
        databaseReference.child("user").child(user.getIdUser()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Usuario guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar usuario", task.getException());
            }
        });
    }
    public void newOrder(Order order){
        databaseReference.child("orders").child(order.getIdOrder2()).setValue(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Order guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar Order", task.getException());
            }
        });
    }
    public void newDetalleOrder(String idProduct, double price, int amount, String idOrder) {
        DetalleOrd detalleOrd = new DetalleOrd(idProduct,price,amount,idOrder);
        String detalleOrderId = UUID.randomUUID().toString();
        databaseReference.child("detalleOrder").child(detalleOrderId).setValue(detalleOrd).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "DetalleOrder guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar DetalleOrder", task.getException());
            }
        });
    }
    public void newCarrito(Carrito carrito){
        databaseReference.child("carrito").child(carrito.getIdCarrito2()).setValue(carrito).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Carrito guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar carrito", task.getException());
            }
        });
    }
    public void newOffer(Offers offers){
        databaseReference.child("offers").child(offers.getIdOffers()).setValue(offers).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Oferta guardado con éxito");
            } else {
                Log.e("Firebase", "Error al guardar Oferta", task.getException());
            }
        });
    }
    /*public void updateUser(User user,int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PhoneU, user.getPhone());
        values.put(EmailU, user.getEmail());
        values.put(NameU, user.getNameU());
        values.put(UrlU, user.getUrlU());
        values.put(IdAddressU, user.getIdAddress());
        values.put(IdPaymentU, user.getIdPayment());
        DataBase.update(NameTableU, values,IdU+" = "+ id,null);
    }
    public void updateDireccion(Address address,int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameA, address.getNameA());
        values.put(Address, address.getAdrress());
        values.put(IdUserA, address.getIdUser());
        DataBase.update(NameTableA, values,IdA+" = "+ id,null);
    }
    public void updateCategory(Category category,int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameC, category.getCategory());
        values.put(UrlC, category.getUrl());
        DataBase.update(NameTableC, values,IdC+" = "+ id,null);
    }
    public void updateOffers(Offers offers,int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DescriptionO, offers.getDescriptionO());
        values.put(Discount, offers.getDiscount());
        DataBase.update(NameTableO, values,IdO+" = "+ id,null);
    }
    public void updateOrder(Order order,int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FechaCompra, order.getFechaCompra());
        values.put(PriceTotal, order.getPriceTotal());
        values.put(AddressOrd, order.getAddress());
        values.put(AmountOrd, order.getAmountProduct());
        values.put(IdUserOrd, order.getIdUser());
        values.put(IdStateOrd, order.getIdState());
        DataBase.update(NameTableOrd, values,IdOrd+" = "+ id,null);
    }
    public void updateStateOrder(int IdState,int id) {
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IdStateOrd, IdState);
        DataBase.update(NameTableOrd, values, IdOrd + " = " + id, null);
    }
    public  void updateProduct(Product product, int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameP, product.getNameP());
        values.put(DescriptionP, product.getDescription());
        values.put(PriceP, product.getPrice());
        values.put(IdCategoryP, product.getIdCategory());
        values.put(UrlP, product.getUrlP());
        values.put(Stock, product.getStock());
        values.put(IdOffersP, product.getIdOffers());
        DataBase.update(NameTableP, values, IdP+" = "+ id,null);
    }
    public void updateProductOffers(int IdOffers,int id) {
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IdOffersP, IdOffers);
        DataBase.update(NameTableP, values, IdP + " = " + id, null);
    }
    public void deleteProduct(int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTableP, IdP	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public void deleteCategory(int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTableC, IdC	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void deleteOffers(int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTableO, IdO	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void deleteDireccion(int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTableA, IdA	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void deleteTargeta(int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTablePay, IdPay	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void deleteItemCarrito(int id){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTableCar, IdCar	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void deleteCarrito(){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        DataBase.delete(NameTableCar, null,null);
    }*/
}
