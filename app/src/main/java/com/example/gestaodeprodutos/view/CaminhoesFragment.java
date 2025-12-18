package com.example.gestaodeprodutos.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.adapter.CaminhaoAdapter;
import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.viewmodel.CaminhoesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CaminhoesFragment extends Fragment implements CaminhaoAdapter.CaminhaoListener {

    private RecyclerView recyclerView;
    private CaminhaoAdapter caminhaoAdapter;
    private List<Caminhao> listaCaminhoes = new ArrayList<>();
    private FloatingActionButton fabAdd, fabAddCaminhao, fabAddMotorista;
    private CaminhoesViewModel caminhoesViewModel;
    private boolean isFabMenuOpen = false;

    // Este launcher agora serve tanto para criar quanto para editar.
    // Ao voltar com RESULT_OK, ele sempre chamará loadCaminhoes().
    private final ActivityResultLauncher<Intent> cadastroCaminhaoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    loadCaminhoes();
                }
            });

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

        fabAdd = view.findViewById(R.id.fab_add);
        fabAddCaminhao = view.findViewById(R.id.fab_add_caminhao);
        fabAddMotorista = view.findViewById(R.id.fab_add_motorista);

        caminhaoAdapter = new CaminhaoAdapter(listaCaminhoes, this);
        recyclerView.setAdapter(caminhaoAdapter);

        caminhoesViewModel = new ViewModelProvider(this).get(CaminhoesViewModel.class);

        setupFabMenu();
        observeViewModel();
        loadCaminhoes();
    }

    private void setupFabMenu() {
        fabAdd.setOnClickListener(v -> {
            if (isFabMenuOpen) {
                closeFabMenu();
            } else {
                openFabMenu();
            }
        });

        fabAddCaminhao.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CadastroCaminhaoActivity.class);
            cadastroCaminhaoLauncher.launch(intent);
            closeFabMenu();
        });

        fabAddMotorista.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CadastroMotoristaActivity.class);
            startActivity(intent);
            closeFabMenu();
        });
    }

    private void openFabMenu() {
        isFabMenuOpen = true;
        fabAdd.animate().rotation(45f);
        fabAddCaminhao.animate().translationY(-getResources().getDimension(R.dimen.fab_margin_1)).alpha(1f).withStartAction(() -> fabAddCaminhao.setVisibility(View.VISIBLE));
        fabAddMotorista.animate().translationY(-getResources().getDimension(R.dimen.fab_margin_2)).alpha(1f).withStartAction(() -> fabAddMotorista.setVisibility(View.VISIBLE));
    }

    private void closeFabMenu() {
        isFabMenuOpen = false;
        fabAdd.animate().rotation(0f);
        fabAddCaminhao.animate().translationY(0).alpha(0f).withEndAction(() -> fabAddCaminhao.setVisibility(View.INVISIBLE));
        fabAddMotorista.animate().translationY(0).alpha(0f).withEndAction(() -> fabAddMotorista.setVisibility(View.INVISIBLE));
    }

    private void observeViewModel() {
        caminhoesViewModel.getCaminhoes().observe(getViewLifecycleOwner(), caminhoes -> {
            if (caminhoes != null) {
                this.listaCaminhoes.clear();
                this.listaCaminhoes.addAll(caminhoes);
                caminhaoAdapter.notifyDataSetChanged();
            }
        });

        caminhoesViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCaminhoes() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        String token = prefs.getString("TOKEN", null);

        if (token != null) {
            caminhoesViewModel.fetchCaminhoes(token);
        } else {
            Toast.makeText(getContext(), "Erro de autenticação", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Este método é chamado quando um caminhão na lista é clicado.
     * Ele agora usa o launcher para abrir a tela de detalhes, garantindo
     * que a lista será atualizada quando a tela de detalhes for fechada.
     */
    @Override
    public void onCaminhaoClick(Caminhao c) {
        Intent intent = new Intent(getContext(), DetalhesCaminhaoActivity.class);
        intent.putExtra("caminhao", c);
        cadastroCaminhaoLauncher.launch(intent);
    }
}
