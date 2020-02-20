package com.hansune.calendarproto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hansune.calendarproto.cal.OneMonthView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.hansune.calendarproto.MyDBHelper;

import static android.R.attr.data;
import static com.hansune.calendarproto.MConfig.TAG;

/**
 * Created by 216 on 2017-06-11.
 */

public class List extends Activity {

    MyDBHelper mDBHelper;
    Cursor cursor;
    String today;
    SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        Intent intent= getIntent();
        today=intent.getStringExtra("Param1");

        //클릭한 날짜와 Db에 있는 날짜와 대조하여 맞는지 검사
        //해당 날짜의 리스트 만들기기
       TextView text=(TextView)findViewById(R.id.day_date);
        text.setText(today);

        StringTokenizer st=  new StringTokenizer(today,"/");
        String date_year=st.nextToken();
        String date_month= st.nextToken();
        String date_date=st.nextToken();

        mDBHelper = new MyDBHelper(this, "Today.db", null, 1);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        cursor = db.rawQuery(
                "SELECT * FROM today WHERE yy='"+date_year+
                "' and mm='"+date_month+
                "' and dd='"+date_date+"'", null);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, new String[] {
                "sch", "period" }, new int[] { android.R.id.text1,
                android.R.id.text2 });

        ListView list=(ListView)findViewById(R.id.day_list);
        list.setAdapter(adapter);



    }
}
