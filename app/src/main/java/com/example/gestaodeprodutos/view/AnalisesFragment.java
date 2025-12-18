package com.example.gestaodeprodutos.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gestaodeprodutos.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class AnalisesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analises, container, false);

        // Gráfico de Barras - Coletas por Dia
        BarChart barChart = view.findViewById(R.id.bar_chart);
        setupBarChart(barChart);

        // Gráfico de Linha - Tendência de Eficiência
        LineChart lineChart = view.findViewById(R.id.line_chart);
        setupLineChart(lineChart);

        // Gráfico de Barras - Volume Mensal
        BarChart barChartMonthly = view.findViewById(R.id.bar_chart_monthly);
        setupBarChartMonthly(barChartMonthly);

        return view;
    }

    private void setupBarChart(BarChart barChart) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 45f));
        entries.add(new BarEntry(1, 52f));
        entries.add(new BarEntry(2, 48f));
        entries.add(new BarEntry(3, 62f));
        entries.add(new BarEntry(4, 56f));
        entries.add(new BarEntry(5, 38f));
        entries.add(new BarEntry(6, 25f));

        BarDataSet dataSet = new BarDataSet(entries, "Coletas");
        dataSet.setColor(Color.parseColor("#00FF87"));

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customização
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        final String[] days = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));

        barChart.invalidate(); // refresh
    }

    private void setupLineChart(LineChart lineChart) {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 92f));
        entries.add(new Entry(1, 88f));
        entries.add(new Entry(2, 95f));
        entries.add(new Entry(3, 90f));
        entries.add(new Entry(4, 94f));
        entries.add(new Entry(5, 85f));

        LineDataSet dataSet = new LineDataSet(entries, "Eficiência");
        dataSet.setColor(Color.parseColor("#29B6F6"));
        dataSet.setCircleColor(Color.parseColor("#29B6F6"));

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Customização
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextColor(Color.WHITE);

        lineChart.invalidate(); // refresh
    }

    private void setupBarChartMonthly(BarChart barChart) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 1200f));
        entries.add(new BarEntry(1, 1300f));
        entries.add(new BarEntry(2, 1250f));
        entries.add(new BarEntry(3, 1400f));
        entries.add(new BarEntry(4, 1350f));
        entries.add(new BarEntry(5, 1500f));

        BarDataSet dataSet = new BarDataSet(entries, "Volume Mensal");
        dataSet.setColor(Color.parseColor("#F06292"));

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customização
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getXAxis().setTextColor(Color.WHITE);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        final String[] months = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun"};
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));

        barChart.invalidate(); // refresh
    }
}
