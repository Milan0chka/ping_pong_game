package com.example.ping_pong.controller;

import com.example.ping_pong.model.Ball;
import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Racket;
import com.example.ping_pong.view.LabCanvas;
/**
 * Manages the movement and collision detection of the ball.
 */
public class BallManager implements Runnable {
    private Game game;
    private LabCanvas canvas;
    private GameController gameController;

    /**
     * Constructs a new BallManager with the specified game, canvas, and game controller.
     *
     * @param c              the Game object representing the game state
     * @param canvas         the LabCanvas object for rendering the game
     * @param gameController the LabController object for controlling the game
     */
    public BallManager(Game c, LabCanvas canvas, GameController gameController) {
        this.game = c;
        this.canvas = canvas;
        this.gameController = gameController;
    }

    /**
     * Runs the ball movement and collision detection logic in a separate thread.
     */
    @Override
    public void run() {
        Ball ball = game.getBall();
        while (!Thread.currentThread().isInterrupted()) {

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            if (!game.isPaused()) {
                ball.move();

                checkVerticalCollision();
                checkRacketCollision();

                if (checkGoal(ball)) {
                    if (isGameEnded())
                        endOfGame();
                    else {
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                        notifyAboutGoal();
                    }
                    continue;
                }
            }

            canvas.drawGame(game);

            if (Thread.interrupted())
                break;
        }
    }

    /**
     * Checks for vertical collision of the ball and updates its direction if a collision occurs.
     */
    public void checkVerticalCollision() {
        if (isVerticalCollision())
            game.getBall().bounceY();
    }

    /**
     * Checks if a vertical collision will occur based on the ball's future position.
     *
     * @return true if a vertical collision is predicted, false otherwise
     */
    public boolean isVerticalCollision() {
        double futurePosY = game.getBall().getPositionY() + game.getBall().getDirectionY() * game.getBall().getSpeed();
        double radius = game.getBall().getRadius();

        return futurePosY < radius + 10 || futurePosY >= game.getHeigh() - radius;
    }

    /**
     * Checks for collision of the ball with the rackets and updates its direction if a collision occurs.
     */
    public void checkRacketCollision() {
        Racket activeRacket = game.getBall().getDirectionX() == -1 ? game.getPlayer1().getRacket() : game.getPlayer2().getRacket();
        if (isRacketCollision(activeRacket, game.getBall()))
            game.getBall().bounceX();

    }

    /**
     * Determines if a collision between the ball and a racket will occur.
     *
     * @param racket the Racket object representing the racket
     * @param ball   the Ball object representing the ball
     * @return true if a collision between the ball and racket is predicted, false otherwise
     */
    public boolean isRacketCollision(Racket racket, Ball ball) {
        double racketLeft = racket.getPositionX();
        double racketRight = racketLeft + racket.getThickness();
        double racketTop = racket.getPositionY();
        double racketBottom = racketTop + racket.getWidth();

        double radius = ball.getRadius();
        double ballLeft = ball.getPositionX() - radius;
        double ballRight = ball.getPositionX() + radius;
        double ballTop = ball.getPositionY() - radius;
        double ballBottom = ball.getPositionY() + radius;

        return ball.getDirectionX() == -1 ?
                (ballLeft < racketRight && ballBottom > racketTop && ballTop < racketBottom) :
                (ballRight > racketLeft + 10 && ballBottom > racketTop && ballTop < racketBottom);

    }

    /**
     * Checks if a goal has been scored by either player.
     *
     * @param ball the Ball object representing the ball
     * @return true if a goal is scored, false otherwise
     */
    private boolean checkGoal(Ball ball) {
        if (hasPlayer1Scored(ball)) {
            gameController.playerScored(game.getPlayer1());
        } else if (hasPlayer2Scored(ball)) {
            gameController.playerScored(game.getPlayer2());
        } else
            return false;

        return true;
    }

    /**
     * Checks if Player 2 has scored a goal.
     *
     * @param ball the Ball object representing the ball
     * @return true if Player 2 has scored, false otherwise
     */
    public boolean hasPlayer2Scored(Ball ball) {
        return ball.getPositionX() < ball.getRadius();
    }

    /**
     * Checks if Player 1 has scored a goal.
     *
     * @param ball the Ball object representing the ball
     * @return true if Player 1 has scored, false otherwise
     */
    public boolean hasPlayer1Scored(Ball ball) {
        return ball.getPositionX() > game.getWidth() - ball.getRadius();
    }

    /**
     * Notifies the players about a goal scored and prompts.
     */
    public void notifyAboutGoal() {
        canvas.drawGame(game);
    }

    /**
     * Checks if the game has ended based on the score limit.
     * @return true if the game has ended, false otherwise
     */
    public boolean isGameEnded() {
        int maxScore = Math.max(game.getPlayer1().getScore(), game.getPlayer2().getScore());
        return game.getScoreLimit() <= maxScore;
    }

    /**
     * Handles the end of the game by notifying the game controller.
     */
    public void endOfGame() {
        gameController.finishGame();
    }
}
