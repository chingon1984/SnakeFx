package com.chingon;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private static ArrayList<Segment> snakeBody;
    private PositionCoordinates headPosition;
    private static Snake instance = null;


    private Snake(int segments) {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Segment(400, 100, Color.RED));
        setInitialSegments(segments);
//        headPosition = new PositionCoordinates(head.getCenterX(),head.getCenterY());
//        for(int i=1; i<=segments; i++)
//            addSegmentToBody();
    }

    public static void createSnake(int segments) {
        if (instance == null)
            instance = new Snake(segments);
    }

    public static void setInitialSegments(int segments) {
        for (int i = 1; i <= segments; i++) {
            snakeBody.add(new Segment(400-30*i,100));
        }
//            addSegmentToBody();
    }

    public PositionCoordinates getHeadPosition() {
        return headPosition;
    }

    static ArrayList<Segment> getBody() {
        return snakeBody;
    }


    Segment getHead() {
        return snakeBody.get(0);
    }




    //    Circle getNewSegment() {
//        return snakeBody.get(snakeBody.size()-1);
//    }


//    void grow() {
//        addSegmentToBody();
//    }
//
    private void addSegmentToBody() {
//        PositionCoordinates positionOfNewSegment = getPositionOfNewSegment();
//        Segment snakeElement = new Segment(positionOfNewSegment.getPosX(), positionOfNewSegment.getPosY());


//        snakeBody.add(snakeElement);
    }



//// TODO    Need change depending on direction of last segment
//    private PositionCoordinates getPositionOfNewSegment() {
//        PositionCoordinates positionOfNewSegment = getPositionOfLastSegment();
//        if (snakeBody.size() > 0)
//            positionOfNewSegment.addToPos(0, 2 * SnakeSettings.RADIUS);
//        else
//            addSizeToHead(positionOfNewSegment);
//        return positionOfNewSegment;
//    }
//
//    private PositionCoordinates getPositionOfLastSegment() {
//        double posX = 0;
//        double posY = 0;
//        if(snakeBody.size() > 0) {
//            posX = snakeBody.get(snakeBody.size() - 1).getCenterX();
//            posY = snakeBody.get(snakeBody.size() - 1).getCenterY();
//        }else {
//            posX = head.getCenterX();
//            posY = head.getCenterY();
//        }
//        return new PositionCoordinates(posX, posY);
//    }
//
//    private void addSizeToHead(PositionCoordinates coordinates) {
//        coordinates.addToPos(0, HEAD_RADIUS + SEGMENT_RADIUS);
//    }


}
