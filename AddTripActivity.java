package com.example.pranav.expensemanager2;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
public class AddTripActivity extends AppCompatActivity {
    EditText from, destination, startDate, endDate, approvedBudget, number;
    String str,destination1;
    boolean flag;
    int i;
    ArrayList<String> cities;
    ArrayList<Long> costs;

    FirebaseDatabase fbDatabase;
    //TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        if(fbDatabase == null) {
            fbDatabase = FirebaseDatabase.getInstance();
            fbDatabase.setPersistenceEnabled(true);
        }
        // message=(TextView) findViewById(R.id.message);
        from = (EditText) findViewById(R.id.from);
        destination = (EditText) findViewById(R.id.destination);
        startDate = (EditText) findViewById(R.id.startDate);
        endDate = (EditText) findViewById(R.id.endDate);
        approvedBudget=(EditText)findViewById(R.id.approvedBudget);
        number=(EditText)findViewById(R.id.number);
        SQLiteDatabase db = openOrCreateDatabase("RCPL_DB5",MODE_APPEND,null);
        db.execSQL("Create table if not exists Customer(id1 INTEGER PRIMARY KEY AUTOINCREMENT,board varchar,destination varchar,startDate TEXT,people Integer,endDate TEXT)");
        db.execSQL("Create table if not exists Expense(id1 INTEGER PRIMARY KEY AUTOINCREMENT,approvedBudget INTEGER, travel INTEGER, food INTEGER, lodging INTEGER, misc INTEGER)");
        /*String query1="insert into Customer(id1,board,destination,startDate,people,endDate)values(" + "'2'" + "," + "'Delhi'" + "," + "'Mumbai'" + "," + "'2017-07-06'" + "," + "'5'" + "," + "'2017-08-09'"+")";
          String query2="insert into Expense(id1,approvedBudget,travel,food,lodging,misc)values(" + "2" + "," + "'30000'" + "," + "'4000'" + "," + "'5000'" + "," + "'7000'" + "," + "'10000'" + ")";
          db.execSQL(query1);
          db.execSQL(query2);
          query1="insert into Customer(id1,board,destination,startDate,people,endDate)values(" + "'1'" + "," + "'Delhi'" + "," + "'Bangalore'" + "," + "'2017-08-15'" + "," + "'4'" + "," + "'2017-09-09'"+")";
          query2="insert into Expense(id1,approvedBudget,travel,food,lodging,misc)values(" + "1" + "," + "40000" + "," + "'5000'" + "," + "'6000'" + "," + "'9500'" + "," + "'11000'" + ")";
          db.execSQL(query1);
          db.execSQL(query2);
          query1="insert into Customer(id1,board,destination,startDate,people,endDate)values(" + "'3'" + "," + "'Delhi'" + "," + "'Ahemdabad'" + "," + "'2017-09-16'" + "," + "'6'" + "," + "'2017-10-11'"+")";
          query2="insert into Expense(id1,approvedBudget,travel,food,lodging,misc)values(" + "3" + "," + "35000" + "," + "'5000'" + "," + "'5000'" + "," + "'9000'" + "," + "'12000'" + ")";
          db.execSQL(query1);
          db.execSQL(query2);
          query1="insert into Customer(id1,board,destination,startDate,people,endDate)values(" + "'4'" + "," + "'Bangalore'" + "," + "'Mumbai'" + "," + "'2017-10-18'" + "," + "'5'" + "," + "'2017-10-29'"+")";
          query2="insert into Expense(id1,approvedBudget,travel,food,lodging,misc)values(" + "4" + "," + "31000" + "," + "'6000'" + "," + "'4000'" + "," + "'8000'" + "," + "'13000'" + ")";
          db.execSQL(query1);
          db.execSQL(query2);
          query1="insert into Customer(id1,board,destination,startDate,people,endDate)values(" + "'5'" + "," + "'Udaipur'" + "," + "'Delhi'" + "," + "'2018-01-05'" + "," + "'7'" + "," + "'2018-01-25'"+")";
          query2="insert into Expense(id1,approvedBudget,travel,food,lodging,misc)values(" + "5" + "," + "32000" + "," + "'4000'" + "," + "'6000'" + "," + "'5000'" + "," + "'10000'" + ")";
          db.execSQL(query1);
          db.execSQL(query2);*/

        db.close();
        Toast.makeText(this, "Table is available", Toast.LENGTH_SHORT).show();

    }
    //////////////////////////////////calendar start///////////////////////////////////
    public void opencalender1(View v)
    {
        MyDateChooser1 ref=new MyDateChooser1();
        Date d=new Date();
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(d);
        int y=gc.get(Calendar.YEAR);
        int m=gc.get(Calendar.MONTH);
        int dt=gc.get(Calendar.DATE);
        DatePickerDialog dialog=new DatePickerDialog(this,ref,y,m,dt);
        dialog.show();

    }
    class MyDateChooser1 implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int date)
        {
            if(month<9 && date<10)
                str=year+"-0"+(month+1)+"-0"+date;
            else if(month<9)
                str=year+"-0"+(month+1)+"-"+date;
            else if(date<10)
                str=year+"-"+(month+1)+"-0"+date;
            else
                str=year+"-"+(month+1)+"-"+date;
            startDate.setText(str);
        }
    }

    public void opencalender2(View v)
    {
        MyDateChooser2 ref=new MyDateChooser2();
        Date d=new Date();
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(d);
        int y=gc.get(Calendar.YEAR);
        int m=gc.get(Calendar.MONTH);
        int dt=gc.get(Calendar.DATE);
        DatePickerDialog dialog=new DatePickerDialog(this,ref,y,m,dt);
        dialog.show();

    }
    class MyDateChooser2 implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int date)
        {
            if(month<9 && date<10)
                str=year+"-0"+(month+1)+"-0"+date;
            else if(month<9)
                str=year+"-0"+(month+1)+"-"+date;
            else if(date<10)
                str=year+"-"+(month+1)+"-0"+date;
            else
                str=year+"-"+(month+1)+"-"+date;
            endDate.setText(str);
        }
    }

    /////////////////////////////////////////calendar end//////////////////////////

    public void insertrecord(View v)
    {

        int id=-1;
        String boarding = from.getText().toString();
        destination1 = destination.getText().toString();
        String startDate1=startDate.getText().toString();
        String startdate2,enddate2;
        String endDate1=endDate.getText().toString();
        String num=number.getText().toString();
        String approvedBudget1=approvedBudget.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase("RCPL_DB5", MODE_APPEND, null);
        boarding = "'"+boarding+"'";
        destination1 = "'"+destination1+"'";
        startDate1 = "'"+startDate1+"'";
        endDate1="'"+endDate1+"'";
        num="'"+num+"'";
        approvedBudget1="'"+approvedBudget1+"'";
        try {
            if((from.getText().toString()).equals(""))
            {
                Toast.makeText(this,"Please fill FROM field", Toast.LENGTH_SHORT).show();
            }
            else if((destination.getText().toString()).equals(""))
            {   Toast.makeText(this,"Please fill DESTINATION field", Toast.LENGTH_SHORT).show();}
            else if((startDate.getText().toString()).equals(""))
            { Toast.makeText(this,"Please select Start Date", Toast.LENGTH_SHORT).show();}
            else if((endDate.getText().toString()).equals(""))
            {   Toast.makeText(this,"Please select End Date", Toast.LENGTH_SHORT).show();}
            else if((approvedBudget.getText().toString()).equals(""))
            {  Toast.makeText(this,"Please fill Approved Budget field", Toast.LENGTH_SHORT).show();}
            else if((number.getText().toString()).equals(""))
            {  Toast.makeText(this,"Please fill Number of people", Toast.LENGTH_SHORT).show();}
            else if(!(num.matches("^'\\d+'$")))
            { Toast.makeText(this,"Invalid number of people"+num, Toast.LENGTH_SHORT).show(); }
            else if(!(startDate1.matches("^'\\d\\d\\d\\d-[0]\\d-[012]\\d'$")) && !(startDate1.matches("^'\\d\\d\\d\\d-[0]\\d-3[01]'$")) && !(startDate1.matches("^'\\d\\d\\d\\d-1[012]-[012]\\d'$")) && !(startDate1.matches("^'\\d\\d\\d\\d-1[012]-3[01]'$")))
            { Toast.makeText(this,"Invalid start date", Toast.LENGTH_SHORT).show(); }
            else if(!(endDate1.matches("^'\\d\\d\\d\\d-[0]\\d-[012]\\d'$")) && !(endDate1.matches("^'\\d\\d\\d\\d-[0]\\d-3[01]'$")) && !(endDate1.matches("^'\\d\\d\\d\\d-1[012]-[012]\\d'$")) && !(endDate1.matches("^'\\d\\d\\d\\d-1[012]-3[01]'$")))
            { Toast.makeText(this,"Invalid end date", Toast.LENGTH_SHORT).show(); }
            else if(compare(startDate1,endDate1)>0)
            { Toast.makeText(this,"Invalid start and end dates", Toast.LENGTH_SHORT).show(); }
            else
            {
                String query = "insert into Customer(board,destination,startDate,people,endDate)values(" + boarding + "," + destination1 + "," + startDate1 + "," + num + "," + endDate1 + ")";
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Calendar c = Calendar.getInstance();
                Cursor cursor=db.rawQuery("Select * from Customer",null);
                while (cursor.moveToNext())
                {
                    startdate2=cursor.getString(3);
                    enddate2=cursor.getString(5);
                    //Toast.makeText(getApplicationContext(),"Here",Toast.LENGTH_SHORT).show();
                    if((compare(startdate2,startDate1)<=0 && compare(enddate2,startDate1)>=0) || (compare(startdate2,endDate1)<=0 && compare(enddate2,endDate1)>=0)){
                        id = cursor.getInt(0);
                        break;
                    }
                }
                if(id!=-1)
                    Toast.makeText(getApplicationContext(),"Trip date clashes with another trip",Toast.LENGTH_SHORT).show();
                else {
                    //Toast.makeText(getApplicationContext(),"Here4",Toast.LENGTH_SHORT).show();
                    try {
                        flag = false;
                        i = 0;
                        //cities = new ArrayList<String>();
                        //costs = new ArrayList<Long>();
                        DatabaseReference dbRef = fbDatabase.getReference().child("Costs");
                        /*dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String s = ",";
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    s += (postSnapshot.getKey() + "," + "\n");
                                    if (("'" + postSnapshot.getKey() + "'").equalsIgnoreCase(destination1)) {
                                        flag = true;
                                        i = (long)postSnapshot.getValue();
                                        break;
                                    }
                                    cities.add(postSnapshot.getKey());
                                    costs.add((long)postSnapshot.getValue());
                                }
                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/
                    String[] cities = {"Bangalore","Chandigarh","Chennai","Delhi","Gurgaon","Hyderabad","Indore","Lucknow","Mangalore","Mumbai","Patna"};
                    int[] costs = {4000,3500,6000,4500,5000,2000,2900,7000,4000,1900};
                    /*readData(dbRef, new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            cities.add(dataSnapshot.getKey());
                            HashMap<String,Object> map = (HashMap<String, Object>)dataSnapshot.getValue();
                            costs.add((long)map.get(dataSnapshot.getKey()));
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure() {

                        }
                    });*/
                    for(i=0;i<10;i++){
                        if(("'"+cities[i]+"'").equalsIgnoreCase(destination1)) {
                            flag = true;
                            break;
                        }
                    }
                        if (flag) {
                            Date start, end;
                            start = df.parse(startDate.getText().toString());
                            end = df.parse(endDate.getText().toString());
                            long getDiff = end.getTime() - start.getTime();
                            long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
                            if (Integer.parseInt(approvedBudget.getText().toString()) < (costs[i] * getDaysDiff))
                                Toast.makeText(getApplicationContext(), "Alloted budget too low for the trip", Toast.LENGTH_SHORT).show();
                            else {
                            db.execSQL(query);
                            Toast.makeText(this, "Record inserted successfully\n"+i, Toast.LENGTH_SHORT).show();
                            query = "insert into Expense(approvedBudget,travel,food,lodging,misc) values (" + approvedBudget1 + ",'0','0','0','0')";
                            db.execSQL(query);
                            }
                        } else {
                        db.execSQL(query);
                        Toast.makeText(this, "Record inserted successfully", Toast.LENGTH_SHORT).show();
                        query = "insert into Expense(approvedBudget,travel,food,lodging,misc) values (" + approvedBudget1 + ",'0','0','0','0')";
                        db.execSQL(query);
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }

                db.close();

            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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

