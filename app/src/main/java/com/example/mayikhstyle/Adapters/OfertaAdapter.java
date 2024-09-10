package com.example.mayikhstyle.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.OfertaProduct;
import com.example.mayikhstyle.Models.Offers;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfertaAdapter  extends RecyclerView.Adapter<ViewHolder>{

    private final List<Offers> offersList;

    public OfertaAdapter(List<Offers> oList) {
        offersList = oList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_oferta, parent, false);
        return new OfertaAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (offersList != null & offersList.size() > 0) {
            return offersList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Offers> oList) {
        offersList.addAll(oList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {


        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.CardOffers)
        CardView CardViewOffert;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.txt_Discount)
        TextView Discount;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.txt_DescriptionO)
        TextView DescriptionO;
        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            Offers offers = offersList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());

            String DiscountS = String.valueOf(offers.getDiscount());
            Discount.setText(DiscountS+"%");

            DescriptionO.setText(offers.getDescriptionO());

            //PRODUCTO POR OFERTA
            CardViewOffert.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), OfertaProduct.class);
                intent.putExtra("IdOffers",offers.getIdOffers());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
