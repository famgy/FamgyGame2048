package com.famgy.famgygame2048;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.famgy.famgygame2048.Utils.AnimLayer;

/**
 * Created by famgy on 1/16/18.
 */

public class MainFragment extends Fragment {

    private int score = 0;
    private TextView tvScore;
    private TextView tvBestScore;
    private AnimLayer animLayer = null;
    public static final String SP_KEY_BEST_SCORE = "bestScore";

    public static MainFragment mainFragment;

    public MainFragment() {
        mainFragment = this;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        tvScore = (TextView) rootView.findViewById(R.id.tvScore);
        tvBestScore = (TextView) rootView.findViewById(R.id.tvBestScore);

        animLayer = (AnimLayer) rootView.findViewById(R.id.animLayer);

        return rootView;
    }

    public static MainFragment getMainFragment() {
        return mainFragment;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
    }

    public void showBestScore(int s) {
        tvBestScore.setText(s + "");
    }

    //获取最高分
    public int getBestScore() {
        return getActivity().getPreferences(getActivity().MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }

    public AnimLayer getAnimLayer() {
        return animLayer;
    }

    public void addScore(int s) {
        score += s;
        showScore();
        int maxScore = Math.max(score, getBestScore());
        saveBestScore(maxScore);
        showBestScore(maxScore);
    }

    public void saveBestScore(int s) {

        // 获取  偏好编辑器
        SharedPreferences.Editor e = getActivity().getPreferences(getActivity().MODE_PRIVATE).edit();

        //往编辑器中放东西
        e.putInt(SP_KEY_BEST_SCORE, s);

        //提交
        e.commit();
    }
}
