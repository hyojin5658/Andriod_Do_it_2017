package com.hansune.calendarproto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import static com.hansune.calendarproto.R.layout.activity_time;

public class time extends Activity {

    int y, mOY, dOM; //AddSchedule로 보낼 년, 월, 일 선언
    DatePicker datePicker; //날짜 선택기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_time);

        datePicker = (DatePicker) findViewById(R.id.datepicker);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {

            // 날짜 선택하면 아래 검정 박스에 뜨는거
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String msg = String.format("%d / %d / %d ", year, monthOfYear + 1, dayOfMonth);
                y=year; mOY=monthOfYear+1; dOM=dayOfMonth;
                Toast.makeText(time.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateadd_btn:
                //선택된 날짜를 AddScheduleActivity에 넘겨주고 종료하기
                AddScheduleActivity.text_date.setText(y+" 년 "+mOY+" 월 "+dOM+" 일 ");
                AddScheduleActivity.yy = y;
                AddScheduleActivity.mm = mOY;
                AddScheduleActivity.dd = dOM;
                finish();
                break;

            case R.id.cancle:
                //취소버튼 누르면 액티비티 종료
                finish();
                break;
        }
    }

}
