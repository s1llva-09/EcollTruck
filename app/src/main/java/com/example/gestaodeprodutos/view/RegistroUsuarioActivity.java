package com.example.gestaodeprodutos.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.viewmodel.AuthViewModel;

public class RegistroUsuarioActivity extends AppCompatActivity {
    private EditText edtNomeCompleto, edtCPF, edtEmailRegistro, edtTelefone, edtDataNascimento, edtSenhaRegistro, edtConfirmarSenha;
    private Button btnRegistrar;
    private TextView txtVoltarLogin;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Referencia os componentes da UI
        edtNomeCompleto = findViewById(R.id.edtNomeCompleto);
        edtCPF = findViewById(R.id.edtCPF);
        edtEmailRegistro = findViewById(R.id.edtEmailRegistro);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtDataNascimento = findViewById(R.id.edtDataNascimento);
        edtSenhaRegistro = findViewById(R.id.edtSenhaRegistro);
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtVoltarLogin = findViewById(R.id.txtVoltarLogin);

        // Define a ação do botão de registrar
        btnRegistrar.setOnClickListener(v -> registrarUsuario());

        // Define a ação do botão de voltar
        txtVoltarLogin.setOnClickListener(v -> finish()); // Volta para a tela anterior (Login)

        // Observa os resultados do ViewModel
        setupObservers();
    }

    private void setupObservers() {
        viewModel.getRegistrationResult().observe(this, authResponse -> {
            if (authResponse != null) {
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG).show();

                // Devolve o email para a tela de login
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EMAIL_REGISTRADO", edtEmailRegistro.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish(); // Fecha a tela de registro
            }
        });

        viewModel.getRegistrationError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registrarUsuario() {
        // Coleta os dados de todos os campos
        String nome = edtNomeCompleto.getText().toString().trim();
        String cpf = edtCPF.getText().toString().trim();
        String email = edtEmailRegistro.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String dataNascimento = edtDataNascimento.getText().toString().trim();
        String senha = edtSenhaRegistro.getText().toString().trim();
        String confirmarSenha = edtConfirmarSenha.getText().toString().trim();

        // Validação dos campos
        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() || dataNascimento.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicia o processo de registro através do ViewModel
        viewModel.registrar(nome, email, senha, cpf, telefone, dataNascimento);
    }
}
