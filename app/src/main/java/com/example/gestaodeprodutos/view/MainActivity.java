package com.example.gestaodeprodutos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gestaodeprodutos.R;

public class MainActivity extends AppCompatActivity {

    private boolean isPaused = false;
    private ImageButton btnPausePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilita o botão de voltar e oculta o título padrão
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Referencia e configura os botões e pontos do mapa
        setupClickableViews();
    }

    private void setupClickableViews() {
        btnPausePlay = findViewById(R.id.btn_pause_play);
        ImageView pointA = findViewById(R.id.point_a);
        ImageView pointB = findViewById(R.id.point_b);
        ImageView pointD = findViewById(R.id.point_d);
        ImageView pointE = findViewById(R.id.point_e);
        Button btnVerPontosColeta = findViewById(R.id.btn_ver_pontos_coleta);
        Button btnVerMedidorCarga = findViewById(R.id.btn_ver_medidor_carga); // Botão que faltava

        // Lógica do botão de Pausa/Play
        btnPausePlay.setOnClickListener(v -> {
            isPaused = !isPaused; // Inverte o estado
            if (isPaused) {
                btnPausePlay.setImageResource(R.drawable.ic_play);
                Toast.makeText(this, "Rota pausada", Toast.LENGTH_SHORT).show();
            } else {
                btnPausePlay.setImageResource(R.drawable.ic_pause);
                Toast.makeText(this, "Rota retomada", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica dos pontos do mapa
        pointA.setOnClickListener(v -> Toast.makeText(this, "Ponto A: Concluído", Toast.LENGTH_SHORT).show());
        pointB.setOnClickListener(v -> Toast.makeText(this, "Ponto B: Concluído", Toast.LENGTH_SHORT).show());
        pointD.setOnClickListener(v -> Toast.makeText(this, "Ponto D: Pendente", Toast.LENGTH_SHORT).show());
        pointE.setOnClickListener(v -> Toast.makeText(this, "Ponto E: Pendente", Toast.LENGTH_SHORT).show());

        btnVerPontosColeta.setOnClickListener(v -> {
            // Substitua "PontosColetaActivity.class" pela sua Activity de pontos de coleta, se o nome for diferente
            Intent intent = new Intent(MainActivity.this, PontosColetaActivity.class);
            startActivity(intent);
        });

        // Lógica para o botão do Medidor de Carga
        btnVerMedidorCarga.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MedidorCargaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla o menu com os ícones de ação (telefone, configurações)
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Trata os cliques nos itens da toolbar
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            // Ação do botão de voltar
            finish(); // Volta para a tela anterior
            return true;
        } else if (itemId == R.id.action_call) {
            // Ação do botão de telefone
            Toast.makeText(this, "Função de ligação não implementada.", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_settings) {
            // Ação do botão de configurações
            Toast.makeText(this, "Função de configurações não implementada.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
