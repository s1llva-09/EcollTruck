package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestaodeprodutos.R;
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

        // Configura o Spinner de estados
        setupEstadoSpinner();

        // Define as ações dos botões
        btnCadastrarEmpresa.setOnClickListener(v -> registrarEmpresa());
        txtVoltarLogin.setOnClickListener(v -> finish());
    }

    private void setupEstadoSpinner() {
        // TODO: Mover a lista de estados para um recurso de array em `res/values/arrays.xml`
        String[] estados = {"UF", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
    }

    private void registrarEmpresa() {
        String cnpj = edtCnpj.getText().toString().trim();
        String nomeResponsavel = edtNomeResponsavel.getText().toString().trim();
        String email = edtEmailEmpresa.getText().toString().trim();
        String telefone = edtTelefoneEmpresa.getText().toString().trim();
        String cidade = edtCidade.getText().toString().trim();
        String estado = spinnerEstado.getSelectedItem().toString();
        String senha = edtSenhaEmpresa.getText().toString().trim();
        String confirmarSenha = edtConfirmarSenhaEmpresa.getText().toString().trim();

        // Validação dos campos
        if (cnpj.isEmpty() || nomeResponsavel.isEmpty() || email.isEmpty() || telefone.isEmpty() || cidade.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (estado.equals("UF")) {
            Toast.makeText(this, "Selecione um estado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: A lógica de registro de empresa ainda não existe no seu AuthViewModel.
        // Você precisará criar um método como `registrarEmpresa()` no ViewModel e no Repository.
        // Por enquanto, apenas uma mensagem de sucesso será exibida.
        Toast.makeText(this, "Cadastro de empresa iniciado! (Lógica a ser implementada)", Toast.LENGTH_LONG).show();

        // Exemplo de como a chamada ao ViewModel poderia ser:
        // viewModel.registrarEmpresa(cnpj, nomeResponsavel, email, telefone, cidade, estado, senha).observe(this, res -> { ... });

        finish(); // Volta para a tela de login
    }
}
