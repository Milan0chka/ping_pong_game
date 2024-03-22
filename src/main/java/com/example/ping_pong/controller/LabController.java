package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.Player;
import com.example.ping_pong.view.LabCanvas;

public class LabController {
    private Game game;
    private LabCanvas canvas;

    private boolean isGameOn;

    public LabController(LabCanvas labCanvas){
        this.game = new Game();
        this.canvas = labCanvas;
        isGameOn = true;
    }

    public void finishGame(){
        Player winner = game.getPlayer1().getScore() > game.getPlayer2().getScore() ? game.getPlayer1() : game.getPlayer2();

        game.setPaused(true);
        canvas.drawGame(game);

        canvas.drawWinner(winner);
        canvas.drawGameFinishInfo();

        isGameOn = false;
    }

    public void restartGame(){
        game.resetGame();
        game.resetScore();
        game.setPaused(true);

        canvas.drawGame(game);
        canvas.drawPressEnter();

        isGameOn = true;
    }

    public void startGame() throws InterruptedException {
        if(isGameOn){
            game.startGame();
        } else {
            restartGame();
        }
    }

    public void playerScored(Player player){
        player.setScore(player.getScore()+1);

        canvas.drawGoal(player);

        game.resetGame();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
