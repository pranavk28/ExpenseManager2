package com.example.pranav.expensemanager2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTripActivity extends AppCompatActivity {
    TextView tv1;
    String[] options = {"Sort By ID","Sort By Date"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    SQLiteDatabase db = openOrCreateDatabase("RCPL_DB5", MODE_APPEND, null);
                    if(i==0){
                        Cursor cursor = db.rawQuery("Select * from Customer", null);
                        Cursor cursor1;
                        tv1.setText("");
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
                                details = details + "Approved Budget is:  " + cursor1.getInt(1) + "\n" + "\n";
                            }
                            tv1.append(details);

                        }
                    } else {
                        Cursor cursor = db.rawQuery("Select * from Customer order by startDate", null);
                        Cursor cursor1;
                        tv1.setText("");
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
                                details = details + "Approved Budget:  " + cursor1.getInt(1) + "\n" + "Travel:" + cursor1.getInt(2) + "\n" + "Food:" + cursor1.getInt(3) + "\n" + "Lodging:" + cursor1.getInt(4) + "\n" + "Miscellaneous:" + cursor1.getInt(5) + "\n" + "\n";
                            }
                            tv1.append(details);

                        }
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

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
                    details = details + "Approved Budget:  " + cursor1.getInt(1) + "\n" + "Travel:" + cursor1.getInt(2) + "\n" + "Food:" + cursor1.getInt(3) + "\n" + "Lodging:" + cursor1.getInt(4) + "\n" + "Miscellaneous:" + cursor1.getInt(5) + "\n" + "\n";
                }
                tv1.append(details);

            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
