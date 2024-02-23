package com.example.ping_pong.model;

public class Ball extends GameObject implements Resizable{

    private  int radius;
    private int speed;
    private int speedChangeRate;

    public Ball(){
        super(230,230);
        this.radius = 15;
        this.speed = 10;
        this.speedChangeRate = 10;
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
