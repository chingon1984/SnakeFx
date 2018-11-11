package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

class Segment extends Circle {

    private Direction currentDirection, lastDirection;

    Segment(double centerX, double centerY) {
        this(centerX, centerY, Color.BLACK);
    }

    Segment(double centerX, double centerY, Paint fill) {
        super(centerX, centerY, SnakeSettings.RADIUS, fill);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
    }

    Direction getCurrentDirection() {
        return currentDirection;
    }

    void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    void setLastDirectionToCurrentDirection() {
        lastDirection = currentDirection;
    }

    Direction getLastDirection() {
        return lastDirection;
    }
}
