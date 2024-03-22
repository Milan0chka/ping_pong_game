package com.example.ping_pong.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class GameSettings implements Serializable {
    private String player1Name;
    private transient Color player1Color;
    private long player1ColorValue;
    private String player2Name;
    private transient Color player2Color;
    private long player2ColorValue;
    private int scoreLimit;
    private int ballSpeed;
    private int speedChangeRate;
    private int racketWidth;
    private int racketThickness;

    public GameSettings() {
        this.player1Name = "Player 1";
        this.player1Color = Color.BLUE;
        this.player2Name = "Player 2";
        this.player2Color = Color.RED;
        this.scoreLimit = 10;
        this.ballSpeed = 10;
        this.speedChangeRate = 10;
        this.racketWidth = 20;
        this.racketThickness = 3;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer1Color(Color player1Color) {
        this.player1Color = player1Color;
        this.player1ColorValue = (long) (player1Color.getRed() * 255) << 16 | (long) (player1Color.getGreen() * 255) << 8 | (long) (player1Color.getBlue() * 255);
    }

    public Color getPlayer1Color() {
        if (player1Color == null) {
            player1Color = Color.rgb((int)((player1ColorValue >> 16) & 0xFF), (int)((player1ColorValue >> 8) & 0xFF), (int)(player1ColorValue & 0xFF));
        }
        return player1Color;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public void setPlayer2Color(Color player2Color) {
        this.player2Color = player2Color;
        this.player2ColorValue = (long) (player2Color.getRed() * 255) << 16 | (long) (player2Color.getGreen() * 255) << 8 | (long) (player2Color.getBlue() * 255);
    }

    // Add a method to get the Color from the RGB value
    public Color getPlayer2Color() {
        if (player2Color == null) {
            player2Color = Color.rgb((int)((player2ColorValue >> 16) & 0xFF), (int)((player2ColorValue >> 8) & 0xFF), (int)(player2ColorValue & 0xFF));
        }
        return player2Color;
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
