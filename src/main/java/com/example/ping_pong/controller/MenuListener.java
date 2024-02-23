package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class MenuListener {
    Game game;

    public MenuListener(Game game){
        this.game = game;
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

    public void setPlayerNames(String p1, String p2){
        System.out.println("NAME");

        game.getPlayer1().setName(p1);
        game.getPlayer2().setName(p2);
    }

    public void updateSettings(int speedChangeRate, int scoreLimit,
                               int ballSpeed, int racketWidth,
                               int racketThickness) {
        System.out.println("SETTIGNS");
        game.setScoreLimit(scoreLimit);

        game.getBall().setSpeed(ballSpeed);
        game.getBall().setSpeedChangeRate(speedChangeRate);

        double width = game.getWidth();
        double heigh = game.getHeigh();

        game.getPlayer1().getRacket().setWidth(width / 100 * racketWidth);
        game.getPlayer2().getRacket().setWidth(width / 100 * racketWidth);

        game.getPlayer1().getRacket().setThickness(heigh / 100 * racketThickness);
        game.getPlayer2().getRacket().setThickness(heigh / 100 * racketThickness);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
