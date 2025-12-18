package com.example.gestaodeprodutos.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.PontoColeta;

import java.util.List;

public class CadastroPontoColetaAdapter extends RecyclerView.Adapter<CadastroPontoColetaAdapter.ViewHolder> {

    private List<PontoColeta> pontosColeta;

    public CadastroPontoColetaAdapter(List<PontoColeta> pontosColeta) {
        this.pontosColeta = pontosColeta;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cadastro_ponto_coleta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Aqui você pode adicionar lógica se precisar preencher os campos com dados existentes
    }

    @Override
    public int getItemCount() {
        return pontosColeta.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText pontoNome;
        EditText pontoLixeiras;
        EditText pontoKg;

        ViewHolder(View view) {
            super(view);
            pontoNome = view.findViewById(R.id.ponto_nome);
            pontoLixeiras = view.findViewById(R.id.ponto_lixeiras);
            pontoKg = view.findViewById(R.id.ponto_kg);
        }
    }
}
