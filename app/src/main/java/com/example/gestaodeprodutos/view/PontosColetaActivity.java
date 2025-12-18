package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.PontoColeta;
import com.example.gestaodeprodutos.view.adapter.PontoColetaAdapter;
import com.example.gestaodeprodutos.viewmodel.PontoColetaViewModel;

import java.util.ArrayList;

public class PontosColetaActivity extends AppCompatActivity implements PontoColetaAdapter.OnItemClickListener, PopupMenu.OnMenuItemClickListener {

    private PontoColetaViewModel viewModel;
    private PontoColetaAdapter adapter;
    private Button btnFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_pontos_coleta);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        // Adiciona o botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PontoColetaAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PontoColetaViewModel.class);
        viewModel.getPontosColeta().observe(this, pontos -> {
            adapter.setPontosColeta(pontos);
        });

        // Configura o botão de filtro
        btnFiltro = findViewById(R.id.btn_filtro);
        btnFiltro.setOnClickListener(this::showFilterMenu);
    }

    private void showFilterMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.filter_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.filter_todos) {
            adapter.filter("todos");
            return true;
        } else if (itemId == R.id.filter_pendentes) {
            adapter.filter("PENDENTE");
            return true;
        } else if (itemId == R.id.filter_concluidos) {
            adapter.filter("CONCLUIDO");
            return true;
        }
        return false;
    }

    @Override
    public void onConcluirButtonClick(PontoColeta pontoColeta) {
        viewModel.marcarComoConcluido(pontoColeta);
    }
}
