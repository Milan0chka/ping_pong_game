package com.example.ping_pong.model;

/**
 * Represents a generic game object with a position in 2D space.
 */
public class GameObject {
    private double positionX;
    private double positionY;

    /**
     * Constructs a GameObject with the specified initial position.
     * @param X The initial position on the X-axis.
     * @param Y The initial position on the Y-axis.
     */
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
