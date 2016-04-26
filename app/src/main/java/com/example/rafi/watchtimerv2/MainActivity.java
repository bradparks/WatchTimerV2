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
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends WearableActivity{

    private ArrayList<TimerView> timers = new ArrayList<>();
    //boolean timersRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

       /* SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.contains("times")){

            Gson gson = new Gson();
            String json = sharedPref.getString("times", null);
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> timeWhenLeaving = new ArrayList<>();
            timeWhenLeaving = gson.fromJson(json, type);
            for(String times:timeWhenLeaving){

                TimerView timerView = new TimerView(this,times, (LinearLayout)findViewById(R.id.contenedor));
                Timer timer = new Timer(timerView, this);
                timers.add(timer);
            }
            //Toast.makeText(this,json,Toast.LENGTH_SHORT).show();
        }*/

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            @Override
            public void onSwipeLeft() {

                setTimerFragment();
            }

            @Override
            public void onSwipeRight() {

                moveTaskToBack(true);
                /*for(Timer timer: timers){
                    if(timer.getTimerView().getPlayButton().getTag().equals("pause")){
                        timersRunning = true;
                    }
                }
                if(timersRunning){
                    moveTaskToBack(true);
                    timersRunning = false;
                }
                else{
                    timersRunning = false;
                    saveData();
                    finish();
                }*/
            }
        });
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails){

        super.onEnterAmbient(ambientDetails);

        RelativeLayout main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.BLACK);
        if(timers.size()!=0){

            for(TimerView timerView: timers){
                LinearLayout container = timerView.getTimerContainer();
                LinearLayout border = (LinearLayout) container.findViewById(R.id.hijo);
                border.setBackgroundColor(Color.WHITE);
                for (int i=0;i<border.getChildCount();i++){
                    border.getChildAt(i).setBackgroundColor(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void onExitAmbient(){

        super.onExitAmbient();

        RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
        main.setBackgroundColor(Color.parseColor("#303F9F"));

        if(timers.size()!=0){

            for(TimerView timerView: timers){
                LinearLayout container = timerView.getTimerContainer();
                LinearLayout border = (LinearLayout) container.findViewById(R.id.hijo);
                border.setBackgroundColor(Color.BLACK);
                for (int i=0;i<border.getChildCount();i++){
                    border.getChildAt(i).setBackgroundColor(Color.parseColor("#303F9F"));
                }
            }
        }
    }

    public void setTimerFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SetTimer fragment = new SetTimer();
        fragmentTransaction.add(R.id.main, fragment);
        fragmentTransaction.commit();
    }

    public ArrayList<TimerView> getTimerArray(){

        return timers;
    }

   /* public void saveData(){

        ArrayList<String> timeWhenLeaving = new ArrayList<>();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();

        for(Timer timer: timers){

            timeWhenLeaving.add(timer.getTimerView().getTime().getText().toString());
        }
        String json = gson.toJson(timeWhenLeaving, type);
        //String json = "Probando la guardada";
        editor.putString("times", json);
        editor.apply();
    }*/
}

