package com.example.mayikhstyle.Adapters.AdminAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.Adapters.ViewHolder;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.Administrador.AdminEditProduct;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminProductoAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Product> producotList;

    public AdminProductoAdapter(List<Product> pList) {
        producotList = pList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_admin_product, parent, false);
        return new AdminProductoAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (producotList != null & producotList.size() > 0) {
            return producotList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Product> pList) {
        producotList.addAll(pList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.imageProduct)
        ImageView ProductImageView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameProduct)
        TextView NamePTextView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.contentAdminProduct)
        LinearLayout ContentProduct;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnEditProducts)
        ImageButton btnEditarProduct;
        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            Product product = producotList.get(position);
            DataBase = new AdminSQLopenHelper(itemView.getContext());

            if (product.getUrlP() != null) {
                Glide.with(itemView.getContext())
                        .load(product.getUrlP())
                        .into(ProductImageView);
            }

            NamePTextView.setText(product.getNameP());

            // EDITAR PRODUCT
            ContentProduct.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AdminEditProduct.class);
                intent.putExtra("idProduct",  product.getIdProduct());
                intent.putExtra("idCategory",  product.getIdCategory());
                itemView.getContext().startActivity(intent);
            });
            btnEditarProduct.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AdminEditProduct.class);
                intent.putExtra("idProduct",  product.getIdProduct());
                intent.putExtra("idCategory",  product.getIdCategory());
                itemView.getContext().startActivity(intent);
            });
            DataBase.close();
        }
    }
}
