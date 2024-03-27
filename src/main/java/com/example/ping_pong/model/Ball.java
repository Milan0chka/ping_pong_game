package com.example.ping_pong.model;

import static java.lang.Math.random;
/**
 * Represents a ball object in the game.
 */
public class Ball extends GameObject implements Resizable {

    private int radius;
    private int speed;
    private int speedChangeRate;
    private int directionY;
    private int directionX;
    private boolean pause;
    private int bounceCount;
    private double angleX;
    private double angleY;

    /**
     * Constructs a new Ball object with default settings.
     */
    public Ball() {
        super(325, 275);
        this.radius = 15;
        this.speed = 3;
        this.speedChangeRate = 25;
        this.pause = true;
        // Direction of ball's movement decides randomly
        this.directionX = random() >= 0.5 ? 1 : -1;
        this.directionY = random() >= 0.5 ? 1 : -1;
        this.angleX = random() + 1;
        this.angleY = random() + 1;
        this.bounceCount = 0;
    }

    /**
     * Moves the ball according to its current direction and speed.
     */
    public void move() {
        this.setPositionX(this.getPositionX() + directionX * (speed + bounceCount * speedChangeRate * 0.01) * angleX);
        this.setPositionY(this.getPositionY() + directionY * (speed + bounceCount * speedChangeRate * 0.01) * angleY);
    }

    /**
     * Changes the vertical direction of the ball's movement and updates bounce count and angle.
     */
    public void bounceY() {
        this.directionY *= -1;
        this.bounceCount++;
        this.angleY = random() + 1;
    }

    /**
     * Changes the horizontal direction of the ball's movement and updates bounce count and angle.
     */
    public void bounceX() {
        this.directionX *= -1;
        this.bounceCount++;
        this.angleX = random() + 1;
    }

    public int getBounceCount() {
        return bounceCount;
    }

    public void setBounceCount(int bounceCount) {
        this.bounceCount = bounceCount;
    }

    public boolean isPause() {
        return this.pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public int getDirectionY() {
        return directionY;
    }


    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }


    public int getDirectionX() {
        return directionX;
    }


    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getSpeed() {
        return speed;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public int getSpeedChangeRate() {
        return speedChangeRate;
    }


    public void setSpeedChangeRate(int speedChangeRate) {
        this.speedChangeRate = speedChangeRate;
    }


    public int getRadius() {
        return radius;
    }


    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void resizeX(double factor) {
        this.setPositionX(this.getPositionX() * factor);
    }

    @Override
    public void resizeY(double factor) {
        this.setPositionY(this.getPositionY() * factor);
    }
}
