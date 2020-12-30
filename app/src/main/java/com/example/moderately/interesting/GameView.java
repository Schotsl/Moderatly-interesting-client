package com.example.moderately.interesting;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.BitmapFactory;

import androidx.core.content.res.ResourcesCompat;

import com.example.moderately.interesting.entities.Enemy;
import com.example.moderately.interesting.entities.Player;
import com.example.moderately.interesting.entities.Star;
import com.example.moderately.interesting.entities.Bullet;
import com.example.moderately.interesting.properties.Position;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Typeface typeface;
    private MainThread thread;
    private FirebaseFirestore database;

    private Star[] starSprites = new Star[1000];
    private Enemy[] enemySprites = new Enemy[10];
    private List<Bullet> bulletSprites = new ArrayList<Bullet>();

    private Player playerSprite;

    private Integer xTouch;
    private Integer yTouch;

    private Integer playerScore = 4;
    private Integer playerHealth = 4;

    private Integer screenWidth;
    private Integer screenHeight;

    private Bitmap enemyBitmap;
    private Bitmap playerBitmap;
    private Bitmap bulletBitmap;

    private Paint firstLayer = new Paint();
    private Paint secondLayer = new Paint();

    public GameView(Context context) {
        super(context);

        setFocusable(true);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        database = FirebaseFirestore.getInstance();
    }

    public void drawText(Canvas canvas, String text) {
        Position position = new Position();
        position.xPosition = screenWidth / 2;
        position.yPosition = screenHeight / 2;

        int xOffset = 0;

        for (int i = 0; i < text.length(); i ++) {
            // Turn the single character into a string
            String character = Character.toString(text.charAt(i));

            // Get the character width
            Rect rect = new Rect();
            firstLayer.getTextBounds(character, 0, 1, rect);
            int width = rect.width();

            // Draw the layers
            canvas.drawText(character, position.xPosition + xOffset, position.yPosition, firstLayer);
            canvas.drawText(character, position.xPosition + xOffset - 2, position.yPosition - 35 / 2, secondLayer);

            // Update the offset
            xOffset += (int) (width * 1.25);
        }
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
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        // Load the bitmaps
        Bitmap enemyRaw = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        Bitmap bulletRaw = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
        Bitmap playerRaw = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        typeface = ResourcesCompat.getFont(getContext(), R.font.pixel);

        // Resize bitmap to fit screen
        enemyBitmap = Util.resizeBitmap((int) (screenWidth * 0.18), enemyRaw);
        bulletBitmap = Util.resizeBitmap((int) (screenWidth * 0.04), bulletRaw);
        playerBitmap = Util.resizeBitmap((int) (screenWidth * 0.16), playerRaw);

        // Initialize the player and every single star
        playerSprite = new Player(screenWidth, screenHeight, playerBitmap);
        for (int i = 0; i < starSprites.length; i ++) starSprites[i] = new Star(screenWidth, screenHeight);
        for (int i = 0; i < enemySprites.length; i ++) enemySprites[i] = new Enemy(screenWidth, screenHeight, enemyBitmap);

        // Create first font layer
        firstLayer.setColor(Color.rgb(178,34,34));
        firstLayer.setTextSize(150);
        firstLayer.setTypeface(typeface);
        firstLayer.setTextAlign(Paint.Align.CENTER);

        // Create second font layer
        secondLayer.setColor(Color.WHITE);
        secondLayer.setTextSize(115);
        secondLayer.setTypeface(typeface);
        secondLayer.setTextAlign(Paint.Align.CENTER);

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

            // Check if Enemies are shot
            for (Enemy enemy : enemySprites) {
                if (Rect.intersects(enemy.getCollisionShape(), bullet.getCollisionShape()) && enemy.onScreen()) {
                    enemy.respawn();
                }
            }
        }

        for (Enemy enemy : enemySprites) {
            if (Rect.intersects(enemy.getCollisionShape(), playerSprite.getCollisionShape())) {
                System.out.println("Player is hit!");

                // Kill the enemy that hit the player
                enemy.respawn();
            }
        }

        // Update the position of the player and every single star
        for (int i = 0; i < starSprites.length; i++) starSprites[i].update();
        for (int i = 0; i < enemySprites.length; i++) enemySprites[i].update();
        Position position = playerSprite.update(xTouch);

        if (playerSprite.canShoot()) {
            Bullet bullet = new Bullet(position, bulletBitmap);
            bulletSprites.add(bullet);
            playerSprite.resetReload();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if (canvas != null) {
            // Draw the player and every single star
            for (int i = 0; i < starSprites.length; i ++) starSprites[i].draw(canvas);
            for (int i = 0; i < enemySprites.length; i ++) enemySprites[i].draw(canvas);
            for (Bullet bullet : bulletSprites) bullet.draw(canvas);
            playerSprite.draw(canvas);

            drawText(canvas, "00000");
        }
    }
}