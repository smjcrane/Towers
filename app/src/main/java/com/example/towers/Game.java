package com.example.towers;

import java.util.ArrayList;

public class Game {
    private ArrayList<Tower> towers;
    private ArrayList<GameEventListener> listeners;

    public Game(int num_towers){
        this.towers = new ArrayList<>();
        for (int i = 0; i < num_towers; i++){
            this.towers.add(new Tower());
        }
        this.listeners = new ArrayList<>();
    }

    public int getNumTowers(){
        return towers.size();
    }

    public Tower getTowerAt(int index){
        return towers.get(index);
    }

    public boolean play(TowerSegment t, int i){
        Tower tower = towers.get(i);
        if (tower.getHeight() > 0 && tower.getTop().color == t.color){
            return false;
        }
        tower.addBlock(t);
        onStateChanged();
        return true;
    }

    public boolean play(Bridge b, int i1, int i2){
        if (Math.abs(i1 - i2) != 1){
            return false;
        }
        Tower t1 = this.towers.get(i1);
        Tower t2 = this.towers.get(i2);
        int height = t1.getHeight();
        if (height == 0){
            return false;
        }
        if (height != t2.getHeight()){
            return false;
        }
        if (t1.getTop() instanceof Bridge || t2.getTop() instanceof Bridge){
            return false;
        }
        if (t1.getTop().color == b.color || t2.getTop().color == b.color){
            return false;
        }
        t1.addBlock(b);
        t2.addBlock(b);
        onStateChanged();
        return true;
    }

    public void addEventListener(GameEventListener g){
        this.listeners.add(g);
    }

    public interface GameEventListener{
        void OnGameStateChanged();
    }

    private void onStateChanged(){
        for (int i = 0; i < listeners.size(); i++){
            listeners.get(i).OnGameStateChanged();
        }
    }

    public String toString(){
        String myString = "";
        for (Tower t : towers){
            String s = "";
            for (int i = 0; i < t.getHeight(); i++){
                s += t.getBlockAt(i).color + " ";
            }
            myString = myString + "Tower: " + s + "\n";
        }
        return myString;
    }
}
