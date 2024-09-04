package com.example.aplicacionlicoreria.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.CarritoUser;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.DetalleOrd;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetallePedidoAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<DetalleOrd> detalleList;

    public DetallePedidoAdapter(List<DetalleOrd> dList) {
        detalleList = dList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_detalle_pedido_activo, parent, false);
        return new DetallePedidoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (detalleList != null & detalleList.size() > 0) {
            return detalleList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<DetalleOrd> dList) {
        detalleList.addAll(dList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.aplicacionlicoreria.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.imageViewCar)
        ImageView ProductImageView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameProduct)
        TextView NamePTextView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.descriptionProduct)
        TextView DescriptionPEditText;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cantidad)
        TextView CantidadEditText;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.price)
        TextView PricePEditText;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.TextCantidad)
        TextView TextCantidad;
        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            DetalleOrd detalleOrd = detalleList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());
            SQLiteDatabase dataBase = DataBase.getWritableDatabase();

            Cursor cursor = dataBase.rawQuery
                    ("SELECT * FROM product WHERE idProduct = "+ detalleOrd.getIdProduct(), null);

            // RECORRER EL CURSOR PARA OBTENER EL NOMBRE DEL ESTADO
            if (cursor != null) {
                cursor.moveToFirst();
                @SuppressLint("Range")
                String NameProduct = cursor.getString(cursor.getColumnIndex("nameP"));
                @SuppressLint("Range")
                String DescriptionP = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range")
                String UrlP = cursor.getString(cursor.getColumnIndex("urlP"));

                if (UrlP != null) {
                    Glide.with(itemView.getContext())
                            .load(UrlP)
                            .override(280, 280)
                            .into(ProductImageView);
                }

                NamePTextView.setText(NameProduct);
                DescriptionPEditText.setText(DescriptionP);
                CantidadEditText.setText(String.valueOf(detalleOrd.getAmount()));
                PricePEditText.setText("S/. " + detalleOrd.getPrice());
                TextCantidad.setText("Cantidad: ");
            }
        }
    }
}