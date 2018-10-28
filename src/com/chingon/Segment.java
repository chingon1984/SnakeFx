package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Segment extends Circle {

    private PVector location;
    private PVector velocity;
    private Direction direction, lastDirection;

    public Segment(double centerX, double centerY) {
         this(centerX,centerY,Color.BLACK);
    }

    public Segment(double centerX, double centerY, Paint fill) {
        super(centerX,centerY,SnakeSettings.RADIUS,fill);
        location = new PVector(getCenterX(), getCenterY());
        velocity = new PVector(0,0);
        direction = Direction.NONE;
        lastDirection = Direction.NONE;
    }

    public PVector getLocation() {
        return new PVector(getCenterX(),getCenterY());
    }

    public PVector getVelocity() {
        return velocity;
    }

    public void follow(Segment segment) {
        velocity = PVector.subtractVector(segment.getLocation(),location);
        location.addVector(velocity);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLastDirectionToDirection() {
        lastDirection = direction;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }
}
