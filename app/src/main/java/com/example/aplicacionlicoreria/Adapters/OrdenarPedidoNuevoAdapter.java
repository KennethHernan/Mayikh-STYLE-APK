package com.example.aplicacionlicoreria.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.aplicacionlicoreria.Components.OrdenarPedido;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.DetalleOrd;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenarPedidoNuevoAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<DetalleOrd> detalleOrdList;

    public OrdenarPedidoNuevoAdapter(List<DetalleOrd> cList) {
        detalleOrdList = cList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_carrito, parent, false);
        return new OrdenarPedidoNuevoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (detalleOrdList != null & detalleOrdList.size() > 0) {
            return detalleOrdList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<DetalleOrd> cList) {
        detalleOrdList.addAll(cList);
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

            DetalleOrd detalleOrd = detalleOrdList.get(position);

            //OCULTAR
            Eliminar.setVisibility(View.GONE);

            DataBase = new AdminSQLopenHelper(itemView.getContext());
            if (detalleOrd.getUrlP() != null) {
                Glide.with(itemView.getContext())
                        .load(detalleOrd.getUrlP())
                        .override(280, 280)
                        .into(ProductImageView);
            }

            NamePTextView.setText(detalleOrd.getNameP());
            DescriptionPEditText.setText(detalleOrd.getDescriptionP());

            CantidadEditText.setText(String.valueOf(detalleOrd.getAmount()));

            PricePEditText.setText("S/. " + detalleOrd.getPrice());

            TextCantidad.setText("Cantidad: ");
        }
    }
}