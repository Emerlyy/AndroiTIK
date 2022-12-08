package com.example.tik;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;



public class MainActivity extends AppCompatActivity {

    EditText editPeriod, editWidth, editAmplitude;
    RelativeLayout mainLayout;
    Button nextStepButton;
    BigDecimal w;
    int period, width,amplitude;
    JLatexMathView latexView;
    JLatexMathDrawable drawable;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPeriod = findViewById(R.id.editPeriod);
        editWidth = findViewById(R.id.editWidth);
        editAmplitude = findViewById(R.id.editAmplitude);
        mainLayout = findViewById(R.id.mainLayout);
        //mainLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        nextStepButton = findViewById(R.id.button);

        latexView = findViewById(R.id.latexView);
        String W = "\\textcolor{OliveGreen}{\\boldsymbol{\\omega=\\frac{2\\pi}{T}";
        drawable = JLatexMathDrawable.builder(W)
                .textSize(70)
                .padding(8)
                .background(0x00000000)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        latexView.setLatexDrawable(drawable);


        nextStepButton.setEnabled(false);
        editPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean enabled = editWidth.length() != 0 && editPeriod.length() != 0 && editAmplitude.length() != 0&&((editPeriod.getText().toString().equals("8")||editPeriod.getText().toString().equals("6")||editPeriod.getText().toString().equals("2"))
                        &&(editWidth.getText().toString().equals("6")||editWidth.getText().toString().equals("4")||editWidth.getText().toString().equals("2")));

                boolean calcW = (editPeriod.getText().toString().equals("8")||editPeriod.getText().toString().equals("6")||editPeriod.getText().toString().equals("2"));

                if(calcW) {
                    period = tryParse(editPeriod.getText().toString());
                    w = new BigDecimal(2 * Math.PI / period).setScale(2, RoundingMode.HALF_UP);
                    drawable = JLatexMathDrawable.builder(W + " = " + w + "\\frac{рад}{с^{-1}}")
                            .textSize(60)
                            .padding(8)
                            .background(0x00000000)
                            .align(JLatexMathDrawable.ALIGN_RIGHT)
                            .build();
                    latexView.setLatexDrawable(drawable);
                }
                if(!enabled){
                    nextStepButton.setEnabled(false);
                    return;
                }
                nextStepButton.setEnabled(true);
            }
        });
        editWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                boolean enabled = editWidth.length() != 0 && editPeriod.length() != 0 && editAmplitude.length() != 0&&((editPeriod.getText().toString().equals("8")||editPeriod.getText().toString().equals("6")||editPeriod.getText().toString().equals("2"))
                        &&(editWidth.getText().toString().equals("6")||editWidth.getText().toString().equals("4")||editWidth.getText().toString().equals("2")));
                if(!enabled){
                    nextStepButton.setEnabled(false);

                    return;
                }
                nextStepButton.setEnabled(true);
            }
        });
    }


    public void startNewActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        width = tryParse(editWidth.getText().toString());
        amplitude = tryParse(editAmplitude.getText().toString());
        intent.putExtra("period", period);
        intent.putExtra("width", width);
        intent.putExtra("amplitude", amplitude);
        startActivity(intent);
    }

    public void expand(View view) {
        CardView card = (CardView) view;
        LinearLayout lin1 = (LinearLayout) card.getChildAt(0);
        TextView detailsText = (TextView) lin1.getChildAt(1);
        ImageView arrow = (ImageView) card.getChildAt(1);
        int isDetailsVisible = (detailsText.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(mainLayout, autoTransition);
        arrow.animate().rotation((isDetailsVisible==View.GONE)?0:180).start();
        detailsText.setVisibility(isDetailsVisible);
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
