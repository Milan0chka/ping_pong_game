package com.example.ping_pong.controller;

import com.example.ping_pong.model.Ball;
import com.example.ping_pong.model.Game;
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
        int counter=0;
        while(true) {
            counter++;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ball.move(game.getHeigh(), game.getWidth());

//            if (ball.getPosX()<10)
//            {
////player 2 has scored, add code here
//            }
//            if (ball.getPosX()>game.getDimensionX()-ball.getRadius())
//            {
////player 1 has scored, add code here
//            }
//// CODE to CHECK BOUNCING WITH RACKET

            canvas.drawGame(game);
        }
    }
}