package com.example.towers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private GameView gameView;

    private Button startButton;
    private Random random;

    private int touchX, touchY;

    private boolean gameInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();

        gameInProgress = false;

        game = new Game(4);
        gameView = findViewById(R.id.game);
        gameView.setGame(game);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX = (int) event.getX();
                    touchY = (int) event.getY();
                }
                return false;
            }
        });

        gameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameInProgress) {
                    return;
                }
                v = (GameView) v;
                Block.Color color = Block.Color.values()[random.nextInt(Block.Color.values().length)];
                GameView.GameClickEvent event = ((GameView) v).whichTowerWasClicked(touchX);
                if (event == null) {
                    return;
                }
                if (event.type == GameView.GameClickEvent.ClickType.Simple){
                    game.play(new TowerSegment(color), event.towerNumber);
                } else {
                    game.play(new Bridge(color), event.bridgeStart, event.bridgeEnd);
                }
            }
        });

        startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int animationDuration = getResources().getInteger(
                        android.R.integer.config_longAnimTime

                );

                startButton.animate()
                        .alpha(0f)
                        .setDuration(animationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                startButton.setVisibility(View.GONE);
                                gameInProgress = true;
                            }
                        });
            }
        });
    }
}
