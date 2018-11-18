package com.chingon;

import javafx.scene.paint.Paint;

public class Head extends Segment {
    private Direction lastDirection;


    public Head(double centerX, double centerY, Paint fill) {
        super(centerX, centerY, fill);
        lastDirection = Direction.RIGHT;
    }

    private void savePosition() {
        Snake.saveHeadCoordinates(this.getCenterX(),this.getCenterY(),getCurrentDirection());
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
            setLastDirection(getCurrentDirection());

        setCurrentDirection(direction);
        savePosition();
    }
}
