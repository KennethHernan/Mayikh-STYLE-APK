package com.example.aplicacionlicoreria.Adapters.AdminAdapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicacionlicoreria.Adapters.ViewHolder;
import com.example.aplicacionlicoreria.BaseDeDatos.AdminSQLopenHelper;
import com.example.aplicacionlicoreria.Components.Administrador.AdminEditProduct;
import com.example.aplicacionlicoreria.Components.Administrador.AdminVenta;
import com.example.aplicacionlicoreria.Components.Administrador.AdminVentaCliente;
import com.example.aplicacionlicoreria.Models.Product;
import com.example.aplicacionlicoreria.Models.User;
import com.example.aplicacionlicoreria.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminUserAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final List<User> userList;

    public AdminUserAdapter(List<User> uList) {
        userList = uList;
    }

    @Override
    public void onBindViewHolder(com.example.aplicacionlicoreria.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.aplicacionlicoreria.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_button, parent, false);
        return new AdminUserAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (userList != null & userList.size() > 0) {
            return userList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<User> uList) {
        userList.addAll(uList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.aplicacionlicoreria.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnUser)
        Button BtnUser;

        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            User user = userList.get(position);
            DataBase = new AdminSQLopenHelper(itemView.getContext());

            BtnUser.setText(user.getNameU());

            //VER VENTA POR CLIENTE
            BtnUser.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AdminVentaCliente.class);
                intent.putExtra("IdUser",  user.getIdUser());
                itemView.getContext().startActivity(intent);
            });
            DataBase.close();
        }
    }
}
