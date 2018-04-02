package com.example.pranav.expensemanager2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        int food=0,lodging=0,travel=0,misc=0,n=0,rec;
        String s = "";
        tv1=(TextView)findViewById(R.id.textView1);
        try {
            SQLiteDatabase db = openOrCreateDatabase("RCPL_DB5", MODE_APPEND, null);
            Cursor cursor = db.rawQuery("Select * from Customer", null);
            Cursor cursor1;

            while (cursor.moveToNext()) {
                int roll = cursor.getInt(0);
                String boarding = cursor.getString(1);
                String destination1 = cursor.getString(2);
                String startDate1 = cursor.getString(3);
                String number = cursor.getString(4);
                String endDate1 = cursor.getString(5);
                cursor1 = db.rawQuery("Select * from Expense where id1 = " + roll + ";" , null);
                String details = "Trip ID  " + roll + "\n" + "From:  " + boarding + "\n" + "Destination:  " + destination1 + "\n" + "Start Date:  " + startDate1 + "\n" + "End Date:  " + endDate1 + "\n" + "Number of people:  " + number + "\n";
                while(cursor1.moveToNext()) {
                    n++;
                    travel+=cursor1.getInt(2);
                    food+=cursor1.getInt(3);
                    lodging+=cursor1.getInt(4);
                    misc+=cursor1.getInt(5);
                    details = details + "Approved Budget:  " + cursor1.getInt(1) + "\n" + "Travel:" + cursor1.getInt(2) + "\n" + "Food:" + cursor1.getInt(3) + "\n" + "Lodging:" + cursor1.getInt(4) + "\n" + "Miscellaneous:" + cursor1.getInt(5) + "\n" + "\n";
                }
                tv1.append(details);
            }
            s+=("Average travel costs per trip :" + (travel/n) + "\n");
            s+=("Average food costs per trip :" + (food/n) + "\n");
            s+=("Average lodging costs per trip :" + (lodging/n) + "\n");
            s+=("Average miscellaneous costs per trip :" + (misc/n) + "\n");
            tv1.append(s);
            rec = (travel + food + lodging + misc)/n;
            String[] cities = {"Bangalore","Chandigarh","Chennai","Delhi","Gurgaon","Hyderabad","Indore","Lucknow","Mangalore","Mumbai","Patna"};
            int[] costs = {4000,3500,6000,4500,5000,2000,2900,7000,4000,1900};
            tv1.append("Recommended cities for trip : ");
            s = "";
            for(int i=0;i<10;i++){
                if(costs[i]<rec){
                    if(i!=9)
                        s+=(cities[i]+",");
                    else
                        s+=(cities[i]+"\n");
                }

            }
            tv1.append(s);
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
