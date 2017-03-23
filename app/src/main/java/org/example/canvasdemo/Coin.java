package org.example.canvasdemo;

import java.util.Random;

public class Coin {
    public final static int radius = 20;
    public int x;
    public int y;
    private Random rand = new Random();

    public Coin(int w, int h) {
        this.x = rand.nextInt(w - 2*radius) + radius;
        this.y = rand.nextInt(h - 2*radius) + radius;
    }
}
