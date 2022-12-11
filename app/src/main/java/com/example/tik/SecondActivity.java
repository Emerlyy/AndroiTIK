package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import android.widget.Button;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;


public class SecondActivity extends AppCompatActivity {
    CheckBox[] arrayCheckBox = new CheckBox[16];
    JLatexMathView[] jLatexMathView = new JLatexMathView[16];
    JLatexMathView[] latexFormulas = new JLatexMathView[2];
    JLatexMathView []textUnderCheckBoxes= new JLatexMathView[16];

    JLatexMathDrawable drawable;

    ToggleButton checkAllButton;
    Button nextStepButton;

    RelativeLayout layout;

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
        double w = 2 * Math.PI / period;
        layout = findViewById(R.id.layout);
        arrayCheckBox[0] = findViewById(R.id.checkBox0);
        jLatexMathView[0] = findViewById(R.id.math_view0);
        //latexEnteredData = findViewById(R.id.entered);
        textUnderCheckBoxes[0] = findViewById(R.id.textUnderCheckButton0);

        arrayData[0] = calcA0(period, width, amplitude);
        nextStepButton = findViewById(R.id.next);

        String hStr = String.valueOf(amplitude);
        String tStr = String.valueOf(width);
        String TStr = String.valueOf(period);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String WStr = decimalFormat.format(w);

        String A0 = "\\boldsymbol{A_{0}=|h\\cdot \\frac{t}{T}|";
        String Ak = "\\boldsymbol{A_{k}=|2\\cdot h\\cdot \\frac{t\\cdot\\sin(k\\cdot \\omega\\cdot \\frac{t}{2})}{T\\cdot k\\cdot \\omega\\cdot \\frac{t}{2}}|";
        String StrUnderCheckButton0 = "\\boldsymbol{\\omega_{0}=0\\:\\frac{рад}{c^{-1}}\\\\Постійна\\:складова - постійний\\:струм}";


        String[] forAllLatexView = {A0, Ak};

        for (int i = 0; i < latexFormulas.length; i++) {
            int latexFormulasId = getResources().getIdentifier("Formula" + i, "id", getPackageName());
            latexFormulas[i] = findViewById(latexFormulasId);

            drawable = JLatexMathDrawable.builder(forAllLatexView[i])
                    .textSize(60)
                    .padding(8)
                    .background(0x00000000)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();
            latexFormulas[i].setLatexDrawable(drawable);
        }

        String calcLatexA0 = "\\boldsymbol{\\textcolor{OliveGreen}{A_{0}}=|" + hStr + "\\cdot \\frac{" + tStr + "}{" + TStr + "}|=\\textcolor{OliveGreen}{" + Math.abs(arrayData[0]) + " ("+getString(R.string.volt)+")}}";
        drawable = JLatexMathDrawable.builder(calcLatexA0)
                .textSize(160)
                .padding(8)
                .background(0x00000000)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        jLatexMathView[0].setLatexDrawable(drawable);

        drawable = JLatexMathDrawable.builder(StrUnderCheckButton0)
                .textSize(40)
                .padding(8)
                .background(0x00000000)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        textUnderCheckBoxes[0].setLatexDrawable(drawable);


        for (int i = 1; i < arrayCheckBox.length; i++) {

            int Id = getResources().getIdentifier("checkBox" + i, "id", getPackageName());
            int LatexId = getResources().getIdentifier("math_view" + i, "id", getPackageName());
            int idStrUnderCheckButtons = getResources().getIdentifier("textUnderCheckButton"+i,"id",getPackageName());

            arrayCheckBox[i] = findViewById(Id);
            jLatexMathView[i] = findViewById(LatexId);
            textUnderCheckBoxes[i] = findViewById(idStrUnderCheckButtons);
            arrayData[i] = calcAn(period, width, amplitude, i);


            drawable = JLatexMathDrawable.builder("\\boldsymbol{\\textcolor{OliveGreen}{A_{" + i + "}}=\\mid2\\cdot " + hStr + "\\cdot \\frac{" + tStr + "\\cdot\\sin(\\textcolor{OliveGreen}{" + i + "}\\cdot "+WStr+"\\cdot \\frac{" + tStr + "}{2})}" +
                            "{" + TStr + "\\cdot\\textcolor{OliveGreen}{" + i + "}\\cdot "+WStr+"\\cdot \\frac{" + tStr + "}{2}}\\mid=\\textcolor{OliveGreen}{" + Math.abs(arrayData[i]) + "("+getString(R.string.volt)+")}}")
                    .textSize(160)
                    .padding(8)
                    .background(0x00000000)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();
            jLatexMathView[i].setLatexDrawable(drawable);

            drawable = JLatexMathDrawable.builder("\\boldsymbol{\\omega_{"+i+"}="+i+"\\cdot"+WStr+"\\:\\frac{"+getString(R.string.rad)+"}{"+getString(R.string.second)+"^{-1}}\\\\Синусоїдальний\\:сигнал\\:з\\:амплітудою\\:A"+i+"}")
                    .textSize(40)
                    .padding(8)
                    .background(0x00000000)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();
            textUnderCheckBoxes[i].setLatexDrawable(drawable);
        }

        checkAllButton = findViewById(R.id.checkAllButton);
        boolean isChecked = checkAllButton.isChecked();
        updateVisibility(isChecked);
        checkAllButton.setOnClickListener(view -> updateVisibility(checkAllButton.isChecked()));

        nextStepButton.setEnabled(false);
        for (CheckBox checkBox : arrayCheckBox) {
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                checkAllButton.setChecked(Arrays.stream(arrayCheckBox).allMatch(CompoundButton::isChecked));
                nextStepButton.setEnabled(Arrays.stream(arrayCheckBox).anyMatch(CompoundButton::isChecked));
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
        BigDecimal result = new BigDecimal((double) h * t / T).setScale(2, RoundingMode.HALF_UP);
        return result.floatValue();
    }

    public float calcAn(int T, int t, int h, int k) {
        double w = 2 * Math.PI / T;
        BigDecimal result = new BigDecimal(2 * h * t * Math.sin(k * w * t / 2) / (T * k * w * t / 2)).setScale(2, RoundingMode.HALF_UP);
        return result.floatValue();
    }

    public void expand1(View view) {
        CardView card = (CardView) view;
        LinearLayout lin1 = (LinearLayout) card.getChildAt(0);
        LinearLayout hiddenLayout = (LinearLayout) lin1.getChildAt(1);
        ImageView arrow = (ImageView) card.getChildAt(1);
        int isDetailsVisible = (hiddenLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(layout, autoTransition);
        arrow.animate().rotation((isDetailsVisible==View.GONE)?0:180).start();
        hiddenLayout.setVisibility(isDetailsVisible);
    }
    public void expand2(View view) {
        CardView card = (CardView) view;
        RelativeLayout lin1 = (RelativeLayout) card.getChildAt(0);
        RelativeLayout hiddenLayout = (RelativeLayout) lin1.getChildAt(3);
        ImageView arrow = (ImageView) card.getChildAt(1);
        int isDetailsVisible = (hiddenLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(layout, autoTransition);
        arrow.animate().rotation((isDetailsVisible==View.GONE)?0:180).start();
        hiddenLayout.setVisibility(isDetailsVisible);
    }

    public void onBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

