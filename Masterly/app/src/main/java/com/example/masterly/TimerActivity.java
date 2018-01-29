package com.example.masterly;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    //default to 10 min
    private static long START_TIME_IN_MILLIS = 600000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTextViewCountDown = (TextView) findViewById(R.id.countdownTextView);

        mButtonStartPause = (Button) findViewById(R.id.startPauseButton);
        mButtonReset = (Button) findViewById(R.id.resetButton);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        Button setTimeButton = (Button) findViewById(R.id.setTimeButton);
        final EditText hoursEditText = (EditText) findViewById(R.id.hoursEditText);
        final EditText minutesEditText = (EditText) findViewById(R.id.minutesEditText);
        final EditText secondsEditText = (EditText) findViewById(R.id.secondsEditText);

        setTimeButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        int hours = Integer.parseInt(hoursEditText.getText().toString());
                        int minutes = Integer.parseInt(minutesEditText.getText().toString());
                        int seconds = Integer.parseInt(secondsEditText.getText().toString());

                        START_TIME_IN_MILLIS = (hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000);

                        Log.d("Countdown", hours+":" + minutes +":" +seconds);

                        mTimeLeftInMillis = START_TIME_IN_MILLIS;
                        updateCountDownText();

                        hoursEditText.setText("");
                        minutesEditText.setText("");
                        secondsEditText.setText("");
                    }
                }

        );

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        long millis = mTimeLeftInMillis;
        //Convert milliseconds into hour,minute and seconds
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        mTextViewCountDown.setText(hms);//set text
    }
}
