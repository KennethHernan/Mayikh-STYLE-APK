package com.example.mayikhstyle.Adapters;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.DetalleP;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<Product> producotList;

    public ProductAdapter(List<Product> pList) {
        producotList = pList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_product, parent, false);
        return new ViewHolder(view);
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
        @BindView(R.id.imageViewP)
        ImageView ProductImageView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.CardView)
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
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.discount)
        TextView TotalDiscount;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ContentDiscount)
        LinearLayout ContentDiscount;

        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        @SuppressLint("SetTextI18n")
        public void onBind(int position) {
            super.onBind(position);

            Product product = producotList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());
            if (product.getUrlP() != null) {
                Glide.with(itemView.getContext())
                        .load(product.getUrlP())
                        .override(280, 280)
                        .into(ProductImageView);
            }
            NamePTextView.setText(product.getNameP());
            DescriptionPEditText.setText(product.getDescription());

            //APLICAR ETIQUETA DE DESCUENTO POR PRODUCTO
/*            if (product.getDiscount() > 0) {
                //APLICAR DESCUENTO DE PRODUCTO
                Double Descuento = product.getPrice() * (((double) product.getDiscount() / 100));
                int DescuentoTotal = (int) (product.getPrice() - Descuento);
                int PrecioTotal = (product.getPrice() - DescuentoTotal);

                //TACHAR PRECIO ORIGINAL
                SpannableString spannableString = new SpannableString("S/."+product.getPrice());
                spannableString.setSpan(new StrikethroughSpan(), 0, ("S/."+product.getPrice()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                PricePEditText.setText(spannableString);
                TotalDiscount.setText("S/."+PrecioTotal);
                //Habilitar Layout
                ContentDiscount.setVisibility(View.VISIBLE);
            }else {
                ContentDiscount.setVisibility(View.GONE);
                PricePEditText.setText("S/." + product.getPrice());
            }*/

            //METODO ONCLICK
            ProductCardView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetalleP.class);
                intent.putExtra("id",  product.getIdProduct());
                itemView.getContext().startActivity(intent);
            });
            DataBase.close();
        }
    }
}
