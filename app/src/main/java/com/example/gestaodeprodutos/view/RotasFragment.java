package com.example.gestaodeprodutos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.adapter.RotaAdapter;
import com.example.gestaodeprodutos.model.PontoParada;
import com.example.gestaodeprodutos.model.Rota;

import java.util.ArrayList;
import java.util.List;

public class RotasFragment extends Fragment {

    private RecyclerView recyclerView;
    private RotaAdapter rotaAdapter;
    private List<Rota> listaRotas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rotas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtVoltar = view.findViewById(R.id.txtVoltar);
        txtVoltar.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        Button btnNovaRota = view.findViewById(R.id.btn_nova_rota);
        btnNovaRota.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CadastroRotaActivity.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recycler_rotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dados de exemplo
        if (listaRotas.isEmpty()) {
            List<PontoParada> paradas1 = new ArrayList<>();
            paradas1.add(new PontoParada("Zona Sul, Rua A", 4, "08:15", PontoParada.Status.COMPLETED));
            paradas1.add(new PontoParada("Zona Sul, Rua B", 3, "08:45", PontoParada.Status.COMPLETED));
            paradas1.add(new PontoParada("Av. Principal, 456", 5, "09:15", PontoParada.Status.IN_PROGRESS));
            paradas1.add(new PontoParada("Parque Municipal", 7, "09:45", PontoParada.Status.PENDING));
            paradas1.add(new PontoParada("Zona Norte", 4, "10:15", PontoParada.Status.PENDING));
            listaRotas.add(new Rota("2302", "Maria Santos", "Rota R002", 40, paradas1, "1"));

            List<PontoParada> paradas2 = new ArrayList<>();
            paradas2.add(new PontoParada("Rua das Acácias, 100", 3, "08:00", PontoParada.Status.COMPLETED));
            paradas2.add(new PontoParada("Av. Central, 250", 5, "08:30", PontoParada.Status.COMPLETED));
            paradas2.add(new PontoParada("Rua das Flores, 123", 4, "09:00", PontoParada.Status.IN_PROGRESS));
            paradas2.add(new PontoParada("Bairro Industrial, 500", 6, "09:30", PontoParada.Status.PENDING));
            paradas2.add(new PontoParada("Centro, Praça Principal", 10, "10:00", PontoParada.Status.PENDING));
            listaRotas.add(new Rota("2301", "João Silva", "Rota R001", 65, paradas2, "2"));
        }

        rotaAdapter = new RotaAdapter(listaRotas, getContext());
        recyclerView.setAdapter(rotaAdapter);
    }
}
