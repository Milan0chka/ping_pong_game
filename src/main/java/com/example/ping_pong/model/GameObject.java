package com.example.ping_pong.model;

public class GameObject {
    private double positionX;
    private double positionY;

    public GameObject(double X, double Y) {
        this.positionX = X;
        this.positionY = Y;
    }

    public double getPositionX() {
       return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}
