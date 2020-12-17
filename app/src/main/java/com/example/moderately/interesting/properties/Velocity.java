package com.example.moderately.interesting.properties;

import java.util.Random;

public class Velocity {
    public int xVelocity;
    public int yVelocity;

    public void xRandom(int min, int max) {
        Random random = new Random();
        this.xVelocity = random.nextInt(max + 1 - min) + min;
    }

    public void yRandom(int min, int max) {
        Random random = new Random();
        this.yVelocity = random.nextInt(max + 1 - min) + min;
    }
}
