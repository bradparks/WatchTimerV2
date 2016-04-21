/*
package com.example.rafi.watchtimer;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }
}
*/
package com.example.rafi.watchtimerv2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends Activity implements SetTimer.OnDataPass{

    boolean returningFromHidden;
    ArrayList<Timer> allTimers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if(savedInstanceState!=null){
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> tiempos= gson.fromJson(json, type);
            LayoutInflater inflater = getLayoutInflater();
            final ViewGroup main= (ViewGroup) findViewById(R.id.main);
            final LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
            for (int i=0;i<tiempos.size();i++){
                View timerFrame = inflater.inflate(R.layout.timer, main, false);
                TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
                TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
                numberFrame.setText(tiempos.get(i));
                originalTime.setText(tiempos.get(i));
                parent.addView(timerFrame);
            }
        }*/
        /*SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        long timeLeaving=0;
        //ArrayList<Long> millisRemaining = new ArrayList<>();
        //Map<String, ?> results = sharedPref.getAll();
        if(sharedPref.contains("timeLeaving")){

            timeLeaving = sharedPref.getLong("timeLeaving", 0);
        }

        if(sharedPref.contains("times")){

            Gson gson = new Gson();
            String json = sharedPref.getString("times", null);
            Type type = new TypeToken<ArrayList<Timer>>() {
            }.getType();
            allTimers = gson.fromJson(json, type);
        }*/

       /* LinearLayout timerContainer = (LinearLayout) findViewById(R.id.contenedor);
        long currentTime = System.currentTimeMillis();
        long timeAbsent = currentTime - timeLeaving;
        for(Long millis: millisRemaining){

            long millisLong = millis.longValue();
            if(millisLong-timeAbsent>0){

                String message = MilliConversions.milliToString(millisLong-timeAbsent);
                TimerView timerView = new TimerView(this,message, timerContainer);
                Timer timer = new Timer(timerView, this);
                timer.startTimer();
            }else{
                TimerView timerView = new TimerView(this, "00:00", timerContainer);
                Timer timer = new Timer(timerView, this);
            }
        }*/

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }

            public void onSwipeRight() {
                onStop();
                finish();
                System.exit(0);
            }
        });
    }


     /*@Override
    protected void onStop(){

        super.onStop();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        *//*ViewGroup timerContainer = (ViewGroup) findViewById(R.id.contenedor);
        ArrayList<Long> millisRemaining = new ArrayList<>();
        for (int i=0; i<timerContainer.getChildCount();i++){
            LinearLayout timerViewParent =(LinearLayout) timerContainer.getChildAt(i);
            for (int j=0; j<timerViewParent.getChildCount();j++){
                LinearLayout timerViewChild = (LinearLayout) timerViewParent.getChildAt(j);
                for (int k=0; k<timerViewChild.getChildCount();k++){
                    View textView = timerViewChild.getChildAt(k);
                    if (textView instanceof TextView){
                        long milliRem = MilliConversions.stringToMilli(((TextView) textView).getText().toString());
                        millisRemaining.add(milliRem);
                    }
                }
            }*//*
            *//*Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Timer>>(){}.getType();
            String json = gson.toJson(allTimers, type);
            editor.putString("times", json);*//*
            //editor.apply();
            long timeLeaving = System.currentTimeMillis();
            editor.putLong("timeLeaving", timeLeaving);
            editor.apply();
        //}
    }*/

    @Override
    protected void onStop() {

        super.onStop();

        ViewGroup timerContainer = (ViewGroup) findViewById(R.id.contenedor);
        ArrayList<Integer> millisRemaining = new ArrayList<>();
        for (int i = 0; i < timerContainer.getChildCount(); i++) {
            LinearLayout timerViewParent = (LinearLayout) timerContainer.getChildAt(i);
            for (int j = 0; j < timerViewParent.getChildCount(); j++) {
                LinearLayout timerViewChild = (LinearLayout) timerViewParent.getChildAt(j);
                for (int k = 0; k < timerViewChild.getChildCount(); k++) {
                    View textView = timerViewChild.getChildAt(k);
                    if (textView instanceof TextView) {
                        long milliRem = MilliConversions.stringToMilli(((TextView) textView).getText().toString());
                        millisRemaining.add((int) milliRem);
                    }
                }
            }
        }
        Intent intent = new Intent(this, TimerCounter.class);
        intent.putIntegerArrayListExtra("millisRemaining", millisRemaining);
        startService(intent);
    }
    @Override
    public void onDataPass(Timer timer) {

        allTimers.add(timer);
    }
}

