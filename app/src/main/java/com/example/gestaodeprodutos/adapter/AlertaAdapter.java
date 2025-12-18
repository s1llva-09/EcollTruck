package com.example.gestaodeprodutos.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Alerta;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AlertaAdapter extends RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder> {

    private final List<Alerta> alertas;
    private final Context context;

    public AlertaAdapter(List<Alerta> alertas, Context context) {
        this.alertas = alertas;
        this.context = context;
    }

    @NonNull
    @Override
    public AlertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alerta, parent, false);
        return new AlertaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertaViewHolder holder, int position) {
        Alerta alerta = alertas.get(position);
        holder.tvTitulo.setText(alerta.getTitulo());
        holder.tvDescricao.setText(alerta.getDescricao());
        holder.tvInfo.setText(alerta.getInfo());
        holder.tvTag.setText(alerta.getTipo());

        int colorRes;
        int iconRes;

        switch (alerta.getTipo()) {
            case "Crítico":
                colorRes = R.color.red;
                iconRes = R.drawable.ic_warning;
                break;
            case "Aviso":
                colorRes = R.color.yellow_pause;
                iconRes = R.drawable.ic_warning;
                break;
            case "Informativo":
                colorRes = R.color.white;
                iconRes = R.drawable.ic_document;
                break;
            case "Resolvido":
                colorRes = R.color.green_accent;
                iconRes = R.drawable.ic_check_circle;
                break;
            default:
                colorRes = R.color.light_gray;
                iconRes = R.drawable.ic_warning;
                break;
        }

        int color = ContextCompat.getColor(context, colorRes);
        holder.cardView.setStrokeColor(color);
        holder.tvTag.setTextColor(color);
        holder.ivIcon.setImageResource(iconRes);
        holder.ivIcon.setColorFilter(color);
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    static class AlertaViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView ivIcon;
        TextView tvTitulo, tvTag, tvDescricao, tvInfo;

        public AlertaViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            ivIcon = itemView.findViewById(R.id.iv_alerta_icon);
            tvTitulo = itemView.findViewById(R.id.tv_alerta_titulo);
            tvTag = itemView.findViewById(R.id.tv_alerta_tag);
            tvDescricao = itemView.findViewById(R.id.tv_alerta_descricao);
            tvInfo = itemView.findViewById(R.id.tv_alerta_info);
        }
    }
}
