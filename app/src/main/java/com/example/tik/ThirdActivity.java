package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
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
        float[] arrData = getIntent().getFloatArrayExtra("data");

        barChart = (BarChart) findViewById(R.id.chart);
        entries = new ArrayList<BarEntry>();
        for (int i = 0; i < arrData.length; i++) {
            entries.add(new BarEntry(i, arrData[i]));
        }
        dataSet = new BarDataSet(entries, "");
        barData = new BarData(dataSet);


        final ArrayList<String> xLabel = new ArrayList<>();
        for (int i = 0; i < arrData.length; i++) {
            xLabel.add("A" + i);
        }


        // dataSet.setDrawValues(false); // removes values above the bars
        dataSet.setValueTextSize(10f);
        dataSet.setValueFormatter(new IValueFormatter() {
            private DecimalFormat mFormat = new DecimalFormat("#.##");

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return mFormat.format(value);
            }
        });
        barData.setBarWidth(0.8f); // bar width
        barData.setHighlightEnabled(false); // disables highlight
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false); // removes the grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // sets xAxis position
        xAxis.setLabelCount(arrData.length);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value);
            }
        });

        //barChart.getAxisLeft().setEnabled(false);
        barChart.setScaleEnabled(false); // disables the scale
        barChart.getAxisRight().setEnabled(false); // removes right axis
        barChart.getAxisLeft().setAxisMinimum(0); // makes minimum Y value to 0
        barChart.animateY(600); // animation
        barChart.getDescription().setEnabled(false); // removes the description
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.getLegend().setEnabled(false); // hide the legend
        //barChart.getXAxis().setDrawLabels(false); // hide the labels
        barChart.setDrawBorders(true);
        barChart.setBorderWidth(0.5f);
        barChart.invalidate();
    }

    public void goBack2(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}