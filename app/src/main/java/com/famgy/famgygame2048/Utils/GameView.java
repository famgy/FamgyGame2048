package com.famgy.famgygame2048.Utils;


import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.famgy.famgygame2048.MainFragment;
import com.famgy.famgygame2048.Model.Card;
import com.famgy.famgygame2048.R;

import java.util.ArrayList;
import java.util.List;


public class GameView extends LinearLayout {

    private Context context;
    private MediaPlayer player;
    private Card[][] cardsMap = new Card[Config.LINES][Config.LINES];
    private List<Point> emptyPoints = new ArrayList<Point>();

    public GameView(Context context) {
        super(context);
        this.context = context;
        player = MediaPlayer.create(context, R.raw.move);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        player = MediaPlayer.create(context, R.raw.move);
        initGameView();
    }


    //初始化Gameview
    private void initGameView() {

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(0xffbbada0);
    }

    //初始化卡片大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Config.CARD_WIDTH = (Math.min(w, h) - 10) / Config.LINES;
        addCards(Config.CARD_WIDTH, Config.CARD_WIDTH);

        startGame();
    }

    //添加卡片
    private void addCards(int cardWidth, int cardHeight) {

        Card c;

        LinearLayout line;
        LayoutParams lineLp;

        for (int y = 0; y < Config.LINES; y++) {
            line = new LinearLayout(getContext());
            lineLp = new LayoutParams(-1, cardHeight);
            addView(line, lineLp);

            for (int x = 0; x < Config.LINES; x++) {
                c = new Card(getContext());
                line.addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }

    //开始游戏（也是重新开始）
    public void startGame() {

        MainFragment aty = MainFragment.getMainFragment();
        aty.clearScore();
        aty.showBestScore(aty.getBestScore());


        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    //添加随机卡片
    private void addRandomNum() {

        emptyPoints.clear();
        for (int y = 0; y < Config.LINES; y++) {
            for (int x = 0; x < Config.LINES; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        if (emptyPoints.size() > 0) {

            Point p = emptyPoints.remove((int) (Math.random() * emptyPoints
                    .size()));
            cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

            MainFragment.getMainFragment().getAnimLayer()
                    .createScaleTo1(cardsMap[p.x][p.y]);
        }
    }

}
