package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView detailsText1,  detailsText2, detailsText3;
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailsText1 = findViewById(R.id.details1);
        detailsText2 = findViewById(R.id.details2);
        detailsText3 = findViewById(R.id.details3);
        mainLayout = findViewById(R.id.mainLayout);
        //layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        mainLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


    }
    public void startNewActivity(View v){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
    public void expand1(View view){
        int v = (detailsText1.getVisibility()==view.GONE)?View.VISIBLE:View.GONE;
        detailsText1.setVisibility(v);
    }
    public void expand2(View view){
        int v = (detailsText2.getVisibility()==view.GONE)?View.VISIBLE:View.GONE;
        detailsText2.setVisibility(v);
    }
    public void expand3(View view){
        int v = (detailsText3.getVisibility()==view.GONE)?View.VISIBLE:View.GONE;
        detailsText3.setVisibility(v);
    }
}