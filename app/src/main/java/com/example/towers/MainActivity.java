package com.example.towers;

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
                v = (GameView) v;
                Block.Color color = Block.Color.values()[random.nextInt(Block.Color.values().length)];
                GameView.GameClickEvent event = ((GameView) v).whichTowerWasClicked(touchX);
                if (event == null) {
                    return;
                }
                if (event.type == GameView.GameClickEvent.ClickType.Simple){
                    game.play(new TowerSegment(color), event.towerNumber);
                } else {
                    Log.d("MAIN", event.bridgeEnd + " " + event.bridgeStart);
                    game.play(new Bridge(color), event.bridgeStart, event.bridgeEnd);
                }
            }
        });

        startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t = random.nextInt(4);
                Block.Color color = Block.Color.values()[random.nextInt(Block.Color.values().length)];
                boolean bridge = random.nextInt(5) == 1;
                if (bridge) {
                    game.play(new Bridge(color), t, t + 1 % 4);
                } else {
                    game.play(new TowerSegment(color), t);
                }
                Log.d("MAIN", "\n" + game.toString());
            }
        });
    }
}
