package com.example.ping_pong.model;

import javafx.scene.paint.Color;

/**
 * Represents the game state and logic for a ping pong game.
 */
public class Game implements Resizable{

    private Player player1;
    private Player player2;
    private Ball ball;
    private int scoreLimit;
    private double width;
    private double heigh;
    private boolean isPaused;

    /**
     * Constructs a new Game object with default settings.
     */
    public Game() {
        this.width = 650;
        this.heigh = 550;
        this.player1 = new Player(Color.BLUE, 15);
        this.player2 = new Player(Color.RED, 620);
        this.ball = new Ball();
        this.scoreLimit = 10;
        this.isPaused = true;
    }

    /**
     * Checks if the game is paused.
     * @return true if the game is paused, false otherwise.
     */
    public boolean isPaused() {
        return isPaused;
    }


    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public int getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(int scoreLimit) {
        this.scoreLimit = scoreLimit;
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeigh() {
        return heigh;
    }

    public void setHeigh(double heigh) {
        this.heigh = heigh;
    }

    /**
     * Resizes the game objects along the X-axis.
     * @param factor The factor by which to resize the game objects.
     */
    @Override
    public void resizeX(double factor) {
        player1.getRacket().resizeX(factor);
        player2.getRacket().resizeX(factor);
        ball.resizeX(factor);
    }

    /**
     * Resizes the game objects along the Y-axis.
     * @param factor The factor by which to resize the game objects.
     */
    @Override
    public void resizeY(double factor) {
        player1.getRacket().resizeY(factor);
        player2.getRacket().resizeY(factor);
        ball.resizeY(factor);
    }
}
