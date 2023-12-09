package com.example.project;

import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Flyweight {

    private static final Map<String, Rectangle> pillarList = new HashMap<>();
    private static final Random random = new Random();

    public static Rectangle getNextPillar(int levelNumber) {
        String key = getKey(levelNumber);

        if (!pillarList.containsKey(key)) {
            Rectangle nextPillar = new Rectangle(random.nextInt(200, 319), 403, random.nextInt(50, 100), 316);
            pillarList.put(key, nextPillar);
        }
        return pillarList.get(key);
    }

    private static String getKey(int levelNumber) {
        int x = random.nextInt(200, 319);
        int y = 403;
        int width = random.nextInt(50, 100);
        int height = 316;
        return String.format("%d_%d_%d_%d_%d", x, y, width, height, levelNumber);
    }

}
