package com.chingon;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private final static double SPEED_X = 2, SPEED_Y = -2;
    private static final int TRANSLATION_DURATION = 5000;
    private double gameBoardHeight;
    private double gameboardWidth;
    private AnimationTimer animationTimer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEnvironment();
        initializeSnake();
        initializeTimer();

    }

    private void initializeEnvironment() {
        gameBoardHeight = gameBoard.getPrefHeight();
        gameboardWidth = gameBoard.getPrefWidth();
        System.out.println(gameboardWidth + " " + gameBoardHeight);
    }

    private void initializeSnake() {
        snake = new Snake(SNAKE_SEGMENTS);
        head = snake.getHead();
        drawSnake();
    }

    private void drawSnake() {
        gameBoard.getChildren().add(head);
        ArrayList<Circle> snakeBody = snake.getBody();
        for (Circle snakeSegment : snakeBody) {
            System.out.println("X : " + snakeSegment.getCenterX() + " Y = " + snakeSegment.getCenterY());
            gameBoard.getChildren().add(snakeSegment);
        }
    }

    private void initializeTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        animationTimer.start();
    }

    private void updateGame() {
        double xPos = head.getCenterX();
        double yPos = head.getCenterY();
        PositionCoordinates headPosition = moveHead(xPos,yPos);

        head.setCenterX(headPosition.getPosX() + SPEED_X);
        head.setCenterY(headPosition.getPosY() + SPEED_Y);
    }

    private PositionCoordinates moveHead(double xPos, double yPos) {
        if (xPos > gameboardWidth - head.getRadius()) {
            xPos = head.getRadius();
        }
        if (xPos < head.getRadius()) {
            xPos = gameboardWidth - head.getRadius();
        }
        if (yPos > gameBoardHeight - head.getRadius()) {
            yPos = head.getRadius();
        }
        if (yPos < head.getRadius()) {
            yPos = gameBoardHeight - head.getRadius();
        }
        return new PositionCoordinates(xPos,yPos);
    }


//
//    private void handleKeyPressed(TranslateTransition snakeTransition) {
//        Circle head = snake.getHead();
//        head.setFocusTraversable(true);
//        head.setOnKeyPressed(event -> {
////            System.out.println("gameBoard Dimensions: " + gameBoard.getLayoutBounds().getWidth() + " x " + gameBoard.getLayoutBounds().getHeight());
//            if (event.getCode() == KeyCode.DOWN) {
//                snakeTransition.setToY(gameBoardHeight);
//                System.out.println("Down Pressed : newY = " + head.getCenterY());
//            } else if (event.getCode() == KeyCode.UP) {
//                snakeTransition.setToY(-gameBoardHeight);
//                System.out.println("Up Pressed");
//            } else if (event.getCode() == KeyCode.LEFT) {
//                snakeTransition.setToX(-gameboardWidth);
//                System.out.println("Left Pressed");
//            } else if (event.getCode() == KeyCode.RIGHT) {
//                snakeTransition.setToX(gameboardWidth);
//                System.out.println("Right Pressed");
//            }
//            snakeTransition.play();
//            System.out.println("head Position: (" + head.getCenterX() + "," + head.getCenterY() + ")");
//        });
//    }


}
