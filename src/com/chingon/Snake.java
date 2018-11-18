package com.chingon;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private static ArrayList<Segment> snakeBody;
    private static ArrayList<HeadMovement> headMovements;

    private static Snake instance = null;


    private Snake(int segments) {
        snakeBody = new ArrayList<>();
        headMovements = new ArrayList<>();
        snakeBody.add(new Head(300, 100, Color.RED));
        setInitialSegments(segments);
//        headPosition = new PositionCoordinates(head.getCenterX(),head.getCenterY());
//        for(int i=1; i<=segments; i++)
//            addSegmentToBody();
    }

    static void createSnake(int segments) {
        if (instance == null)
            instance = new Snake(segments);
    }

    private static void setInitialSegments(int segments) {
        for (int i = 1; i <= segments; i++) {
            snakeBody.add(new Segment(300 - 30 * i, 100));
        }
//            addSegmentToBody();
    }

    static ArrayList<Segment> getBody() {
        return snakeBody;
    }

    static void saveHeadCoordinates(double posX, double posY, Direction direction) {
        PositionCoordinates position = new PositionCoordinates(posX, posY);
        HeadMovement headMovement = new HeadMovement(position,direction);
        headMovements.add(headMovement);
        System.out.println("Position of Change: " + posX + " : " + posY + "   -   Following Direction = " + direction.toString());

    }

    public static HeadMovement getNextPosition(int i) {
        try {
            return headMovements.get(i);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static PositionCoordinates getHeadCoordinatesFromBehind( int i) {
        try {
            return headMovements.get(headMovements.size()-(1+i)).getPositionCoordinates();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    private void addSegmentToBody() {
//        PositionCoordinates positionOfNewSegment = getPositionOfNewSegment();
//        Segment snakeElement = new Segment(positionOfNewSegment.getPosX(), positionOfNewSegment.getPosY());


//        snakeBody.add(snakeElement);
    }


}
