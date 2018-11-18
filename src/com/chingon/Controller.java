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
            if ((event.getCode() == KeyCode.UP) && (head.getCurrentDirection() != Direction.DOWN))
                head.setDirectionAndPosition(Direction.UP);
            else if ((event.getCode() == KeyCode.DOWN) && (head.getCurrentDirection() != Direction.UP))
                head.setDirectionAndPosition(Direction.DOWN);
            else if ((event.getCode() == KeyCode.LEFT) && (head.getCurrentDirection() != Direction.RIGHT))
                head.setDirectionAndPosition(Direction.LEFT);
            else if ((event.getCode() == KeyCode.RIGHT) && (head.getCurrentDirection() != Direction.LEFT))
                head.setDirectionAndPosition(Direction.RIGHT);
        });
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


    private void moveBody() {
        for (int i = 1; i < snakeBody.size(); i++) {
            Segment currentSegment = snakeBody.get(i);
            int positionCounter = currentSegment.getPositionCounter();


            if(checkPosition(currentSegment)) {
                HeadMovement headMovement = Snake.getNextPosition(positionCounter);
                currentSegment.setCurrentDirection(headMovement.getFollowingDirection());
                currentSegment.incrementPositionCounter();
            }

            moveInDirection(currentSegment);
        }
    }


    private boolean checkPosition(Segment currentSegment) {
        PositionCoordinates currentSegmentsPosition = new PositionCoordinates(currentSegment.getCenterX(), currentSegment.getCenterY());
        int positionCounter = currentSegment.getPositionCounter();
        HeadMovement headMovement = Snake.getNextPosition(positionCounter);
        double nextPosX = -1;
        double nextPosY = -1;

        if (headMovement != null) {
            nextPosX = headMovement.getPositionCoordinates().getPosX();
            nextPosY = headMovement.getPositionCoordinates().getPosY();
        }
        return currentSegmentsPosition.getPosX() == nextPosX && currentSegmentsPosition.getPosY() == nextPosY;
    }

//***********************************
//    private void moveBody() {
//        for (int i = 1; i < snakeBody.size(); i++) {
//            Segment currentSegment = snakeBody.get(i);
//            PositionCoordinates currentSegmentsPosition = new PositionCoordinates(currentSegment.getCenterX(), currentSegment.getCenterY());
//            Direction predecessorsCurrentDirection = snakeBody.get(i - 1).getCurrentDirection();
//            Double posX = currentSegment.getCenterX();
//            Double posY = currentSegment.getCenterY();
//
//
//            if (currentSegment.getCurrentDirection() != predecessorsCurrentDirection) {
//                if (!currentSegment.isPositionMarked() && snakeBody.get(0).getCurrentDirection() != Direction.NONE) {
//                    switch (currentSegment.getCurrentDirection()) {
//                        case RIGHT:
//                            currentSegment.setPositionOfDirectionChange(PositionCoordinates.getPositionCoordinates(posX + 2*SnakeSettings.RADIUS, posY));
//                            break;
//                        case LEFT:
//                            currentSegment.setPositionOfDirectionChange(PositionCoordinates.getPositionCoordinates(posX - 2*SnakeSettings.RADIUS, posY));
//                            break;
//                        case UP:
//                            currentSegment.setPositionOfDirectionChange(PositionCoordinates.getPositionCoordinates(posX,posY - 2*SnakeSettings.RADIUS));
//                            break;
//                        case DOWN:
//                            currentSegment.setPositionOfDirectionChange(PositionCoordinates.getPositionCoordinates(posX,posY + 2*SnakeSettings.RADIUS));
//                            break;
//                        case NONE:
//                            currentSegment.setPositionOfDirectionChange(snakeBody.get(0).getPositionOfDirectionChange());
//                            currentSegment.setCurrentDirection(Direction.RIGHT);
//                            break;
//                    }
//                    currentSegment.setMarkPosition(true);
//                }
//            }
//
//            if (currentSegmentsPosition.getPosX() == currentSegment.getPositionOfDirectionChange().getPosX() && currentSegmentsPosition.getPosY() == currentSegment.getPositionOfDirectionChange().getPosY()) {
//                currentSegment.setCurrentDirection(predecessorsCurrentDirection);
//                currentSegment.setMarkPosition(false);
//            }
//
//            moveInDirection(currentSegment);

//            *******************

//            if (positionOfDirectionChange != null) {
//                if(!currentSegment.isPositionMarked()) {
//                    if (currentSegmentsPosition.getPosX() == positionOfDirectionChange.getPosX() && currentSegmentsPosition.getPosY() == positionOfDirectionChange.getPosY()) {
//                        currentSegment.setCurrentDirection(snakeBody.get(i - 1).getCurrentDirection());
//                        currentSegment.setPositionMarked(true);
//                    } else if (currentSegmentsPosition.getPosX() < positionOfDirectionChange.getPosX()) {
//                        currentSegment.setCurrentDirection(Direction.RIGHT);
//                    } else if (currentSegmentsPosition.getPosX() > positionOfDirectionChange.getPosX()) {
//                        currentSegment.setCurrentDirection(Direction.LEFT);
//                    } else if (currentSegmentsPosition.getPosY() < positionOfDirectionChange.getPosY()) {
//                        currentSegment.setCurrentDirection(Direction.DOWN);
//                    } else if (currentSegmentsPosition.getPosY() > positionOfDirectionChange.getPosY()) {
//                        currentSegment.setCurrentDirection(Direction.UP);
//                    }
//                }
//            }
//            moveInDirection(currentSegment);
//        }
//    }

//
//    private void moveBody() {
//        for (int i = 0; i < snakeBody.size() - 1; i++)
//            linkSegments(snakeBody.get(i), snakeBody.get(i + 1));
//    }
//
//    private void linkSegments(Segment segment1, Segment segment2) {
//        double XPosSegment1 = segment1.getCenterX();
//        double YPosSegment1 = segment1.getCenterY();
//        double XPosSegment2 = segment2.getCenterX();
//        double YPosSegment2 = segment2.getCenterY();
//
//
//        switch (segment1.getCurrentDirection()) {
//            case UP:
//                if (XPosSegment2 < XPosSegment1)
//                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
//                else if (XPosSegment2 > XPosSegment1)
//                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
//                else {
//                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
//                    segment2.setCurrentDirection(segment1.getCurrentDirection());
//                }
//                break;
//            case DOWN:
//                if (XPosSegment2 < XPosSegment1)
//                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
//                else if (XPosSegment2 > XPosSegment1)
//                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
//                else {
//                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
//                    segment2.setCurrentDirection(segment1.getCurrentDirection());
//                }
//                break;
//            case LEFT:
//                if (YPosSegment2 < YPosSegment1)
//                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
//                else if (YPosSegment2 > YPosSegment1)
//                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
//                else {
//                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
//                    segment2.setCurrentDirection(segment1.getCurrentDirection());
//                }
//                break;
//            case RIGHT:
//                if (YPosSegment2 < YPosSegment1)
//                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
//                else if (YPosSegment2 > YPosSegment1)
//                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
//                else {
//                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
//                    segment2.setCurrentDirection(segment1.getCurrentDirection());
//                }
//                break;
//        }
//    }

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
