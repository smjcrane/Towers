package com.example.towers;

import java.util.ArrayList;
import java.util.Random;

public class WhatsNext {
    private Random random;
    private Block.Color[] upNext;
    private int selected;
    private ArrayList<WhatsNextListener> listeners;

    public static final int NONE = -1;

    public WhatsNext() {
        random = new Random();
        upNext = new Block.Color[3];
        for (int i = 0; i < 3; i++) {
            upNext[i] = nextColor();
        }
        selected = NONE;
        listeners = new ArrayList<>();
    }

    public Block.Color getColorAt(int i){
        if (0<=i && i < 3){
            return upNext[i];
        }
        return null;
    }

    public int getSelected(){
        return selected;
    }

    public Block.Color getSelectedColor(){
        if (selected == NONE){
            return null;
        }
        return upNext[selected];
    }

    public boolean select(int i){
        if (0<=i && i<3){
            selected = i;
            onStateChanged();
            return true;
        }
        return false;
    }

    public void deselect(){
        selected = NONE;
        onStateChanged();
    }

    public boolean playSelected(){
        if (selected != NONE){
            upNext[selected] = nextColor();
            onStateChanged();
            return true;
        }
        return false;
    }

    public Block.Color nextColor(){
        Block.Color color = Block.Color.values()[random.nextInt(Block.Color.values().length)];
        return color;
    }

    public void addWhatsNextListener(WhatsNextListener l){
        listeners.add(l);
    }

    public void onStateChanged(){
        for (int i = 0; i < listeners.size(); i++){
            listeners.get(i).onWhatsNextChanged(this);
        }
    }

    interface WhatsNextListener{
        public void onWhatsNextChanged(WhatsNext whatsNext);
    }

}

