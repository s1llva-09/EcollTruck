package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.utils.MaskUtils;
import com.example.gestaodeprodutos.viewmodel.AuthViewModel;

public class RegistroEmpresaActivity extends AppCompatActivity {

    private EditText edtCnpj, edtNomeResponsavel, edtEmailEmpresa, edtTelefoneEmpresa, edtCidade, edtSenhaEmpresa, edtConfirmarSenhaEmpresa;
    private Spinner spinnerEstado;
    private Button btnCadastrarEmpresa;
    private TextView txtVoltarLogin;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_registro_empresa);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Referencia os componentes da UI
        edtCnpj = findViewById(R.id.edtCnpj);
        edtNomeResponsavel = findViewById(R.id.edtNomeResponsavel);
        edtEmailEmpresa = findViewById(R.id.edtEmailEmpresa);
        edtTelefoneEmpresa = findViewById(R.id.edtTelefoneEmpresa);
        edtCidade = findViewById(R.id.edtCidade);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        edtSenhaEmpresa = findViewById(R.id.edtSenhaEmpresa);
        edtConfirmarSenhaEmpresa = findViewById(R.id.edtConfirmarSenhaEmpresa);
        btnCadastrarEmpresa = findViewById(R.id.btnCadastrarEmpresa);
        txtVoltarLogin = findViewById(R.id.txtVoltarLogin);

        // Configura as máscaras
        setupMasks();

        // Configura o Spinner de estados
        setupEstadoSpinner();

        // Observa os resultados do registro
        viewModel.getRegistrationResult().observe(this, authResponse -> {
            if (authResponse != null && authResponse.getUser() != null) {
                Toast.makeText(RegistroEmpresaActivity.this, "Empresa cadastrada com sucesso! Verifique seu e-mail.", Toast.LENGTH_LONG).show();
                finish(); // Volta para a tela de login
            }
        });

        viewModel.getRegistrationError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(RegistroEmpresaActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });

        // Define as ações dos botões
        btnCadastrarEmpresa.setOnClickListener(v -> registrarEmpresa());
        txtVoltarLogin.setOnClickListener(v -> finish());
    }

    private void setupMasks() {
        edtCnpj.addTextChangedListener(MaskUtils.mask(edtCnpj, MaskUtils.FORMAT_CNPJ));
        edtTelefoneEmpresa.addTextChangedListener(MaskUtils.mask(edtTelefoneEmpresa, MaskUtils.FORMAT_FONE));
    }

    private void setupEstadoSpinner() {
        // TODO: Mover a lista de estados para um recurso de array em `res/values/arrays.xml`
        String[] estados = {"UF", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
    }

    private void registrarEmpresa() {
        // Remove caracteres especiais das strings mascaradas
        String cnpj = MaskUtils.unmask(edtCnpj.getText().toString().trim());
        String nomeResponsavel = edtNomeResponsavel.getText().toString().trim();
        String email = edtEmailEmpresa.getText().toString().trim();
        String telefone = MaskUtils.unmask(edtTelefoneEmpresa.getText().toString().trim());
        String cidade = edtCidade.getText().toString().trim();
        String estado = spinnerEstado.getSelectedItem().toString();
        String senha = edtSenhaEmpresa.getText().toString().trim();
        String confirmarSenha = edtConfirmarSenhaEmpresa.getText().toString().trim();

        // Validação dos campos
        if (cnpj.isEmpty() || cnpj.length() != 14) {
            Toast.makeText(this, "CNPJ inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nomeResponsavel.isEmpty()) {
            Toast.makeText(this, "Preencha o campo Nome do Responsável", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Preencha o campo E-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (telefone.isEmpty() || telefone.length() < 10) {
            Toast.makeText(this, "Telefone inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cidade.isEmpty()) {
            Toast.makeText(this, "Preencha o campo Cidade", Toast.LENGTH_SHORT).show();
            return;
        }
        if (estado.equals("UF")) {
            Toast.makeText(this, "Selecione um Estado", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha.isEmpty()) {
            Toast.makeText(this, "Preencha o campo Senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha o campo Confirmar Senha", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chama o método no ViewModel enviando os dados (preferencialmente sem formatação para o banco)
        viewModel.registrarEmpresa(cnpj, nomeResponsavel, email, telefone, cidade, estado, senha);
    }
}
