package com.chingon;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private static ArrayList<Segment> snakeBody;
    private static ArrayList<PositionAndDirection> positionAndDirections;

    private static Snake instance = null;


    private Snake(int segments) {
        snakeBody = new ArrayList<>();
        positionAndDirections = new ArrayList<>();
        snakeBody.add(new Head(600 - SnakeSettings.RADIUS, 100, Color.RED));
        setAdditionalSegments(segments);
    }

    static void createSnake(int segments) {
        if (instance == null)
            instance = new Snake(segments);
    }

    private static void setAdditionalSegments(int segments) {
        for (int i = 1; i <= segments; i++) {

            snakeBody.add(new Segment(600 - SnakeSettings.RADIUS - 2 * SnakeSettings.RADIUS * i, 100));
        }
    }

    static ArrayList<Segment> getBody() {
        return snakeBody;
    }

    static void saveHeadCoordinates(double posX, double posY, Direction direction) {
        PositionCoordinates position = new PositionCoordinates(posX, posY);
        PositionAndDirection positionAndDirection = new PositionAndDirection(position, direction);
        positionAndDirections.add(positionAndDirection);
        System.out.println("Position of Change: " + posX + " : " + posY + "   -   Following Direction = " + direction.toString());

    }

    public static PositionAndDirection getNextPosition(int i) {
        try {
            return positionAndDirections.get(i);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static PositionCoordinates getLatestCoordinates(int i) {
        try {
            return positionAndDirections.get(positionAndDirections.size() - (1 + i)).getPositionCoordinates();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    public static Segment addSegmentsToBody() {
        Segment lastSegment = snakeBody.get(snakeBody.size() - 1);
        Direction directionOfLastSegment = lastSegment.getCurrentDirection();
        PositionCoordinates positionOfLastSegment = lastSegment.getPosition();
        int positionCounter = lastSegment.getPositionCounter();



        PositionCoordinates validCoordinates = new PositionCoordinates(0,0);
        switch (directionOfLastSegment) {
            case RIGHT:
                validCoordinates =  getValidCoordinates(positionOfLastSegment.getPosX() - 2 * SnakeSettings.RADIUS, positionOfLastSegment.getPosY());
                break;
            case LEFT:
                validCoordinates =  getValidCoordinates(positionOfLastSegment.getPosX() + 2 * SnakeSettings.RADIUS, positionOfLastSegment.getPosY());
                break;
            case UP:
                validCoordinates =  getValidCoordinates(positionOfLastSegment.getPosX(), positionOfLastSegment.getPosY() + 2 * SnakeSettings.RADIUS);
                break;
            case DOWN:
                validCoordinates =  getValidCoordinates(positionOfLastSegment.getPosX(), positionOfLastSegment.getPosY() - 2 * SnakeSettings.RADIUS);
                break;
        }
        Segment newSegment = new Segment(validCoordinates.getPosX(),validCoordinates.getPosY());
        newSegment.saveCurrentDirection(directionOfLastSegment);
        newSegment.setPositionCounter(positionCounter);
        snakeBody.add(newSegment);

        return newSegment;
    }

    private static PositionCoordinates getValidCoordinates(double posX, double posY) {
//        TODO: return valid Coordinates, in case when added Segment is out of the borders.
        return new PositionCoordinates(posX,posY);
    }
}
