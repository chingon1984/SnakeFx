package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

class Segment extends Circle {

    private Direction currentDirection, lastDirection;

    private PositionCoordinates positionOfDirectionChange;

    Segment(double centerX, double centerY) {
        this(centerX, centerY, Color.BLACK);
    }

    Segment(double centerX, double centerY, Paint fill) {
        super(centerX, centerY, SnakeSettings.RADIUS, fill);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
//        positionOfDirectionChange = new PositionCoordinates(this.getCenterX(), this.getLayoutY());
    }

    Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setPositionOfDirectionChange(PositionCoordinates positionOfDirectionChange) {
        this.positionOfDirectionChange = positionOfDirectionChange;
    }

    void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    void setLastDirection(Direction direction) {
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
        positionOfDirectionChange = new PositionCoordinates(this.getCenterX(), this.getCenterY());
    }

    public PositionCoordinates getPositionOfDirectionChange() {
        return positionOfDirectionChange;
    }
}
