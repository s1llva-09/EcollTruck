package com.example.gestaodeprodutos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.model.Cliente;
import com.example.gestaodeprodutos.model.Motorista;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_caminhao, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caminhao caminhao = lista.get(position);

        holder.txtCaminhaoId.setText("Caminhão #" + caminhao.getId());
        holder.txtCaminhaoPlaca.setText(caminhao.getPlaca());
        holder.txtStatus.setText(caminhao.getStatus());

        // LÓGICA FINAL PARA EXIBIR O NOME DO MOTORISTA
        Cliente cliente = caminhao.getCliente();
        if (cliente != null && cliente.getMotoristas() != null && !cliente.getMotoristas().isEmpty()) {
            Motorista primeiroMotorista = cliente.getMotoristas().get(0);
            holder.txtMotoristaNome.setText(primeiroMotorista.getNomeCompleto());
        } else {
            // Se não encontrar, exibe o nome do cliente ou um texto padrão
            if (cliente != null && cliente.getNomeCompleto() != null) {
                holder.txtMotoristaNome.setText(cliente.getNomeCompleto());
            } else {
                holder.txtMotoristaNome.setText("Sem motorista/cliente");
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCaminhaoClick(caminhao);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCaminhaoId, txtCaminhaoPlaca, txtStatus, txtMotoristaNome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCaminhaoId = itemView.findViewById(R.id.txt_caminhao_id);
            txtCaminhaoPlaca = itemView.findViewById(R.id.txt_caminhao_placa);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtMotoristaNome = itemView.findViewById(R.id.txt_motorista_nome);
        }
    }
}
