package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    JLatexMathView[] latexFormulas = new JLatexMathView[3];
    JLatexMathView latexEnteredData;

    JLatexMathDrawable drawable;

    ToggleButton checkAllButton;
    Button nextStepButton;

    RelativeLayout layout;

    float[] arrayData = new float[arrayCheckBox.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

       // ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view1).findViewById(R.id.expand_text_view1);
        //expTv1.setText(getString(R.string.expandable_text1));

        setTitle(R.string.second_activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        int period = getIntent().getIntExtra("period", 0);
        int width = getIntent().getIntExtra("width", 0);
        int amplitude = getIntent().getIntExtra("amplitude", 0);
        double w = 2 * Math.PI / period;
        layout = findViewById(R.id.layout);
        arrayCheckBox[0] = findViewById(R.id.checkBox0);
        jLatexMathView[0] = findViewById(R.id.math_view0);
        latexEnteredData = findViewById(R.id.entered);

        arrayData[0] = calcA0(period, width, amplitude);
        nextStepButton = findViewById(R.id.next);

        String hStr = String.valueOf(amplitude);
        String tStr = String.valueOf(width);
        String TStr = String.valueOf(period);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String A0 = "\\boldsymbol{A_{0}=h\\cdot \\frac{t}{T}";
        String Ak = "\\boldsymbol{A_{k}=2\\cdot h\\cdot \\frac{t\\cdot\\sin(k\\cdot \\omega\\cdot \\frac{t}{2})}{T\\cdot k\\cdot \\omega\\cdot \\frac{t}{2}}";
        String W = "\\boldsymbol{\\omega=\\frac{2\\pi}{T}} = " + decimalFormat.format(w);


        String[] forAllLatexView = {A0, Ak, W};

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

        String enteredData = "\\boldsymbol{\\textcolor{OliveGreen}{T}=" + TStr + "\\\\\\textcolor{OliveGreen}{t}=" + tStr + "}";
        drawable = JLatexMathDrawable.builder(enteredData)
                .textSize(160)
                .padding(8)
                .background(0x00000000)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        latexEnteredData.setLatexDrawable(drawable);

        String calcLatexA0 = "\\boldsymbol{A_{0}=" + hStr + "\\cdot \\frac{" + tStr + "}{" + TStr + "}=" + arrayData[0] + "}";
        drawable = JLatexMathDrawable.builder(calcLatexA0)
                .textSize(160)
                .padding(8)
                .background(0x00000000)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        jLatexMathView[0].setLatexDrawable(drawable);


        for (int i = 1; i < arrayCheckBox.length; i++) {

            int Id = getResources().getIdentifier("checkBox" + i, "id", getPackageName());
            int LatexId = getResources().getIdentifier("math_view" + i, "id", getPackageName());

            arrayCheckBox[i] = findViewById(Id);
            jLatexMathView[i] = findViewById(LatexId);
            arrayData[i] = calcAn(period, width, amplitude, i);

            drawable = JLatexMathDrawable.builder("\\boldsymbol{A_{\\textcolor{OliveGreen}{" + i + "}}=2\\cdot " + hStr + "\\cdot \\frac{" + tStr + "\\cdot\\sin(\\textcolor{OliveGreen}{" + i + "}\\cdot \\frac{2\\pi}{" + TStr + "}\\cdot \\frac{" + tStr + "}{2})}" +
                            "{" + TStr + "\\cdot\\textcolor{OliveGreen}{" + i + "}\\cdot \\frac{2\\pi}{" + TStr + "}\\cdot \\frac{" + tStr + "}{2}}=" + arrayData[i] + "}")
                    .textSize(160)
                    .padding(8)
                    .background(0x00000000)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();
            jLatexMathView[i].setLatexDrawable(drawable);
        }

        checkAllButton = findViewById(R.id.checkAllButton);
        boolean isChecked = checkAllButton.isChecked();
        updateVisibility(isChecked);
        checkAllButton.setOnClickListener(view -> updateVisibility(checkAllButton.isChecked()));

        nextStepButton.setEnabled(true);
        for (CheckBox checkBox : arrayCheckBox) {
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                checkAllButton.setChecked(Arrays.stream(arrayCheckBox).allMatch(checkBox1 -> checkBox1.isChecked()));
                nextStepButton.setEnabled(Arrays.stream(arrayCheckBox).anyMatch(checkBox1 -> checkBox1.isChecked()));
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

}

