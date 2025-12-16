package com.example.gestaodeprodutos.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        // Adiciona as máscaras de formatação
        edtCPF.addTextChangedListener(new CpfTextWatcher(edtCPF));
        edtTelefone.addTextChangedListener(new PhoneTextWatcher(edtTelefone));
        edtDataNascimento.addTextChangedListener(new DateTextWatcher(edtDataNascimento));

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
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EMAIL_REGISTRADO", edtEmailRegistro.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        viewModel.getRegistrationError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registrarUsuario() {
        // Coleta e limpa os dados dos campos usando uma expressão regular mais robusta
        String nome = edtNomeCompleto.getText().toString().trim();
        String cpfRaw = edtCPF.getText().toString().replaceAll("[^0-9]", "");
        String email = edtEmailRegistro.getText().toString().trim();
        String telefoneRaw = edtTelefone.getText().toString().replaceAll("[^0-9]", "");
        String dataNascimento = edtDataNascimento.getText().toString().trim();
        String senha = edtSenhaRegistro.getText().toString().trim();
        String confirmarSenha = edtConfirmarSenha.getText().toString().trim();

        // Validação dos campos
        if (nome.isEmpty() || cpfRaw.isEmpty() || email.isEmpty() || telefoneRaw.isEmpty() || dataNascimento.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cpfRaw.length() != 11) {
            Toast.makeText(this, "CPF inválido. Deve conter 11 dígitos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (telefoneRaw.length() != 11) {
            Toast.makeText(this, "Telefone inválido. Deve conter 11 dígitos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String apiFormattedDate = convertDateToApiFormat(dataNascimento);
        if (apiFormattedDate == null) {
            Toast.makeText(this, "Data de nascimento inválida. Use o formato DD/MM/AAAA.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.registrar(nome, email, senha, cpfRaw, telefoneRaw, apiFormattedDate);
    }

    private String convertDateToApiFormat(String brDate) {
        try {
            String[] parts = brDate.split("/");
            if (parts.length == 3 && parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 4) {
                return parts[2] + "-" + parts[1] + "-" + parts[0]; // AAAA-MM-DD
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    // --- TextWatcher Base para evitar código duplicado ---
    private abstract static class BaseTextWatcher implements TextWatcher {
        protected final EditText editText;
        protected boolean isUpdating;

        public BaseTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        protected void updateText(String formatted) {
            if (isUpdating) return;
            isUpdating = true;
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            isUpdating = false;
        }
    }

    // --- Implementações Específicas e Robustas para cada Máscara ---

    private static class CpfTextWatcher extends BaseTextWatcher {
        public CpfTextWatcher(EditText editText) { super(editText); }

        @Override
        public void afterTextChanged(Editable s) {
            String digits = s.toString().replaceAll("[^0-9]", "");
            StringBuilder formatted = new StringBuilder();
            int len = digits.length();

            if (len > 0) formatted.append(digits.substring(0, Math.min(len, 3)));
            if (len > 3) formatted.append(".").append(digits.substring(3, Math.min(len, 6)));
            if (len > 6) formatted.append(".").append(digits.substring(6, Math.min(len, 9)));
            if (len > 9) formatted.append("-").append(digits.substring(9, Math.min(len, 11)));

            updateText(formatted.toString());
        }
    }

    private static class PhoneTextWatcher extends BaseTextWatcher {
        public PhoneTextWatcher(EditText editText) { super(editText); }

        @Override
        public void afterTextChanged(Editable s) {
            String digits = s.toString().replaceAll("[^0-9]", "");
            StringBuilder formatted = new StringBuilder();
            int len = digits.length();

            if (len > 0) formatted.append("(").append(digits.substring(0, Math.min(len, 2)));
            if (len > 2) formatted.append(") ").append(digits.substring(2, Math.min(len, 7)));
            if (len > 7) formatted.append("-").append(digits.substring(7, Math.min(len, 11)));

            updateText(formatted.toString());
        }
    }

    private static class DateTextWatcher extends BaseTextWatcher {
        public DateTextWatcher(EditText editText) { super(editText); }

        @Override
        public void afterTextChanged(Editable s) {
            String digits = s.toString().replaceAll("[^0-9]", "");
            StringBuilder formatted = new StringBuilder();
            int len = digits.length();

            if (len > 0) formatted.append(digits.substring(0, Math.min(len, 2)));
            if (len > 2) formatted.append("/").append(digits.substring(2, Math.min(len, 4)));
            if (len > 4) formatted.append("/").append(digits.substring(4, Math.min(len, 8)));

            updateText(formatted.toString());
        }
    }
}
