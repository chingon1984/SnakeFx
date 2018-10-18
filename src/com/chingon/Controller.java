package com.chingon;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Pane gameBoard;

    private Snake snake;
    private static final int TRANSLATION_DURATION = 5000;
    private double gameBoardHeight;
    private double gameboardWidth;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEnvironment();
        snake = new Snake();
//        snake.grow();
        drawSnake(snake.getBody());
        TranslateTransition snakeTransition = createTransition();
        handleKeyPressed(snakeTransition);
//        snakeTransition.play();
    }

    private void initializeEnvironment() {
        gameBoardHeight = gameBoard.getPrefHeight();
        gameboardWidth = gameBoard.getPrefWidth();
        System.out.println(gameboardWidth + " " + gameBoardHeight);
    }

    private void handleKeyPressed(TranslateTransition snakeTransition) {
        Circle head = snake.getHead();
        head.setFocusTraversable(true);
        head.setOnKeyPressed(event -> {
//            System.out.println("gameBoard Dimensions: " + gameBoard.getLayoutBounds().getWidth() + " x " + gameBoard.getLayoutBounds().getHeight());
            if (event.getCode() == KeyCode.DOWN) {
                snakeTransition.setToX(gameBoardHeight);
                System.out.println("Down Pressed : newY = " + head.getCenterY());
            } else if (event.getCode() == KeyCode.UP) {
                snakeTransition.setToX(-gameBoardHeight);
                System.out.println("Up Pressed");
            } else if (event.getCode() == KeyCode.LEFT) {
                snakeTransition.setToX(-gameboardWidth);
                System.out.println("Left Pressed");
            } else if (event.getCode() == KeyCode.RIGHT) {
                snakeTransition.setToX(gameboardWidth);
                System.out.println("Right Pressed");
            }
            snakeTransition.play();
            System.out.println("head Position: (" + head.getCenterX() + "," + head.getCenterY() + ")");
        });
    }

    private void drawSnake(List<Circle> snakeBody) {
        for (Circle circle : snakeBody)
            gameBoard.getChildren().add(circle);

    }

    private TranslateTransition createTransition() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(TRANSLATION_DURATION), snake.getHead());
        transition.setOnFinished((event -> {
                    Circle head = snake.getHead();
                    head.setCenterX(head.getCenterX() + head.getTranslateX());
                    head.setCenterY(head.getCenterY() + head.getTranslateY());
                    head.setTranslateX(0);
                    head.setTranslateY(0);
                }
                )
        );
        return transition;
    }


}
