package com.example.aplicacionlicoreria.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.aplicacionlicoreria.Adapters.CarritoAdapter;
import com.example.aplicacionlicoreria.Models.Address;
import com.example.aplicacionlicoreria.Models.CancelOrder;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.Category;
import com.example.aplicacionlicoreria.Models.DetalleOrd;
import com.example.aplicacionlicoreria.Models.Offers;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.Models.Payment;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.Models.State;
import com.example.aplicacionlicoreria.Models.User;

import java.util.ArrayList;
import java.util.List;


public class AdminSQLopenHelper extends SQLiteOpenHelper{

    private static final String NameBD = "administracion";

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

    public AdminSQLopenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NameBD, null, 1);
    }

    public AdminSQLopenHelper(@Nullable Context context) {
        super(context, NameBD, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase DataBase) {
        String Table_Users = "CREATE TABLE " + NameTableU +
                "(" + IdU + " INTEGER PRIMARY KEY," + PhoneU+ " INTEGER," + EmailU+ " TEXT," + NameU+ " TEXT,"+ UrlU+ " TEXT," + IdAddressU+ " INTEGER," + IdPaymentU+ " INTEGER);";
        String Table_Products = "CREATE TABLE " + NameTableP +
                "(" + IdP + " INTEGER PRIMARY KEY," + NameP+ " TEXT," + DescriptionP+ " TEXT," + PriceP+ " INTEGER," + IdCategoryP+ " INTEGER," + UrlP+ " TEXT," + Stock+ " INTEGER," + IdOffersP+ " INTEGER);";
        String Table_Category = "CREATE TABLE " + NameTableC +
                "(" + IdC + " INTEGER PRIMARY KEY," + NameC+ " TEXT," + UrlC +" TEXT);";
        String Table_Address = "CREATE TABLE " + NameTableA +
                "(" + IdA + " INTEGER PRIMARY KEY," + NameA+ " TEXT,"+ Address +" TEXT,"+ IdUserA+ " INTEGER);";
        String Table_Payment = "CREATE TABLE " + NameTablePay +
                "(" + IdPay + " INTEGER PRIMARY KEY," + IdUserP + " INTEGER,"+ CardNumber+ " TEXT," + Month +" INTEGER,"+ Year+ " INTEGER," + Cvv +" INTEGER);";
        String Table_Offers = "CREATE TABLE " + NameTableO +
                "(" + IdO + " INTEGER PRIMARY KEY," + Discount+ " INTEGER," + DescriptionO +" TEXT);";
        String Table_Carrito = "CREATE TABLE " + NameTableCar +
                "(" + IdCar + " INTEGER PRIMARY KEY," + IdProductoCar + " INTEGER,"+ Amount+ " INTEGER," + PriceCar +" INTEGER,"+ NamePCar+ " TEXT," + DescriptionCar+ " TEXT,"+ UrlCar+ " TEXT,"+ IdUserCar+ " INTEGER);";
        String Table_DetalleOrder = "CREATE TABLE " + NameTableDetalleOrd +
                "(" + IdDetalleOrd + " INTEGER PRIMARY KEY," + IdProductoDetalleOrd + " INTEGER,"+ PriceDetalleOrd+ " INTEGER," + AmountDetalle +" INTEGER,"+IdOrdDetalle+ " INTEGER);";
        String Table_Order = "CREATE TABLE " + NameTableOrd +
                "(" + IdOrd + " INTEGER PRIMARY KEY," + FechaCompra + " TEXT,"+ PriceTotal+ " INTEGER,"+ AddressOrd+ " TEXT," + AmountOrd +" INTEGER,"+IdUserOrd+ " INTEGER,"+IdStateOrd+ " INTEGER);";
        String Table_State = "CREATE TABLE " + NameTableEst +
                "(" + IdEst + " INTEGER PRIMARY KEY," + NameS + " TEXT);";
        String Table_CancelOrd = "CREATE TABLE " + NameTableCancel +
                "(" + IdCancel + " INTEGER PRIMARY KEY," +DescriptionCancel+ " TEXT," +IdOrderCancel+ " INTEGER);";

        DataBase.execSQL(Table_Users);
        DataBase.execSQL(Table_Products);
        DataBase.execSQL(Table_Category);
        DataBase.execSQL(Table_Address);
        DataBase.execSQL(Table_Payment);
        DataBase.execSQL(Table_Offers);
        DataBase.execSQL(Table_Carrito);
        DataBase.execSQL(Table_DetalleOrder);
        DataBase.execSQL(Table_Order);
        DataBase.execSQL(Table_State);
        DataBase.execSQL(Table_CancelOrd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DataBase, int oldVersion, int newVersion) {
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableU);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableP);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableC);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableA);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTablePay);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableO);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableCar);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableDetalleOrd);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableOrd);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableEst);
        DataBase.execSQL("DROP TABLE IF EXISTS " + NameTableCancel);
        onCreate(DataBase);
    }

    public List<Product> listProduct(){
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
    public List<Address> listAddress(int idUser){
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
                int IdUser = cursor.getInt(0);
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
                int IdUser = cursor.getInt(0);
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
    }

    public void newProduct(Product product){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameP, product.getNameP());
        values.put(DescriptionP, product.getDescription());
        values.put(PriceP, product.getPrice());
        values.put(IdCategoryP, product.getIdCategory());
        values.put(UrlP, product.getUrlP());
        values.put(Stock, product.getStock());
        values.put(IdOffersP, product.getIdOffers());
        DataBase.insert(NameTableP, null, values);
    }
    public void newCategoy(Category category){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameC, category.getCategory());
        values.put(UrlC, category.getUrl());
        DataBase.insert(NameTableC, null, values);
    }
    public void newState(State state){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameS, state.getNameS());
        DataBase.insert(NameTableEst, null, values);
    }

    public void newCancelOrd(CancelOrder cancelOrder){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DescriptionCancel, cancelOrder.getDescriptionCancel());
        values.put(IdOrderCancel, cancelOrder.getIdOrder());
        DataBase.insert(NameTableCancel, null, values);
    }

    public void newDireccion(Address address){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NameA, address.getNameA());
        values.put(Address, address.getAdrress());
        values.put(IdUserA, address.getIdUser());
        DataBase.insert(NameTableA, null, values);
    }
    public void newTarget(Payment payment){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IdUserP, payment.getIdUser());
        values.put(CardNumber, payment.getCardNumber());
        values.put(Month, payment.getMonth());
        values.put(Year, payment.getYear());
        values.put(Cvv, payment.getCvv());
        DataBase.insert(NameTablePay, null, values);
    }
    public void newUser(User user){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PhoneU, user.getPhone());
        values.put(EmailU, user.getEmail());
        values.put(NameU, user.getNameU());
        values.put(UrlU, user.getUrlU());
        values.put(IdAddressU, user.getIdAddress());
        values.put(IdPaymentU, user.getIdPayment());
        DataBase.insert(NameTableU, null, values);
    }
    public void newOrder(Order order){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FechaCompra, order.getFechaCompra());
        values.put(PriceTotal, order.getPriceTotal());
        values.put(AddressOrd, order.getAddress());
        values.put(AmountOrd, order.getAmountProduct());
        values.put(IdUserOrd, order.getIdUser());
        values.put(IdStateOrd, order.getIdState());
        DataBase.insert(NameTableOrd, null, values);
    }
    public void newDetalleOrder(int idProduct, double price, int amount, int idOrder) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IdProductoDetalleOrd, idProduct);
        values.put(PriceDetalleOrd, price);
        values.put(AmountDetalle, amount);
        values.put(IdOrdDetalle, idOrder);
        database.insert(NameTableDetalleOrd, null, values);
    }
    public void newCarrito(Carrito carrito){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IdProductoCar, carrito.getIdProducto());
        values.put(Amount, carrito.getAmount());
        values.put(PriceCar, carrito.getPrice());
        values.put(NamePCar, carrito.getNameP());
        values.put(DescriptionCar, carrito.getDescription());
        values.put(UrlCar, carrito.getUrlP());
        values.put(IdUserCar, carrito.getIdUser());
        DataBase.insert(NameTableCar, null, values);
    }

    public void newUserIdAddress(User user){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IdAddressU, user.getIdAddress());
        DataBase.insert(NameTableU, null, values);
    }
    public void newOffer(Offers offers){
        SQLiteDatabase DataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Discount, offers.getDiscount());
        values.put(DescriptionO, offers.getDescriptionO());
        DataBase.insert(NameTableO, null, values);
    }
    public void updateUser(User user,int id){
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
    }
}
