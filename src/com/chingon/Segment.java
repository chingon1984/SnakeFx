package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

class Segment extends Circle {
    private Direction currentDirection;
    private int positionCounter;

    Segment(double centerX, double centerY) {
        this(centerX, centerY, Color.WHITE);
    }

    Segment(double centerX, double centerY, Paint fill) {
        super(centerX, centerY, SnakeSettings.RADIUS, fill);
        currentDirection = Direction.RIGHT;
        positionCounter = 0;
    }

    Direction getCurrentDirection() {
        return currentDirection;
    }

    int getPositionCounter() {
        return positionCounter;
    }

    void saveCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void incrementPositionCounter() {
        this.positionCounter += 1;
    }

    public PositionCoordinates getPosition() {
        return new PositionCoordinates(this.getCenterX(),this.getCenterY());
    }

    public void setPositionCounter(int positionCounter) {
        this.positionCounter = positionCounter;
    }
}
