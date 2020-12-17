package com.example.moderately.interesting.properties;

import java.util.Random;

public class Position {
    public int xPosition;
    public int yPosition;

    public void xRandom(int min, int max) {
        Random random = new Random();
        this.xPosition = random.nextInt(max + 1 - min) + min;
    }

    public void yRandom(int min, int max) {
        Random random = new Random();
        this.yPosition = random.nextInt(max + 1 - min) + min;
    }
}
