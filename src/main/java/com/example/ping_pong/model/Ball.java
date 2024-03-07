package com.example.ping_pong.model;

import static java.lang.Math.random;

public class Ball extends GameObject implements Resizable{

    private  int radius;
    private int speed;
    private int speedChangeRate;
    private int directionY;
    private int directionX;

    public Ball(){
        super(325,275);
        this.radius = 15;
        this.speed = 5;
        this.speedChangeRate = 10;
        //direction of ball`s movement decides randomly
        this.directionX = random() >= 0.5 ? 1: -1;
        this.directionY = random() >= 0.5 ? 1: -1;
    }

    public void move(double height, double width) {
        double halfHeight = height / 2;
        double halfWidth = width / 2;


        checkHorizontalCollision(halfWidth);
        checkVerticalCollision(halfHeight);


        this.setPositionX(directionX * speed);
        this.setPositionY(directionY * speed);
    }

    private void checkHorizontalCollision(double halfWidth) {
        double futurePosX = this.getPositionX() + directionX * speed;

        if (futurePosX - radius < -halfWidth || futurePosX + radius > halfWidth)
            directionX *= -1;

    }

    private void checkVerticalCollision(double halfHeight) {
        double futurePosY = this.getPositionY() + directionY * speed;

        if (futurePosY - radius < -halfHeight || futurePosY + radius > halfHeight)
            directionY *= -1;

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
        this.setPositionX(this.getPositionX()*factor);
    }

    @Override
    public void resizeY(double factor) {
        this.setPositionY(this.getPositionY()*factor);
    }


}
