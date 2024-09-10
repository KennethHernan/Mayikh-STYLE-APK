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
import com.example.mayikhstyle.Components.Administrador.AdminEditCategory;
import com.example.mayikhstyle.Components.Administrador.AdminProduct;
import com.example.mayikhstyle.Models.Category;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminCategoryAdapter  extends RecyclerView.Adapter<ViewHolder>{
    private final List<Category> categoryList;

    public AdminCategoryAdapter(List<Category> cList) {
        categoryList = cList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }
    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.conten_admin_categoria, parent, false);
        return new AdminCategoryAdapter.ViewHolder(view);
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
        @BindView(R.id.imageCategory)
        ImageView CategoryImage;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ContentCategory)
        LinearLayout ContentCategory;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameCategory)
        TextView NameC;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnEditCategory)
        ImageButton BtnEditCategory;
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
                        .into(CategoryImage);
            }

            NameC.setText(category.getCategory());

            //REDIRECCION CON ID
            ContentCategory.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AdminProduct.class);
                intent.putExtra("idCategory",  category.getId());
                itemView.getContext().startActivity(intent);
            });

            //EDITAR CATEGORY
            BtnEditCategory.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AdminEditCategory.class);
                intent.putExtra("idCategory",  category.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}

