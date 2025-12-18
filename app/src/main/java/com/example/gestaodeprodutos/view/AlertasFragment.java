package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.adapter.AlertaAdapter;
import com.example.gestaodeprodutos.model.Alerta;

import java.util.ArrayList;
import java.util.List;

public class AlertasFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlertaAdapter alertaAdapter;
    private List<Alerta> listaAlertas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alertas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_alertas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dados de exemplo
        listaAlertas.add(new Alerta("Nível Crítico de Combustível", "Combustível abaixo de 15%. Reabastecimento urgente necessário.", "Crítico", "Caminhão #2305  •  12 min atrás"));
        listaAlertas.add(new Alerta("Manutenção Preventiva Agendada", "Revisão de 10.000 km programada para amanhã às 08:00.", "Aviso", "Caminhão #2308  •  1 hora atrás"));
        listaAlertas.add(new Alerta("Atraso na Rota", "Rota com atraso de 25 minutos devido ao tráfego intenso.", "Aviso", "Caminhão #2302  •  2 horas atrás"));
        listaAlertas.add(new Alerta("Nova Área de Coleta Adicionada", "Setor Industrial Oeste foi incluído nas rotas da próxima semana.", "Informativo", "3 horas atrás"));
        listaAlertas.add(new Alerta("Rota Concluída com Sucesso", "Todos os pontos foram coletados dentro do prazo previsto.", "Resolvido", "Caminhão #2301  •  5 horas atrás"));


        alertaAdapter = new AlertaAdapter(listaAlertas, getContext());
        recyclerView.setAdapter(alertaAdapter);
    }
}
