package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestaodeprodutos.R;

public class MedidorCargaActivity extends AppCompatActivity {

    private TextView txtMedidorCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidor_carga);

        // Encontrar o TextView pelo ID
        txtMedidorCarga = findViewById(R.id.txt_medidor_carga);

        // Definir um OnClickListener para o TextView
        txtMedidorCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação a ser executada ao clicar.
                Toast.makeText(MedidorCargaActivity.this, "Botão do Medidor de Carga clicado!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
