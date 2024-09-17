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
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.Models.Product;
import com.example.mayikhstyle.Models.ProductDetails;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<ProductDetails> productList;

    public ProductAdapter(List<ProductDetails> pList) {
        productList = pList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (productList != null & productList.size() > 0) {
            return productList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<ProductDetails> pList) {
        productList.addAll(pList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
            ProductDetails productDetails = productList.get(position);
            Product product = productDetails.getProduct();
            Offers offers = productDetails.getOffer();

            if (product != null) {
                if (productDetails.getProduct().getUrlP() != null) {
                    Glide.with(itemView.getContext())
                            .load(productDetails.getProduct().getUrlP())
                            .override(280, 280)
                            .into(ProductImageView);
                }
                NamePTextView.setText(productDetails.getProduct().getNameP());
                DescriptionPEditText.setText(productDetails.getProduct().getDescription());

                if (offers != null && productDetails.getOffer().getDiscount() > 0) {
                    double discountAmount = productDetails.getProduct().getPrice() * (productDetails.getOffer().getDiscount() / 100.0);
                    int discountedPrice = (int) (productDetails.getProduct().getPrice() - discountAmount);

                    SpannableString spannableString = new SpannableString("S/." + productDetails.getProduct().getPrice());
                    spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    PricePEditText.setText(spannableString);
                    TotalDiscount.setText("S/." + discountedPrice);
                    ContentDiscount.setVisibility(View.VISIBLE);
                } else {
                    ContentDiscount.setVisibility(View.GONE);
                    PricePEditText.setText("S/." + productDetails.getProduct().getPrice());
                }
            } else {
                // Manejo si `product` es null
                ProductImageView.setImageDrawable(null);
                NamePTextView.setText("null");
                DescriptionPEditText.setText("null");
                PricePEditText.setText("0");
                TotalDiscount.setText("0");
                ContentDiscount.setVisibility(View.GONE);
            }

            //METODO ONCLICK
            ProductCardView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetalleP.class);
                intent.putExtra("idProduct",  productDetails.getProduct().getIdProduct());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
