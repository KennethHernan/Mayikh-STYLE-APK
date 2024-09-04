package com.example.aplicacionlicoreria.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Models.User;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<User> userList;

    public UserAdapter(List<User> uList) {
        userList = uList;
    }


    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_product, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addItems(List<User> uList) {
        userList.addAll(uList);
        notifyDataSetChanged();
    }
    public class ViewHolder extends com.example.aplicacionlicoreria.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ProductCardView)
        CardView ProductCardView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameProduct)
        TextView NamePTextView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.descriptionProduct)
        TextView DescriptionPEditText;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.priceProduct)
        TextView PricePEditText;

        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

          public void onBind(int position) {
//            super.onBind(position);
//
//            Product product = producotList.get(position);
//
//            DataBase = new AdminSQLopenHelper(itemView.getContext());
//            if (product.getUrl() != null) {
//                Glide.with(itemView.getContext())
//                        .load(product.getUrl())
//                        .override(280, 280)
//                        .into(ProductImageView);
//            }
//            NamePTextView.setText(product.getName());
//            DescriptionPEditText.setText(String.valueOf(product.getDescription()));
//            PricePEditText.setText(String.valueOf(product.getPrice()));
//
//            DataBase.close();
            //AL HACER CLICK AL PRODUCCION - REDIRECCION A RESCRIPCION DE PRODUCT
//            ProductCardView.setOnClickListener(v -> {
//                Intent intent=new Intent(itemView.getContext(), DetalleProduct.class);
//                intent.putExtra("id",  product.getId());
//                itemView.getContext().startActivity(intent);
//            });
        }
    }
}
