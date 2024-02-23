package com.example.ping_pong;

import com.example.ping_pong.model.Ball;
import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class LabCanvas extends Canvas {
    public LabCanvas(int width, int heigh){
        super(width,heigh);

    }

    public void drawGame(Game game){
        resetSize(game);
        GraphicsContext  gc = super.getGraphicsContext2D();
        fillBackground(gc);
        drawBall(gc, game.getBall());
        int racketOffset = (int) getWidth()/50;
        drawRacket(gc,game.getPlayer1(), racketOffset);
        drawRacket(gc, game.getPlayer2(), game.getWidth()- racketOffset- game.getPlayer1().getRacket().getThickness());
        int textOffset = (int) getWidth()/4;
        drawTitle(gc,game.getPlayer1(),textOffset);
        drawTitle(gc,game.getPlayer2(), (double) -textOffset /2);
    }

    private void resetSize(Game game) {
        this.setWidth(game.getWidth());
        this.setHeight(game.getHeigh());
    }

    public void fillBackground(GraphicsContext gc){
        gc.clearRect(0,0,getWidth(),getHeight());
        Stop[] stops = new Stop[] { new Stop(0, Color.ALICEBLUE), new Stop(1, Color.THISTLE)};
        LinearGradient lg2 = new LinearGradient(0, 100, 0, getHeight()-100, false, CycleMethod.NO_CYCLE, stops);
        gc.setFill(lg2);
        gc.fillRect(0,0, getWidth(),getHeight());
    }

    public void drawBall(GraphicsContext gc, Ball ball){
        double radius = ball.getRadius();
        double centerX = getWidth() / 2 - radius;
        double centerY = getHeight() / 2 - radius;
        gc.setFill(Color.BLACK);
        gc.fillOval(centerX, centerY, radius, radius);
    }

    public void drawRacket(GraphicsContext gc, Player player, double racketOffset){
        gc.setFill(player.getColor());
        double topLeftPositionX = player.getRacket().getPositionX() + racketOffset;
        double topLeftPositionY = getHeight()/2 + player.getRacket().getPositionY() - player.getRacket().getWidth()/2;
        gc.fillRect(topLeftPositionX, topLeftPositionY,
                    player.getRacket().getThickness(),
                    player.getRacket().getWidth());
    }

    public void drawTitle(GraphicsContext gc, Player player, double textOffsetX){
        gc.setFill(player.getColor());
        String score = player.getName() + " - " + player.getScore();
        double midPos = getWidth()/2;
        double textOffsetY =  getHeight()/7;
        gc.fillText(score, midPos-textOffsetX, textOffsetY);
    }
}
