package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Segment extends Circle {

    private Direction currentDirection, lastDirection;
    private PositionCoordinates positionOfLastDirectionChange;

    public Segment(double centerX, double centerY) {
         this(centerX,centerY,Color.BLACK);
    }

    public Segment(double centerX, double centerY, Paint fill) {
        super(centerX,centerY,SnakeSettings.RADIUS,fill);
        currentDirection = Direction.NONE;
        lastDirection = Direction.NONE;
        positionOfLastDirectionChange = new PositionCoordinates(0,0);
    }

    public PositionCoordinates getPositionOfLastDirectionChange() {
        return positionOfLastDirectionChange;
    }

    public void setPositionOfLastDirectionChange(double XPos, double YPos) {
        this.positionOfLastDirectionChange.setPosX(XPos);
        this.positionOfLastDirectionChange.setPosY(YPos);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setLastDirectionToCurrentDirection() {
        lastDirection = currentDirection;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }
}
