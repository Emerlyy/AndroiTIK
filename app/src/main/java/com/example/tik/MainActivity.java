package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TextView detailsText1, detailsText2, detailsText3;
    EditText editPeriod, editWidth, editAmplitude;
    RelativeLayout mainLayout;
    Button nextStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailsText1 = findViewById(R.id.details1);
        detailsText2 = findViewById(R.id.details2);
        detailsText3 = findViewById(R.id.details3);
        editPeriod = findViewById(R.id.editPeriod);
        editWidth = findViewById(R.id.editWidth);
        editAmplitude = findViewById(R.id.editAmplitude);
        mainLayout = findViewById(R.id.mainLayout);
        //layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        mainLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        nextStepButton=(Button)findViewById(R.id.button);

        nextStepButton.setEnabled(false);
        editWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0||editPeriod.length()==0){
                    nextStepButton.setEnabled(false);
                } else {
                    nextStepButton.setEnabled(true);
                }
            }
        });
        editPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()==0||editWidth.length()==0){
                    nextStepButton.setEnabled(false);
                } else {
                    nextStepButton.setEnabled(true);
                }
            }
        });


    }
    public void startNewActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
       int period = tryParse(editPeriod.getText().toString());
       int width = tryParse(editWidth.getText().toString());
       int amplitude = tryParse(editAmplitude.getText().toString());
       intent.putExtra("period", period);
       intent.putExtra("width",width);
       intent.putExtra("amplitude",amplitude);
       startActivity(intent);
    }


    public void expand1(View view) {
        int v = (detailsText1.getVisibility() == view.GONE) ? View.VISIBLE : View.GONE;
        detailsText1.setVisibility(v);
    }

    public void expand2(View view) {
        int v = (detailsText2.getVisibility() == view.GONE) ? View.VISIBLE : View.GONE;
        detailsText2.setVisibility(v);
    }

    public void expand3(View view) {
        int v = (detailsText3.getVisibility() == view.GONE) ? View.VISIBLE : View.GONE;
        detailsText3.setVisibility(v);
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
