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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity{

    private ArrayList<Timer> timers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        /*if(sharedPref.contains("times")){

            Gson gson = new Gson();
            String json = sharedPref.getString("times", null);
            Type type = new TypeToken<ArrayList<Timer>>() {
            }.getType();
            timers = gson.fromJson(json, type);

            for(Timer timer: timers){

                timer.getTimerView().resetTimerView();
                timer.setUpTimer();
            }
        }*/


        /*LinearLayout timerContainer = (LinearLayout) findViewById(R.id.contenedor);
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

            @Override
            public void onSwipeRight() {

                for(Timer timer: timers){
                    if(timer.getTimerView().getPlayButton().getTag().equals("pause")){
                        onDestroy();
                    }
                }
                moveTaskToBack(true);
            }
        });
    }

    public void addTimer(Timer timer){

        timers.add(timer);
    }

    /*@Override
    protected void onDestroy(){

        super.onDestroy();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        ViewGroup timerContainer = (ViewGroup) findViewById(R.id.contenedor);
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
            }
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Timer>>(){}.getType();
            String json = gson.toJson(timers, type);
            editor.putString("times", json);
            editor.apply();
    }*/
}

