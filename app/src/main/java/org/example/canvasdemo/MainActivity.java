package org.example.canvasdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button right = (Button) findViewById(R.id.right);
		Button left = (Button) findViewById(R.id.left);
		Button top = (Button) findViewById(R.id.top);
		Button bottom = (Button) findViewById(R.id.bottom);
		gameView = (GameView) findViewById(R.id.gameView);
        final TextView txtValue = (TextView) findViewById(R.id.score_num);
        txtValue.setText(Integer.toString(gameView.score));

        right.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    gameView.moveRight(80);
                    txtValue.setText(Integer.toString(gameView.score));
                }
                return false;
            }
        });

		left.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    gameView.moveLeft(80);
                    txtValue.setText(Integer.toString(gameView.score));
                }
                return false;
            }
		});

		top.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    gameView.moveTop(80);
                    txtValue.setText(Integer.toString(gameView.score));
                }
                return false;
            }
		});

		bottom.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    gameView.moveBottom(80);
                    txtValue.setText(Integer.toString(gameView.score));
                }
                return false;
            }
		});
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
