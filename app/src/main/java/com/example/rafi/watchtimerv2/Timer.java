package com.example.rafi.watchtimerv2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    private TimerView timerView;
    private CountDownTimer countDownTimer;
    private Context context;

    public Timer(final TimerView timerView, final Context context){

        this.context = context;
        //setTimerView(timerView);
        this.timerView = timerView;
        //Set the listener and event handler for pressing the play button
        ImageButton playButton = timerView.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageButton play = (ImageButton) v;

                if(play.getTag().equals("play")){

                    setUpTimer();
                    startTimer();
                    play.setImageResource(R.drawable.ic_media_pause);
                    play.setTag("pause");
                }

                else if(play.getTag().equals("pause")){

                    cancelTimer();
                    play.setImageResource(R.drawable.ic_media_play);
                    play.setTag("play");
                }
            }
        });

        //Set the listener and event handler for the reset button
        ImageButton resetButton = timerView.getResetButton();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countDownTimer!=null)
                    cancelTimer();
                timerView.resetTimerView();
            }
        });

        //Set the listener and event handler for the erase button
        ImageButton eraseButton = timerView.getEraseButton();
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countDownTimer!=null)
                    cancelTimer();
                timerView.removeTimerView();
            }
        });


        /*timerView.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {

                final Activity activity = (Activity) context;
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }

            public void onSwipeRight() {

                final Activity activity = (Activity) context;
                activity.finish();
                System.exit(0);
            }
        });*/
    }


    /*public CountDownTimer getCountDownTimer(){

        return countDownTimer;
    }

    public TimerView getTimerView(){

        return timerView;
    }*/

    /*public void setTimerView(TimerView timerView){

        this.timerView = timerView;
    }*/

    private void setUpTimer(){

        String timeString = timerView.getTime().getText().toString();
        long timeMillis = MilliConversions.stringToMilli(timeString);
        countDownTimer = new CountDownTimer(timeMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                String timeString = MilliConversions.milliToString(millisUntilFinished);
                TextView time = timerView.getTime();
                time.setText(timeString);
            }

            @Override
            public void onFinish() {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                timerView.resetTimerView();
                v.vibrate(250);
            }
        };
    }

    /*private void startTimer(){

        countDownTimer.start();
    }

    private void cancelTimer(){

        countDownTimer.cancel();
    }*/







}
