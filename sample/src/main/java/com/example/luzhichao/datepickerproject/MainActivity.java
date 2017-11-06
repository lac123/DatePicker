package com.example.luzhichao.datepickerproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.datepicker.DatePicker;
import com.tencent.bugly.crashreport.CrashReport;

public class MainActivity extends AppCompatActivity {
    private Context context;

    private Button singleButton, doubleButton;
    private TextView singleTV, doubleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        singleButton = (Button) findViewById(R.id.bt_choose_date_single);
        doubleButton = (Button) findViewById(R.id.bt_choose_date_double);
        singleTV = (TextView) findViewById(R.id.tv_date_single);
        doubleTV = (TextView) findViewById(R.id.tv_date_double);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePicker.Builder(context)
                        .setDefaultDate(singleTV.getText().toString())
                        .setSelectedCallback(new DatePicker.OnSelectDateListener() {
                            @Override
                            public void onDateSelected(String dateString) {
                                singleTV.setText(dateString);
                            }
                        })
                        .setType(DatePicker.NORMAL_TYPE)
                        .showDatePicker();
            }
        });
        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePicker.Builder(context)
                        .setDefaultDate(doubleTV.getText().toString())
                        .setSelectedCallback(new DatePicker.OnSelectDateListener() {
                            @Override
                            public void onDateSelected(String dateString) {
                                doubleTV.setText(dateString);
                            }
                        })
                        .isDoubleDate(true)
                        .setType(DatePicker.NORMAL_TYPE)
                        .showDatePicker();
            }
        });
        String aaa = "12123.png";
        Log.e("ninini", aaa.contains(".") + "");
    }
}
