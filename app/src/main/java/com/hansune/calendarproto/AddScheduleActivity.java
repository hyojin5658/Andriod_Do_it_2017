package com.hansune.calendarproto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import static com.hansune.calendarproto.MConfig.TAG;

/**
 * Created by 224PC on 2017-06-08.
 */
public class AddScheduleActivity extends Activity {

    Button btn_enter, btn_cancle;
    EditText editdate,editschedule,editperiod;
    MyDBHelper mDBHelper;
    int mId;
    String today;

    //time에서 필요한 자원
    Button date_button;
    static EditText text_date; //time.java에서 받기 위해 선언한거
    Intent intent; //새 액티비티 띄우기 위해 선언
    static int yy, mm , dd;// 년 월 일 데이터베이스에 따로 저장하기위해 선언

    //repeate에서 필요한 자원
    static EditText period_date;
    static EditText repeat_time;
    static EditText period;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addschedule);

        //time 자원
        date_button = (Button) findViewById(R.id.select_date);
        text_date = (EditText) findViewById(R.id.text_date);
        period_date=(EditText) findViewById(R.id.period_date);

        btn_cancle=(Button) findViewById(R.id.btn_cancle);
        btn_enter = (Button) findViewById(R.id.btn_enter);
        editdate = (EditText) findViewById(R.id.text_date);
        editschedule = (EditText) findViewById(R.id.text_schedule);
        editperiod = (EditText) findViewById(R.id.period_date);

       //repeat자원
        repeat_time=(EditText) findViewById(R.id.repeat_time);
        period = (EditText) findViewById(R.id.period);



        mDBHelper = new MyDBHelper(this, "Today.db", null, 1);
        Intent intent = getIntent();
        mId = intent.getIntExtra("ParamID", -1);
        today = intent.getStringExtra("ParamDate");




    }

    public void onClick(View v){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        switch (v.getId()){

            //날짜 선택 버튼 누르면 새 액티비티 띄우기
            case R.id.select_date:
                intent = new Intent(this,time.class);
                startActivity(intent);
                break;

            //반복 누르면 새 액티비티 띄우기
            case R.id.repeat:
                intent = new Intent(this, repeat.class);
                startActivity(intent);
                break;

            //등록버튼 누르면 db에 저장,
            case R.id.btn_enter:

                //빈칸 있는지 검사
                if(editschedule.getText().toString().equals("") || editdate.getText().toString().equals("")
                        ||period.getText().toString().equals("") || period_date.getText().toString().equals("")||
                        repeat_time.getText().toString().equals("")) {
                    //경고창 띄움
                    Toast.makeText(this, " 모든 빈칸을 채워주십시오.", Toast.LENGTH_SHORT).show();

                }
                //빈칸이 없으면 데이터 베이스에 저장
                else {
                    Date date = null;

                    String date_int;
                    if (mm <= 9)
                        date_int = Integer.toString(yy) + "0" + Integer.toString(mm) + Integer.toString(dd);
                    else
                        date_int = Integer.toString(yy) + Integer.toString(mm) + Integer.toString(dd);

                    try {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        date = dateFormat.parse(date_int);


                        //현재 날짜에 주기 날짜 더하기
                        for (int i = 0; i < Integer.parseInt(repeat_time.getText().toString()); i++) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            int period_int = Integer.parseInt(editperiod.getText().toString());

                            switch (period.getText().toString()) {
                                case "일":
                                    cal.add(Calendar.DAY_OF_YEAR, period_int * i);
                                    editperiod.setText(editperiod.getText());
                                    break;
                                case "주":
                                    cal.add(Calendar.DAY_OF_YEAR, period_int * 7 * i);
                                    break;
                                case "달":
                                    cal.add(Calendar.MONTH, period_int * i);
                                    break;
                            }

                            //   Log.d(TAG,"date format  1   "+dateFormat.format((cal.getTime())));

                            //Date 형이였던 날짜를 int형으로 다시 바꾸기
                            DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Log.d(TAG, "date format  2   " + sdFormat.format(cal.getTime()));

                            StringTokenizer st_date = new StringTokenizer(sdFormat.format(cal.getTime()), "/");
                            int yy_st = Integer.parseInt(st_date.nextToken());
                            int mm_st = Integer.parseInt(st_date.nextToken());
                            int dd_st = Integer.parseInt(st_date.nextToken());
                            //Log.d(TAG,"date format  3    "+ yy_st +mm_st+dd_st);

                            //데이터 베이스에 저장
                            db.execSQL("INSERT INTO today VALUES(null, '"
                                    + yy_st + "','" + mm_st + "','" + dd_st + "','"
                                    + editschedule.getText().toString() + "', '"
                                    + editperiod.getText().toString() + "');");


                        }
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (java.lang.IllegalStateException ile) {

                    }
                    mDBHelper.close();
                    setResult(RESULT_OK);
                    Toast.makeText(this, " 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, yy + "년 " + mm + "월" + dd + "일" + "에 " + editschedule + "가 " + editperiod + "주기로 저장되었습니다.");
                    finish();
                    break;
                }
            case R.id.btn_cancle:
                finish();
                break;

        }
    }
}

