package com.example.gestaodeprodutos.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.viewmodel.CaminhaoViewModel;
import com.example.gestaodeprodutos.viewmodel.MotoristaViewModel;

public class CadastroCaminhaoActivity extends AppCompatActivity {

    private EditText edtPlaca, edtCapacidade;
    private AutoCompleteTextView actStatus, actMotorista;
    private Button btnSalvar;
    private ImageButton btnVoltar;
    private CaminhaoViewModel caminhaoViewModel;
    private MotoristaViewModel motoristaViewModel;
    private Caminhao caminhao;
    private Integer clienteSelecionadoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_cadastro_caminhao);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        caminhaoViewModel = new ViewModelProvider(this).get(CaminhaoViewModel.class);
        motoristaViewModel = new ViewModelProvider(this).get(MotoristaViewModel.class);

        edtPlaca = findViewById(R.id.edtPlaca);
        actStatus = findViewById(R.id.actStatus);
        edtCapacidade = findViewById(R.id.edtCapacidade);
        actMotorista = findViewById(R.id.actMotorista);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btn_voltar);

        String[] statusOptions = getResources().getStringArray(R.array.status_options);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statusOptions);
        actStatus.setAdapter(statusAdapter);

        caminhao = (Caminhao) getIntent().getSerializableExtra("caminhao");

        if (caminhao != null) {
            preencherCampos();
        }

        btnSalvar.setOnClickListener(v -> salvarCaminhao());
        btnVoltar.setOnClickListener(v -> finish());

        observeCaminhaoViewModel();
        observeMotoristaViewModel();
        loadMotoristas();
    }

    private void preencherCampos() {
        edtPlaca.setText(caminhao.getPlaca());
        actStatus.setText(caminhao.getStatus(), false);
        edtCapacidade.setText(String.valueOf(caminhao.getCapacidade()));
    }

    private void salvarCaminhao() {
        if (TextUtils.isEmpty(edtPlaca.getText().toString().trim())) {
            edtPlaca.setError("Campo obrigatório");
            return;
        }
        if (TextUtils.isEmpty(actStatus.getText().toString().trim())) {
            Toast.makeText(this, "Selecione um status", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edtCapacidade.getText().toString().trim())) {
            edtCapacidade.setError("Campo obrigatório");
            return;
        }
        if (clienteSelecionadoId == null) {
            Toast.makeText(this, "Selecione um motorista para associar a um cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (caminhao == null) {
            caminhao = new Caminhao();
        }

        caminhao.setPlaca(edtPlaca.getText().toString().trim());
        caminhao.setStatus(actStatus.getText().toString().trim());
        caminhao.setCapacidade(Double.parseDouble(edtCapacidade.getText().toString().trim()));
        caminhao.setIdCliente(clienteSelecionadoId);

        SharedPreferences prefs = getSharedPreferences("APP", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", null);
        caminhaoViewModel.saveCaminhao(token, caminhao);
    }

    private void observeCaminhaoViewModel() {
        caminhaoViewModel.getSaveSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Caminhão salvo com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        caminhaoViewModel.getSaveError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeMotoristaViewModel() {
        motoristaViewModel.getMotoristas().observe(this, motoristas -> {
            if (motoristas != null) {
                ArrayAdapter<Motorista> motoristaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, motoristas);
                actMotorista.setAdapter(motoristaAdapter);

                if (caminhao != null && caminhao.getIdCliente() != null) {
                    for (Motorista m : motoristas) {
                        if (m.getIdCliente().equals(caminhao.getIdCliente())) {
                            actMotorista.setText(m.toString(), false);
                            clienteSelecionadoId = m.getIdCliente();
                            break;
                        }
                    }
                }
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

    private void loadMotoristas() {
        SharedPreferences prefs = getSharedPreferences("APP", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", null);
        if (token != null) {
            motoristaViewModel.fetchMotoristas(token);
        }
    }
}
