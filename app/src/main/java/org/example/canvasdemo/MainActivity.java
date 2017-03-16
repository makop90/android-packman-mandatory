package org.example.canvasdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	MyView myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button right = (Button) findViewById(R.id.right);
		Button left = (Button) findViewById(R.id.left);
		Button top = (Button) findViewById(R.id.top);
		Button bottom = (Button) findViewById(R.id.bottom);
		myView = (MyView) findViewById(R.id.gameView);
		//listener of our pacman
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myView.moveRight(10);
			}
		});
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myView.moveLeft(10);
			}
		});
		top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myView.moveTop(10);
			}
		});
		bottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myView.moveBottom(10);
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
