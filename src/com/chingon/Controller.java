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

    private Segment head;
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
        head = snakeBody.get(0);
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
            head.setLastDirectionToCurrentDirection();
            if ((event.getCode() == KeyCode.UP) && (head.getLastDirection() != Direction.DOWN))
                Snake.setDirectionAndLogCoordinates(Direction.UP);
            else if ((event.getCode() == KeyCode.DOWN) && (head.getLastDirection() != Direction.UP))
                Snake.setDirectionAndLogCoordinates(Direction.DOWN);
            else if ((event.getCode() == KeyCode.LEFT) && (head.getLastDirection() != Direction.RIGHT))
                Snake.setDirectionAndLogCoordinates(Direction.LEFT);
            else if ((event.getCode() == KeyCode.RIGHT) && (head.getLastDirection() != Direction.LEFT))
                Snake.setDirectionAndLogCoordinates(Direction.RIGHT);
        });
    }

    private void move() {
        moveHead();
        moveBody();
    }


    private void moveHead() {
        double X_Head = head.getCenterX();
        double Y_Head = head.getCenterY();
        if (head.getCurrentDirection() != null)
            switch (head.getCurrentDirection()) {
                case UP:
                    head.setCenterY(Y_Head - SnakeSettings.GAME_SPEED);
                    break;
                case DOWN:
                    head.setCenterY(Y_Head + SnakeSettings.GAME_SPEED);
                    break;
                case LEFT:
                    head.setCenterX(X_Head - SnakeSettings.GAME_SPEED);
                    break;
                case RIGHT:
                    head.setCenterX(X_Head + SnakeSettings.GAME_SPEED);
                    break;
            }
    }

    private void moveBody() {
        for (int i = 0; i < snakeBody.size() - 1; i++)
            linkSegments(snakeBody.get(i), snakeBody.get(i + 1));
    }

    private void linkSegments(Segment segment1, Segment segment2) {
        double XPosSegment1 = segment1.getCenterX();
        double YPosSegment1 = segment1.getCenterY();
        double XPosSegment2 = segment2.getCenterX();
        double YPosSegment2 = segment2.getCenterY();


        switch (segment1.getCurrentDirection()) {
            case UP:
                if (XPosSegment2 < XPosSegment1)
                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (XPosSegment2 > XPosSegment1)
                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
                else {
                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
                    segment2.setCurrentDirection(segment1.getCurrentDirection());
                }
                break;
            case DOWN:
                if (XPosSegment2 < XPosSegment1)
                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (XPosSegment2 > XPosSegment1)
                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
                else {
                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
                    segment2.setCurrentDirection(segment1.getCurrentDirection());
                }
                break;
            case LEFT:
                if (YPosSegment2 < YPosSegment1)
                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (YPosSegment2 > YPosSegment1)
                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
                else {
                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
                    segment2.setCurrentDirection(segment1.getCurrentDirection());
                }
                break;
            case RIGHT:
                if (YPosSegment2 < YPosSegment1)
                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (YPosSegment2 > YPosSegment1)
                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
                else {
                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
                    segment2.setCurrentDirection(segment1.getCurrentDirection());
                }
                break;
        }
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
