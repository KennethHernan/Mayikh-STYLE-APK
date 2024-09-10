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
import com.example.mayikhstyle.Components.Direccion;
import com.example.mayikhstyle.Components.DireccionEdit;
import com.example.mayikhstyle.Models.Address;
import com.example.mayikhstyle.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DireccionAdapter extends RecyclerView.Adapter<ViewHolder>{
    private final List<Address> direccionList;

    public DireccionAdapter(List<Address> dList) {
        direccionList = dList;
    }

    @Override
    public void onBindViewHolder(com.example.mayikhstyle.Adapters.ViewHolder holder, int position) {
        holder.onBind(position);
    }
    @NonNull
    @Override
    public com.example.mayikhstyle.Adapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_direccion, parent, false);
        return new DireccionAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (direccionList != null & direccionList.size() > 0) {
            return direccionList.size();

        } else {
            return 0;

        }
    }

    public void addItems(List<Address> dList) {
        direccionList.addAll(dList);
        notifyDataSetChanged();
    }
    public class ViewHolder extends com.example.mayikhstyle.Adapters.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.nameDireccion)
        TextView NameDTextView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.direccion)
        TextView DireccionTextView;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.EditDireccion)
        ImageButton EditDireccion;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.btnEliminar)
        ImageButton EliminarDireccion;

        AdminSQLopenHelper DataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {}

        public void onBind(int position) {
            super.onBind(position);

            Address address = direccionList.get(position);

            DataBase = new AdminSQLopenHelper(itemView.getContext());

            if (address.getNameA() != null) {
                NameDTextView.setText(address.getNameA());
                DireccionTextView.setText(address.getAdrress());
            }

            //EDITAR CON ID
            EditDireccion.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DireccionEdit.class);
                intent.putExtra("idAddress", address.getIdAddress());
                itemView.getContext().startActivity(intent);
            });

           //ELIMINAR CON ID
            EliminarDireccion.setOnClickListener(v -> {
                DataBase.deleteDireccion(address.getIdAddress());

                Intent intent = new Intent(itemView.getContext(), Direccion.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}