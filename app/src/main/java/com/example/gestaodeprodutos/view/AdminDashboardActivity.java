package com.example.gestaodeprodutos.view;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.adapter.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_admin_dashboard);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout); // Assumindo que você adicionou um ID para a AppBarLayout
        ViewCompat.setOnApplyWindowInsetsListener(appBarLayout, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
            return insets;
        });

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ImageButton btnVoltar = findViewById(R.id.btn_voltar);

        btnVoltar.setOnClickListener(v -> finish());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Visão Geral");
                    tab.setIcon(R.drawable.ic_dashboard);
                    break;
                case 1:
                    tab.setText("Caminhões");
                    tab.setIcon(R.drawable.ic_truck);
                    break;
                case 2:
                    tab.setText("Rotas");
                    tab.setIcon(R.drawable.ic_route);
                    break;
                case 3:
                    tab.setText("Análises");
                    tab.setIcon(R.drawable.ic_analises);
                    break;
                case 4:
                    tab.setText("Alertas");
                    tab.setIcon(R.drawable.ic_warning);
                    break;
            }
        }).attach();
    }
}
