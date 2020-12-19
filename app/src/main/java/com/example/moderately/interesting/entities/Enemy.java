package com.example.moderately.interesting.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.moderately.interesting.Util;
import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Enemy {
    private Bitmap bitmap;
    private Velocity velocity = new Velocity();
    private Position position = new Position();

    private int screenWidth;
    private int screenHeight;


    public Enemy(int screenWidth, int screenHeight, Bitmap bitmap) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.bitmap = bitmap;
        this.position.yRandom(-screenHeight, 0);
        this.position.xRandom(0, screenWidth);
        this.velocity.yRandom(7, 10);
    }

    public void draw(Canvas canvas) {
        Util.centerDraw(position, bitmap, canvas);
    }

    public void respawn() {
        position.yPosition = -bitmap.getHeight() / 2 - (int) (Math.random() * 2500); // Add random height to randomise spawn rate.
        position.xPosition = (int) (Math.random() * screenWidth);
    }

    public Position update() {
        position.yPosition += velocity.yVelocity;

        if (position.yPosition - bitmap.getHeight() / 2 > screenHeight) {
            this.respawn();
        }

        // Have yet to find a way to clone the position object
        Position position = new Position();
        position.yPosition = this.position.yPosition;
        position.xPosition = this.position.xPosition;
        return position;
    }

    /**
     * Returns the Rect shape corresponding with this bitmap, used for collision detection
     * @return Rect matching the bitmap size.
     */
    public Rect getCollisionShape() {
        int left = position.xPosition - bitmap.getWidth() / 2;
        int top = position.yPosition - bitmap.getHeight() / 2;
        int right = position.xPosition + bitmap.getWidth() / 2;
        int bottom = position.yPosition + bitmap.getWidth() / 2;

        return new Rect(left, top, right, bottom);
    }

    /**
     * Checks entity yPosition returns true when entity is on screen.
     * @return true if yPosition is on screen a.k.a. >= 0;
     */
    public boolean onScreen() {
        return this.position.yPosition >= 0;
    }
}
