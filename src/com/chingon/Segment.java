package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

class Segment extends Circle {
    private Direction currentDirection, lastDirection;
    private int positionCounter;


    Segment(double centerX, double centerY) {
        this(centerX, centerY, Color.BLACK);
    }

    Segment(double centerX, double centerY, Paint fill) {
        super(centerX, centerY, SnakeSettings.RADIUS, fill);
        currentDirection = Direction.RIGHT;
        lastDirection = Direction.RIGHT;
        positionCounter = 0;
    }

    Direction getCurrentDirection() {
        return currentDirection;
    }

    int getPositionCounter() {
        return positionCounter;
    }

    void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    private void setLastDirection(Direction direction) {
        lastDirection = direction;
    }

    Direction getLastDirection() {
        return lastDirection;
    }

    void setDirectionAndPosition(Direction direction) {
        if (lastDirection == Direction.NONE)
            setLastDirection(direction);
        else
            setLastDirection(currentDirection);

        setCurrentDirection(direction);
    }

    public void incrementPositionCounter() {
        this.positionCounter += 1;
    }
}
