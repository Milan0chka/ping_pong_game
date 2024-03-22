package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.GameSettings;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Optional;

public class MenuListener {
    Game game;
    GameSettings gameSettings;
    SceneSwitcher sceneSwitcher;
    LabController gameController;

    public MenuListener(Game game, SceneSwitcher sceneSwitcher, LabController gameController){
        this.game = game;
        this.gameSettings = GameSettingsSerializer.deserialize();
        setInitialGameSettings();
        this.sceneSwitcher = sceneSwitcher;
        this.gameController = gameController;
    }

    public void setInitialGameSettings(){
        game.getPlayer1().setName(gameSettings.getPlayer1Name());
        game.getPlayer1().setColor(gameSettings.getPlayer1Color());

        game.getPlayer2().setName(gameSettings.getPlayer2Name());
        game.getPlayer2().setColor(gameSettings.getPlayer2Color());

        this.setRacketThickness(gameSettings.getRacketThickness());
        this.setRacketWidth(gameSettings.getRacketWidth());

        game.getBall().setSpeed(gameSettings.getBallSpeed());
        game.getBall().setSpeedChangeRate(gameSettings.getSpeedChangeRate());

        game.setScoreLimit(gameSettings.getScoreLimit());

    }

    public void setExit() {
        System.out.println("EXIT");
        GameSettingsSerializer.serialize(gameSettings);
        Platform.exit();
    }
    public void setAbout() {
        System.out.println("ABOUT ");
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Super Ping pong");
        alert.setHeaderText("Made By Milana Zhuhaievych");
        alert.setContentText("Player 1 should use W and S to move a racket. Player 2 should use UP and DOWN to do same." +
                "To start new round or unpause - press Enter. To pause - press Pause button. To restart game - press Restart button or press Enter (only works after game finish.)");
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
        gameSettings.setPlayer1Name(playerName);
    }

    public void setPlayer2Name(String playerName) {
        game.getPlayer2().setName(playerName);
        gameSettings.setPlayer2Name(playerName);
    }

    public void setColor1(Color color){
        game.getPlayer1().setColor(color);
        gameSettings.setPlayer1Color(color);
    }

    public void setColor2(Color color){
        game.getPlayer2().setColor(color);
        gameSettings.setPlayer2Color(color);
    }


    public void setBallSpeed(int ballSpeed) {
        game.getBall().setSpeed(ballSpeed);
        gameSettings.setBallSpeed(ballSpeed);
    }

    public void setRacketWidth(int racketWidth) {
        game.getPlayer1().getRacket().setWidth(game.getHeigh()/100*racketWidth);
        game.getPlayer2().getRacket().setWidth(game.getHeigh()/100*racketWidth);
        gameSettings.setRacketWidth(racketWidth);
    }

    public void setRacketThickness(int racketThickness) {
        game.getPlayer1().getRacket().setThickness(game.getWidth()/100*racketThickness);

        double difference = game.getPlayer2().getRacket().getThickness() - game.getWidth()/100*racketThickness;
        game.getPlayer2().getRacket().setThickness(game.getWidth()/100*racketThickness);
        game.getPlayer2().getRacket().setPositionX(game.getPlayer2().getRacket().getPositionX() + difference);

        gameSettings.setRacketThickness(racketThickness);
    }

    public void setSpeedChangeRate(int speedChangeRate) {
        game.getBall().setSpeedChangeRate(speedChangeRate);
        gameSettings.setSpeedChangeRate(speedChangeRate);
    }

    public void setScoreLimit(int scoreLimit) {
       game.setScoreLimit(scoreLimit);
       gameSettings.setScoreLimit(scoreLimit);
    }

    public void setPlay(){
        if (game.getPlayer1().getName().isEmpty()) {
            game.getPlayer1().setName("Player 1");
        }
        if (game.getPlayer2().getName().isEmpty()) {
            game.getPlayer2().setName("Player 2");
        }
        sceneSwitcher.switchToGame();
    }

    public void toggleSettingMenu(VBox settingMenu){

        if (settingMenu.isVisible())
            game.setPaused(false);
        else
            game.setPaused(true);

        settingMenu.setVisible(!settingMenu.isVisible());
    }

    public void setPause(boolean isSettingMenuOn){
        if (! isSettingMenuOn)
            game.setPaused(! game.isPaused());
    }

    public void setRestart(){
        gameController.restartGame();
    }

    public void setBackToMenu(){
        gameController.restartGame();
        gameController.setGameOn(false);
        sceneSwitcher.switchToMainMenu();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
