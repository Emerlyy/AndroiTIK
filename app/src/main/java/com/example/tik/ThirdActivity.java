package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    BarChart barChart;
    List<BarEntry> entries;
    BarDataSet dataSet;
    BarData barData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        barChart = (BarChart) findViewById(R.id.chart);
        entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(1,2));
        entries.add(new BarEntry(2,1));
        entries.add(new BarEntry(3,2));
        entries.add(new BarEntry(4,7));
        entries.add(new BarEntry(5,4));
        dataSet = new BarDataSet(entries,"Label");
        barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }
    public void goBack2(View v){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}