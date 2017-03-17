package org.example.canvasdemo;

public class Pacman {
    //The coordinates for our dear pacman
    public int pacx;
    public int pacy;


    public Pacman() {}

    public Pacman(int x, int y) {
        this.pacx = x;
        this.pacy = y;
    }

    public void moveRight(int x) {
        this.pacx = pacx + x;
    }

    public void moveLeft(int x) {
        this.pacx = pacx - x;
    }

    public void moveTop(int y) {
        this.pacy = pacy - y;
    }

    public void moveBottom(int y) {
        this.pacy = pacy + y;
    }
}
