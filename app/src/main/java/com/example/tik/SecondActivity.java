package com.example.tik;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.View;
import android.widget.CheckBox;

public class SecondActivity extends AppCompatActivity {
    int period,width,amplitude;
    CheckBox checkBox0,checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9,checkBox10,checkBox11,checkBox12,checkBox13,checkBox14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        period = getIntent().getIntExtra("period", 0);
        width = getIntent().getIntExtra("width", 0);
        amplitude = getIntent().getIntExtra("amplitude", 0);
        checkBox0 = findViewById(R.id.checkBox);
        String str0 = "A0 = ";
        SpannableString str00 = new SpannableString(str0 + calcA0(period, width, amplitude));
        str00.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox0.setText(str00);

        checkBox1 = findViewById(R.id.checkBox1);
        String str1 = "A1 = ";
        SpannableString str01 = new SpannableString(str1 + calcAn(period, width, amplitude, 1));
        str01.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox1.setText(str01);

        checkBox2 = findViewById(R.id.checkBox2);
        String str2 = "A2 = ";
        SpannableString str02 = new SpannableString(str2 + calcAn(period, width, amplitude, 2));
        str02.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox2.setText(str02);

        checkBox3 = findViewById(R.id.checkBox3);
        String str3 = "A3 = ";
        SpannableString str03 = new SpannableString(str3 + calcAn(period, width, amplitude, 3));
        str03.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox3.setText(str03);

        checkBox4 = findViewById(R.id.checkBox4);
        String str4 = "A4 = ";
        SpannableString str04 = new SpannableString(str4 + calcAn(period, width, amplitude, 4));
        str04.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox4.setText(str04);

        checkBox5 = findViewById(R.id.checkBox5);
        String str5 = "A5 = ";
        SpannableString str05 = new SpannableString(str5 + calcAn(period, width, amplitude, 5));
        str05.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox5.setText(str05);

        checkBox6 = findViewById(R.id.checkBox6);
        String str6 = "A6 = ";
        SpannableString str06 = new SpannableString(str6 + calcAn(period, width, amplitude, 6));
        str06.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox6.setText(str06);

        checkBox7 = findViewById(R.id.checkBox7);
        String str7 = "A7 = ";
        SpannableString str07 = new SpannableString(str7 + calcAn(period, width, amplitude, 7));
        str07.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox7.setText(str07);

        checkBox8 = findViewById(R.id.checkBox8);
        String str8 = "A8 = ";
        SpannableString str08 = new SpannableString(str8 + calcAn(period, width, amplitude, 8));
        str08.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox8.setText(str08);

        checkBox9 = findViewById(R.id.checkBox9);
        String str9 = "A9 = ";
        SpannableString str09 = new SpannableString(str9 + calcAn(period, width, amplitude, 9));
        str09.setSpan(new RelativeSizeSpan(0.6f),1,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox9.setText(str09);

        checkBox10 = findViewById(R.id.checkBox10);
        String str10 = "A10 = ";
        SpannableString str010 = new SpannableString(str10 + calcAn(period, width, amplitude, 10));
        str010.setSpan(new RelativeSizeSpan(0.6f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox10.setText(str010);

        checkBox11 = findViewById(R.id.checkBox11);
        String str11 = "A11 = ";
        SpannableString str011 = new SpannableString(str11 + calcAn(period, width, amplitude, 11));
        str011.setSpan(new RelativeSizeSpan(0.6f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox11.setText(str011);

        checkBox12 = findViewById(R.id.checkBox12);
        String str12 = "A12 = ";
        SpannableString str012 = new SpannableString(str12 + calcAn(period, width, amplitude, 12));
        str012.setSpan(new RelativeSizeSpan(0.6f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox12.setText(str012);

        checkBox13 = findViewById(R.id.checkBox13);
        String str13 = "A13 = ";
        SpannableString str013 = new SpannableString(str13 + calcAn(period, width, amplitude, 13));
        str013.setSpan(new RelativeSizeSpan(0.6f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox13.setText(str013);

        checkBox14 = findViewById(R.id.checkBox14);
        String str14 = "A14 = ";
        SpannableString str014 = new SpannableString(str14 + calcAn(period, width, amplitude, 14));
        str014.setSpan(new RelativeSizeSpan(0.6f),1,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBox14.setText(str014);


    }
    public void startNewActivity2(View v){
        Intent intent = new Intent(this,ThirdActivity.class);
        startActivity(intent);
    }

    public double calcA0(int T,int t,int h){
        double result = (double) h*t/T;
        return Double.parseDouble(String.format("%.2f", result));
    }

    public double calcAn(int T,int t,int h,int k) {
        double w = 2 * Math.PI / T;
        double result = 2 * h * t * Math.sin(k * w * t / 2) / (T * k * w * t / 2);
        return Double.parseDouble(String.format("%.2f", result));
    }

}