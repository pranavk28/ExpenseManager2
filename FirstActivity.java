package com.example.pranav.expensemanager2;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class FirstActivity extends AppCompatActivity {

    EditText t;
    RadioButton r1,r2,r3,r4;
    Button b;
    int id=-1,a,food,travel,lodging,misc,max;
    ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        p = (ProgressBar)findViewById(R.id.progressBar2);
        String startDate,endDate,today;
        t = (EditText)findViewById(R.id.editText);
        r1 = (RadioButton)findViewById(R.id.radioButton2);
        r2 = (RadioButton)findViewById(R.id.radioButton);
        r3 = (RadioButton)findViewById(R.id.radioButton3);
        r4 = (RadioButton)findViewById(R.id.radioButton4);
        b = (Button)findViewById(R.id.button2);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        today = df.format(c.getTime());
        SQLiteDatabase db=openOrCreateDatabase("RCPL_DB5",MODE_APPEND,null);
        //Toast.makeText(getApplicationContext(),"Here",Toast.LENGTH_SHORT).show();
        Cursor cursor=db.rawQuery("Select * from Customer",null);
        while (cursor.moveToNext())
        {
            startDate=cursor.getString(3);
            endDate=cursor.getString(5);
            //Toast.makeText(getApplicationContext(),startDate + endDate,Toast.LENGTH_SHORT);
            if(compare(startDate,today)<=0 && compare(endDate,today)>=0) {
                id = cursor.getInt(0);
                break;
            }
        }
        if(id!=-1) {
            cursor = db.rawQuery("Select * from Expense where id1=" + id + ";", null);
            while(cursor.moveToNext()) {
                max = cursor.getInt(1);
                travel = cursor.getInt(2);
                food = cursor.getInt(3);
                lodging = cursor.getInt(4);
                misc = cursor.getInt(5);
            }
            p.setMax(max);
            p.setProgress(travel+food+lodging+misc);
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==-1) {
                    Toast.makeText(getApplicationContext(), "No active trip now", Toast.LENGTH_SHORT).show();
                } else{
                    //if(t.getText().toString().matches("^\\d+$")){
                        a = Integer.parseInt(t.getText().toString());
                        if((a + food + travel + lodging + misc)>max)
                            Toast.makeText(getApplicationContext(),"Exceeding maximum alloted budget",Toast.LENGTH_SHORT).show();
                        else{
                            p.incrementProgressBy(a);
                            SQLiteDatabase db=openOrCreateDatabase("RCPL_DB5",MODE_APPEND,null);
                            if(r1.isChecked())
                                db.execSQL("Update Expense set travel=" + (a + travel) + " where id1=" + id + ";");
                            else if(r2.isChecked())
                                db.execSQL("Update Expense set food=" + (a + food) + " where id1=" + id + ";");
                            else if(r3.isChecked())
                                db.execSQL("Update Expense set lodging=" + (a + lodging) + " where id1=" + id + ";");
                            else if(r4.isChecked()){
                                db.execSQL("Update Expense set misc=" + (a + misc) + " where id1=" + id + ";");
                            }
                            else
                                Toast.makeText(getApplicationContext(),"Please select an option.",Toast.LENGTH_SHORT);
                        }
                    }
                }

        });
    }
    public int compare(String a, String b){
        String[] c,d;
        c = a.replace("'","").split("-");
        d = b.replace("'","").split("-");
        int y1,y2,m1,m2,d1,d2;
        y1 = Integer.parseInt(c[0]);
        m1 = Integer.parseInt(c[1]);
        d1 = Integer.parseInt(c[2]);
        y2 = Integer.parseInt(d[0]);
        m2 = Integer.parseInt(d[1]);
        d2 = Integer.parseInt(d[2]);
        if(y1<y2)
            return -1;
        else if(y1>y2)
            return 1;
        else if(m1<m2)
            return -1;
        else if(m1>m2)
            return 1;
        else if(d1<d2)
            return -1;
        else if(d1>d2)
            return 1;
        else
            return 0;
    }
}

