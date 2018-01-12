package com.famgy.famgygame2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.famgy.famgygame2048.Utils.WaterTextView;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends Activity implements View.OnClickListener{

    private Timer timer;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //两个动画按钮
        final WaterTextView mTvStartGame = (WaterTextView) findViewById(R.id.startGame);
        final WaterTextView mTvStartCharts = (WaterTextView) findViewById(R.id.startCharts);
        final NumberProgressBar numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);

        timer = new Timer();
        counter = 0;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberProgressBar.incrementProgressBy(1);
                        counter++;
                        if (counter == 100) {
                            numberProgressBar.setProgress(0);
                            numberProgressBar.setVisibility(View.INVISIBLE);

                            mTvStartGame.setVisibility(View.VISIBLE);
                            mTvStartCharts.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }, 500, 50);

        mTvStartCharts.setOnClickListener(this);
        mTvStartGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.startGame:
//                intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                finish();
                break;
            case R.id.startCharts:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
