package com.example.ping_pong.model;

import javafx.scene.paint.Color;

/**
 * Represents a player in the ping pong game.
 */
public class Player {
    private String name;
    private int score;
    private Racket racket;
    private Color color;

    /**
     * Constructs a player with the specified color and initial racket position.
     * @param color The color of the player.
     * @param posX The initial X position of the player's racket.
     */
    public Player(Color color, double posX){
        this.name = "";
        this.score = 0;
        this.racket = new Racket(posX);
        this.color = color;
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
