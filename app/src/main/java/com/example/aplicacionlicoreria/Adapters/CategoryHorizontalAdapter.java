package com.example.aplicacionlicoreria.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.CategoryProduct;
import com.example.aplicacionlicoreria.Models.Category;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryHorizontalAdapter extends RecyclerView.Adapter<ViewHolder>{
    private final List<Category> categoryList;

    public CategoryHorizontalAdapter(List<Category> cList) {
        categoryList = cList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }
    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_category_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (categoryList != null & categoryList.size() > 0) {
            return categoryList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Category> cList) {
        categoryList.addAll(cList);
        notifyDataSetChanged();
    }
    public class ViewHolder extends com.example.aplicacionlicoreria.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnCategory_Home)
        Button BtnCategory;
        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            Category category = categoryList.get(position);
            DataBase = new AdminSQLopenHelper(itemView.getContext());

            BtnCategory.setText(category.getCategory());

            //REDIRECCION CON ID
            BtnCategory.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CategoryProduct.class);
                intent.putExtra("idCategory",  category.getId());
                itemView.getContext().startActivity(intent);
            });

        }
    }
}
