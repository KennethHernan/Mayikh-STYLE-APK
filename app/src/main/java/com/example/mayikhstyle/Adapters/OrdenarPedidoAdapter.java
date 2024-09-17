package com.example.mayikhstyle.Adapters;

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
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.OrdenarPedido;
import com.example.mayikhstyle.Models.Carrito;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdenarPedidoAdapter  extends RecyclerView.Adapter<ViewHolder>{

    private final List<Carrito> carritoList;

    public OrdenarPedidoAdapter(List<Carrito> cList) {
        carritoList = cList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_carrito, parent, false);
        return new OrdenarPedidoAdapter.ViewHolder(view);
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

    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {

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
            DescriptionPEditText.setText(carrito.getDescriptionCar());

            CantidadEditText.setText(String.valueOf(carrito.getAmount()));

            PricePEditText.setText("S/. " + carrito.getPrice());

            TextCantidad.setText("Cantidad: ");

            //AL HACER CLICK AL UN PRODUCTO - REDIRECCION A DESCRIPTIONP
            Eliminar.setOnClickListener(v -> {
                //DataBase.deleteItemCarrito(carrito.getIdCarrito());

                Intent intent = new Intent(itemView.getContext(), OrdenarPedido.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}