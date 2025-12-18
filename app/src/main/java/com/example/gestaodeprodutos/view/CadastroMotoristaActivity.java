package com.example.gestaodeprodutos.view;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.model.Usuario;
import com.example.gestaodeprodutos.viewmodel.MotoristaViewModel;
import com.example.gestaodeprodutos.viewmodel.UsuarioViewModel;

import java.util.List;

public class CadastroMotoristaActivity extends AppCompatActivity {

    private AutoCompleteTextView actUsuario;
    private EditText edtNomeCompleto, edtCnh, edtCategoriaCnh, edtTelefone;
    private Button btnSalvarMotorista;
    private ImageButton btnVoltar;
    private UsuarioViewModel usuarioViewModel;
    private MotoristaViewModel motoristaViewModel;
    private Integer usuarioSelecionadoIdCliente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_cadastro_motorista);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        motoristaViewModel = new ViewModelProvider(this).get(MotoristaViewModel.class);

        actUsuario = findViewById(R.id.actUsuario);
        edtNomeCompleto = findViewById(R.id.edtNomeCompleto);
        edtCnh = findViewById(R.id.edtCnh);
        edtCategoriaCnh = findViewById(R.id.edtCategoriaCnh);
        edtTelefone = findViewById(R.id.edtTelefone);
        btnSalvarMotorista = findViewById(R.id.btnSalvarMotorista);
        btnVoltar = findViewById(R.id.btn_voltar);

        btnSalvarMotorista.setOnClickListener(v -> salvarMotorista());
        btnVoltar.setOnClickListener(v -> onBackPressed());

        observeViewModels();
        loadData();
    }

    private void observeViewModels() {
        usuarioViewModel.getUsuarios().observe(this, usuarios -> {
            if (usuarios != null) {
                ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, usuarios);
                actUsuario.setAdapter(adapter);
            }
        });

        usuarioViewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        motoristaViewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        actUsuario.setOnItemClickListener((parent, view, position, id) -> {
            Usuario usuario = (Usuario) parent.getItemAtPosition(position);
            this.usuarioSelecionadoIdCliente = usuario.getIdCliente();
        });

        motoristaViewModel.getSaveSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Motorista salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadData() {
        SharedPreferences prefs = getSharedPreferences("APP", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", null);
        if (token != null) {
            usuarioViewModel.fetchUsuarios(token);
        }
    }

    private void salvarMotorista() {
        String nome = edtNomeCompleto.getText().toString().trim();
        String cnh = edtCnh.getText().toString().trim();
        String categoriaCnh = edtCategoriaCnh.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();

        if (usuarioSelecionadoIdCliente == null || nome.isEmpty() || cnh.isEmpty() || categoriaCnh.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        Motorista motorista = new Motorista();
        motorista.setNomeCompleto(nome);
        motorista.setCnh(cnh);
        motorista.setCategoriaCnh(categoriaCnh);
        motorista.setTelefone(telefone);
        motorista.setStatus("ativo");
        motorista.setIdCliente(usuarioSelecionadoIdCliente);

        SharedPreferences prefs = getSharedPreferences("APP", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", null);
        motoristaViewModel.saveMotorista(token, motorista);
    }
}
