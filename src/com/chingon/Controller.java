package com.chingon;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Pane gameBoard;

    private Head head;
    private double gameBoardHeight;
    private double gameBoardWidth;
    private ArrayList<Segment> snakeBody;
    private Direction savedDirection;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEnvironment();
        initializeSnake();
        initializeTimer();
    }

    private void initializeEnvironment() {
        gameBoardHeight = gameBoard.getPrefHeight();
        gameBoardWidth = gameBoard.getPrefWidth();
    }

    private void initializeSnake() {
        Snake.createSnake(SnakeSettings.SNAKE_SEGMENTS);
        snakeBody = Snake.getBody();
        head = ((Head) snakeBody.get(0));
        drawSnake();
        savedDirection = Direction.NONE;
    }

    private void drawSnake() {
        snakeBody.forEach(segment -> {
            gameBoard.getChildren().add(segment);
        });
    }

    private void initializeTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        animationTimer.start();
    }

    private void updateGame() {
        getUserInput();
        move();
        checkBorders();
    }

    private void getUserInput() {
        head.setFocusTraversable(true);
        head.setOnKeyPressed(event -> {

            Direction direction = Direction.NONE;
            if ((event.getCode() == KeyCode.UP) && (head.getCurrentDirection() != Direction.DOWN))
                direction = Direction.UP;
//                head.setDirectionAndPosition(Direction.UP);
            else if ((event.getCode() == KeyCode.DOWN) && (head.getCurrentDirection() != Direction.UP))
                direction = Direction.DOWN;
//                head.setDirectionAndPosition(Direction.DOWN);
            else if ((event.getCode() == KeyCode.LEFT) && (head.getCurrentDirection() != Direction.RIGHT))
                direction = Direction.LEFT;
//                head.setDirectionAndPosition(Direction.LEFT);
            else if ((event.getCode() == KeyCode.RIGHT) && (head.getCurrentDirection() != Direction.LEFT))
                direction = Direction.RIGHT;
//                head.setDirectionAndPosition(Direction.RIGHT);

            validateInput(direction);
        }
        );
    }

    private void validateInput(Direction direction) {
        Direction nextDirection = (savedDirection == Direction.NONE ? direction : savedDirection);
        if(checkDistanceBetweenTwoChanges()) {
            switch (nextDirection) {
                case UP:
                    head.setDirectionAndPosition(Direction.UP);
                    break;
                case DOWN:
                    head.setDirectionAndPosition(Direction.DOWN);
                    break;
                case LEFT:
                    head.setDirectionAndPosition(Direction.LEFT);
                    break;
                case RIGHT:
                    head.setDirectionAndPosition(Direction.RIGHT);
                    break;
            }
            savedDirection = Direction.NONE;
        }else {
            savedDirection = direction;
        }
    }

    private void move() {
        moveHead();
        moveBody();
    }


    private void moveHead() {
        if (head.getCurrentDirection() != null)
            moveInDirection(head);
    }

    private void moveInDirection(Segment segment) {
        double X_Segment = segment.getCenterX();
        double Y_Segment = segment.getCenterY();
        switch (segment.getCurrentDirection()) {
            case UP:
                segment.setCenterY(Y_Segment - SnakeSettings.GAME_SPEED);
                break;
            case DOWN:
                segment.setCenterY(Y_Segment + SnakeSettings.GAME_SPEED);
                break;
            case LEFT:
                segment.setCenterX(X_Segment - SnakeSettings.GAME_SPEED);
                break;
            case RIGHT:
                segment.setCenterX(X_Segment + SnakeSettings.GAME_SPEED);
                break;
        }
    }

    private boolean checkDistanceBetweenTwoChanges() {
        double distance = Math.abs(getDistance());
        System.out.println("Distance = " + distance);
        return  distance == 0 || distance >= 2 * SnakeSettings.RADIUS;
    }

    private double getDistance() {
        PositionCoordinates currentPosition = new PositionCoordinates(head.getCenterX(),head.getCenterY());
        PositionCoordinates lastPosition = Snake.getHeadCoordinatesFromBehind(0);

        if(lastPosition != null) {
            double distanceX = currentPosition.getPosX() - lastPosition.getPosX();
            double distanceY = currentPosition.getPosY() - lastPosition.getPosY();

            return distanceX == 0 ? distanceY : distanceX;
        }
        return 0;
    }


    private void moveBody() {
        for (int i = 1; i < snakeBody.size(); i++) {
            Segment currentSegment = snakeBody.get(i);

            if (comparePositions(currentSegment)) {
                int positionCounter = currentSegment.getPositionCounter();
                HeadMovement headMovement = Snake.getNextPosition(positionCounter);
                currentSegment.setCurrentDirection(headMovement.getFollowingDirection());
                currentSegment.incrementPositionCounter();
            }

            moveInDirection(currentSegment);
        }
    }


    private boolean comparePositions(Segment currentSegment) {
        PositionCoordinates currentSegmentsPosition = new PositionCoordinates(currentSegment.getCenterX(), currentSegment.getCenterY());
        int positionCounter = currentSegment.getPositionCounter();
        Direction direction = currentSegment.getCurrentDirection();
        HeadMovement headMovement = Snake.getNextPosition(positionCounter);


        if (headMovement != null) {
            double nextPosX = headMovement.getPositionCoordinates().getPosX();
            double nextPosY = headMovement.getPositionCoordinates().getPosY();

            switch (direction) {
                case RIGHT:
                    return currentSegmentsPosition.getPosX() >= nextPosX;
                case LEFT:
                    return currentSegmentsPosition.getPosX() <= nextPosX;
                case UP:
                    return currentSegmentsPosition.getPosY() <= nextPosY;
                case DOWN:
                    return currentSegmentsPosition.getPosY() >= nextPosY;
            }
        }
        return false;
    }

    private void checkBorders() {
        snakeBody.forEach(segment -> {
            if (segment.getCenterX() > gameBoardWidth) {
                segment.setCenterX(0);
            } else if (segment.getCenterX() < 0) {
                segment.setCenterX(gameBoardWidth);
            } else if (segment.getCenterY() > gameBoardHeight) {
                segment.setCenterY(0);
            } else if (segment.getCenterY() < 0) {
                segment.setCenterY(gameBoardHeight);
            }
        });
    }

}
