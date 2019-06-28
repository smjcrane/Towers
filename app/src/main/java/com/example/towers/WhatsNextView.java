package com.example.towers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class WhatsNextView extends View {

    private WhatsNext whatsNext;
    private static Paint redBlock, blueBlock, yellowBlock, greenBlock, greyBlock;
    private static Map<Block.Color, Paint> blockColors;

    private int viewWidth, viewHeight, blockSize, gapSize;

    public WhatsNextView(Context context, AttributeSet attrs){
        super(context, attrs);
        setUpPaints();
    }

    public void setWhatsNext(WhatsNext whatsNext){
        this.whatsNext = whatsNext;
        this.whatsNext.addWhatsNextListener(new WhatsNext.WhatsNextListener() {
            @Override
            public void onWhatsNextChanged(WhatsNext whatsNext) {
                invalidate();
            }
        });
    }

    private void setUpPaints(){
        redBlock = new Paint();
        redBlock.setColor(getResources().getColor(R.color.redBlock));
        redBlock.setStrokeWidth(5);
        blueBlock = new Paint();
        blueBlock.setColor(getResources().getColor(R.color.blueBlock));
        blueBlock.setStrokeWidth(5);
        greenBlock = new Paint();
        greenBlock.setColor(getResources().getColor(R.color.greenBlock));
        greenBlock.setStrokeWidth(5);
        yellowBlock = new Paint();
        yellowBlock.setColor(getResources().getColor(R.color.yellowBlock));
        yellowBlock.setStrokeWidth(5);
        greyBlock = new Paint();
        greyBlock.setColor(getResources().getColor(R.color.greyBlock));
        greyBlock.setStrokeWidth(5);
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

    private void drawBlock(Canvas canvas, int x, int y, Block.Color color){
        canvas.drawRect(x, y, x+blockSize, y+blockSize, blockColors.get(color));
    }

    private void drawSelector(Canvas canvas, int selected){
        int margin = 10;
        int x1 = gapSize + (blockSize + 2 * gapSize) * selected - margin;
        int x2 = gapSize * (1 + 2 * selected) + blockSize * (selected + 1) + margin;
        int y1 = blockSize / 2 - margin;
        int y2 = blockSize * 3 / 2 + margin;
        Paint paint = blockColors.get(whatsNext.getColorAt(selected));
        canvas.drawLine(x1, y1, x2, y1, paint);
        canvas.drawLine(x1, y2, x2, y2, paint);
        canvas.drawLine(x1, y1, x1, y2, paint);
        canvas.drawLine(x2, y1, x2, y2, paint);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewWidth = getWidth();
        viewHeight = getHeight();
        blockSize = viewHeight / 2;
        gapSize = (viewWidth - 3 * blockSize) / 6;

        for (int i = 0; i < 3; i++){
            drawBlock(canvas, gapSize + (blockSize + 2 * gapSize) * i, blockSize / 2, whatsNext.getColorAt(i));
        }

        int selected = whatsNext.getSelected();
        if (selected != WhatsNext.NONE){
            drawSelector(canvas, selected);
        }
    }

    public int whatWasSelected(int touchX) {
        int tolerance = gapSize / 5;
        if (gapSize - tolerance < touchX && touchX < gapSize + blockSize + tolerance) {
            return 0;
        } else if (3 * gapSize + blockSize - tolerance < touchX && touchX < 3 * gapSize + 2 * blockSize + tolerance) {
            return 1;
        } else if (5 * gapSize + 2 * blockSize - tolerance < touchX && touchX < 5 * gapSize + 3 * blockSize + tolerance) {
            return 2;
        }
        return WhatsNext.NONE;
    }

}
