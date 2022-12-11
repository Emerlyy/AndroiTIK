package com.example.tik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class FourthActivity extends AppCompatActivity {

    JLatexMathDrawable drawable;
    JLatexMathView []mathView = new JLatexMathView[11];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);


        for (int i = 1; i < mathView.length; i++) {
            int IdVariants = getResources().getIdentifier("enableVariants" + i, "id", getPackageName());
            mathView[i] = findViewById(IdVariants);

            drawable = JLatexMathDrawable.builder(i+".\\:T="+i+"\\:с,\\:t="+i+"\\:с,\\:h=1\\:В")
                    .textSize(40)
                    .padding(8)
                    .background(0x00000000)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();
            mathView[i].setLatexDrawable(drawable);
        }
    }

    public void onBack(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}