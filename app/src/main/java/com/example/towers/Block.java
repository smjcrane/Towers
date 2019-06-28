package com.example.towers;

public abstract class Block {

    protected Color color;

    public enum Color {
        red, blue, yellow, green, grey
    }

    public Block() {
        this.color = Color.grey;
    }

    public void explode(){}
}