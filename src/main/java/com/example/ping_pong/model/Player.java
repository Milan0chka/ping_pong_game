package com.example.ping_pong.model;

import javafx.scene.paint.Color;

public class Player {
    private String name;
    private int score;
    private Racket racket;
    private Color color;

    public Player(){
        this.name = "";
        this.score = 0;
        this.racket = new Racket();
        this.color = Color.BLACK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Racket getRacket() {
        return racket;
    }

    public void setRacket(Racket racket) {
        this.racket = racket;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name + " - " + score;
    }

}
