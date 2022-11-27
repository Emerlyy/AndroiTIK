package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Button;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;


public class SecondActivity extends AppCompatActivity {
    CheckBox[] arrayCheckBox = new CheckBox[16];
    JLatexMathView[] jLatexMathView = new JLatexMathView[16];

    ToggleButton checkAllButton;
    Button nextStepButton;

    float[] arrayData = new float[arrayCheckBox.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(R.string.second_activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        int period = getIntent().getIntExtra("period", 0);
        int width = getIntent().getIntExtra("width", 0);
        int amplitude = getIntent().getIntExtra("amplitude", 0);
        double w = 2*Math.PI/period;

        arrayCheckBox[0] = (CheckBox) findViewById(R.id.checkBox0);
        jLatexMathView[0] = (JLatexMathView) findViewById(R.id.math_view0);
        arrayData[0] = calcA0(period, width, amplitude);
        nextStepButton = (Button) findViewById(R.id.next);

        String hStr = String.valueOf(amplitude);
        String tStr = String.valueOf(width);
        String TStr = String.valueOf(period);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String wStr = decimalFormat.format(2*Math.PI/period);

        SpannableString text = new SpannableString("A0 = \t\t\t\t\t\t\t\t\t\t\t\t\t= "+arrayData[0]);
        JLatexMathDrawable drawable = JLatexMathDrawable.builder(hStr+"\\cdot \\frac{"+tStr+"}{"+TStr+"}")
                .textSize(120)
                .padding(8)
                .background(0x00000000)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        jLatexMathView[0].setLatexDrawable(drawable);
        text.setSpan(new RelativeSizeSpan(0.6f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        arrayCheckBox[0].setText(text);


        for (int i = 1; i < arrayCheckBox.length; i++) {
            String strId = "checkBox" + String.valueOf(i);
            String latexMathViewId = "math_view" + String.valueOf(i);

            int resId = getResources().getIdentifier(strId, "id", getPackageName());
            int resLatexId = getResources().getIdentifier(latexMathViewId,"id", getPackageName());

            arrayCheckBox[i] = (CheckBox) findViewById(resId);
            jLatexMathView[i] = (JLatexMathView) findViewById(resLatexId);
            arrayData[i] = calcAn(period, width, amplitude, i);

            text = new SpannableString("A" + i + " = \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t = "+arrayData[i]);
            drawable = JLatexMathDrawable.builder("\\boldsymbol{2\\cdot "+hStr+"\\cdot \\frac{"+tStr+"\\cdot\\sin("+i+"\\cdot "+wStr+"\\cdot \\frac{"+tStr+"}{2})}" +
                            "{"+TStr+"\\cdot"+i+"\\cdot"+wStr+"\\cdot \\frac{"+tStr+"}{2}}")
                    .textSize(160)
                    .padding(8)
                    .background(0x00000000)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();
            jLatexMathView[i].setLatexDrawable(drawable);

            text.setSpan(new RelativeSizeSpan(0.6f), 1, String.valueOf(i).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            arrayCheckBox[i].setText(text);
        }
        for (int i = 0; i < arrayCheckBox.length; i++) {
            arrayCheckBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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

