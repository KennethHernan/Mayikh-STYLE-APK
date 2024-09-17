package com.example.mayikhstyle.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.MetodoPago;
import com.example.mayikhstyle.Models.Payment;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MetodoPagoAdapter  extends RecyclerView.Adapter<ViewHolder>{
    private final List<Payment> paymentList;

    public MetodoPagoAdapter(List<Payment> pList) {
        paymentList = pList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }
    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_metodopay, parent, false);
        return new MetodoPagoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (paymentList != null & paymentList.size() > 0) {
            return paymentList.size();

        } else {
            return 0;

        }
    }

    public void addItems(List<Payment> pList) {
        paymentList.addAll(pList);
        notifyDataSetChanged();
    }
    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameTarget)
        TextView CardNumberTextView;
        @BindView(R.id.text_status)
        TextView TextStatus;
        @BindView(R.id.btnEliminar)
        ImageButton btnEliminar;

        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            Payment payment = paymentList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());

            CardNumberTextView.setText(payment.getCardNumber());
            TextStatus.setText("Conectado ");

           //ELIMINAR CON ID
            btnEliminar.setOnClickListener(v -> {
                //DataBase.deleteTargeta(payment.getIdPayment());

                Intent intent = new Intent(itemView.getContext(), MetodoPago.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
