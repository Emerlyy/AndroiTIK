package com.example.tik;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class MainActivity extends AppCompatActivity {

    EditText editPeriod, editWidth, editAmplitude;
    RelativeLayout mainLayout;
    Button nextStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        editPeriod = findViewById(R.id.editPeriod);
        editWidth = findViewById(R.id.editWidth);
        editAmplitude = findViewById(R.id.editAmplitude);
        mainLayout = findViewById(R.id.mainLayout);
        //mainLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        nextStepButton = (Button) findViewById(R.id.button);

        nextStepButton.setEnabled(false);
        editWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                nextStepButton.setEnabled(s.toString().trim().length() != 0 && editPeriod.length() != 0);
            }
        });
        editPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                nextStepButton.setEnabled(s.toString().trim().length() != 0 && editWidth.length() != 0);
            }
        });
    }

    public void startNewActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        int period = tryParse(editPeriod.getText().toString());
        int width = tryParse(editWidth.getText().toString());
        int amplitude = tryParse(editAmplitude.getText().toString());
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
