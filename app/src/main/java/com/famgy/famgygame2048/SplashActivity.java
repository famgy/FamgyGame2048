package com.famgy.famgygame2048;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SplashActivity extends FragmentActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(startLoginActivity, 2000);
    }

    Runnable startLoginActivity = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
