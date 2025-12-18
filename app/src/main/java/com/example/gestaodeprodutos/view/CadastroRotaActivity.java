package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.model.Cliente;
import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.model.PontoColeta;
import com.example.gestaodeprodutos.model.PontoParada;
import com.example.gestaodeprodutos.model.Rota;
import com.example.gestaodeprodutos.view.adapter.CadastroPontoColetaAdapter;
import com.example.gestaodeprodutos.viewmodel.CaminhaoViewModel;
import com.example.gestaodeprodutos.viewmodel.RotaViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CadastroRotaActivity extends AppCompatActivity {

    private CaminhaoViewModel caminhaoViewModel;
    private RotaViewModel rotaViewModel;
    private AutoCompleteTextView actCaminhao;
    private TextInputEditText edtNomeRota;
    private ArrayAdapter<Caminhao> caminhaoAdapter;
    private RecyclerView recyclerViewPontosColeta;
    private CadastroPontoColetaAdapter pontoColetaAdapter;
    private List<PontoColeta> pontosColetaList;
    private List<Caminhao> caminhaoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_cadastro_rota);

        ImageButton btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        Button btnAdicionarPonto = findViewById(R.id.btnAdicionarPonto);
        Button btnSalvarRota = findViewById(R.id.btnSalvarRota);
        actCaminhao = findViewById(R.id.actCaminhao);
        edtNomeRota = findViewById(R.id.edtNomeRota);
        recyclerViewPontosColeta = findViewById(R.id.recyclerViewPontosColeta);

        caminhaoViewModel = new ViewModelProvider(this).get(CaminhaoViewModel.class);
        rotaViewModel = new ViewModelProvider(this).get(RotaViewModel.class);
        caminhaoList = new ArrayList<>();

        caminhaoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        actCaminhao.setAdapter(caminhaoAdapter);

        setupRecyclerView();

        btnAdicionarPonto.setOnClickListener(v -> {
            adicionarNovoPonto();
        });

        btnSalvarRota.setOnClickListener(v -> {
            salvarRota();
        });

        observeViewModels();

        String token = getSharedPreferences("APP", MODE_PRIVATE).getString("TOKEN", null);
        if (token != null) {
            caminhaoViewModel.fetchCaminhoes(token);
        }
    }

    private void setupRecyclerView() {
        pontosColetaList = new ArrayList<>();
        pontoColetaAdapter = new CadastroPontoColetaAdapter(pontosColetaList);
        recyclerViewPontosColeta.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPontosColeta.setAdapter(pontoColetaAdapter);
    }

    private void adicionarNovoPonto() {
        pontosColetaList.add(new PontoColeta("", "", "", 0, 0, PontoColeta.Status.PENDENTE));
        pontoColetaAdapter.notifyItemInserted(pontosColetaList.size() - 1);
    }

    private void salvarRota() {
        String nomeRota = edtNomeRota.getText().toString();
        String caminhaoPlaca = actCaminhao.getText().toString();

        if (nomeRota.isEmpty() || caminhaoPlaca.isEmpty() || pontosColetaList.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos e adicione ao menos um ponto de coleta", Toast.LENGTH_SHORT).show();
            return;
        }

        Caminhao caminhaoSelecionado = null;
        for (Caminhao caminhao : caminhaoList) {
            if (caminhao.getPlaca().equals(caminhaoPlaca)) {
                caminhaoSelecionado = caminhao;
                break;
            }
        }

        if (caminhaoSelecionado == null) {
            Toast.makeText(this, "Caminhão não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        List<PontoParada> pontosParada = pontosColetaList.stream()
                .map(ponto -> new PontoParada(ponto.getNome(), ponto.getLixeiras(), ponto.getHorario(), PontoParada.Status.PENDING))
                .collect(Collectors.toList());

        String motoristaNome = "";
        Cliente cliente = caminhaoSelecionado.getCliente();
        if (cliente != null && cliente.getMotoristas() != null && !cliente.getMotoristas().isEmpty()) {
            motoristaNome = cliente.getMotoristas().get(0).getNomeCompleto();
        }

        String clienteId = String.valueOf(caminhaoSelecionado.getIdCliente());

        Rota rota = new Rota(String.valueOf(caminhaoSelecionado.getId()), motoristaNome, nomeRota, 0, pontosParada, clienteId);

        String token = getSharedPreferences("APP", MODE_PRIVATE).getString("TOKEN", null);
        if (token != null) {
            rotaViewModel.criarRota(rota, token);
        }
    }


    private void observeViewModels() {
        caminhaoViewModel.getCaminhoes().observe(this, caminhoes -> {
            if (caminhoes != null) {
                caminhaoList.clear();
                caminhaoList.addAll(caminhoes);
                caminhaoAdapter.clear();
                caminhaoAdapter.addAll(caminhoes);
                caminhaoAdapter.notifyDataSetChanged();
            }
        });

        caminhaoViewModel.getGenericError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        rotaViewModel.getSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Rota salva com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        rotaViewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
