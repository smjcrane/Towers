package com.example.towers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();

        game = new Game(4);
        gameView = findViewById(R.id.game);
        gameView.setGame(game);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    touchX = (int) event.getX();
                    touchY = (int) event.getY();
                }
                return false;
            }
        });

        gameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v = (GameView) v;
                Block.Color color = Block.Color.values()[random.nextInt(Block.Color.values().length)];
                int tower = ((GameView) v).whichTowerWasClicked(touchX);
                game.play(new TowerSegment(color), tower);
            }
        });

        startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t = random.nextInt(4);
                Block.Color color = Block.Color.values()[random.nextInt(Block.Color.values().length)];
                game.play(new TowerSegment(color), t);
                Log.d("MAIN", "\n"+game.toString());
            }
        });
    }
}
