package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class MenuListener {
    Game game;
    SceneSwitcher sceneSwitcher;

    public MenuListener(Game game, SceneSwitcher sceneSwitcher){
        this.game = game;
        this.sceneSwitcher = sceneSwitcher;
    }

    public void setExit() {
        System.out.println("EXIT");
        Platform.exit();
    }
    public void setAbout() {
        System.out.println("ABOUT");
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Super Ping pong");
        alert.setHeaderText("Made in Cork");
        alert.setContentText("All rights resereved");
        alert.showAndWait().ifPresent((btnType) -> {
        });
    }

    public void setRate(){
        System.out.println("RATE");
        TextInputDialog dialog = new TextInputDialog(); // create an instance
        dialog.setTitle("Rate our game!");
        dialog.setHeaderText("Rate out game from 1 to 10");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(string -> {

        });
    }

//    public void updateSettings(int speedChangeRate, int scoreLimit,
//                               int ballSpeed, int racketWidth,
//                               int racketThickness) {
//        System.out.println("SETTIGNS");
//        game.setScoreLimit(scoreLimit);
//
//        game.getBall().setSpeed(ballSpeed);
//        game.getBall().setSpeedChangeRate(speedChangeRate);
//
//        double width = game.getWidth();
//        double heigh = game.getHeigh();
//
//        game.getPlayer1().getRacket().setWidth(heigh / 100 * racketWidth);
//        game.getPlayer2().getRacket().setWidth(heigh / 100 * racketWidth);
//
//        game.getPlayer1().getRacket().setThickness(width / 100 * racketThickness);
//        game.getPlayer2().getRacket().setThickness(width / 100 * racketThickness);
//    }

    public void setPlayer1Name(String playerName) {
        game.getPlayer1().setName(playerName);
    }

    public void setPlayer2Name(String playerName) {
        game.getPlayer2().setName(playerName);
    }

    public void setBallSpeed(int ballSpeed) {
        game.getBall().setSpeed(ballSpeed);
    }

    public void setRacketWidth(int racketWidth) {
        game.getPlayer1().getRacket().setWidth(game.getWidth()/100*racketWidth);
        game.getPlayer2().getRacket().setWidth(game.getWidth()/100*racketWidth);
    }

    public void setRacketThickness(int racketThickness) {
        game.getPlayer1().getRacket().setThickness(game.getHeigh()/100*racketThickness);
        game.getPlayer2().getRacket().setThickness(game.getHeigh()/100*racketThickness);
    }

    public void setSpeedChangeRate(int speedChangeRate) {
        game.getBall().setSpeedChangeRate(speedChangeRate);
    }

    public void setScoreLimit(int scoreLimit) {
       game.setScoreLimit(scoreLimit);
    }

    public void setPlay(VBox settingMenu){
        settingMenu.setVisible(!settingMenu.isVisible());

        if (game.getPlayer1().getName().isEmpty()) {
            game.getPlayer1().setName("Player 1");
        }
        if (game.getPlayer2().getName().isEmpty()) {
            game.getPlayer2().setName("Player 2");
        }

        sceneSwitcher.switchToGame();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
