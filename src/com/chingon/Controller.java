package com.chingon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Pane gameBoard;

    private Snake snake;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        snake = new Snake();
        handleKeyPressed();
        snake.grow();
        snake.grow();
        drawSnake(snake.getBody());

    }

    private void handleKeyPressed() {
        Circle head = snake.getHead();
        head.setFocusTraversable(true);
        head.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DOWN) {
                System.out.println("Down Pressed : newY = " + head.getCenterY());
            }else if(event.getCode() == KeyCode.UP) {
                System.out.println("Up Pressed");
            }else if(event.getCode() == KeyCode.LEFT) {
                System.out.println("Left Pressed");
            }else if(event.getCode() == KeyCode.RIGHT) {
                System.out.println("Right Pressed");
            }
        });
    }

    private void drawSnake(List<Circle> snakeBody) {
        for(Circle circle : snakeBody)
            gameBoard.getChildren().add(circle);

    }







}
