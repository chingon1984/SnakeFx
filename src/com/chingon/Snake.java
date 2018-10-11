package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private ArrayList<Circle> snakeBody;
    private final double HEAD_RADIUS = 10.0, SEGMENT_RADIUS = 8.0;
    private final Circle head;


    Snake() {
        snakeBody = new ArrayList<>();
        head = new Circle(100, 100, HEAD_RADIUS, Color.RED);

        snakeBody.add(head);

    }

    List<Circle> getBody() {
        return snakeBody;
    }

    Circle getHead() {
        return head;
    }

    void grow() {
        addSegmentToBody();
    }

    private void addSegmentToBody() {
        PositionCoordinates positionOfNewSegment = getPositionOfNewSegment();
        Circle snakeElement = new Circle(positionOfNewSegment.getPosX(), positionOfNewSegment.getPosY(), SEGMENT_RADIUS);
        snakeBody.add(snakeElement);
    }


    /*Need change depending on direction of last segment*/
    private PositionCoordinates getPositionOfNewSegment() {
        PositionCoordinates positionOfNewSegment = getPositionOfLastSegment();
        if (snakeBody.size() > 1)
            positionOfNewSegment.addToPos(0, 2 * SEGMENT_RADIUS);
        else
            addSizeToHead(positionOfNewSegment);
        return positionOfNewSegment;
    }

    private PositionCoordinates getPositionOfLastSegment() {
        double posX = snakeBody.get(snakeBody.size() - 1).getCenterX();
        double posY = snakeBody.get(snakeBody.size() - 1).getCenterY();
        return new PositionCoordinates(posX, posY);
    }

    private void addSizeToHead(PositionCoordinates coordinates) {
        coordinates.addToPos(0, HEAD_RADIUS + SEGMENT_RADIUS);
    }


}
