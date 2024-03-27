package com.example.ping_pong.view;

import com.example.ping_pong.controller.MenuListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Represents the menu controls and layouts for the game settings.
 */
public class Menu {
    private VBox mainMenu;
    private VBox settingMenu;
    private HBox gameMenu;
    private TextField player1Name, player2Name, speedChangeRate, scoreLimit;
    private Slider ballSpeed;
    private ToggleGroup racketWidth, racketThickness;
    private ColorPicker colorPicker1, colorPicker2;
    private MenuListener menuListener;

    /**
     * Constructs a Menu object with the provided MenuListener.
     * @param menuListener The listener for menu events.
     */
    public Menu(MenuListener menuListener) {
        this.menuListener = menuListener;
        initializeFields();

        createSettingMenu();
        createMainMenu();
        createGameMenu();

        handleSettingAction();
    }

    /**
     * Initializes the menu fields based on earlier deserialized settings.
     */
    private void initializeFields() {
        if (Objects.equals(menuListener.getGameSettings().getPlayer1Name(), "Player 1")
                && Objects.equals(menuListener.getGameSettings().getPlayer2Name(), "Player 2")){
            player1Name = new TextField();
            player2Name = new TextField();
        } else {
            player1Name = new TextField(menuListener.getGameSettings().getPlayer1Name());
            player2Name = new TextField(menuListener.getGameSettings().getPlayer2Name());
        }

        speedChangeRate = new TextField(String.valueOf( menuListener.getGameSettings().getSpeedChangeRate()));
        scoreLimit = new TextField(String.valueOf(menuListener.getGameSettings().getScoreLimit()));
        ballSpeed = new Slider(1, 20, menuListener.getGameSettings().getBallSpeed());
        racketWidth = new ToggleGroup();
        racketThickness = new ToggleGroup();
        colorPicker1 = new ColorPicker(menuListener.getGameSettings().getPlayer1Color());
        colorPicker2 = new ColorPicker(menuListener.getGameSettings().getPlayer2Color());

    }

    /**
     * Creates the settings menu layout.
     */
    private void createSettingMenu() {
        Label settingsLabel = createStyledLabel("GAME SETTINGS", "settings-label");
        VBox settingsBox = createSettingsBox();

        settingMenu = new VBox(10, settingsLabel, settingsBox);
        styleVBox(settingMenu, "main-vbox");
        settingMenu.setMaxWidth(450);
        settingMenu.setMaxHeight(450);
    }

    /**
     * Creates the settings controls box.
     * @return The VBox containing the settings controls.
     */
    private VBox createSettingsBox() {
        int selectedWidth = menuListener.getGameSettings().getRacketWidth();
        int selectedThickness = menuListener.getGameSettings().getRacketThickness();

        return new VBox(5,
                createSettingControl("BALL SPEED:", ballSpeed),
                createSettingControl("BALL SPEED CHANGE:", speedChangeRate),
                createToggleGroup("RACKET WIDTH:", racketWidth, "Small", 10, "Medium", 20, "Big", 30, selectedWidth),
                createToggleGroup("RACKET THICKNESS:", racketThickness, "Thin", 1, "Medium", 3, "Thick", 5, selectedThickness),
                createSettingControl("SCORE LIMIT:", scoreLimit));
    }

