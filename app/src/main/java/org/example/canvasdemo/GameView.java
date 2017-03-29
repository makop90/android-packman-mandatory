package org.example.canvasdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {

    //All pacman rotations
    Bitmap tempPackmanLeft = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_left);
    Bitmap bitmapPackmanLeft = Bitmap.createScaledBitmap(tempPackmanLeft, 80, 80, false);

    Bitmap tempPackmanRight = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_right);
    Bitmap bitmapPackmanRight = Bitmap.createScaledBitmap(tempPackmanRight, 80, 80, false);

    Bitmap tempPackmanUp = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_up);
    Bitmap bitmapPackmanUp = Bitmap.createScaledBitmap(tempPackmanUp, 80, 80, false);

    Bitmap tempPackmanDown = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_down);
    Bitmap bitmapPackmanDown = Bitmap.createScaledBitmap(tempPackmanDown, 80, 80, false);

    //All enemy rotations
    Bitmap tempEnemyRight = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_right);
    Bitmap bitmapEnemyRight = Bitmap.createScaledBitmap(tempEnemyRight, 80, 80, false);

    Bitmap tempEnemyLeft = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_left);
    Bitmap bitmapEnemyLeft = Bitmap.createScaledBitmap(tempEnemyLeft, 80, 80, false);

    Bitmap tempEnemyUp = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_up);
    Bitmap bitmapEnemyUp = Bitmap.createScaledBitmap(tempEnemyUp, 80, 80, false);

    Bitmap tempEnemyDown = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_down);
    Bitmap bitmapEnemyDown = Bitmap.createScaledBitmap(tempEnemyDown, 80, 80, false);

    //The coordinates for our dear pacman: (0,0) is the top-left corner
    Pacman pacman = new Pacman(0, 0);

    List<Enemy> enemies = new ArrayList<Enemy>();
    Paint paint = new Paint();
    int h, w; //used for storing our height and width
    int highScore = 0;
    String username = "-";
    int score = 0;
    boolean finished = false;
    String orientation = "right";
    //generate first coin
    Coin coin = new Coin(500, 500);

    //Username input dialog
    final EditText username_input = new EditText(getContext());

    /* The next 3 constructors are needed for the Android view system,
when we have a custom view.
 */
    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void moveEnemies(int x, int y) {
        Random rand = new Random();
        for (Enemy enemy : enemies) {
            if (enemy.move_duration == 0) {
                enemy.move_direction = rand.nextInt(4);
                enemy.move_duration = rand.nextInt(15) + 1;
            }
            switch (enemy.move_direction) {
                case 0:
                    if (enemy.enemyX + x + bitmapEnemyRight.getWidth() <= w) {
                        enemy.moveRight(x);
                        enemy.orientation = "right";
                    }
                    else {
                        enemy.move_duration = 1;
                        enemy.moveLeft(x);
                        enemy.orientation = "left";
                    }
                    invalidate(); //redraw everything - this ensures onDraw() is called.
                    break;
                case 1:
                    if (enemy.enemyX - x >= 0) {
                        enemy.moveLeft(x);
                        enemy.orientation = "left";
                    }
                    else {
                        enemy.move_duration = 1;
                        enemy.moveRight(x);
                        enemy.orientation = "right";
                    }
                    invalidate(); //redraw everything - this ensures onDraw() is called.
                    break;
                case 2:
                    if (enemy.enemyY - y >= 0) {
                        enemy.moveTop(y);
                        enemy.orientation = "up";
                    }
                    else {
                        enemy.move_duration = 1;
                        enemy.moveBottom(x);
                        enemy.orientation = "down";
                    }
                    invalidate(); //redraw everything - this ensures onDraw() is called.
                    break;
                case 3:
                    if (enemy.enemyY + y + bitmapEnemyRight.getHeight() <= h) {
                        enemy.moveBottom(y);
                        enemy.orientation = "down";
                    }
                    else {
                        enemy.move_duration = 1;
                        enemy.moveTop(x);
                        enemy.orientation = "up";
                    }
                    invalidate(); //redraw everything - this ensures onDraw() is called.
                    break;
                default:
                    break;
            }
            enemy.move_duration--;
        }
    }


    public void moveRight(int x) {
        //still within our boundaries?
        if (pacman.pacx + x + bitmapPackmanLeft.getWidth() <= w)
            pacman.moveRight(x);
        orientation = "right";
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveLeft(int x) {
        //still within our boundaries?
        if (pacman.pacx - x >= 0)
            pacman.moveLeft(x);
        orientation = "left";
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveTop(int y) {
        //still within our boundaries?
        if (pacman.pacy - y >= 0)
            pacman.moveTop(y);
        orientation = "up";
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveBottom(int y) {
        //still within our boundaries?
        if (pacman.pacy + y + bitmapPackmanLeft.getHeight() <= h)
            pacman.moveBottom(y);
        orientation = "down";
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    @Override
    protected void onDraw(Canvas canvas) {
        //Here we get the height and weight
        h = canvas.getHeight();
        w = canvas.getWidth();
        for (Enemy enemy : enemies) {
//               check if enemy in range of packman
            boolean touchingEnemy = ((75 > pacman.pacx - enemy.enemyX && pacman.pacx - enemy.enemyX > -75) && (75 > pacman.pacy - enemy.enemyY && pacman.pacy - enemy.enemyY > -75));
            if (touchingEnemy) {
                finished = true;
                if(score > highScore){
                    new AlertDialog.Builder(getContext())
                            .setTitle("New high score!")
                            .setMessage("Enter your name")
                            .setView(username_input)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    username = username_input.getText().toString();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    username = "Unnamed";
                                }
                            })
                            .show();
                    highScore = score;
                }
                score = 0;
            }
        }

        if (Math.sqrt(((pacman.pacx + 40 - coin.x) * (pacman.pacx + 40 - coin.x)) + ((pacman.pacy + 40 - coin.y) * (pacman.pacy + 40 - coin.y))) < 40) {
            score += 10;
            coin = new Coin(w, h);
        }

        canvas.drawColor(Color.WHITE);

        paint.setColor(Color.CYAN);

        //drawing a circle based on the coin instance
        canvas.drawCircle(coin.x, coin.y, Coin.radius, paint);

        //select which PacMan image to select based on the way which he is moving
        switch(orientation){
            case "right":
                canvas.drawBitmap(bitmapPackmanRight, pacman.pacx, pacman.pacy, paint);
                break;
            case "left":
                canvas.drawBitmap(bitmapPackmanLeft, pacman.pacx, pacman.pacy, paint);
                break;
            case "up":
                canvas.drawBitmap(bitmapPackmanUp, pacman.pacx, pacman.pacy, paint);
                break;
            case "down":
                canvas.drawBitmap(bitmapPackmanDown, pacman.pacx, pacman.pacy, paint);
                break;
            default:
                canvas.drawBitmap(bitmapPackmanRight, pacman.pacx, pacman.pacy, paint);
        }

        for (Enemy enemy : enemies) {
            //select which enemy image to select based on the way which he is moving
            switch (enemy.orientation){
                case "right":
                    canvas.drawBitmap(bitmapEnemyRight, enemy.enemyX, enemy.enemyY, paint);
                    break;
                case "left":
                    canvas.drawBitmap(bitmapEnemyLeft, enemy.enemyX, enemy.enemyY, paint);
                    break;
                case "up":
                    canvas.drawBitmap(bitmapEnemyUp, enemy.enemyX, enemy.enemyY, paint);
                    break;
                case "down":
                    canvas.drawBitmap(bitmapEnemyDown, enemy.enemyX, enemy.enemyY, paint);
                    break;
                default:
                    canvas.drawBitmap(bitmapEnemyRight, enemy.enemyX, enemy.enemyY, paint);
            }
        }
        super.onDraw(canvas);
    }

}
