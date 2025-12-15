package com.example.gestaodeprodutos.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gestaodeprodutos.view.CaminhoesFragment;
import com.example.gestaodeprodutos.view.VisaoGeralFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CaminhoesFragment();
            default:
                return new VisaoGeralFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Visão Geral e Caminhões
    }
}
