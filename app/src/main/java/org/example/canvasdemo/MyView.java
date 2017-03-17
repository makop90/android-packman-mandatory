package org.example.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
    //The coordinates for our dear pacman: (0,0) is the top-left corner
    Pacman pacman = new Pacman(0,0);
    Paint paint = new Paint();
    int h, w; //used for storing our height and width
    int score = 0;
    //generate first coin
    Coin coin = new Coin(500, 500);

    public void moveRight(int x) {
        //still within our boundaries?
        if (pacman.pacx + x + bitmap.getWidth() <= w)
            pacman.moveRight(x);
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveLeft(int x) {
        //still within our boundaries?
        if (pacman.pacx - x  >= 0)
            pacman.moveLeft(x);
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveTop(int y) {
        //still within our boundaries?
        if (pacman.pacy - y  >= 0)
            pacman.moveTop(y);
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveBottom(int y) {
        //still within our boundaries?
        if (pacman.pacy + y + bitmap.getHeight() <= h)
            pacman.moveBottom(y);
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    /* The next 3 constructors are needed for the Android view system,
    when we have a custom view.
     */
    public MyView(Context context) {
        super(context);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    @Override
    protected void onDraw(Canvas canvas) {
        //Here we get the height and weight
        h = canvas.getHeight();
        w = canvas.getWidth();

        if(Math.sqrt(((pacman.pacx + 80 - coin.x) * (pacman.pacx + 80 - coin.x)) + ((pacman.pacy + 80 - coin.y) + (pacman.pacy + 80 - coin.y))) < 80)
        {
            score += 10;
            coin = new Coin(w, h);
        }

        canvas.drawColor(Color.WHITE);

        paint.setColor(Color.CYAN);

        //drawing a circle based on the coin instance
        canvas.drawCircle(coin.x, coin.y, Coin.radius, paint);

        canvas.drawBitmap(bitmap, pacman.pacx, pacman.pacy, paint);
        super.onDraw(canvas);
    }

}
