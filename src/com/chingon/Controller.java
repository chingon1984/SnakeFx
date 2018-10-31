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
    private ArrayList<Segment> snakeBody;


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
        snakeBody = new ArrayList<>();
        head = snake.getHead();



        drawSnake();
    }

    private void drawSnake() {
        Segment testCircle1 = new Segment(70, 100);
        Segment testCircle2 = new Segment(40, 100);
        Segment testCircle3 = new Segment(10, 100);
        Segment testCircle4 = new Segment(580, 100);
        Segment testCircle5 = new Segment(550, 100);


        snakeBody = new ArrayList<>();
        snakeBody.add(head);
        snakeBody.add(testCircle1);
        snakeBody.add(testCircle2);
        snakeBody.add(testCircle3);
        snakeBody.add(testCircle4);
        snakeBody.add(testCircle5);


        snakeBody.forEach(segment -> {
            gameBoard.getChildren().add(segment);
        });

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

        getUserInput();
        validateInput();
        move();
        checkBorders();
    }

    private void checkBorders() {
        snakeBody.forEach(segment -> {
        if (segment.getCenterX() > gameBoardWidth) {
            segment.setCenterX(0);
        } else if (segment.getCenterX() < 0 ) {
            segment.setCenterX(gameBoardWidth);
        } else if (segment.getCenterY() > gameBoardHeight) {
            segment.setCenterY(0);
        } else if (segment.getCenterY() < 0) {
            segment.setCenterY(gameBoardHeight);
        }
        });
    }


    private void getUserInput() {
        head.setFocusTraversable(true);
        head.setOnKeyPressed(event -> {
            head.setLastDirectionToDirection();

            if (event.getCode() == KeyCode.UP)
                head.setDirection(Direction.UP);
            if (event.getCode() == KeyCode.DOWN)
                head.setDirection(Direction.DOWN);
            if (event.getCode() == KeyCode.LEFT)
                head.setDirection(Direction.LEFT);
            if (event.getCode() == KeyCode.RIGHT)
                head.setDirection(Direction.RIGHT);
        });
    }

    private void validateInput() {
        if (head.getDirection() == Direction.UP && head.getLastDirection() == Direction.DOWN)
            head.setDirection(Direction.DOWN);
        if (head.getDirection() == Direction.DOWN && head.getLastDirection() == Direction.UP)
            head.setDirection(Direction.UP);
        if (head.getDirection() == Direction.LEFT && head.getLastDirection() == Direction.RIGHT)
            head.setDirection(Direction.RIGHT);
        if (head.getDirection() == Direction.RIGHT && head.getLastDirection() == Direction.LEFT)
            head.setDirection(Direction.LEFT);
    }

    private void move() {
        double X_Head = head.getCenterX();
        double Y_Head = head.getCenterY();
        if (head.getDirection() != null)
            switch (head.getDirection()) {
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

        for (int i = 0; i < snakeBody.size() -1; i++)
            linkSegments(snakeBody.get(i), snakeBody.get(i + 1));
    }

    private void linkSegments(Segment segment1, Segment segment2) {
        double XPosSegment1 = segment1.getCenterX();
        double YPosSegment1 = segment1.getCenterY();
        double XPosSegment2 = segment2.getCenterX();
        double YPosSegment2 = segment2.getCenterY();


        switch (head.getDirection()) {
            case UP:
                if (XPosSegment2 < XPosSegment1)
                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (XPosSegment2 > XPosSegment1)
                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
                else
                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);

                break;
            case DOWN:
                if (XPosSegment2 < XPosSegment1)
                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (XPosSegment2 > XPosSegment1)
                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
                else
                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
                break;
            case LEFT:
                if (YPosSegment2 < YPosSegment1)
                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (YPosSegment2 > YPosSegment1)
                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
                else
                    segment2.setCenterX(XPosSegment2 - SnakeSettings.GAME_SPEED);
                break;
            case RIGHT:
                if (YPosSegment2 < YPosSegment1)
                    segment2.setCenterY(YPosSegment2 + SnakeSettings.GAME_SPEED);
                else if (YPosSegment2 > YPosSegment1)
                    segment2.setCenterY(YPosSegment2 - SnakeSettings.GAME_SPEED);
                else
                    segment2.setCenterX(XPosSegment2 + SnakeSettings.GAME_SPEED);
                break;
        }

    }


}
