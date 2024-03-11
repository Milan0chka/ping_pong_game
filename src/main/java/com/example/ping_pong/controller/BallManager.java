package com.example.ping_pong.controller;

import com.example.ping_pong.model.Ball;
import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Player;
import com.example.ping_pong.model.Racket;
import com.example.ping_pong.view.LabCanvas;

public class BallManager implements Runnable{
    private Game game;
    private LabCanvas canvas;
    public BallManager(Game c, LabCanvas canvas) {
        this.game=c;
        this.canvas=canvas;
    }
    @Override
    public void run() {
        Ball ball = game.getBall();
        while(true) {

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ball.move(game.getHeigh(), game.getWidth());
            ball.bounceFromRacket(game.getPlayer1().getRacket(), game.getPlayer2().getRacket());

            checkGoal(ball);

            canvas.drawGame(game);
            if (ball.isPause())
                canvas.drawPressEnter();
        }
    }

    private void checkGoal(Ball ball){
        Player playerScored;

        if (ball.getPositionX() < ball.getRadius()){
            game.getPlayer2().setScore(game.getPlayer2().getScore()+1);
            playerScored = game.getPlayer2();
        }
        else if (ball.getPositionX() > game.getWidth() - ball.getRadius()){
            game.getPlayer1().setScore(game.getPlayer1().getScore()+1);
            playerScored = game.getPlayer1();
        }
        else
            return;

        game.resetGame();
        canvas.drawGoal(playerScored);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}