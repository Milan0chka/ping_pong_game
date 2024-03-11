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
import javafx.scene.text.Text;

public class LabCanvas extends Canvas {

    public LabCanvas(int width, int height) {
        super(width, height);
    }

    public void drawGame(Game game) {
        resetSize(game);
        GraphicsContext gc = super.getGraphicsContext2D();

        fillBackground(gc, game);

        drawRacket(gc, game.getPlayer1());
        drawRacket(gc, game.getPlayer2());

        drawCenteredTitles(gc, game);

        drawBall(gc, game.getBall());
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

    public void drawRacket(GraphicsContext gc, Player player) {
        double topLeftPositionX = player.getRacket().getPositionX();
        double topLeftPositionY = player.getRacket().getPositionY();

        gc.setFill(player.getColor());

        gc.fillRect(topLeftPositionX, topLeftPositionY,
                player.getRacket().getThickness(),
                player.getRacket().getWidth());
    }

    public void drawCenteredTitles(GraphicsContext gc, Game game) {
        final int fontSize = (int) getHeight() / 20;

        String player1Score = game.getPlayer1().getName() + " - " + game.getPlayer1().getScore() + "\t";
        String player2Score = game.getPlayer2().getName() + " - " + game.getPlayer2().getScore();

        double widthPlayer1 = findTextWidth(fontSize, player1Score);
        double widthPlayer2 = findTextWidth(fontSize, player2Score);

        double startX = (getWidth() - widthPlayer1-widthPlayer2) / 2;
        double yPos = getHeight() / 7;

        // Draw Player 1 Score
        styleText(gc, fontSize, game.getPlayer1().getColor(), 1.5);
        gc.strokeText(player1Score, startX, yPos);

        // Update startX for Player 2 Score
        startX += widthPlayer1;

        // Draw Player 2 Score
        styleText(gc, fontSize, game.getPlayer2().getColor(), 1.5);
        gc.strokeText(player2Score, startX, yPos);
    }

    public void drawText(GraphicsContext gc, int fontPercent, String text,
                         Color color, int lineWidth,
                         double heightOffset, double widthOffset){
        int fontSize = (int) getHeight() / fontPercent;

        styleText(gc,fontSize,color,lineWidth);

        double textOffsetY = getHeight() * heightOffset;
        double textOffsetX = (getWidth() - findTextWidth(fontSize, text)) * widthOffset;

        gc.strokeText(text, textOffsetX, textOffsetY);
    }

    public void styleText(GraphicsContext gc, int fontSize, Color color, double lineWidth){
        gc.setFont(new Font("Consolas", fontSize));
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
    }

    public double findTextWidth(int fontSize, String message){
        Text text = new Text(message);
        text.setFont(new Font("Consolas", fontSize));
        return text.getLayoutBounds().getWidth();
    }
    public void drawGoal(Player player){
        GraphicsContext gc = super.getGraphicsContext2D();

        drawText(gc, 10, "GOAL!", player.getColor(),3, 0.4, 0.5);

        drawPressEnter();
    }

    public void drawPressEnter() {
        GraphicsContext gc = super.getGraphicsContext2D();
        String message = "Press Enter to start round";

        drawText(gc,30, message, Color.GRAY, 1, 0.8, 0.5);
    }


}
