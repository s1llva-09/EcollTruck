package com.example.gestaodeprodutos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.AuthResponse;
import com.example.gestaodeprodutos.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtSenha;
    private Button btnLogin, btnUsuario, btnEmpresa, btnCadastroUsuario, btnCadastroEmpresa;
    private CheckBox chkLembreDeMim;
    private AuthViewModel viewModel;
    private SharedPreferences sharedPreferences;

    private boolean isUserLogin = true; // Controla se o login é de usuário ou empresa
    private static final int REGISTRO_REQUEST_CODE = 1;
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String PREF_EMAIL = "email";
    private static final String PREF_REMEMBER = "remember";
    private static final String ADMIN_EMAIL = "admin@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_login);

        // Inicializa o ViewModel
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Referencia os componentes da UI
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnUsuario = findViewById(R.id.btnUsuario);
        btnEmpresa = findViewById(R.id.btnEmpresa);
        btnCadastroUsuario = findViewById(R.id.btnCadastroUsuario);
        btnCadastroEmpresa = findViewById(R.id.btnCadastroEmpresa);
        chkLembreDeMim = findViewById(R.id.chkLembreDeMim);

        // Define as ações dos botões
        setupClickListeners();
        // Define o estado inicial dos botões
        selectUserType(true);
        // Observa os resultados do ViewModel
        setupObservers();
        // Carrega as preferências salvas
        loadPreferences();
    }

    private void setupClickListeners() {
        // Ação do botão de Login
        btnLogin.setOnClickListener(v -> realizarLogin());

        // Ação do botão de Cadastro de Usuário
        btnCadastroUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroUsuarioActivity.class);
            startActivityForResult(intent, REGISTRO_REQUEST_CODE);
        });

        // Ação do botão de Cadastro de Empresa
        btnCadastroEmpresa.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroEmpresaActivity.class);
            startActivity(intent);
        });

        // Ação do seletor Usuário/Empresa
        btnUsuario.setOnClickListener(v -> selectUserType(true));
        btnEmpresa.setOnClickListener(v -> selectUserType(false));
    }

    private void setupObservers() {
        viewModel.getLoginResult().observe(this, authResponse -> {
            if (authResponse != null && authResponse.user != null) {
                // Lógica de "Lembre-se de mim"
                if (chkLembreDeMim.isChecked()) {
                    // Salva as credenciais
                    savePreferences();
                } else {
                    // Limpa as credenciais salvas
                    clearPreferences();
                }

                // Salvar token
                getSharedPreferences("APP", MODE_PRIVATE)
                        .edit()
                        .putString("TOKEN", authResponse.getToken_type() + " " + authResponse.getAccess_token())
                        .apply();

                // Lógica de redirecionamento
                boolean isAdmin = ADMIN_EMAIL.equalsIgnoreCase(authResponse.user.email);

                if (isUserLogin) {
                    // Aba Usuário selecionada
                    if (isAdmin) {
                        Toast.makeText(this, "Login de administrador não é permitido nesta aba.", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                } else {
                    // Aba Empresa selecionada
                    if (isAdmin) {
                        startActivity(new Intent(this, AdminDashboardActivity.class));
                    } else {
                        Toast.makeText(this, "Apenas administradores podem acessar a aba Empresa.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        viewModel.getLoginError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadPreferences() {
        boolean rememberMe = sharedPreferences.getBoolean(PREF_REMEMBER, false);
        chkLembreDeMim.setChecked(rememberMe);
        if (rememberMe) {
            String email = sharedPreferences.getString(PREF_EMAIL, "");
            edtEmail.setText(email);
        }
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_REMEMBER, chkLembreDeMim.isChecked());
        editor.putString(PREF_EMAIL, edtEmail.getText().toString().trim());
        editor.apply();
    }

    private void clearPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_EMAIL);
        editor.putBoolean(PREF_REMEMBER, false);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTRO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String emailRegistrado = data.getStringExtra("EMAIL_REGISTRADO");
            if (emailRegistrado != null) {
                edtEmail.setText(emailRegistrado);
                // Opcional: Focar no campo de senha para agilizar o login
                edtSenha.requestFocus();
            }
        }
    }

    private void selectUserType(boolean isUser) {
        isUserLogin = isUser;

        if (isUser) {
            // Botão Usuário selecionado
            btnUsuario.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_accent)));
            btnUsuario.setTextColor(ContextCompat.getColor(this, R.color.dark_background));

            // Botão Empresa não selecionado
            btnEmpresa.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)));
            btnEmpresa.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            // Botão Empresa selecionado
            btnEmpresa.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green_accent)));
            btnEmpresa.setTextColor(ContextCompat.getColor(this, R.color.dark_background));

            // Botão Usuário não selecionado
            btnUsuario.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.dark_gray)));
            btnUsuario.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    private void realizarLogin() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha e-mail e senha!", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.login(email, senha);
    }
}
