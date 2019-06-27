package com.example.towers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class GameView extends View implements Game.GameEventListener {

    private Game game;
    private static Paint redBlock, blueBlock, yellowBlock, greenBlock, greyBlock;
    private static Map<Block.Color, Paint> blockColors;

    private int viewHeight, viewWidth, blockHeight, blockWidth;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        setUpPaints();
    }

    public void setGame(Game game){
        this.game = game;
        game.addEventListener(this);
    }

    @Override
    public void OnGameStateChanged(){
        invalidate();
    }

    private void setUpPaints(){
        redBlock = new Paint();
        redBlock.setColor(getResources().getColor(R.color.redBlock));
        blueBlock = new Paint();
        blueBlock.setColor(getResources().getColor(R.color.blueBlock));
        greenBlock = new Paint();
        greenBlock.setColor(getResources().getColor(R.color.greenBlock));
        yellowBlock = new Paint();
        yellowBlock.setColor(getResources().getColor(R.color.yellowBlock));
        greyBlock = new Paint();
        greyBlock.setColor(getResources().getColor(R.color.greyBlock));
        blockColors = new HashMap<Block.Color, Paint>() {
            {
                put(Block.Color.red, redBlock);
                put(Block.Color.blue, blueBlock);
                put(Block.Color.green, greenBlock);
                put(Block.Color.yellow, yellowBlock);
                put(Block.Color.grey, greyBlock);
            }
        };
    }

    private void drawBlock(Canvas canvas, int x, int y, Block block){
        canvas.drawRect(x, viewHeight - y - blockHeight, x+blockWidth, viewHeight - y, blockColors.get(block.color));
    }

    private void drawTower(Canvas canvas, int index, Tower tower){
        for (int i = 0; i < tower.getHeight(); i++){
            drawBlock(canvas, (int) ((2*index+0.5) * blockWidth), blockHeight * i, tower.getBlockAt(i));
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewWidth = getWidth();
        viewHeight = getHeight();
        blockWidth = viewWidth / (2 * game.getNumTowers());
        blockHeight =  blockWidth;//(int) (viewHeight / 10.0);
        for (int i = 0; i < game.getNumTowers(); i++){
            drawTower(canvas, i, game.getTowerAt(i));
        }
    }
}
