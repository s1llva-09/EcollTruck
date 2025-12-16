package com.example.gestaodeprodutos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Rota;

import java.util.List;

public class RotaAdapter extends RecyclerView.Adapter<RotaAdapter.RotaViewHolder> {

    private final List<Rota> rotas;
    private final Context context;

    public RotaAdapter(List<Rota> rotas, Context context) {
        this.rotas = rotas;
        this.context = context;
    }

    @NonNull
    @Override
    public RotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rota, parent, false);
        return new RotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RotaViewHolder holder, int position) {
        Rota rota = rotas.get(position);

        holder.txtCaminhaoId.setText(String.format("Caminhão #%s", rota.getCaminhaoId()));
        holder.txtMotoristaNome.setText(rota.getMotoristaNome());
        holder.txtRotaNome.setText(rota.getRotaNome());
        holder.txtProgressoPercentual.setText(String.format("%d%%", rota.getProgresso()));
        holder.progressBarRota.setProgress(rota.getProgresso());

        // Configurar o RecyclerView interno para os pontos de parada
        PontoParadaAdapter pontoParadaAdapter = new PontoParadaAdapter(rota.getPontosParada(), context);
        holder.recyclerPontosParada.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerPontosParada.setAdapter(pontoParadaAdapter);
    }

    @Override
    public int getItemCount() {
        return rotas.size();
    }

    static class RotaViewHolder extends RecyclerView.ViewHolder {
        TextView txtCaminhaoId, txtMotoristaNome, txtRotaNome, txtProgressoPercentual;
        ProgressBar progressBarRota;
        RecyclerView recyclerPontosParada;

        public RotaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCaminhaoId = itemView.findViewById(R.id.txt_caminhao_id);
            txtMotoristaNome = itemView.findViewById(R.id.txt_motorista_nome);
            txtRotaNome = itemView.findViewById(R.id.txt_rota_nome);
            txtProgressoPercentual = itemView.findViewById(R.id.txt_progresso_percentual);
            progressBarRota = itemView.findViewById(R.id.progress_bar_rota);
            recyclerPontosParada = itemView.findViewById(R.id.recycler_pontos_parada);
        }
    }
}
