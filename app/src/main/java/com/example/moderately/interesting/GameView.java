package com.example.moderately.interesting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.BitmapFactory;

import com.example.moderately.interesting.entities.Enemy;
import com.example.moderately.interesting.entities.Player;
import com.example.moderately.interesting.entities.Star;
import com.example.moderately.interesting.entities.Bullet;
import com.example.moderately.interesting.properties.Position;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private Star[] starSprites = new Star[1000];
    private Enemy[] enemySprites = new Enemy[10];
    private List<Bullet> bulletSprites = new ArrayList<Bullet>();

    private Player playerSprite;

    private Integer xTouch;
    private Integer yTouch;

    private Bitmap enemyBitmap;
    private Bitmap playerBitmap;
    private Bitmap bulletBitmap;

    public GameView(Context context) {
        super(context);

        setFocusable(true);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        xTouch = (int) event.getX();
        yTouch = (int) event.getY();

        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            xTouch = null;
            yTouch = null;
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        Bitmap enemyRaw = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        Bitmap bulletRaw = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        Bitmap playerRaw = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        enemyBitmap = Util.resizeBitmap((int) (screenWidth * 0.18), enemyRaw);
        bulletBitmap = Util.resizeBitmap((int) (screenWidth * 0.04), bulletRaw);
        playerBitmap = Util.resizeBitmap((int) (screenWidth * 0.16), playerRaw);

        // Initialize the player and every single star
        playerSprite = new Player(screenWidth, screenHeight, playerBitmap);
        for (int i = 0; i < starSprites.length; i ++) starSprites[i] = new Star(screenWidth, screenHeight);
        for (int i = 0; i < enemySprites.length; i ++) enemySprites[i] = new Enemy(screenWidth, screenHeight, enemyBitmap);

        // Start the game logic
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
    }

    public void update() {
        // Make a copy of the array so we can clean it
        List<Bullet> bulletsCopy = new ArrayList<>(bulletSprites);

        for (Bullet bullet : bulletsCopy) {
            // Remove the bullet if its outside the screen
            if (bullet.outside()) bulletSprites.remove(bullet);
            else bullet.update();
        }

        // Update the position of the player and every single star
        for (int i = 0; i < starSprites.length; i ++) starSprites[i].update();
        for (int i = 0; i < enemySprites.length; i ++) enemySprites[i].update();
        Position position = playerSprite.update(xTouch);

        Bullet bullet = new Bullet(position);
        bulletSprites.add(bullet);

    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if (canvas != null) {
            // Draw the player and every single star
            for (int i = 0; i < starSprites.length; i ++) starSprites[i].draw(canvas);
            for (int i = 0; i < enemySprites.length; i ++) enemySprites[i].draw(canvas);
            playerSprite.draw(canvas);
            for (Bullet bullet : bulletSprites) bullet.draw(canvas);
        }
    }
}