package org.example.canvasdemo;

public class Enemy {
    public int enemyX;
    public int enemyY;
    public String orientation;
    int move_duration = 0;
    int move_direction = 0;

    public Enemy() {
    }

    public Enemy(int x, int y) {
        this.orientation = "right";
        this.enemyX = x;
        this.enemyY = y;
    }

    public void moveRight(int x) {
        this.enemyX = enemyX + x;
    }

    public void moveLeft(int x) {
        this.enemyX = enemyX - x;
    }

    public void moveTop(int y) {
        this.enemyY = enemyY - y;
    }

    public void moveBottom(int y) {
        this.enemyY = enemyY + y;
    }
}