    /**
     * Creates a setting control with a label and a node.
     * @param label The label for the control.
     * @param control The node representing the control.
     * @return The HBox containing the setting control.
     */
    private HBox createSettingControl(String label, javafx.scene.Node control) {
        Label settingLabel = new Label(label);
        HBox hbox = new HBox(10, settingLabel, control);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * Creates a toggle group setting control.
     * @param label The label for the control.
     * @param group The toggle group for the control.
     * @param n1 The label for the first option.
     * @param n1v The value for the first option.
     * @param n2 The label for the second option.
     * @param n2v The value for the second option.
     * @param n3 The label for the third option.
     * @param n3v The value for the third option.
     * @param selectedValue The selected value for the toggle group from saved settings.
     * @return The HBox containing the toggle group setting control.
     */
    private HBox createToggleGroup(String label, ToggleGroup group,
                                   String n1, int n1v,
                                   String n2, int n2v,
                                   String n3, int n3v,
                                   int selectedValue) {
        Label settingLabel = new Label(label);

        RadioButton s = new RadioButton(n1);
        RadioButton m = new RadioButton(n2);
        RadioButton b = new RadioButton(n3);

        s.setUserData(n1v);
        m.setUserData(n2v);
        b.setUserData(n3v);

        s.setToggleGroup(group);
        m.setToggleGroup(group);
        b.setToggleGroup(group);

        if (selectedValue == n1v)
            s.setSelected(true);
        else if (selectedValue == n3v)
            b.setSelected(true);
        else
            m.setSelected(true);

        HBox hbox = new HBox(10, settingLabel, s, m, b);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * Creates the PLAY button.
     * @return The created play button.
     */
    private Button createPlayButton() {
        Button button = new Button("PLAY");
        button.getStyleClass().add("start-game-button");

        button.setOnAction(event -> menuListener.setPlay());

        return button;
    }

    /**
     * Creates a label with the specified text and style class.
     * @param text The text of the label.
     * @param styleClass The CSS style class for the label.
     * @return The created label.
     */
    private Label createStyledLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    /**
     * Applies the specified style class to the VBox and aligns it to the center.
     * @param vbox The VBox to style.
     * @param styleClass The CSS style class to apply.
     */
    private void styleVBox(VBox vbox, String styleClass) {
        vbox.getStyleClass().add(styleClass);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStylesheets().add("stylesheet.css");
    }

    /**
     * Creates the main menu components including the title, player input boxes, setting menu, and play button.
     */
    private void createMainMenu() {
        Label titleLabel = createStyledLabel("PING-PONG", "title-label");
        HBox playerBox = createPlayerBox();
        Button playButton = createPlayButton();

        mainMenu = new VBox(titleLabel, playerBox, settingMenu, playButton);
        mainMenu.setAlignment(Pos.CENTER);
        styleVBox(mainMenu, "main-vbox");
    }

    /**
     * Creates a box containing player input elements.
     * @return The created player input box.
     */
    private HBox createPlayerBox() {
        HBox hbox = new HBox(10,
                createPlayerInput("PLAYER 1", player1Name, colorPicker1),
                createPlayerInput("PLAYER 2", player2Name, colorPicker2));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     * Creates a VBox containing player input elements.
     * @param label The label text for the player input.
     * @param textField The text field for entering player information.
     * @param colorPicker The color picker for selecting player color.
     * @return The created player input VBox.
     */
    private VBox createPlayerInput(String label, TextField textField, ColorPicker colorPicker) {
        Label playerLabel = new Label(label);
        textField.getStyleClass().add("player-text-field");
        VBox box = new VBox(5, playerLabel, textField, colorPicker);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Creates the game menu components including buttons for actions such as rating, settings, info, etc.
     */
    private void createGameMenu() {
        Button rate = createButtonWithIcon("/star.png");
        Button settings = createButtonWithIcon("/settings.png");
        Button info = createButtonWithIcon("/info.png");
        Button exit = createButtonWithIcon("/exit.png");
        Button pause = createButtonWithIcon("/pause.png");
        Button restart = createButtonWithIcon("/restart.png");
        Button backToMenu = createButtonWithIcon("/back.png");

        exit.setOnAction(event -> menuListener.setExit());
        info.setOnAction(event -> menuListener.setAbout());
        rate.setOnAction(event -> menuListener.setRate());
        settings.setOnAction(event -> menuListener.toggleSettingMenu(settingMenu));
        pause.setOnAction(event -> menuListener.setPause(settingMenu.isVisible()));
        restart.setOnAction(event -> menuListener.setRestart());
        backToMenu.setOnAction(event -> menuListener.setBackToMenu());

        HBox leftButtons = new HBox(10, rate, info, settings);
        leftButtons.setAlignment(Pos.TOP_LEFT);
        HBox rightButtons = new HBox(10, pause, restart, backToMenu, exit);
        rightButtons.setAlignment(Pos.TOP_RIGHT);

        Pane filler = new Pane();
        HBox.setHgrow(filler, Priority.ALWAYS);

        gameMenu = new HBox(leftButtons, filler, rightButtons);
        gameMenu.setAlignment(Pos.TOP_CENTER);
        gameMenu.setPadding(new Insets(10, 10, 10, 10));
        gameMenu.getStylesheets().add("stylesheet.css");
    }
    /**
     * Creates a button with an icon loaded from the specified path.
     * @param pathName The path to the icon image.
     * @return The created button with the icon.
     */
    private Button createButtonWithIcon(String pathName) {
        Image image = new Image(pathName);
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(15);
        imageView.setFitHeight(15);

        Button buttonWithIcon = new Button();
        buttonWithIcon.setGraphic(imageView);
        buttonWithIcon.getStyleClass().add("menu_button");
        buttonWithIcon.setFocusTraversable(false);

        return buttonWithIcon;
    }

    /**
     * Handles actions for various setting inputs such as player names, colors, ball speed, etc.
     */
    public void handleSettingAction() {
        player1Name.textProperty().addListener((observable, oldValue, newValue) -> menuListener.setPlayer1Name(newValue));
        player2Name.textProperty().addListener((observable, oldValue, newValue) -> menuListener.setPlayer2Name(newValue));
        colorPicker1.valueProperty().addListener((observable, oldColor, newColor) -> menuListener.setColor1(newColor));
        colorPicker2.valueProperty().addListener((observable, oldColor, newColor) -> menuListener.setColor2(newColor));
        ballSpeed.valueProperty().addListener((obs, oldVal, newVal) -> menuListener.setBallSpeed(newVal.intValue()));

        racketWidth.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int racketWidthValue = (int) newValue.getUserData();
                menuListener.setRacketWidth(racketWidthValue);
            }
        });

        racketThickness.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int racketThicknessValue = (int) newValue.getUserData();
                menuListener.setRacketThickness(racketThicknessValue);
            }
        });

        speedChangeRate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                menuListener.setSpeedChangeRate(Integer.parseInt(newValue));
            }
        });

        scoreLimit.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                menuListener.setScoreLimit(Integer.parseInt(newValue));
            }
        });
    }

    /**
     * Resets the main menu by removing existing components and adding the setting menu and play button.
     */
    public void resetMainMenu(){
        mainMenu.getChildren().remove(2);
        mainMenu.getChildren().add(settingMenu);
        mainMenu.getChildren().add(createPlayButton());

        settingMenu.setVisible(true);
    }


    public VBox getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(VBox mainMenu) {
        this.mainMenu = mainMenu;
    }

    public VBox getSettingMenu() {
        return settingMenu;
    }

    public void setSettingMenu(VBox settingMenu) {
        this.settingMenu = settingMenu;
    }

    public HBox getGameMenu() {
        return gameMenu;
    }

    public void setGameMenu(HBox gameMenu) {
        this.gameMenu = gameMenu;
    }

    public TextField getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(TextField player1Name) {
        this.player1Name = player1Name;
    }

    public TextField getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(TextField getPlayer2Name) {
        this.player2Name = getPlayer2Name;
    }

    public TextField getSpeedChangeRate() {
        return speedChangeRate;
    }

    public void setSpeedChangeRate(TextField speedChangeRate) {
        this.speedChangeRate = speedChangeRate;
    }

    public TextField getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(TextField scoreLimit) {
        this.scoreLimit = scoreLimit;
    }

    public Slider getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(Slider ballSpeed) {
        this.ballSpeed = ballSpeed;
    }
}
