package com.example.aplicacionlicoreria.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.CarritoUser;
import com.example.aplicacionlicoreria.Components.CategoryProduct;
import com.example.aplicacionlicoreria.Components.DetalleP;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarritoAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Carrito> carritoList;

    public CarritoAdapter(List<Carrito> cList) {
        carritoList = cList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_carrito, parent, false);
        return new CarritoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (carritoList != null & carritoList.size() > 0) {
            return carritoList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Carrito> cList) {
        carritoList.addAll(cList);
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
        @BindView(R.id.eliminar)
        ImageButton Eliminar;

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

            Carrito carrito = carritoList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());
            if (carrito.getUrlP() != null) {
                Glide.with(itemView.getContext())
                        .load(carrito.getUrlP())
                        .override(280, 280)
                        .into(ProductImageView);
            }

            NamePTextView.setText(carrito.getNameP());
            DescriptionPEditText.setText(carrito.getDescription());

            CantidadEditText.setText(String.valueOf(carrito.getAmount()));

            PricePEditText.setText("S/. " + carrito.getPrice());

            TextCantidad.setText("Cantidad: ");

            //ELIMINAR ITEM PRODUCTO POR ID
            Eliminar.setOnClickListener(v -> {
                DataBase.deleteItemCarrito(carrito.getIdCarrito());

                Intent intent = new Intent(itemView.getContext(), CarritoUser.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}