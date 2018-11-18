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

    PositionCoordinates addToPos(double posX, double posY) {
        this.posX += posX;
        this.posY += posY;
        return returnPosition();
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "PosX: " + this.posX + " PosY: " + this.posY;
    }

    public static PositionCoordinates getPositionCoordinates(double posX, double posY) {
        return new PositionCoordinates(posX, posY);
    }

    public PositionCoordinates returnPosition() {
        return PositionCoordinates.getPositionCoordinates(this.posX,this.posY);
    }
}
