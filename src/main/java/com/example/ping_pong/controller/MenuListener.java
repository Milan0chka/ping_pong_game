package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import com.example.ping_pong.model.GameSave;
import com.example.ping_pong.model.GameSettings;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Optional;

import static java.lang.System.exit;

/**
 * Listens for menu actions and handles corresponding events and adjusting game settings.
 */
public class MenuListener {
    private Game game;
    private GameSettings gameSettings;
    private SceneSwitcher sceneSwitcher;
    private GameController gameController;
    private GameSerializer gameSerializer;

    /**
     * Constructs a new MenuListener with the specified Game, SceneSwitcher, and LabController.
     * Initializes game settings, scene switcher, and game controller.
     *
     * @param game           the Game object representing the game state
     * @param sceneSwitcher  the SceneSwitcher object for switching scenes
     * @param gameController the LabController object for controlling the game
     */
    public MenuListener(Game game, SceneSwitcher sceneSwitcher, GameController gameController) {
        this.game = game;
        this.gameSerializer = GameSerializer.getInstance();
        this.gameSettings = gameSerializer.deserializeSettings();
        setInitialGameSettings();
        this.sceneSwitcher = sceneSwitcher;
        this.gameController = gameController;
    }

    /**
     * Sets the initial game settings based on the deserialized GameSettings object.
     */
    public void setInitialGameSettings() {
        game.getPlayer1().setName("Player 1");
        game.getPlayer1().setColor(Color.RED);

        game.getPlayer2().setName("Player 2");
        game.getPlayer2().setColor(Color.BLUE);

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
        gameSerializer.serializeSettings(gameSettings);
        Platform.exit();
        exit(0);
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
    }

    public void setPlayer2Name(String playerName) {
        game.getPlayer2().setName(playerName);
    }

    public void setColor1(Color color) {
        game.getPlayer1().setColor(color);
    }

    public void setColor2(Color color) {
        game.getPlayer2().setColor(color);
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

    public String[] getSaveNames(){
        File saveFolder = new File("data/saves");
        File[] saves = saveFolder.listFiles();

        if (saves == null || saves.length == 0 )
            return null;

        String[] namesOfSaves = new String[saves.length];

        for(int i=0; i < saves.length; i++)
            namesOfSaves[i] = saves[i].getName().substring(0, saves[i].getName().length()-4);

        return namesOfSaves;
    }

    public void loadSave(GameSave save){
        game.getPlayer1().setName(save.getPlayer1());
        game.getPlayer1().setScore(save.getPlayer1Score());
        game.getPlayer1().setColor(save.getPlayer1Color());

        game.getPlayer2().setName(save.getPlayer2());
        game.getPlayer2().setScore(save.getPlayer2Score());
        game.getPlayer2().setColor(save.getPlayer2Color());
    }

    public void setLoadSave(){
        String[] saves = getSaveNames();
        if (saves == null){
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("NO SAVES FOUND");
            alert.setHeaderText("No saves were found in a system.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(saves[0], saves);
        dialog.setTitle("Name a save");
        dialog.setHeaderText("Enter name of your save.");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(saveName -> {
            if(!saveName.isEmpty()){
                GameSave save = gameSerializer.deserializeSave(saveName);
                if(save != null){
                    System.out.println("SAVE LOADED -" + saveName);
                    loadSave(save);
                    this.sceneSwitcher.switchToGame();
                }
                else
                    showAlert(Alert.AlertType.ERROR, "Save is not loaded", "Problem occured while loading game. Try again later.");
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Empty input", "Save can not have empty name. Try again with proper input.");
            }
        });
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

    /**
     * Checks whether chosen name for a save is valid for creating file.
     * @param name name of a save to be checked
     * @return true if valid, false if not
     */
    public boolean isNameValidForFileName(String name){
        if (name.contains("/") || name.contains("\\") || name.contains(":")
                || name.contains("*") || name.contains("?") || name.contains("\"")
                || name.contains("<") || name.contains(">") || name.contains("|")) {
            return false;
        }

        // Check for leading and trailing spaces or periods
        if (name.endsWith(" ") || name.endsWith(".") || name.startsWith(" ") || name.startsWith(".")) {
            return false;
        }

        return true;
    }

    /**
     * Saves a game.
     */
    public void setSaveGame(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name a save");
        dialog.setHeaderText("Enter name of your save.");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(saveName -> {
            if(!saveName.isEmpty() && isNameValidForFileName(saveName)){
                GameSave save = getGameSaveInfo();

                if(gameSerializer.serializeSave(save, saveName)){
                    showAlert(Alert.AlertType.CONFIRMATION, "Game is saved", "Your game has been saved with name "+ saveName);
                    return;
                }

            }

            showAlert(Alert.AlertType.ERROR, "Wrong save name", "Save name can not contain special characters, end with space or dot, or be empty. Try again with proper save name.");

        });
    }

    public GameSave getGameSaveInfo(){
        return new GameSave(game.getPlayer1().getName(), game.getPlayer1().getScore(), game.getPlayer1().getColor(),
                            game.getPlayer2().getName(), game.getPlayer2().getScore(), game.getPlayer2().getColor());
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
