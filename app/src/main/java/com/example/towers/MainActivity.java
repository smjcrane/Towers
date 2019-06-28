package com.example.towers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private GameView gameView;

    private WhatsNext whatsNext;
    private WhatsNextView whatsNextView;

    private Button startButton;

    private int touchGameX, touchGameY, touchNextX, touchNextY;

    private boolean gameInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameInProgress = false;

        game = new Game(4);
        gameView = findViewById(R.id.game);
        gameView.setGame(game);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchGameX = (int) event.getX();
                    touchGameY = (int) event.getY();
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
                Block.Color color = whatsNext.getSelectedColor();
                if (color == null){
                    return;
                }
                GameView.GameClickEvent event = ((GameView) v).whichTowerWasClicked(touchGameX);
                if (event == null) {
                    return;
                }
                if (event.type == GameView.GameClickEvent.ClickType.Simple){
                    game.play(new TowerSegment(color), event.towerNumber);
                } else {
                    game.play(new Bridge(color), event.bridgeStart, event.bridgeEnd);
                }
                whatsNext.playSelected();
                whatsNext.deselect();
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

        whatsNext = new WhatsNext();
        whatsNextView = findViewById(R.id.whatsNext);
        whatsNextView.setWhatsNext(whatsNext);

        whatsNextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchNextX = (int) event.getX();
                    touchNextY = (int) event.getY();
                }
                return false;
            }
        });

        whatsNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameInProgress) {
                    return;
                }
                int selected = whatsNextView.whatWasSelected(touchNextX);
                if (selected == WhatsNext.NONE){
                    whatsNext.deselect();
                } else {
                    whatsNext.select(selected);
                }
            }
        });
    }
}
