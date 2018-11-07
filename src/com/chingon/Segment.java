package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Segment extends Circle {

    private Direction currentDirection, lastDirection;

    public Segment(double centerX, double centerY) {
         this(centerX,centerY,Color.BLACK);
    }

    public Segment(double centerX, double centerY, Paint fill) {
        super(centerX,centerY,SnakeSettings.RADIUS,fill);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setLastDirectionToDirection() {
        lastDirection = currentDirection;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }
}
