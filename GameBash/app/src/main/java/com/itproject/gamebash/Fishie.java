package com.itproject.gamebash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Fishie extends AppCompatActivity {

    private final static long Interval = 30;

    private Handler handler = new Handler();
    private PopupWindow popUp;
    private View popUpView;
    private TextView popUpTextView;
    Timer timer;

    FishieGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new FishieGame(this);
        setContentView(game.contentView);

        popUpView = getLayoutInflater().inflate(R.layout.popup, null);
        popUp = new PopupWindow(popUpView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popUpTextView = popUpView.findViewById(R.id.textview);

        popUpView.findViewById(R.id.fishie_quit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(game.sounds.change);
                shouldEndGame = true;
                finish();
            }
        });

        timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (game.isGameOver) {
                            popUp.setOutsideTouchable(false);
                            popUp.setFocusable(false);
                            popUpTextView.setTextSize(24);
                            popUpTextView.setText("Your Score is " + game.score);
                            finish();
                            return;
                        }
                        game.contentView.invalidate();
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, Interval);
    }


    @Override
    protected void onPause() {
        super.onPause();

        Sounds.pause(Sounds.game_music);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Sounds.play(Sounds.game_music);
    }


    boolean shouldEndGame = false;
    @Override
    public void finish() {
        if(shouldEndGame) {
            popUp.dismiss();
            super.finish();
        } else {
            Sounds.play(game.sounds.change);
            popUp.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
            if(game.isGameOver) {
                timer.cancel();
            }
        }
    }

}