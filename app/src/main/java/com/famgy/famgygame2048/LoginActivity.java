package com.famgy.famgygame2048;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.famgy.famgygame2048.Service.BackGroundMusicService;
import com.famgy.famgygame2048.Utils.Titanic;
import com.famgy.famgygame2048.Utils.TitanicTextView;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends Activity implements View.OnClickListener{

    private long firsttime = 0;
    private Timer timer;
    private int counter;
    private Intent musicServiceIntent;
    private BackGroundMusicService musicService = null;
    boolean mBound = false;

    TitanicTextView mTvStartGame;
    TitanicTextView mTvStartCharts;

    Titanic startTvStartGame = new Titanic();
    Titanic startTvStartCharts = new Titanic();

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((BackGroundMusicService.AudioBinder)service).getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //两个动画按钮
        mTvStartGame = (TitanicTextView) findViewById(R.id.startGame);
        mTvStartCharts = (TitanicTextView) findViewById(R.id.startCharts);
        final NumberProgressBar numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);

        mTvStartGame.setVisibility(View.INVISIBLE);
        mTvStartCharts.setVisibility(View.INVISIBLE);

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
                int num = musicService.getRandomNumber();
                Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.startCharts:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //animation
        startTvStartGame.start(mTvStartGame);
        startTvStartCharts.start(mTvStartCharts);

        //music
        musicServiceIntent = new Intent(this, BackGroundMusicService.class);
        bindService(musicServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        startTvStartGame.cancel();
        startTvStartCharts.cancel();

        // Unbind from the service
        if (mBound == true) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - firsttime < 3000) {
                finish();
                return true;
            } else {
                firsttime = System.currentTimeMillis();
                Toast.makeText(this, "再点一次退出", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return false;
    }
}
