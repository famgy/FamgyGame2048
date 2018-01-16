package com.famgy.famgygame2048.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.famgy.famgygame2048.R;

import java.util.Random;
import java.util.TimerTask;

public class BackGroundMusicService extends Service implements MediaPlayer.OnCompletionListener {

    MediaPlayer player;

    private final IBinder binder = new AudioBinder();
    private final Random mGenerator = new Random();

    @Override
    public void onCreate() {
        super.onCreate();
        //建立音频播放
        player = MediaPlayer.create(this, R.raw.login_back);
        player.start();

        //监听音乐是否播放完成
        player.setOnCompletionListener(this);
    }

    //销毁service
    @Override
    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //监听音乐是否完成的函数
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.start();
    }

    public class AudioBinder extends Binder {
        //返回Service对象
        public BackGroundMusicService getService() {
            return BackGroundMusicService.this;
        }
    }

    public int getRandomNumber() {
        return mGenerator.nextInt();
    }
}
