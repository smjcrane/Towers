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
        if (block instanceof TowerSegment){
            canvas.drawRect(x, viewHeight - y - blockHeight, x+blockWidth, viewHeight - y, blockColors.get(block.color));
        } else if (block instanceof Bridge){
            canvas.drawRect((int) (x - blockWidth / 2), viewHeight - y - blockHeight, (int) (x+blockWidth * 1.5) + 1, viewHeight - y, blockColors.get(block.color));
        }
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

    public GameClickEvent whichTowerWasClicked(int touchX){
        GameClickEvent g = new GameClickEvent();

        int strip = 2 * touchX / blockWidth;
        if (strip == 0 || strip == 2 * game.getNumTowers()){
            return null;
        }
        Log.d("VIEW", "s: " + strip + ", X: "+ touchX);

        if (strip % 4 == 0 || strip % 4 == 3){
            g.type = GameClickEvent.ClickType.Bridge;
            g.bridgeStart = (strip - 1) / 4;
            g.bridgeEnd = g.bridgeStart + 1;
        } else {
            g.type = GameClickEvent.ClickType.Simple;
            g.towerNumber = strip / 4;
        }
        return g;
    }

    static class GameClickEvent {

        public enum ClickType {
            Simple, Bridge
        }

        public ClickType type;
        public int towerNumber;
        public int bridgeStart;
        public int bridgeEnd;
    }
}
