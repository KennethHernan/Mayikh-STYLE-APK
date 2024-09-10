package com.example.mayikhstyle.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mayikhstyle.BaseDeDatos.AdminSQLopenHelper;
import com.example.mayikhstyle.Components.CategoryProduct;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryImgHorizontalAdapter extends RecyclerView.Adapter<ViewHolder>{
    private final List<Category> categoryList;

    public CategoryImgHorizontalAdapter(List<Category> cList) {
        categoryList = cList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }
    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.content_category, parent, false);
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
    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameCategory)
        TextView nameCategory;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.imageViewC)
        ImageView imageViewC;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.CategoryImg)
        CardView CategoryCardView;

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

            if (category.getUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(category.getUrl())
                        .into(imageViewC);
            }

            nameCategory.setText(category.getCategory());

            //REDIRECCION CON ID
            CategoryCardView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CategoryProduct.class);
                intent.putExtra("idCategory",  category.getId());
                itemView.getContext().startActivity(intent);
            });

        }
    }
}
