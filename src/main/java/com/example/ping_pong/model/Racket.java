package com.example.ping_pong.model;

public class Racket extends GameObject implements Resizable{
    private double width;
    private double thickness;

    public Racket(double PosX){
        super(PosX,260);
        this.width = 130;
        this.thickness = 15;
    }

    public void moveUp(double height){
        if (this.getPositionY() - 30 > 0) {
            this.setPositionY(this.getPositionY() - 10);
        }
    }

    public void moveDown(double height){
        if (this.getPositionY() + 30 + this.getWidth() < height) {
            this.setPositionY(this.getPositionY() + 10);
        }
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
        this.setPositionX(this.getPositionX()*factor);
        this.thickness =this.thickness*factor;
    }

    @Override
    public void resizeY(double factor) {
        this.setPositionY(this.getPositionY()*factor);
        this.width = this.width*factor;
    }
}
