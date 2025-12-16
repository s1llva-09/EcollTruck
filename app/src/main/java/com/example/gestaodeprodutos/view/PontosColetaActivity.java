package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.model.PontoColeta;
import com.example.gestaodeprodutos.view.adapter.PontoColetaAdapter;
import com.example.gestaodeprodutos.viewmodel.PontoColetaViewModel;

public class PontosColetaActivity extends AppCompatActivity implements PontoColetaAdapter.OnItemClickListener {

    private PontoColetaViewModel viewModel;
    private PontoColetaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos_coleta);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PontoColetaAdapter(null, this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(PontoColetaViewModel.class);
        viewModel.getPontosColeta().observe(this, pontos -> {
            adapter.setPontosColeta(pontos);
        });
    }

    @Override
    public void onConcluirButtonClick(PontoColeta pontoColeta) {
        viewModel.marcarComoConcluido(pontoColeta);
    }
}
