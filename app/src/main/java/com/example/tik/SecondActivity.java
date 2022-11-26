package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.Button;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity {

    CheckBox[] arrayCheckBox = new CheckBox[16];
    float[] arrayData = new float[arrayCheckBox.length];
    ToggleButton checkAllButton;
    Button nextStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(R.string.second_activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        int period = getIntent().getIntExtra("period", 0);
        int width = getIntent().getIntExtra("width", 0);
        int amplitude = getIntent().getIntExtra("amplitude", 0);

        arrayCheckBox[0] = (CheckBox) findViewById(R.id.checkBox0);
        nextStepButton = (Button) findViewById(R.id.next);
        arrayData[0] = calcA0(period, width, amplitude);
        SpannableString text = new SpannableString("A0 = " + arrayData[0]);
        text.setSpan(new RelativeSizeSpan(0.6f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        arrayCheckBox[0].setText(text);
        for (int i = 1; i < arrayCheckBox.length; i++) {
            String strId = "checkBox" + String.valueOf(i);
            int resId = getResources().getIdentifier(strId, "id", getPackageName());
            arrayCheckBox[i] = (CheckBox) findViewById(resId);
            arrayData[i] = calcAn(period, width, amplitude, i);
            text = new SpannableString("A" + i + " = " + arrayData[i]);
            text.setSpan(new RelativeSizeSpan(0.6f), 1, String.valueOf(i).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            arrayCheckBox[i].setText(text);

        }
        for (int i = 0; i < arrayCheckBox.length; i++) {
            arrayCheckBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChacked) {
                    updateButton();
                }
            });
        }
        checkAllButton = findViewById(R.id.checkAllButton);
        boolean isChecked = checkAllButton.isChecked();
        updateVisibility(isChecked);
        checkAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVisibility(checkAllButton.isChecked());
            }
        });
        nextStepButton.setEnabled(true);
        for (int i = 0; i < arrayCheckBox.length; i++) {
            arrayCheckBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(arrayCheckBox[0].isChecked()||arrayCheckBox[1].isChecked()||arrayCheckBox[2].isChecked()||arrayCheckBox[3].isChecked()||arrayCheckBox[4].isChecked()||
                            arrayCheckBox[5].isChecked()||arrayCheckBox[6].isChecked()||arrayCheckBox[7].isChecked()||arrayCheckBox[8].isChecked()|| arrayCheckBox[9].isChecked()||
                            arrayCheckBox[10].isChecked()||arrayCheckBox[11].isChecked()||arrayCheckBox[12].isChecked()||arrayCheckBox[13].isChecked()||arrayCheckBox[14].isChecked()||arrayCheckBox[15].isChecked()) {
                        nextStepButton.setEnabled(true);
                    }
                    else{
                        nextStepButton.setEnabled(false);
                    }
                }
            });
        }

    }

    private void updateButton() {
        for (int i = 0; i < arrayCheckBox.length; i++) {
            if (!arrayCheckBox[i].isChecked()) {
                checkAllButton.setChecked(false);
                return;
            }
        }
        checkAllButton.setChecked(true);
    }

    private void updateVisibility(boolean isChecked) {
        if (isChecked) {
            for (int i = 0; i < arrayCheckBox.length; i++) {
                arrayCheckBox[i].setChecked(true);
            }

        } else {
            for (int i = 0; i < arrayCheckBox.length; i++) {
                arrayCheckBox[i].setChecked(false);
            }
        }
    }

    public void startNewActivity2(View v) {
        List<Float> absData = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        Intent intent = new Intent(this, ThirdActivity.class);
        for (int i = 0; i < arrayData.length; i++) {
            if (arrayCheckBox[i].isChecked()) {
                    absData.add(Math.abs(arrayData[i]));
                        indexes.add(i);
            }
        }
        intent.putExtra("data", (Serializable) absData);
        intent.putExtra("indexes", (Serializable) indexes);
        startActivity(intent);
    }

    public float calcA0(int T, int t, int h) {
        BigDecimal result = new BigDecimal((double) h * t / T).setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.floatValue();
    }

    public float calcAn(int T, int t, int h, int k) {
        double w = 2 * Math.PI / T;
        BigDecimal result = new BigDecimal(2 * h * t * Math.sin(k * w * t / 2) / (T * k * w * t / 2)).setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.floatValue();
    }

}

