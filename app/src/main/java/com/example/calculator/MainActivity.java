package com.example.calculator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    protected TextView t1,t2;
    int flag,f;
    String result,equation;
    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "channel1";

    public void putOne(View v){
        putNum("1");
    }

    public void putTwo(View v){
        putNum("2");
    }

    public void putThree(View v){
        putNum("3");
    }

    public void putFour(View v){
        putNum("4");
    }

    public void putFive(View v){
        putNum("5");
    }

    public void putSix(View v){
        putNum("6");
    }

    public void putSeven(View v){
        putNum("7");
    }

    public void putEight(View v){
        putNum("8");
    }

    public void putNine(View v){
        putNum("9");
    }

    public void putZero(View v){
        putNum("0");
    }

    public void putDot(View v){
        if(equation.equals("INVALID OPERATION") || equation.equals("Cannot Divide by 0") || equation.equals("") ||
                equation.equals("RANGE EXCEEDED") || flag==1) {
            equation="0";
            flag=0;
        }
        if(equation.charAt(equation.length()-1)=='+' || equation.charAt(equation.length()-1)=='-' ||
                equation.charAt(equation.length()-1)=='*' || equation.charAt(equation.length()-1)=='/')
            equation=equation+"0";
        equation=equation+".";
        t1.setText(equation);
    }

    private void putNum(String n){
        if(equation.equals("0") || flag==1) {
            equation = "";
            flag = 0;
        }
        equation=equation+n;
        t1.setText(equation);
    }

    private void calculate(){
        if(equation.contains("..")){
            result="INVALID OPERATION";
            return;
        }
        int i=0;
        float num1=0,num2=0;
        char[] tokens = equation.toCharArray();
        char op='\0';
        for (i=tokens.length-1;i>=0;i--) {
            if(tokens[i]=='+' || tokens[i]=='-' || tokens[i]=='*' || tokens[i]=='/'){
                op=tokens[i];
                break;
            }
        }
        if(i>0 && i!= tokens.length-1){
            num1=Float.parseFloat(equation.substring(0,i));
            num2=Float.parseFloat(equation.substring(i+1));
            switch (op){
                case '+':
                    result=Float.toString(num1+num2);
                    break;
                case '-':
                    result=Float.toString(num1-num2);
                    break;
                case '*':
                    result=Float.toString(num1*num2);
                    break;
                case '/':
                    if(num2!=0) result=Float.toString(num1/num2);
                    else result="Cannot Divide by 0";
                    break;
            }
            equation=result;
        }
        else if(equation.charAt(equation.length()-1)=='+' || equation.charAt(equation.length()-1)=='-' ||
                equation.charAt(equation.length()-1)=='*' || equation.charAt(equation.length()-1)=='/')
            result=equation.substring(0,equation.length()-1);
        else
            result=equation;
    }

    private void putOperator(String op){
        if(flag==1) flag=0;
        if(equation.equals("INVALID OPERATION") || equation.equals("Cannot Divide by 0") || equation.equals("") || equation.equals("RANGE EXCEEDED"))
            equation="0";
        if(equation.charAt(equation.length()-1)=='+' || equation.charAt(equation.length()-1)=='-' || equation.charAt(equation.length()-1)=='*' || equation.charAt(equation.length()-1)=='/')
            equation=equation.substring(0,equation.length()-1);
        if(equation.length()<=12 || equation.equals("Cannot Divide by 0") || equation.equals("INVALID OPERATION"))
            calculate();
        else
            result="RANGE EXCEEDED";
        t2.setText(result);
        equation=equation.concat(op);
        t1.setText(equation);
    }

    public void doSum(View v) {
        putOperator("+");
    }

    public void doSub(View v){
        putOperator("-");
    }

    public void doMul(View v){
        putOperator("*");
    }

    public void doDiv(View v){
        putOperator("/");
    }

    public void doEqual(View v) {
        flag=1;
        if(equation.length()<=12 || equation.equals("Cannot Divide by 0") || equation.equals("INVALID OPERATION"))
            calculate();
        else
            result="RANGE EXCEEDED";
        t2.setText(result);
    }

    public void clearNum(View v){
        equation="0";
        t1.setText(equation);
        t2.setText("");
    }

    private void toPop(){
        if(result.equals("") || result.equals(null))
            return;
        else{
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID, "Channel1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    private void toNotify(){
        if(result.equals("")){
            result ="INVALID OPERATION";
        }
        Notification notification=new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_calculator)
                .setContentTitle("Result")
                .setContentText(result)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    private void toShare(){
        if(result.equals("")){
            result ="INVALID OPERATION";
        }
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, result);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Result");
        startActivity(Intent.createChooser(sharingIntent,"Share using"));
    }

    public void toPop(View v){
        toPop();
    }

    public void toNotify(View v){
        toNotify();
    }

    public void toShare(View v){
        toShare();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.agecalc:
                Intent intent = new Intent(MainActivity.this,AgeCalculator.class);
                startActivity(intent);
                return true;
            case R.id.calc:
                return true;
            default:
                return true;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.num);
        result ="0";
        equation="0";
        t2 = findViewById(R.id.result);
        flag=0;
        f=0;

        createNotificationChannels();
        notificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        equation="0";
        t1.setText(equation);
        result="0";
        t2.setText(result);
    }

}