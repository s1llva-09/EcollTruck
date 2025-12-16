package com.example.gestaodeprodutos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Caminhao;

import java.util.List;

public class CaminhaoAdapter extends RecyclerView.Adapter<CaminhaoAdapter.ViewHolder> {

    private List<Caminhao> lista;
    private CaminhaoListener listener;

    public interface CaminhaoListener {
        void onCaminhaoClick(Caminhao c);
    }

    public CaminhaoAdapter(List<Caminhao> lista, CaminhaoListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public void atualizarLista(List<Caminhao> novaLista) {
        this.lista = novaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_caminhao, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caminhao c = lista.get(position);
        holder.txtCaminhaoId.setText("Caminhão #" + c.getId());
        holder.txtCaminhaoPlaca.setText(c.getPlaca());
        holder.txtStatus.setText(c.getStatus());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCaminhaoClick(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCaminhaoId, txtCaminhaoPlaca, txtStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCaminhaoId = itemView.findViewById(R.id.txt_caminhao_id);
            txtCaminhaoPlaca = itemView.findViewById(R.id.txt_caminhao_placa);
            txtStatus = itemView.findViewById(R.id.txt_status);
        }
    }
}
