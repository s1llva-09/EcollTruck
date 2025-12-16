package com.example.gestaodeprodutos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.adapter.CaminhaoAdapter;
import com.example.gestaodeprodutos.model.Caminhao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.gestaodeprodutos.view.CadastroCaminhaoActivity;

import java.util.ArrayList;
import java.util.List;

public class CaminhoesFragment extends Fragment implements CaminhaoAdapter.CaminhaoListener {

    private RecyclerView recyclerView;
    private CaminhaoAdapter caminhaoAdapter;
    private List<Caminhao> listaCaminhoes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_caminhoes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_caminhoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabAdicionarCaminhao = view.findViewById(R.id.fab_adicionar_caminhao);
        fabAdicionarCaminhao.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CadastroCaminhaoActivity.class);
            startActivity(intent);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 10 && fabAdicionarCaminhao.isShown()) {
                    fabAdicionarCaminhao.hide();
                } else if (dy < -10 && !fabAdicionarCaminhao.isShown()) {
                    fabAdicionarCaminhao.show();
                }
            }
        });

        // Dados de exemplo
        Caminhao c1 = new Caminhao("Mercedes-Benz Actros", "BRA2E19", "Em Rota", 65000.0, 2021);
        c1.setId(1);
        Caminhao c2 = new Caminhao("Volvo FH 540", "BRA3E20", "Disponível", 70000.0, 2022);
        c2.setId(2);
        Caminhao c3 = new Caminhao("Scania R450", "BRA4E21", "Em Manutenção", 68000.0, 2020);
        c3.setId(3);

        listaCaminhoes.add(c1);
        listaCaminhoes.add(c2);
        listaCaminhoes.add(c3);

        caminhaoAdapter = new CaminhaoAdapter(listaCaminhoes, this);
        recyclerView.setAdapter(caminhaoAdapter);
    }

    @Override
    public void onCaminhaoClick(Caminhao c) {
        // Exemplo: Abrir a tela de detalhes quando um item for clicado
        Toast.makeText(getContext(), "Caminhão clicado: " + c.getModelo(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MedidorCargaActivity.class);
        intent.putExtra("caminhao", c);
        startActivity(intent);
    }
}
