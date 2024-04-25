package com.example.ping_pong.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Represents the settings for a game.
 */
public class GameSettings implements Serializable {
    private int scoreLimit;
    private int ballSpeed;
    private int speedChangeRate;
    private int racketWidth;
    private int racketThickness;

    /**
     * Constructs a GameSettings object with default settings.
     */
    public GameSettings() {
        this.scoreLimit = 10;
        this.ballSpeed = 10;
        this.speedChangeRate = 25;
        this.racketWidth = 20;
        this.racketThickness = 3;
    }

    public int getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(int scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(int ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public int getSpeedChangeRate() {
        return speedChangeRate;
    }

    public void setSpeedChangeRate(int speedChangeRate) {
        this.speedChangeRate = speedChangeRate;
    }

    public int getRacketWidth() {
        return racketWidth;
    }

    public void setRacketWidth(int racketWidth) {
        this.racketWidth = racketWidth;
    }

    public int getRacketThickness() {
        return racketThickness;
    }

    public void setRacketThickness(int racketThickness) {
        this.racketThickness = racketThickness;
    }
}
