package com.chingon;

import javafx.scene.paint.Paint;

public class Head extends Segment {

    public Head(double centerX, double centerY, Paint fill) {
        super(centerX, centerY, fill);
    }

    private void savePosition() {
        Snake.saveHeadCoordinates(this.getCenterX(),this.getCenterY(),getCurrentDirection());
    }

    void saveDirectionAndPosition(Direction direction) {
        saveCurrentDirection(direction);
        savePosition();
    }
}
