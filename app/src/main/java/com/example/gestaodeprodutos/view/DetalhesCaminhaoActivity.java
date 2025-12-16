package com.example.gestaodeprodutos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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
        setContentView(R.layout.activity_cadastro_caminhao);

        edtModelo = findViewById(R.id.edtModelo);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtStatus = findViewById(R.id.edtStatus);
        edtCapacidade = findViewById(R.id.edtCapacidade);
        edtAno = findViewById(R.id.edtAno);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        viewModel = new ViewModelProvider(this).get(CaminhaoViewModel.class);
        caminhaoAtual = (Caminhao) getIntent().getSerializableExtra("caminhao");

        String token = this.getSharedPreferences("APP", MODE_PRIVATE).getString("token", null);

        if (caminhaoAtual != null) {
            edtModelo.setText(caminhaoAtual.getModelo());
            edtPlaca.setText(caminhaoAtual.getPlaca());
            edtStatus.setText(caminhaoAtual.getStatus());
            edtCapacidade.setText(String.valueOf(caminhaoAtual.getCapacidade()));
            edtAno.setText(String.valueOf(caminhaoAtual.getAno()));
        }

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

            Caminhao caminhao = new Caminhao(modelo, placa, status, capacidade, ano);

            if (caminhaoAtual != null) {
                caminhao.setId(caminhaoAtual.getId());
                viewModel.atualizarCaminhao(caminhao, token).observe(this, sucesso -> {
                    if (sucesso) {
                        Toast.makeText(this, "Caminhão salvo!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                viewModel.inserirCaminhao(caminhao, token).observe(this, sucesso -> {
                    if (sucesso) {
                        Toast.makeText(this, "Caminhão salvo!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
