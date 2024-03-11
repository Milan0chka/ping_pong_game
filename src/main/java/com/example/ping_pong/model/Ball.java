package com.example.ping_pong.model;

import static java.lang.Math.random;

public class Ball extends GameObject implements Resizable{

    private  int radius;
    private int speed;
    private int speedChangeRate;
    private int directionY;
    private int directionX;
    private boolean pause;

    public Ball(){
        super(325,275);
        this.radius = 15;
        this.speed = 3;
        this.speedChangeRate = 10;
        this.pause = true;
        //direction of ball`s movement decides randomly
        this.directionX = random() >= 0.5 ? 1: -1;
        this.directionY = random() >= 0.5 ? 1: -1;
    }
    public void move(double height, double width) {
        if (!pause){

            checkVerticalCollision(height);

            // Update the position based on the current position, direction, and speed
            this.setPositionX(this.getPositionX() + directionX * speed);
            this.setPositionY(this.getPositionY() + directionY * speed);
        }
    }

    private void checkVerticalCollision(double height) {
        double futurePosY = this.getPositionY() + directionY * speed;

        if (futurePosY - radius < 0 || futurePosY + radius > height)
            directionY *= -1; // Reverse direction on collision
    }

    public void bounceFromRacket(Racket racket1, Racket racket2) {
        Racket activeRacket = this.getDirectionX() == -1 ? racket1 : racket2;

        double racketLeft = activeRacket.getPositionX();
        double racketRight = racketLeft + activeRacket.getThickness();
        double racketTop = activeRacket.getPositionY();
        double racketBottom = racketTop + activeRacket.getWidth();

        double ballLeft = this.getPositionX() - this.getRadius();
        double ballRight = this.getPositionX() + this.getRadius();
        double ballTop = this.getPositionY() - this.getRadius();
        double ballBottom = this.getPositionY() + this.getRadius();

        // Check collision based on ball direction
        boolean collision = this.getDirectionX() == -1 ?
                (ballLeft < racketRight && ballBottom > racketTop && ballTop < racketBottom) :
                (ballRight > racketLeft + 15 && ballBottom > racketTop && ballTop < racketBottom);

        if (collision) {
            this.setDirectionX(-this.getDirectionX());
        }
    }


    public boolean isPause(){
        return this.pause;
    }

    public void setPause(boolean pause){
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
        this.setPositionX(this.getPositionX()*factor);
    }

    @Override
    public void resizeY(double factor) {
        this.setPositionY(this.getPositionY()*factor);
    }


}
