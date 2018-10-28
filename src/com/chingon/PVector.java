package com.chingon;

public class PVector {
    double xPos, yPos;

    public PVector(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    static PVector subtractVector(PVector v1, PVector v2) {
        return new PVector(v2.xPos - v1.xPos , v2.yPos - v2.yPos);
    }

    void addVector(PVector pVector) {
        xPos += pVector.xPos;
        yPos += pVector.yPos;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }
}
