package com.example.towers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private GameView gameView;

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game(4);
        gameView = findViewById(R.id.game);
        gameView.setGame(game);

        final Random random = new Random();

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
