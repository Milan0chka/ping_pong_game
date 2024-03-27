package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.GameSettings;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Listens for menu actions and handles corresponding events and adjusting game settings.
 */
public class MenuListener {
    private Game game;
    private GameSettings gameSettings;
    private SceneSwitcher sceneSwitcher;
    private LabController gameController;

    /**
     * Constructs a new MenuListener with the specified Game, SceneSwitcher, and LabController.
     * Initializes game settings, scene switcher, and game controller.
     *
     * @param game           the Game object representing the game state
     * @param sceneSwitcher  the SceneSwitcher object for switching scenes
     * @param gameController the LabController object for controlling the game
     */
    public MenuListener(Game game, SceneSwitcher sceneSwitcher, LabController gameController) {
        this.game = game;
        this.gameSettings = GameSettingsSerializer.deserialize();
        setInitialGameSettings();
        this.sceneSwitcher = sceneSwitcher;
        this.gameController = gameController;
    }

    /**
     * Sets the initial game settings based on the deserialized GameSettings object.
     */
    public void setInitialGameSettings() {
        game.getPlayer1().setName(gameSettings.getPlayer1Name());
        game.getPlayer1().setColor(gameSettings.getPlayer1Color());

        game.getPlayer2().setName(gameSettings.getPlayer2Name());
        game.getPlayer2().setColor(gameSettings.getPlayer2Color());

        setRacketThickness(gameSettings.getRacketThickness());
        setRacketWidth(gameSettings.getRacketWidth());

        game.getBall().setSpeed(gameSettings.getBallSpeed());
        game.getBall().setSpeedChangeRate(gameSettings.getSpeedChangeRate());

        game.setScoreLimit(gameSettings.getScoreLimit());
    }

    /**
     * Exits the game and serializes the current game settings.
     */
    public void setExit() {
        System.out.println("EXIT");
        GameSettingsSerializer.serialize(gameSettings);
        Platform.exit();
    }

    /**
     * Displays information about the game.
     */
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

    /**
     * Prompts the user to rate the game.
     */
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

    /**
     * Displays an alert dialog with the specified type, title, and content text.
     *
     * @param alertType   the type of the alert dialog
     * @param title       the title of the alert dialog
     * @param contentText the content text of the alert dialog
     */
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

    public void setColor1(Color color) {
        game.getPlayer1().setColor(color);
        gameSettings.setPlayer1Color(color);
    }

    public void setColor2(Color color) {
        game.getPlayer2().setColor(color);
        gameSettings.setPlayer2Color(color);
    }


    public void setBallSpeed(int ballSpeed) {
        game.getBall().setSpeed(ballSpeed);
        gameSettings.setBallSpeed(ballSpeed);
    }

    /**
     * Sets the width of the rackets in a percents of a screen height for both players.
     *
     * @param racketWidth the width of the rackets to be set
     */
    public void setRacketWidth(int racketWidth) {
        game.getPlayer1().getRacket().setWidth(game.getHeigh() / 100 * racketWidth);
        game.getPlayer2().getRacket().setWidth(game.getHeigh() / 100 * racketWidth);
        gameSettings.setRacketWidth(racketWidth);
    }

    /**
     * Sets the thickness of the rackets in a percents of a screen width for both players.
     *
     * @param racketThickness the thickness of the rackets to be set
     */
    public void setRacketThickness(int racketThickness) {
        game.getPlayer1().getRacket().setThickness(game.getWidth() / 100 * racketThickness);

        double difference = game.getPlayer2().getRacket().getThickness() - game.getWidth() / 100 * racketThickness;
        game.getPlayer2().getRacket().setThickness(game.getWidth() / 100 * racketThickness);
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

    /**
     * Sets up the game and switches to the game scene. If player names are empty, defaults to "Player 1" and "Player 2".
     */
    public void setPlay() {
        if (game.getPlayer1().getName().isEmpty()) {
            game.getPlayer1().setName("Player 1");
        }
        if (game.getPlayer2().getName().isEmpty()) {
            game.getPlayer2().setName("Player 2");
        }
        sceneSwitcher.switchToGame();
    }

    /**
     * Toggles the visibility of the settings menu and adjusts game pause state accordingly.
     *
     * @param settingMenu the VBox containing the settings menu
     */
    public void toggleSettingMenu(VBox settingMenu) {
        if (settingMenu.isVisible())
            game.setPaused(false);
        else
            game.setPaused(true);

        if (!gameController.isGameOn())
            game.setPaused(true);

        settingMenu.setVisible(!settingMenu.isVisible());
    }

    /**
     * Pauses or resumes the game based on the state of the settings menu and game status.
     *
     * @param isSettingMenuOn true if the settings menu is currently visible, false otherwise
     */
    public void setPause(boolean isSettingMenuOn) {
        if (!isSettingMenuOn)
            game.setPaused(!game.isPaused());

        if (!gameController.isGameOn())
            game.setPaused(true);
    }

    public void setRestart() {
        gameController.restartGame();
    }

    /**
     * Restarts the game and switches back to the main menu.
     */
    public void setBackToMenu() {
        gameController.restartGame();
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
