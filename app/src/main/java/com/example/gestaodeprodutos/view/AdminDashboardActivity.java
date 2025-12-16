package com.example.gestaodeprodutos.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gestaodeprodutos.R;
import com.example.gestaodeprodutos.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

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
            }
        }).attach();
    }
}
