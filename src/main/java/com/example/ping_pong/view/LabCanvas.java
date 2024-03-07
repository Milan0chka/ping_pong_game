package com.example.ping_pong.view;

import com.example.ping_pong.model.Ball;
import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

public class LabCanvas extends Canvas {

    public LabCanvas(int width, int height) {
        super(width, height);
    }

    public void drawGame(Game game) {
        resetSize(game);
        GraphicsContext gc = super.getGraphicsContext2D();

        fillBackground(gc, game);

        drawBall(gc, game.getBall());

        int racketOffset = (int) getWidth() / 50;
        drawRacket(gc, game.getPlayer1(), racketOffset);
        drawRacket(gc, game.getPlayer2(),  -racketOffset - game.getPlayer1().getRacket().getThickness());

        drawTitle(gc, game.getPlayer1(), getWidth() / 12 * 2);
        drawTitle(gc, game.getPlayer2(), getWidth() / 12 * 7);
    }

    private void resetSize(Game game) {
        this.setWidth(game.getWidth());
        this.setHeight(game.getHeigh());
    }

    public void fillBackground(GraphicsContext gc, Game game) {
        gc.clearRect(0, 0, getWidth(), getHeight());

        Color player1Color = game.getPlayer1().getColor().deriveColor(0, 1, 1, 0.25);
        Color player2Color = game.getPlayer2().getColor().deriveColor(0, 1, 1, 0.25);

        Stop[] stops = new Stop[]{new Stop(0, player1Color), new Stop(1, player2Color)};
        LinearGradient lg2 = new LinearGradient(0, 100, 0, getHeight() - 100, false, CycleMethod.NO_CYCLE, stops);

        gc.setFill(lg2);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    public void drawBall(GraphicsContext gc, Ball ball) {
        double radius = ball.getRadius();
        double centerX = ball.getPositionX() - ball.getRadius();
        double centerY = ball.getPositionY() - ball.getRadius();

        gc.setFill(Color.BLACK);
        gc.fillOval(centerX, centerY, radius, radius);
    }

    public void drawRacket(GraphicsContext gc, Player player, double racketOffset) {
        double topLeftPositionX = player.getRacket().getPositionX() + racketOffset;
        double topLeftPositionY = player.getRacket().getPositionY();

        gc.setFill(player.getColor());

        gc.fillRect(topLeftPositionX, topLeftPositionY,
                player.getRacket().getThickness(),
                player.getRacket().getWidth());
    }

    public void drawTitle(GraphicsContext gc, Player player, double textOffsetX) {
        int fontSize = (int) getHeight() / 20;

        gc.setStroke(player.getColor());
        gc.setLineWidth(1.5);

        gc.setFont(new Font("Consolas", fontSize));
        String score = player.getName() + " - " + player.getScore();

        double textOffsetY = getHeight() / 7;
        gc.strokeText(score, textOffsetX, textOffsetY);
    }
}
