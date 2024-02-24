package com.example.ping_pong.model;

public class Racket extends GameObject implements Resizable{
    private double width;
    private double thickness;

    public Racket(){
        super(0,0);
        this.width = 55;
        this.thickness = 15;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    @Override
    public void resizeX(double factor) {
        this.setPositionY(this.getPositionY()*factor);
        this.thickness =this.thickness*factor;
    }

    @Override
    public void resizeY(double factor) {
        this.setPositionX(this.getPositionX()*factor);
        this.width = this.width*factor;
    }
}
