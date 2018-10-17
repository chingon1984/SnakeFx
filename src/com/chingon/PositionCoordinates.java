package com.chingon;

public class PositionCoordinates {
    private double posX, posY;

    PositionCoordinates(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    double getPosX() {
        return posX;
    }

    double getPosY() {
        return posY;
    }

    void addToPos(double posX, double posY) {
        this.posX += posX;
        this.posY += posY;
    }
}
