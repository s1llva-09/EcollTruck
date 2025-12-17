package com.example.gestaodeprodutos.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.PontoColeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PontoColetaAdapter extends RecyclerView.Adapter<PontoColetaAdapter.PontoColetaViewHolder> {

    private List<PontoColeta> pontosColeta; // The original full list
    private List<PontoColeta> pontosColetaFiltrados; // The list to be displayed
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onConcluirButtonClick(PontoColeta pontoColeta);
    }

    public PontoColetaAdapter(List<PontoColeta> pontosColeta, OnItemClickListener listener) {
        this.listener = listener;
        setPontosColeta(pontosColeta);
    }

    @NonNull
    @Override
    public PontoColetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ponto_coleta, parent, false);
        return new PontoColetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PontoColetaViewHolder holder, int position) {
        PontoColeta ponto = pontosColetaFiltrados.get(position);
        holder.bind(ponto, listener);
    }

    @Override
    public int getItemCount() {
        return pontosColetaFiltrados != null ? pontosColetaFiltrados.size() : 0;
    }

    public void setPontosColeta(List<PontoColeta> pontos) {
        if (pontos != null) {
            this.pontosColeta = new ArrayList<>(pontos);
            this.pontosColetaFiltrados = new ArrayList<>(pontos);
        } else {
            this.pontosColeta = new ArrayList<>();
            this.pontosColetaFiltrados = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void filter(String status) {
        if (pontosColeta == null) {
            if (pontosColetaFiltrados != null) {
                pontosColetaFiltrados.clear();
            }
            notifyDataSetChanged();
            return;
        }

        pontosColetaFiltrados.clear();
        if (status.equalsIgnoreCase("todos")) {
            pontosColetaFiltrados.addAll(pontosColeta);
        } else {
            List<PontoColeta> collect = pontosColeta.stream()
                    .filter(p -> p.getStatus() != null && p.getStatus().name().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
            pontosColetaFiltrados.addAll(collect);
        }
        notifyDataSetChanged();
    }


    static class PontoColetaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView statusIcon;
        private final TextView pontoNome;
        private final TextView pontoDetalhes;
        private final TextView pontoHorario;
        private final Button concluirButton;
        private final View itemContainer;

        public PontoColetaViewHolder(@NonNull View itemView) {
            super(itemView);
            statusIcon = itemView.findViewById(R.id.status_icon);
            pontoNome = itemView.findViewById(R.id.ponto_nome);
            pontoDetalhes = itemView.findViewById(R.id.ponto_detalhes);
            pontoHorario = itemView.findViewById(R.id.ponto_horario);
            concluirButton = itemView.findViewById(R.id.concluir_button);
            itemContainer = itemView.findViewById(R.id.item_container);
        }

        public void bind(final PontoColeta ponto, final OnItemClickListener listener) {
            pontoNome.setText(ponto.getNome());
            pontoDetalhes.setText(String.format("%d lixeiras • ~%dkg estimado", ponto.getLixeiras(), ponto.getPesoEstimadoKg()));
            pontoHorario.setText(ponto.getHorario());

            if (ponto.getStatus() != null) {
                switch (ponto.getStatus()) {
                    case CONCLUIDO:
                        statusIcon.setImageResource(R.drawable.ic_map_point_completed);
                        concluirButton.setVisibility(View.GONE);
                        itemContainer.setBackgroundResource(R.drawable.rounded_corners);
                        break;
                    case ATIVO:
                        statusIcon.setImageResource(R.drawable.ic_navigation);
                        concluirButton.setVisibility(View.VISIBLE);
                        itemContainer.setBackgroundResource(R.drawable.rounded_corners_green);
                        break;
                    case PENDENTE:
                        statusIcon.setImageResource(R.drawable.ic_map_point_pending);
                        concluirButton.setVisibility(View.GONE);
                        itemContainer.setBackgroundResource(R.drawable.rounded_corners);
                        break;
                }
            }

            concluirButton.setOnClickListener(v -> {
                if(listener != null){
                    listener.onConcluirButtonClick(ponto);
                }
            });
        }
    }
}
