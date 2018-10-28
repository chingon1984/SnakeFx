package com.chingon;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Pane gameBoard;

    private Snake snake;
    private Segment head;
    private final static int SNAKE_SEGMENTS = 2;
    private double gameBoardHeight;
    private double gameBoardWidth;
    private Direction direction, lastDirection;


    Segment testCircle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEnvironment();
        initializeSnake();
        initializeTimer();


    }

    private void initializeEnvironment() {
//        direction = Direction.RIGHT;
        gameBoardHeight = gameBoard.getPrefHeight();
        gameBoardWidth = gameBoard.getPrefWidth();
        System.out.println(gameBoardWidth + " " + gameBoardHeight);
    }

    private void initializeSnake() {
        snake = new Snake(SNAKE_SEGMENTS);
        head = snake.getHead();
        drawSnake();
    }

    private void drawSnake() {
        /*Test circle*/
        testCircle = new Segment(70, 100);
        gameBoard.getChildren().add(testCircle);
        /*END*/

        gameBoard.getChildren().add(head);
    }

    private void drawSnakeBody() {
        ArrayList<Circle> snakeBody = snake.getBody();
        for (Circle snakeSegment : snakeBody) {
            System.out.println("X : " + snakeSegment.getCenterX() + " Y = " + snakeSegment.getCenterY());
            gameBoard.getChildren().add(snakeSegment);
        }
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
        checkBorders();
        getUserInput();
        validateInput();
        move();
    }

    private void checkBorders() {
        if (head.getCenterX() > gameBoardWidth - head.getRadius()) {
            head.setCenterX(head.getRadius());
        } else if (head.getCenterX() < head.getRadius()) {
            head.setCenterX(gameBoardWidth - head.getRadius());
        } else if (head.getCenterY() > gameBoardHeight - head.getRadius()) {
            head.setCenterY(head.getRadius());
        } else if (head.getCenterY() < head.getRadius()) {
            head.setCenterY(gameBoardHeight - head.getRadius());
        }
    }


    private void getUserInput() {
        head.setFocusTraversable(true);
        head.setOnKeyPressed(event -> {
            head.setLastDirectionToDirection();
            if (event.getCode() == KeyCode.UP) {
//                direction = Direction.UP;
                head.setDirection(Direction.UP);
            }
            if (event.getCode() == KeyCode.DOWN) {
//                direction = Direction.DOWN;
                head.setDirection(Direction.DOWN);
            }
            if (event.getCode() == KeyCode.LEFT) {
//                direction = Direction.LEFT;
                head.setDirection(Direction.LEFT);
            }
            if (event.getCode() == KeyCode.RIGHT) {
//                direction = Direction.RIGHT;
                head.setDirection(Direction.RIGHT);
            }
        });
    }

    private void validateInput() {
        if(head.getDirection() == Direction.UP && head.getLastDirection() == Direction.DOWN)
            head.setDirection(Direction.DOWN);
        if(head.getDirection() == Direction.DOWN && head.getLastDirection() == Direction.UP)
            head.setDirection(Direction.UP);
        if(head.getDirection() == Direction.LEFT && head.getLastDirection() == Direction.RIGHT)
            head.setDirection(Direction.RIGHT);
        if(head.getDirection() == Direction.RIGHT && head.getLastDirection() == Direction.LEFT)
            head.setDirection(Direction.LEFT);
    }

    private void move() {
        double X_Head = head.getCenterX();
        double Y_Head = head.getCenterY();
        if (head.getDirection() != null)
            switch (head.getDirection()) {
                case UP:
                    head.setCenterY(Y_Head - SnakeSettings.GAME_SPEED);

                    if (testCircle.getCenterX() < X_Head)
                        testCircle.setCenterX(testCircle.getCenterX() + SnakeSettings.GAME_SPEED);
                    else if (testCircle.getCenterX() > X_Head)
                        testCircle.setCenterX(testCircle.getCenterX() - SnakeSettings.GAME_SPEED);
                    else
                        testCircle.setCenterY(testCircle.getCenterY() - SnakeSettings.GAME_SPEED);

                    break;
                case DOWN:
                    head.setCenterY(Y_Head + SnakeSettings.GAME_SPEED);
                    if (testCircle.getCenterX() < X_Head)
                        testCircle.setCenterX(testCircle.getCenterX() + SnakeSettings.GAME_SPEED);
                    else if (testCircle.getCenterX() > X_Head)
                        testCircle.setCenterX(testCircle.getCenterX() - SnakeSettings.GAME_SPEED);
                    else
                        testCircle.setCenterY(testCircle.getCenterY() + SnakeSettings.GAME_SPEED);
                    break;
                case LEFT:
                    head.setCenterX(X_Head - SnakeSettings.GAME_SPEED);
                    if (testCircle.getCenterY() < Y_Head)
                        testCircle.setCenterY(testCircle.getCenterY() + SnakeSettings.GAME_SPEED);
                    else if (testCircle.getCenterY() > Y_Head)
                        testCircle.setCenterY(testCircle.getCenterY() - SnakeSettings.GAME_SPEED);
                    else
                        testCircle.setCenterX(testCircle.getCenterX() - SnakeSettings.GAME_SPEED);
                    break;
                case RIGHT:
                    head.setCenterX(X_Head + SnakeSettings.GAME_SPEED);
                    if (testCircle.getCenterY() < Y_Head)
                        testCircle.setCenterY(testCircle.getCenterY() + SnakeSettings.GAME_SPEED);
                    else if (testCircle.getCenterY() > Y_Head)
                        testCircle.setCenterY(testCircle.getCenterY() - SnakeSettings.GAME_SPEED);
                    else
                        testCircle.setCenterX(testCircle.getCenterX() + SnakeSettings.GAME_SPEED);
                    break;
            }
    }

    private void linkSegments(Segment segment1, Segment segment2) {
        double XPosSegment1 = segment1.getCenterX();
        double YPosSegment1 = segment1.getCenterY();
        double XPosSegment2 = segment2.getCenterX();
        double YPosSegment2 = segment2.getCenterY();



    }






}
