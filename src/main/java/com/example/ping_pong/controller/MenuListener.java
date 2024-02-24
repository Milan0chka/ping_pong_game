package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

    public void setRate() {
        System.out.println("RATE");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rate our game!");
        dialog.setHeaderText("Rate our game from 1 to 10");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(rating -> {
            try {
                int numericRating = Integer.parseInt(rating);
                if (numericRating >= 1 && numericRating <= 10) {
                    showAlert(Alert.AlertType.INFORMATION, "Thank you for rating our game!", "Your rating: " + numericRating);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Invalid Rating", "Please enter a valid rating between 1 and 10.");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric rating.");
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setPlayer1Name(String playerName) {
        game.getPlayer1().setName(playerName);
    }

    public void setPlayer2Name(String playerName) {
        game.getPlayer2().setName(playerName);
    }

    public void setColor1(Color color){
        game.getPlayer1().setColor(color);
    }

    public void setColor2(Color color){
        game.getPlayer2().setColor(color);
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
