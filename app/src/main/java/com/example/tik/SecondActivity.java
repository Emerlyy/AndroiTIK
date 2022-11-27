package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.Button;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view1).findViewById(R.id.expand_text_view1);
        expTv1.setText(getString(R.string.expandable_text1));
        setTitle(R.string.second_activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        int period = getIntent().getIntExtra("period", 0);
        int width = getIntent().getIntExtra("width", 0);
        int amplitude = getIntent().getIntExtra("amplitude", 0);
        double w = 2*Math.PI/period;
        arrayCheckBox[0] = (CheckBox) findViewById(R.id.checkBox0);
        nextStepButton = (Button) findViewById(R.id.next);
        arrayData[0] = calcA0(period, width, amplitude);

        String hStr = String.valueOf(amplitude);
        String tStr = String.valueOf(width);
        String TStr = String.valueOf(period);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String wStr = decimalFormat.format(2*Math.PI/period);

        SpannableString text = new SpannableString("A0 = " + hStr+"*"+tStr+"/"+TStr+" = "+arrayData[0]);
        text.setSpan(new RelativeSizeSpan(0.6f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        arrayCheckBox[0].setText(text);


        for (int i = 1; i < arrayCheckBox.length; i++) {
            String strId = "checkBox" + i;
            int resId = getResources().getIdentifier(strId, "id", getPackageName());
            arrayCheckBox[i] = (CheckBox) findViewById(resId);
            arrayData[i] = calcAn(period, width, amplitude, i);
            text = new SpannableString("A" + i + " = 2*" +hStr+"*"+tStr+"*sin("+i+"*"+wStr+"*"+tStr+"/2)/("+TStr+"*("+i+"*"+wStr+"*"+tStr+"/2)) = "+arrayData[i]);
            text.setSpan(new RelativeSizeSpan(0.6f), 1, String.valueOf(i).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            arrayCheckBox[i].setText(text);
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

        for (CheckBox checkBox : arrayCheckBox) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkAllButton.setChecked(Arrays.stream(arrayCheckBox).allMatch(checkBox -> checkBox.isChecked()));
                    nextStepButton.setEnabled(Arrays.stream(arrayCheckBox).anyMatch(checkBox -> checkBox.isChecked()));
                }
            });
        }

    }

    private void updateVisibility(boolean isChecked) {
        for (CheckBox checkBox : arrayCheckBox) {
            checkBox.setChecked(isChecked);
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

