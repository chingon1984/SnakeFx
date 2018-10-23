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
    private Circle head;
    private final static int SNAKE_SEGMENTS = 2;
    private final static double GAME_SPEED = 5;
    private double gameBoardHeight;
    private double gameBoardWidth;
    private Direction direction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEnvironment();
        initializeSnake();
        initializeTimer();

    }

    private void initializeEnvironment() {
        direction = Direction.RIGHT;
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
        moveHead();
        handleUserInput();
    }

    private void moveHead() {
        if (head.getCenterX() > gameBoardWidth - head.getRadius()) {
            head.setCenterX(head.getRadius());
        }
        if (head.getCenterX() < head.getRadius()) {
            head.setCenterX(gameBoardWidth - head.getRadius());
        }
        if (head.getCenterY() > gameBoardHeight - head.getRadius()) {
            head.setCenterY(head.getRadius());
        }
        if (head.getCenterY() < head.getRadius()) {
            head.setCenterY(gameBoardHeight - head.getRadius());
        }
    }

    private void handleUserInput() {
        head.setFocusTraversable(true);
        head.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                direction = Direction.UP;
            }
            if (event.getCode() == KeyCode.DOWN) {
                direction = Direction.DOWN;
            }
            if (event.getCode() == KeyCode.LEFT) {
                direction = Direction.LEFT;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                direction = Direction.RIGHT;
            }
        });

        switch (direction) {
            case UP:
                head.setCenterY(head.getCenterY() - GAME_SPEED);
                break;
            case DOWN:
                head.setCenterY(head.getCenterY() + GAME_SPEED);
                break;
            case LEFT:
                head.setCenterX(head.getCenterX() - GAME_SPEED);
                break;
            case RIGHT:
                head.setCenterX(head.getCenterX() + GAME_SPEED);
                break;
        }
    }

}
