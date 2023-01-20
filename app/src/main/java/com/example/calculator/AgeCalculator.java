package com.example.calculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class AgeCalculator extends AppCompatActivity {
    TextView ageView;
    ArrayAdapter ad,day_ad;
    String result;
    int cur;
    String day,month,year;
    Spinner daySpin,monthSpin,yearSpin;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void doCalculate(View v){
        if(day!="-Day-" && month!="-Month-"){
            LocalDate dob = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
            Period period = Period.between(dob,LocalDate.now());
            result=period.getYears()+" Years\n"+ period.getMonths()+" Months\n"+period.getDays()+" Days";
        }
        else
            result="Select Month and Date";
        ageView.setText(result);
    }

    public void putMonth(){
        monthSpin=findViewById(R.id.month);
        ad=ArrayAdapter.createFromResource(this,
                R.array.months,R.layout.spinner_layout);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpin.setAdapter(ad);

    }

    public void putYear(){
        cur= Calendar.getInstance().get(Calendar.YEAR);
        String yearStr="";
        for(int i=1900;i<=cur;i++){
            yearStr=yearStr+Integer.toString(i)+",";
        }
        String[] yearList= yearStr.split(",");
        yearSpin=findViewById(R.id.year);
        ad=new  ArrayAdapter(this, android.R.layout.simple_spinner_item,yearList);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpin.setAdapter(ad);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.calc:
                Intent intent = new Intent(AgeCalculator.this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.agecalc:
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_calculator);

        day=Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        month=Integer.toString(Calendar.getInstance().get(Calendar.MONTH));
        year=Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        result="";
        ageView=findViewById(R.id.age);

        putYear();
        yearSpin.setSelection(Calendar.getInstance().get(Calendar.YEAR)-1900);

        putMonth();
        monthSpin.setSelection(Calendar.getInstance().get(Calendar.MONTH));

        day_ad=ArrayAdapter.createFromResource(this,R.array.empty_day,R.layout.spinner_layout);

        yearSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year=yearSpin.getSelectedItem().toString();
                monthSpin=findViewById(R.id.month);
                monthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        month= monthSpin.getSelectedItem().toString();
                        daySpin=findViewById(R.id.day);
                        int parentID=adapterView.getId();
                        if(parentID==R.id.month){
                            switch(Integer.parseInt(month)){
                                case 1:
                                case 3:
                                case 5:
                                case 7:
                                case 8:
                                case 10:
                                case 12:
                                    day_ad=ArrayAdapter.createFromResource(adapterView.getContext(),
                                            R.array.days_31,R.layout.spinner_layout);
                                    break;
                                case 4:
                                case 6:
                                case 9:
                                case 11:
                                    day_ad=ArrayAdapter.createFromResource(adapterView.getContext(),
                                            R.array.days_30,R.layout.spinner_layout);
                                    break;
                                case 2:
                                    if(Integer.parseInt(year)%4==0)
                                        day_ad=ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.days_29,R.layout.spinner_layout);
                                    else
                                        day_ad=ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.days_28,R.layout.spinner_layout);
                                    break;
                                default:
                                    break;
                            }
                            day_ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            daySpin.setAdapter(day_ad);
                            daySpin.setSelection(0);
                            daySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    day=daySpin.getSelectedItem().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onStop() {
        super.onStop();
    }
}
