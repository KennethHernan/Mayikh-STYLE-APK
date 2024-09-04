package com.example.aplicacionlicoreria.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.CancelarOrders;
import com.example.aplicacionlicoreria.Components.CarritoUser;
import com.example.aplicacionlicoreria.Components.DetallePedidoActivo;
import com.example.aplicacionlicoreria.Models.Carrito;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PedidoAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Order> orderList;

    public PedidoAdapter(List<Order> oList) {
        orderList = oList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pedido_activos, parent, false);
        return new PedidoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (orderList != null & orderList.size() > 0) {
            return orderList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Order> oList) {
        orderList.addAll(oList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.aplicacionlicoreria.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.titlePedido)
        TextView TitlePedido;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.cantidadProductos)
        TextView CantidaProduct;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.precioTotal)
        TextView TitlePrecioTotal;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.priceProduct)
        TextView PrecioTotal;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnState)
        Button Statebtn;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnCancelarOrder)
        Button CancelarOrder;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.CardView)
        CardView ContentPedido;
        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            Order oders = orderList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());
            SQLiteDatabase dataBase = DataBase.getWritableDatabase();

            Cursor cursor = dataBase.rawQuery
                    ("SELECT nameS FROM state WHERE idState = "+ oders.getIdState(), null);

            // RECORRER EL CURSOR PARA OBTENER EL NOMBRE DEL ESTADO
            if (cursor != null) {
                cursor.moveToFirst();
                @SuppressLint("Range")
                String NameState = cursor.getString(cursor.getColumnIndex("nameS"));

                TitlePedido.setText("Código de Pedido N° "+oders.getIdOrder());
                CantidaProduct.setText(oders.getAmountProduct()+" Productos");

                TitlePrecioTotal.setText("Precio Total:");

                PrecioTotal.setText("S/." + oders.getPriceTotal());

                Statebtn.setText(NameState);
            }

            //METODOS ONCLIK
            ContentPedido.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetallePedidoActivo.class);
                intent.putExtra("idOrders",oders.getIdOrder());
                itemView.getContext().startActivity(intent);
            });

            CancelarOrder.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CancelarOrders.class);
                intent.putExtra("idOrders",oders.getIdOrder());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
