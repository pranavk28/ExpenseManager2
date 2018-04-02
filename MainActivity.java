package com.example.pranav.expensemanager2;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity
{

    GridView grid;
    String[] web={"Add trip","Add Expenses","Trips Details","Report"};
    int[] imageId={
            R.drawable.addtrip,R.drawable.add,R.drawable.viewtrips,R.drawable.reports};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomGrid adapter=new CustomGrid(MainActivity.this,web,imageId);
        grid=(GridView)findViewById(R.id.grid);

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainActivity.this, "You clicked at" +web[+position], Toast.LENGTH_SHORT).show();

                String str=web[position];

                if(str.equals("Add trip"))
                {
                    Intent i=new Intent(MainActivity.this,AddTripActivity.class);
                    startActivity(i);
                }
                else if(str.equals("Add Expenses"))
                {
                    Intent i=new Intent(MainActivity.this,FirstActivity.class);
                    startActivity(i);
                }
                else if(str.equals("Trips Details"))
                {

                    Intent i=new Intent(MainActivity.this,ViewTripActivity.class);
                    startActivity(i);
                }
                else if(str.equals("Report"))
                {
                    Intent i=new Intent(MainActivity.this,ReportActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}
