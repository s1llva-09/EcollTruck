package com.example.gestaodeprodutos.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.gestaodeprodutos.viewmodel.CaminhaoViewModel;

public class CadastroCaminhaoActivity extends AppCompatActivity {

    private EditText edtModelo, edtPlaca, edtStatus, edtCapacidade, edtAno;
    private Button btnSalvar;
    private ImageButton btnVoltar;
    private CaminhaoViewModel caminhaoViewModel;
    private Caminhao caminhao;

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

        edtModelo = findViewById(R.id.edtModelo);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtStatus = findViewById(R.id.edtStatus);
        edtCapacidade = findViewById(R.id.edtCapacidade);
        edtAno = findViewById(R.id.edtAno);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btn_voltar);

        caminhao = (Caminhao) getIntent().getSerializableExtra("caminhao");

        if (caminhao != null) {
            preencherCampos();
        }

        btnSalvar.setOnClickListener(v -> salvarCaminhao());

        btnVoltar.setOnClickListener(v -> finish());

        caminhaoViewModel.getSaveSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Caminhão salvo com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK); // Define o resultado como OK
                finish();
            }
        });

        caminhaoViewModel.getSaveError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preencherCampos() {
        edtModelo.setText(caminhao.getModelo());
        edtPlaca.setText(caminhao.getPlaca());
        edtStatus.setText(caminhao.getStatus());
        edtCapacidade.setText(String.valueOf(caminhao.getCapacidade()));
        edtAno.setText(String.valueOf(caminhao.getAno()));
    }

    private void salvarCaminhao() {
        String modelo = edtModelo.getText().toString().trim();
        String placa = edtPlaca.getText().toString().trim();
        String status = edtStatus.getText().toString().trim();
        String capacidadeStr = edtCapacidade.getText().toString().trim();
        String anoStr = edtAno.getText().toString().trim();

        if (modelo.isEmpty() || placa.isEmpty() || status.isEmpty() || capacidadeStr.isEmpty() || anoStr.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (caminhao == null) {
            caminhao = new Caminhao();
        }

        caminhao.setModelo(modelo);
        caminhao.setPlaca(placa);
        caminhao.setStatus(status);
        caminhao.setCapacidade(Double.parseDouble(capacidadeStr));
        caminhao.setAno(Integer.parseInt(anoStr));

        SharedPreferences prefs = getSharedPreferences("APP", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", null);

        if (token == null) {
            Toast.makeText(this, "Erro de autenticação. Faça login novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        caminhaoViewModel.saveCaminhao(token, caminhao);
    }
}
