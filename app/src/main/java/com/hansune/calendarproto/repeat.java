package com.hansune.calendarproto;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

import static com.hansune.calendarproto.R.layout.activity_repeat;

public class repeat extends Activity {

    //반복 횟수 설정
    public static String selected="";

    //repeat.xml에서 java로 가져오기 위함
    EditText repeat_time;
    EditText repeat_text;
    Spinner period;
    //입력한 반복 횟수 AddSchedule에 뿌리기 위함
    String r1,r2,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

        Spinner spinner = (Spinner)findViewById(R.id.repeat_type);
        ArrayAdapter repeatAdapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_type, android.R.layout.simple_spinner_dropdown_item);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(repeatAdapter);

        repeat_text=(EditText) findViewById(R.id.repeat_text);
        repeat_time = (EditText) findViewById(R.id.repeat_time);
        period = (Spinner) findViewById(R.id.repeat_type);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repeatadd_btn:
                r1 = repeat_text.getText().toString();
                r2 = repeat_time.getText().toString();
                p = (String)period.getSelectedItem();
                //선택된 날짜를 AddScheduleActivity에 넘겨주고 종료하기
                AddScheduleActivity.period_date.setText(r1);
                AddScheduleActivity.repeat_time.setText(r2);
                AddScheduleActivity.period.setText(p);
                finish();

                break;

            case R.id.cancle:
                //취소버튼 누르면 액티비티 종료
                finish();
                break;
        }
    }
}
