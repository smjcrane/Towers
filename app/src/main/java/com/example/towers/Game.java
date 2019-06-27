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
        this.towers.get(i).addBlock(t);
        onStateChanged();
        return true;
    }

    public boolean play(Bridge b, int i1, int i2){
        if (Math.abs(i1 - i2) != 1){
            return false;
        }
        Tower t1 = this.towers.get(i1);
        Tower t2 = this.towers.get(i2);
        if (t1.getHeight() != t2.getHeight()){
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
