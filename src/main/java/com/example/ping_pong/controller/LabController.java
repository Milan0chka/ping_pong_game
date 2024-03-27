package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Player;
import com.example.ping_pong.view.LabCanvas;

import java.util.concurrent.TimeUnit;

/**
 * Controls the flow of the game and manages game states.
 */
public class LabController {
    private Game game;
    private LabCanvas canvas;
    private boolean isGameOn;

    /**
     * Constructs a new LabController with the specified LabCanvas.
     * Creates instance of Game and flag to check whether game is not won yet.
     *
     * @param labCanvas the LabCanvas object for rendering the game
     */
    public LabController(LabCanvas labCanvas) {
        this.game = new Game();
        this.canvas = labCanvas;
        isGameOn = true;
    }

    /**
     * Resets the attributes of the game to their initial state.
     */
    public void resetGameAttributes(){
        game.getPlayer1().getRacket().setPositionY(game.getHeigh()/2 - game.getPlayer1().getRacket().getWidth()/2);
        game.getPlayer2().getRacket().setPositionY(game.getHeigh()/2 - game.getPlayer2().getRacket().getWidth()/2);


        game.getBall().setPositionY(game.getHeigh()/2);
        game.getBall().setPositionX(game.getWidth()/2);
        game.getBall().setDirectionX(-game.getBall().getDirectionX());

        game.getBall().setBounceCount(0);

        game.setPaused(true);
    }



    /**
     * Finishes the game after winner determined, pauses the game, and displays game information.
     */
    public void finishGame() {
        Player winner = game.getPlayer1().getScore() > game.getPlayer2().getScore() ? game.getPlayer1() : game.getPlayer2();

        game.setPaused(true);
        canvas.drawGame(game);

        canvas.drawWinner(winner);
        canvas.drawGameFinishInfo();

        isGameOn = false;
    }

    /**
     * Restarts the game by resetting game states and rendering the initial game view.
     */
    public void restartGame() {
        resetGameAttributes();
        resetPlayerScores();
        game.setPaused(true);

        canvas.drawGame(game);
        canvas.drawPressEnter();

        isGameOn = true;
    }

    /**
     * Resets players scores to 0.
     */
    public void resetPlayerScores(){
        game.getPlayer1().setScore(0);
        game.getPlayer2().setScore(0);
    }
    /**
     * Starts the game or restarts it if it's already running.
     *
     * @throws InterruptedException if an interrupt occurs during game start
     */
    public void startGame() throws InterruptedException {
        if (isGameOn) {
            game.setPaused(false);
            TimeUnit.MILLISECONDS.sleep(30);
        } else {
            restartGame();
        }
    }

    /**
     * Handles a player scoring a goal by updating the player's score and rendering the goal event.
     *
     * @param player the Player object who scored the goal
     */
    public void playerScored(Player player) {
        player.setScore(player.getScore() + 1);

        canvas.drawGoal(player);

        resetGameAttributes();
    }


    public boolean isGameOn() {
        return isGameOn;
    }

    public void setGameOn(boolean gameOn) {
        isGameOn = gameOn;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
