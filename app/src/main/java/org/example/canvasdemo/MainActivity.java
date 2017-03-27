package org.example.canvasdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    GameView gameView;
    private final int LEVEL_TIME = 30;
    private int current_level = 1;
    private Timer movingTimer;
    private Timer enemyMovingTimer;
    private Timer countdownTimer;
    private int timePassed = 0;
    private boolean running = false;
    private String direction = "Right";
    private TextView scoreView;
    private TextView countdownView;
    boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button right = (Button) findViewById(R.id.right);
        Button left = (Button) findViewById(R.id.left);
        Button top = (Button) findViewById(R.id.top);
        Button bottom = (Button) findViewById(R.id.bottom);
        gameView = (GameView) findViewById(R.id.gameView);
        scoreView = (TextView) findViewById(R.id.score_num);
        countdownView = (TextView) findViewById(R.id.countdown_num);
        scoreView.setText(Integer.toString(gameView.score));
        countdownView.setText(Integer.toString(LEVEL_TIME) + " sec");
        right.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN && running) {
                    direction = "Right";
                }
                return false;
            }
        });

        left.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN && running) {
                    direction = "Left";
                }
                return false;
            }
        });

        top.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN && running) {
                    direction = "Top";
                }
                return false;
            }
        });

        bottom.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN && running) {
                    direction = "Bottom";
                }
                return false;
            }
        });
        movingTimer = new Timer();
        movingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(Packman_Move);
            }

        }, 0, 30); //0 indicates we start now

        enemyMovingTimer = new Timer();
        enemyMovingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(Enemy_Move);
            }

        }, 0, 50); //0 indicates we start now
        countdownTimer = new Timer();
        countdownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(Time_Countdown);
            }

        }, 0, 1000); //0 indicates we start now, 200
        //is the number of miliseconds between each call


        final Button pause_start = (Button) findViewById(R.id.pause_start);
        pause_start.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    if (running) {
                        running = false;
                        pause_start.setText("Start");
                    } else {
                        running = true;
                        pause_start.setText("Pause");
                    }
                    if(finished){
                        finished = false;
                    }
                }
                return false;
            }
        });

    }

    private Runnable Time_Countdown = new Runnable() {
        public void run() {
            if (running && !finished) {
                if (timePassed < LEVEL_TIME) {
                    timePassed++;
                    countdownView.setText(Integer.toString(LEVEL_TIME - timePassed) + " sec");
                } else {
                    timePassed = 0;
                    current_level++;
                    running = false;
                    Enemy enemy = new Enemy(gameView.w / 2, gameView.h / 2);
                    gameView.enemies.add(enemy);
                    new CountDownTimer(5000, 1000) {
                        int tick = 4;
                        Toast toast;

                        public void onTick(long millisUntilFinished) {
                            if (tick == 4) {
                                toast = Toast.makeText(getApplicationContext(), "Level " + Integer.toString(current_level - 1) + " Finished", Toast.LENGTH_SHORT);
                            } else {
                                toast.cancel();
                                toast = Toast.makeText(getApplicationContext(), "Level " + Integer.toString(current_level) + ". Start in " + Integer.toString(tick) + " sec", Toast.LENGTH_SHORT);
                            }
                            tick--;
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        public void onFinish() {
                            running = true;
                        }
                    }.start();
                }
            }
        }
    };


    private Runnable Packman_Move = new Runnable() {
        public void run() {
            for(Enemy enemy : gameView.enemies){
//               check if enemy in range of packman
                boolean expression1 = (  (75 > gameView.pacman.pacx - enemy.enemyX && gameView.pacman.pacx - enemy.enemyX > -75) && (75 > gameView.pacman.pacy - enemy.enemyY && gameView.pacman.pacy - enemy.enemyY > -75));
                if (expression1) {
                    finished = true;
                    running = false;
                    final Button pause_start = (Button) findViewById(R.id.pause_start);
                    pause_start.setText("Restart");
                    Toast toast = Toast.makeText(getApplicationContext(), "Game over", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    gameView.pacman = new Pacman(0, 0);
                    gameView.enemies = new ArrayList<>();
                }
            }
            if (running && !finished) {
                switch (direction) {
                    case "Right":
                        gameView.moveRight(10);
                        break;
                    case "Left":
                        gameView.moveLeft(10);
                        break;
                    case "Top":
                        gameView.moveTop(10);
                        break;
                    case "Bottom":
                        gameView.moveBottom(10);
                        break;
                    default:
                        break;
                }
                scoreView.setText(Integer.toString(gameView.score));
            }

        }
    };
    private Runnable Enemy_Move = new Runnable() {
        public void run() {
            if (running) {
                if (gameView.enemies.size() < 3) {
                    for (int i = 0; i < 3; i++) {
                        Enemy enemy = new Enemy(gameView.w / 2, gameView.h / 2);
                        gameView.enemies.add(enemy);
                    }
                }
                gameView.moveEnemies(10,10);

            }

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        //just to make sure if the app is killed, that we stop the timer.
        movingTimer.cancel();
        countdownTimer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
