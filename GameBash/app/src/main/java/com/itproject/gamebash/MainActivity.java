package com.itproject.gamebash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
View v;
    // Popup Views and Widgets
    PopupWindow newGamePopUp;
    View newGamePopUpView;
    Button playButton;
    EditText player1;
    EditText player2;

    CardView tic_tac_toe_card;
    CardView fishie_card;

    boolean isShowing=false;

    static Sounds sounds;
    public  void set(final View myButton)
    {
        myButton.setEnabled(false);

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        myButton.setEnabled(true);

                    }
                });
            }
        },1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sounds = new Sounds(this);
        settings();
        final ImageView info = findViewById(R.id.info);
        final ImageView mute = findViewById(R.id.mute);

        tic_tac_toe_card = findViewById(R.id.card0);
        fishie_card = findViewById(R.id.card1);

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(sounds.tap);
                Sounds.mute(mute);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(sounds.change);
                Intent intent = new Intent(MainActivity.this, Info.class);
                startActivity(intent);
            }
        });




        tic_tac_toe_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
                Sounds.play(sounds.tap);
                 set(v);
            }
        });




        fishie_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(sounds.tap);
                Intent intent = new Intent(MainActivity.this, Fishie.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
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



    void startNewGame() {


    newGamePopUpView = getLayoutInflater().inflate(R.layout.pop_up_new_game, null);
    newGamePopUp = new PopupWindow(newGamePopUpView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        playButton = newGamePopUpView.findViewById(R.id.play_button);
        player1 = newGamePopUpView.findViewById(R.id.player1_edit_text);
        player2 = newGamePopUpView.findViewById(R.id.player2_edit_text);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sounds.play(sounds.change);
                if (player1.getText().toString().equals("") || player2.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Player names cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    newGamePopUp.dismiss();
                    Intent intent1 = new Intent(MainActivity.this, TicTacToe.class);
                    intent1.putExtra("player1name", player1.getText().toString());
                    intent1.putExtra("player2name", player2.getText().toString());
                    startActivity(intent1);
                }
            }
        });

        player1.setText("Player 1");
        player2.setText("Player 2");


    newGamePopUp.setAnimationStyle(R.style.pop_animation);
    newGamePopUp.showAtLocation(newGamePopUpView, Gravity.CENTER, 0, 0);

    }


    void settings() {
        Sounds.enableSound = true;
        Sounds.game_music = MediaPlayer.create(this, R.raw.game_music);
        Sounds.game_music.setLooping(true);
    }
}