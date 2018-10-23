package com.chingon;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Snake {
    private ArrayList<Circle> snakeBody;
    private final double HEAD_RADIUS = 10.0, SEGMENT_RADIUS = 8.0;
    private final Circle head;
    private PositionCoordinates headPosition;


    Snake(int segments) {
        snakeBody = new ArrayList<>();
        head = new Circle(100, 100, HEAD_RADIUS, Color.RED);
        headPosition = new PositionCoordinates(head.getCenterX(),head.getCenterY());
        for(int i=1; i<=segments; i++)
            addSegmentToBody();
    }

    public PositionCoordinates getHeadPosition() {
        return headPosition;
    }

    ArrayList<Circle> getBody() {
        return snakeBody;
    }

//    Circle getNewSegment() {
//        return snakeBody.get(snakeBody.size()-1);
//    }

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
        if (snakeBody.size() > 0)
            positionOfNewSegment.addToPos(0, 2 * SEGMENT_RADIUS);
        else
            addSizeToHead(positionOfNewSegment);
        return positionOfNewSegment;
    }

    private PositionCoordinates getPositionOfLastSegment() {
        double posX = 0;
        double posY = 0;
        if(snakeBody.size() > 0) {
            posX = snakeBody.get(snakeBody.size() - 1).getCenterX();
            posY = snakeBody.get(snakeBody.size() - 1).getCenterY();
        }else {
            posX = head.getCenterX();
            posY = head.getCenterY();
        }
        return new PositionCoordinates(posX, posY);
    }

    private void addSizeToHead(PositionCoordinates coordinates) {
        coordinates.addToPos(0, HEAD_RADIUS + SEGMENT_RADIUS);
    }


}
