package com.example.aplicacionlicoreria.Adapters.AdminAdapter;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionlicoreria.Adapters.ViewHolder;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.Order;
import com.example.aplicacionlicoreria.Models.User;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminPedidoCompleteAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Order> orderList;

    public AdminPedidoCompleteAdapter(List<Order> oList) {
        orderList = oList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pedido_complete, parent, false);
        return new AdminPedidoCompleteAdapter.ViewHolder(view);
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
        @BindView(R.id.btn_Pedir)
        Button PedirNuevo;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.LayoutButton)
        LinearLayout ContentButton;

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

                TitlePedido.setText("Pedido N° "+oders.getIdOrder());
                CantidaProduct.setText(oders.getAmountProduct()+" Productos");

                TitlePrecioTotal.setText("Precio Total:");

                PrecioTotal.setText("S/." + oders.getPriceTotal());

                Statebtn.setText(NameState);
            }
            ContentButton.setVisibility(View.GONE);
        }
    }
}
