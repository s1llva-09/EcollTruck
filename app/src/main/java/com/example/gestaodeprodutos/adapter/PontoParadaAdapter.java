package com.example.gestaodeprodutos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.PontoParada;

import java.util.List;

public class PontoParadaAdapter extends RecyclerView.Adapter<PontoParadaAdapter.PontoParadaViewHolder> {

    private final List<PontoParada> pontosParada;
    private final Context context;

    public PontoParadaAdapter(List<PontoParada> pontosParada, Context context) {
        this.pontosParada = pontosParada;
        this.context = context;
    }

    @NonNull
    @Override
    public PontoParadaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ponto_parada, parent, false);
        return new PontoParadaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PontoParadaViewHolder holder, int position) {
        PontoParada ponto = pontosParada.get(position);

        holder.txtLocal.setText(ponto.getLocal());
        holder.txtLixeiras.setText(String.format("%d lixeiras", ponto.getLixeiras()));
        holder.txtHorario.setText(ponto.getHorario());

        switch (ponto.getStatus()) {
            case COMPLETED:
                holder.statusIcon.setImageResource(R.drawable.ic_status_completed);
                holder.timelineLine.setBackgroundColor(ContextCompat.getColor(context, R.color.green_accent));
                break;
            case IN_PROGRESS:
                holder.statusIcon.setImageResource(R.drawable.ic_status_in_progress);
                holder.timelineLine.setBackgroundColor(ContextCompat.getColor(context, R.color.green_accent));
                break;
            case PENDING:
                holder.statusIcon.setImageResource(R.drawable.ic_status_pending);
                holder.timelineLine.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return pontosParada.size();
    }

    static class PontoParadaViewHolder extends RecyclerView.ViewHolder {
        ImageView statusIcon;
        TextView txtLocal, txtLixeiras, txtHorario;
        View timelineLine;

        public PontoParadaViewHolder(@NonNull View itemView) {
            super(itemView);
            statusIcon = itemView.findViewById(R.id.status_icon);
            txtLocal = itemView.findViewById(R.id.txt_local);
            txtLixeiras = itemView.findViewById(R.id.txt_lixeiras);
            txtHorario = itemView.findViewById(R.id.txt_horario);
            timelineLine = itemView.findViewById(R.id.timeline_line);
        }
    }
}
