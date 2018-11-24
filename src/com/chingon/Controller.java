package com.chingon;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Pane gameBoard;
    @FXML
    Label scoreLabel;

    private Head head;
    private double gameBoardHeight;
    private double gameBoardWidth;
    private ArrayList<Segment> snakeBody;
    private Direction savedDirection;
    private Food food;
    private boolean isGameOver;
    private boolean isFoodAvailable;
    private int score;

    private long timecheck = 0;


    private long lastTime;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeEnvironment();
        initializeSnake();
        initializeTimer();
    }

    private void initializeEnvironment() {
        score = 0;
        scoreLabel.setText(Integer.toString(score));

        gameBoardHeight = gameBoard.getPrefHeight();
        gameBoardWidth = gameBoard.getPrefWidth();

        SnakeSettings.GAMEBOARD_HEIGHT = gameBoardHeight;
        SnakeSettings.GAMEBOARD_WIDTH = gameBoardWidth;

        isGameOver = false;
        isFoodAvailable = false;
        lastTime = 0;


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

    private double generateRandomPos(double min, double max) {
        return Math.random() * (max - 2 * min) + min;
    }

    private void initializeTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(timecheck == 0)
                    timecheck = now;

                long delta = now - timecheck;
                if(delta >= 10_000_000) {
                    System.out.println(delta);
                    timecheck = now;

                    updateGame(now);
                    if (isGameOver) {
                        this.stop();
                        System.out.println("Your score was: " + score);
                    }
                }

            }
        };
        animationTimer.start();


    }

    private void updateGame(long now) {
        if (isFoodAvailable)
            checkFoodCollision();
        getUserInput();
        move();
        checkSnakeCollision();
        checkBorders();
        createFoodAfterTime(now);
    }

    private void getUserInput() {
        head.setFocusTraversable(true);

        head.setOnKeyPressed(event -> {
                    Direction direction = Direction.NONE;
                    if ((event.getCode() == KeyCode.UP) && (head.getCurrentDirection() != Direction.DOWN))
                        direction = Direction.UP;
                    else if ((event.getCode() == KeyCode.DOWN) && (head.getCurrentDirection() != Direction.UP))
                        direction = Direction.DOWN;
                    else if ((event.getCode() == KeyCode.LEFT) && (head.getCurrentDirection() != Direction.RIGHT))
                        direction = Direction.LEFT;
                    else if ((event.getCode() == KeyCode.RIGHT) && (head.getCurrentDirection() != Direction.LEFT))
                        direction = Direction.RIGHT;

                    preventOverlapping(direction);
                }
        );
        if (savedDirection != Direction.NONE)
            preventOverlapping(savedDirection);
    }

    private void preventOverlapping(Direction direction) {
        /*When "U-Turning" Overlapping of Head with rest of Body is prevented, when next KeyEvent is too soon...
         * Intended Direction will be saved and used when Overlapping is not possible anymore */

        Direction nextDirection = (savedDirection == Direction.NONE ? direction : savedDirection);

        if (checkDistanceBetweenTwoChanges()) {
            switch (nextDirection) {
                case UP:
                    head.saveDirectionAndPosition(Direction.UP);
                    break;
                case DOWN:
                    head.saveDirectionAndPosition(Direction.DOWN);
                    break;
                case LEFT:
                    head.saveDirectionAndPosition(Direction.LEFT);
                    break;
                case RIGHT:
                    head.saveDirectionAndPosition(Direction.RIGHT);
                    break;
            }
            savedDirection = Direction.NONE;
        } else {
            savedDirection = direction;
        }
    }

    private void move() {
        moveHead();
        moveSnakeBody();
    }


    private void moveHead() {
        if (head.getCurrentDirection() != null)
            moveSegmentToCurrentDirection(head);
    }

    private void moveSegmentToCurrentDirection(Segment segment) {
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
        double distance = Math.abs(getDistanceBetweenTwoChanges());
//        System.out.println("Distance = " + distance);
        return distance == 0 || distance >= 2 * SnakeSettings.RADIUS;
    }

    private double getDistanceBetweenTwoChanges() {
        PositionCoordinates currentPosition = new PositionCoordinates(head.getCenterX(), head.getCenterY());
        PositionCoordinates lastPosition = Snake.getLatestCoordinates(0);

        if (lastPosition != null) {
            double distanceX = currentPosition.getPosX() - lastPosition.getPosX();
            double distanceY = currentPosition.getPosY() - lastPosition.getPosY();

            return distanceX == 0 ? distanceY : distanceX;
        }
        return 0;
    }


    private void moveSnakeBody() {
        for (int i = 1; i < snakeBody.size(); i++) {
            Segment currentSegment = snakeBody.get(i);

            if (checkIfSegmentPassedSavedPosition(currentSegment)) {
                int positionCounter = currentSegment.getPositionCounter();
                PositionAndDirection positionAndDirection = Snake.getNextPosition(positionCounter);
                currentSegment.saveCurrentDirection(positionAndDirection.getFollowingDirection());
                currentSegment.incrementPositionCounter();
            }

            moveSegmentToCurrentDirection(currentSegment);
        }
    }


    private boolean checkIfSegmentPassedSavedPosition(Segment currentSegment) {
        PositionCoordinates currentSegmentsPosition = new PositionCoordinates(currentSegment.getCenterX(), currentSegment.getCenterY());
        int positionCounter = currentSegment.getPositionCounter();
        Direction direction = currentSegment.getCurrentDirection();
        PositionAndDirection positionAndDirection = Snake.getNextPosition(positionCounter);
        double delta = 0.5;


        if (positionAndDirection != null) {
            double nextPosX = positionAndDirection.getPositionCoordinates().getPosX();
            double nextPosY = positionAndDirection.getPositionCoordinates().getPosY();

//            CHeck if currentSegment passed the point of direction Change
            switch (direction) {
                case RIGHT:
                case LEFT:
                    return currentSegmentsPosition.getPosX() <= nextPosX+delta && currentSegmentsPosition.getPosX() >= nextPosX-delta;
                case UP:
                case DOWN:
                    return currentSegmentsPosition.getPosY() <= nextPosY+delta && currentSegmentsPosition.getPosY() >= nextPosY-delta;
            }
        }
        return false;
    }


    private void checkSnakeCollision() {
        if (checkCollisions(head, 2, 0)) {
            isGameOver = true;
            System.out.println("Snake bit itself");
        }
    }

    private void checkFoodCollision() {
        if (checkCollisions(food, 0, snakeBody.size() - 1)) {
            resetFood();
            grow(SnakeSettings.GROW_SEGMENTS_PER_MEAL);
            isFoodAvailable = false;
            score += 50;
            scoreLabel.setText(Integer.toString(score));
            System.out.println("FOOD was eaten!");
        }
    }

    private boolean checkCollisions(Segment checkSegment, int start, int end) {
        if (checkSegment == null)
            return false;
        for (int i = start; i < snakeBody.size() - end; i++) {
            Segment segment = snakeBody.get(i);
            Shape intersectionShape = Shape.intersect(checkSegment, segment);
            boolean isIntersection = intersectionShape.getBoundsInLocal().getWidth() != -1;
            if (isIntersection)
                return true;
        }
        return false;
    }

    private void resetFood() {
        food.setVisible(false);
//        gameBoard.getChildren().remove(food);
//        food = null;
        lastTime = 0;

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

    private void grow(int amount) {
        for (int i = 0; i < amount; i++) {
            gameBoard.getChildren().add(Snake.addSegmentsToBody());
        }
    }

    private void createFoodAfterTime(long now) {
        if (lastTime == 0) {
            lastTime = now;
            return;
        }

        long deltaTime = (now - lastTime) / 1_000_000_000;
        if (deltaTime >= SnakeSettings.FOOD_REFRESH_TIME) {
            createFood();
            lastTime = now;
        }
    }

    private void createFood() {
        if (!isFoodAvailable) {
            boolean checker = false;
            while (!checker) {
                double posX = generateRandomPos(SnakeSettings.RADIUS, SnakeSettings.GAMEBOARD_WIDTH);
                double posY = generateRandomPos(SnakeSettings.RADIUS, SnakeSettings.GAMEBOARD_HEIGHT);
                Food food = new Food(posX, posY);

                if (!checkCollisions(food, 0, 0)) {
                    checker = true;
                    this.food = food;
                    makeFoodAvailable();
                    if (!gameBoard.getChildren().contains(this.food))
                        gameBoard.getChildren().add(this.food);
                }
            }
        }
    }

    private void makeFoodAvailable() {
        this.food.setVisible(true);
        isFoodAvailable = true;
    }

}
