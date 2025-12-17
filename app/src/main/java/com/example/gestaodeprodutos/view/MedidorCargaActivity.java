package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.gestaodeprodutos.R;

public class MedidorCargaActivity extends AppCompatActivity {

    private Button btnMedidorCarga;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_medidor_carga);

        // Encontrar os componentes pelo ID
        btnMedidorCarga = findViewById(R.id.btn_medidor_carga);
        imgBack = findViewById(R.id.img_back);

        // Definir um OnClickListener para o Button
        btnMedidorCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação a ser executada ao clicar.
                Toast.makeText(MedidorCargaActivity.this, "Botão do Medidor de Carga clicado!", Toast.LENGTH_SHORT).show();
            }
        });

        // Definir um OnClickListener para o botão de voltar
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finaliza a activity e volta para a tela anterior
                finish();
            }
        });
    }
}
