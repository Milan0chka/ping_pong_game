package com.example.ping_pong.controller;

import com.example.ping_pong.model.Ball;
import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Player;
import com.example.ping_pong.model.Racket;
import com.example.ping_pong.view.LabCanvas;

public class BallManager implements Runnable{
    private Game game;
    private LabCanvas canvas;
    private LabController gameController;

    private boolean isGameOn;

    public BallManager(Game c, LabCanvas canvas, LabController gameController) {
        this.game=c;
        this.canvas=canvas;
        this.gameController = gameController;
        isGameOn = true;
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

            if (! game.isPaused()){
                ball.move();

                checkVerticalCollision();
                checkRacketCollision();

                if (checkGoal(ball)){
                    if (isGameEnded())
                        endOfGame();
                    else
                        notifyAboutGoal();
                    continue;
                }

                canvas.drawGame(game);
            }
        }
    }

    public void checkVerticalCollision(){
        if(isVerticalCollision())
            game.getBall().bounceY();
    }

    public boolean isVerticalCollision(){
        double futurePosY = game.getBall().getPositionY() + game.getBall().getDirectionY() * game.getBall().getSpeed();
        double radius = game.getBall().getRadius();

        return futurePosY < radius + 10 || futurePosY >= game.getHeigh() - radius;
    }

    public void checkRacketCollision(){
        Racket activeRacket = game.getBall().getDirectionX() == -1 ? game.getPlayer1().getRacket() : game.getPlayer2().getRacket();
        if ( isRacketCollision(activeRacket, game.getBall()))
            game.getBall().bounceX();

    }

    public boolean isRacketCollision(Racket racket, Ball ball){
        double racketLeft = racket.getPositionX();
        double racketRight = racketLeft + racket.getThickness();
        double racketTop = racket.getPositionY();
        double racketBottom = racketTop + racket.getWidth();

        double radius = ball.getRadius();
        double ballLeft = ball.getPositionX() -radius;
        double ballRight = ball.getPositionX() + radius;
        double ballTop = ball.getPositionY() - radius;
        double ballBottom = ball.getPositionY() + radius;

        return ball.getDirectionX() == -1 ?
                (ballLeft < racketRight && ballBottom > racketTop && ballTop < racketBottom) :
                (ballRight > racketLeft + 10 && ballBottom > racketTop && ballTop < racketBottom);

    }

    private boolean checkGoal(Ball ball){
        if (hasPlayer1Scored(ball)){
            gameController.playerScored(game.getPlayer1());
        }
        else if (hasPlayer2Scored(ball)){
            gameController.playerScored(game.getPlayer2());
        }
        else
            return false;

        return true;
    }

    public boolean hasPlayer2Scored(Ball ball) {
        return ball.getPositionX() < ball.getRadius();
    }

    public boolean hasPlayer1Scored(Ball ball){
        return ball.getPositionX() > game.getWidth() - ball.getRadius();
    }

    public void notifyAboutGoal(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        canvas.drawGame(game);
        canvas.drawPressEnter();
    }
    public boolean isGameEnded()
    {
        int maxScore = Math.max(game.getPlayer1().getScore(), game.getPlayer2().getScore());
        return game.getScoreLimit()<=maxScore;
    }

    public void endOfGame(){
        gameController.finishGame();
    }

}