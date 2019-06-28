package com.example.towers;

import java.util.ArrayList;

public class Tower {
    private ArrayList<Block> blocks;

    public Tower(){
        this.blocks = new ArrayList<>();
    }

    public int getHeight(){
        return this.blocks.size();
    }

    public Block getBlockAt(int index){
        return this.blocks.get(index);
    }

    public Block getTop(){
        return blocks.get(blocks.size() - 1);
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public void toppleFrom(int index){
        for (int i = index; i<this.blocks.size(); i++ ){
            this.blocks.get(i).explode();
        }
        this.blocks = (ArrayList) this.blocks.subList(0, index);
    }
}
