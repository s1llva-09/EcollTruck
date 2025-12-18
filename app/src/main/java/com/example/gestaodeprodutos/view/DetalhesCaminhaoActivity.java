package com.example.gestaodeprodutos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.viewmodel.CaminhaoViewModel;
import com.example.gestaodeprodutos.viewmodel.MotoristaViewModel;

import java.util.ArrayList;

public class DetalhesCaminhaoActivity extends AppCompatActivity {

    private EditText edtPlaca, edtCapacidade;
    private AutoCompleteTextView actStatus, actMotorista;
    private CaminhaoViewModel caminhaoViewModel;
    private MotoristaViewModel motoristaViewModel;
    private Caminhao caminhaoAtual;
    private Integer clienteSelecionadoId;
    private ArrayAdapter<Motorista> motoristaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_cadastro_caminhao);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        ImageButton btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        edtPlaca = findViewById(R.id.edtPlaca);
        actStatus = findViewById(R.id.actStatus);
        edtCapacidade = findViewById(R.id.edtCapacidade);
        actMotorista = findViewById(R.id.actMotorista);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnExcluir = findViewById(R.id.btnExcluir);

        String[] statusOptions = getResources().getStringArray(R.array.status_options);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statusOptions);
        actStatus.setAdapter(statusAdapter);

        caminhaoViewModel = new ViewModelProvider(this).get(CaminhaoViewModel.class);
        motoristaViewModel = new ViewModelProvider(this).get(MotoristaViewModel.class);

        motoristaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        actMotorista.setAdapter(motoristaAdapter);

        caminhaoAtual = (Caminhao) getIntent().getSerializableExtra("caminhao");

        if (caminhaoAtual != null) {
            preencherCampos();
            btnExcluir.setVisibility(View.VISIBLE);
            btnExcluir.setOnClickListener(v -> excluirCaminhao());
        } else {
            btnExcluir.setVisibility(View.GONE);
        }

        observeViewModels();
        setupMotoristaSearch();

        if (btnSalvar != null) {
            btnSalvar.setOnClickListener(v -> salvarCaminhao());
        }
    }

    private void preencherCampos() {
        edtPlaca.setText(caminhaoAtual.getPlaca());
        actStatus.setText(caminhaoAtual.getStatus(), false);
        edtCapacidade.setText(String.valueOf(caminhaoAtual.getCapacidade()));
        if (caminhaoAtual.getCliente() != null && caminhaoAtual.getCliente().getMotoristas() != null && !caminhaoAtual.getCliente().getMotoristas().isEmpty()) {
            Motorista motorista = caminhaoAtual.getCliente().getMotoristas().get(0);
            if (motorista != null) {
                actMotorista.setText(motorista.toString(), false);
                clienteSelecionadoId = motorista.getIdCliente();
            }
        }
    }

    private void salvarCaminhao() {
        String placa = edtPlaca.getText().toString();
        String status = actStatus.getText().toString();
        String capacidadeStr = edtCapacidade.getText().toString();

        if (placa.isEmpty() || status.isEmpty() || capacidadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (clienteSelecionadoId == null && caminhaoAtual != null) {
            clienteSelecionadoId = caminhaoAtual.getIdCliente();
        }

        if (clienteSelecionadoId == null) {
            Toast.makeText(this, "Selecione um motorista para associar a um cliente.", Toast.LENGTH_SHORT).show();
            return;
        }

        Double capacidade = Double.parseDouble(capacidadeStr);

        Caminhao caminhaoParaSalvar = new Caminhao();
        if (caminhaoAtual != null) {
            caminhaoParaSalvar.setId(caminhaoAtual.getId());
        }

        caminhaoParaSalvar.setPlaca(placa);
        caminhaoParaSalvar.setStatus(status);
        caminhaoParaSalvar.setCapacidade(capacidade);
        caminhaoParaSalvar.setIdCliente(clienteSelecionadoId);

        String token = getSharedPreferences("APP", MODE_PRIVATE).getString("TOKEN", null);
        if (token == null) {
            Toast.makeText(this, "Erro de autenticação. Faça login novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        caminhaoViewModel.saveCaminhao(token, caminhaoParaSalvar);
    }

    private void excluirCaminhao() {
        if (caminhaoAtual == null) {
            return;
        }

        String token = getSharedPreferences("APP", MODE_PRIVATE).getString("TOKEN", null);
        if (token == null) {
            Toast.makeText(this, "Erro de autenticação. Faça login novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        caminhaoViewModel.deleteCaminhao(token, caminhaoAtual.getId());
    }


    private void observeViewModels() {
        caminhaoViewModel.getSaveSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Caminhão salvo!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        caminhaoViewModel.getSaveError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        caminhaoViewModel.getDeleteSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Caminhão excluído!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        caminhaoViewModel.getDeleteError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        motoristaViewModel.getMotoristas().observe(this, motoristas -> {
            if (motoristas != null) {
                motoristaAdapter.clear();
                motoristaAdapter.addAll(motoristas);
                motoristaAdapter.notifyDataSetChanged();
            }
        });

        actMotorista.setOnItemClickListener((parent, view, position, id) -> {
            Motorista motorista = (Motorista) parent.getItemAtPosition(position);
            this.clienteSelecionadoId = motorista.getIdCliente();
        });

        motoristaViewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupMotoristaSearch() {
        actMotorista.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) { // Start searching after 2 characters
                    String token = getSharedPreferences("APP", MODE_PRIVATE).getString("TOKEN", null);
                    if (token != null) {
                        motoristaViewModel.searchMotoristas(token, s.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
