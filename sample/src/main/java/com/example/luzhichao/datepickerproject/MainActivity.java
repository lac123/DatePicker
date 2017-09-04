package com.example.luzhichao.datepickerproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.datepicker.DatePicker;

public class MainActivity extends AppCompatActivity {
    private Context context;

    private Button button;
    private TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        button = (Button) findViewById(R.id.bt_choose_date);
        dateTextView = (TextView) findViewById(R.id.tv_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePicker.Builder(context)
                        .setDefaultDate(dateTextView.getText().toString())
                        .setSelectedCallback(new DatePicker.OnSelectDateListener() {
                            @Override
                            public void onDateSelected(String dateString) {
                                dateTextView.setText(dateString);
                            }
                        })
                        .setType(DatePicker.NORMAL_TYPE)
                        .showDatePicker();
            }
        });
    }
}
