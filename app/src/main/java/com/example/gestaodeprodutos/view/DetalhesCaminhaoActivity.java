package com.example.gestaodeprodutos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.viewmodel.CaminhaoViewModel;

public class DetalhesCaminhaoActivity extends AppCompatActivity {

    private EditText edtModelo, edtPlaca, edtStatus, edtCapacidade, edtAno;
    private CaminhaoViewModel viewModel;
    private Caminhao caminhaoAtual;

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

        edtModelo = findViewById(R.id.edtModelo);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtStatus = findViewById(R.id.edtStatus);
        edtCapacidade = findViewById(R.id.edtCapacidade);
        edtAno = findViewById(R.id.edtAno);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        viewModel = new ViewModelProvider(this).get(CaminhaoViewModel.class);
        caminhaoAtual = (Caminhao) getIntent().getSerializableExtra("caminhao");

        if (caminhaoAtual != null) {
            edtModelo.setText(caminhaoAtual.getModelo());
            edtPlaca.setText(caminhaoAtual.getPlaca());
            edtStatus.setText(caminhaoAtual.getStatus());
            edtCapacidade.setText(String.valueOf(caminhaoAtual.getCapacidade()));
            edtAno.setText(String.valueOf(caminhaoAtual.getAno()));
        }

        // Configura os observers para sucesso e erro
        viewModel.getSaveSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Caminhão salvo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getSaveError().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        btnSalvar.setOnClickListener(v -> {
            String modelo = edtModelo.getText().toString();
            String placa = edtPlaca.getText().toString();
            String status = edtStatus.getText().toString();
            String capacidadeStr = edtCapacidade.getText().toString();
            String anoStr = edtAno.getText().toString();

            if (modelo.isEmpty() || placa.isEmpty() || status.isEmpty() || capacidadeStr.isEmpty() || anoStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Double capacidade = Double.parseDouble(capacidadeStr);
            Integer ano = Integer.parseInt(anoStr);

            Caminhao caminhaoParaSalvar = (caminhaoAtual != null) ? caminhaoAtual : new Caminhao();
            caminhaoParaSalvar.setModelo(modelo);
            caminhaoParaSalvar.setPlaca(placa);
            caminhaoParaSalvar.setStatus(status);
            caminhaoParaSalvar.setCapacidade(capacidade);
            caminhaoParaSalvar.setAno(ano);

            String token = getSharedPreferences("APP", MODE_PRIVATE).getString("TOKEN", null);
            if (token == null) {
                Toast.makeText(this, "Erro de autenticação. Faça login novamente.", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.saveCaminhao(token, caminhaoParaSalvar);
        });
    }
}
