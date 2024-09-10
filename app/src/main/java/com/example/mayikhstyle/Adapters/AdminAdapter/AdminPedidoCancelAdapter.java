package com.example.mayikhstyle.Adapters.AdminAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayikhstyle.Adapters.ViewHolder;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Models.CancelOrder;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminPedidoCancelAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<CancelOrder> orderList;

    public AdminPedidoCancelAdapter(List<CancelOrder> oList) {
        orderList = oList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pedido_cancel, parent, false);
        return new AdminPedidoCancelAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (orderList != null & orderList.size() > 0) {
            return orderList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<CancelOrder> oList) {
        orderList.addAll(oList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {

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
        @BindView(R.id.txtDescriptionCancel)
        TextView DescriptionC;
        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            CancelOrder orders = orderList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());

            TitlePedido.setText("Código de Pedido N° "+orders.getIdOrder());
            CantidaProduct.setText(orders.getAmountProduct()+" Productos");
            PrecioTotal.setText("S/." + orders.getPrecioTotal());
            Statebtn.setText(orders.getNameState());
            DescriptionC.setText(orders.getDescriptionCancel());
        }
    }
}
