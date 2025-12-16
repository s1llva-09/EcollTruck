package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestaodeprodutos.R;

public class CadastroCaminhaoActivity extends AppCompatActivity {

    private EditText edtModelo, edtPlaca, edtStatus, edtCapacidade, edtAno;
    private Button btnSalvar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_caminhao);

        edtModelo = findViewById(R.id.edtModelo);
        edtPlaca = findViewById(R.id.edtPlaca);
        edtStatus = findViewById(R.id.edtStatus);
        edtCapacidade = findViewById(R.id.edtCapacidade);
        edtAno = findViewById(R.id.edtAno);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {
            // Lógica para salvar o caminhão
            Toast.makeText(this, "Caminhão salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a activity após salvar
        });
    }
}
