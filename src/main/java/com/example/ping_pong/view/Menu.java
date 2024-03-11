package com.example.ping_pong.view;

import com.example.ping_pong.controller.MenuListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Menu {
    private VBox mainMenu;
    private VBox settingMenu;
    private HBox gameMenu;
    private TextField player1Name, player2Name, speedChangeRate, scoreLimit;
    private Slider ballSpeed;
    private ToggleGroup racketWidth, racketThickness;
    private ColorPicker colorPicker1, colorPicker2;
    private MenuListener menuListener;

    public Menu(MenuListener menuListener) {
        this.menuListener = menuListener;
        initializeFields();

        createSettingMenu();
        createMainMenu();
        createGameMenu();

        handleSettingAction();
    }

    private void initializeFields() {
        player1Name = new TextField();
        player2Name = new TextField();
        speedChangeRate = new TextField("10");
        scoreLimit = new TextField("10");
        ballSpeed = new Slider(1, 20, 10);
        racketWidth = new ToggleGroup();
        racketThickness = new ToggleGroup();
        colorPicker1 = new ColorPicker(Color.BLUE);
        colorPicker2 = new ColorPicker(Color.RED);
    }

    private void createSettingMenu() {
        Label settingsLabel = createStyledLabel("GAME SETTINGS", "settings-label");
        VBox settingsBox = createSettingsBox();

        settingMenu = new VBox(10, settingsLabel, settingsBox);
        styleVBox(settingMenu, "main-vbox");
        settingMenu.setMaxWidth(450);
        settingMenu.setMaxHeight(450);
    }

    private VBox createSettingsBox() {
        return new VBox(5,
                createSettingControl("BALL SPEED:", ballSpeed),
                createSettingControl("BALL SPEED CHANGE:", speedChangeRate),
                createToggleGroup("RACKET WIDTH:", racketWidth, "Small", 10, "Medium", 20, "Big", 30),
                createToggleGroup("RACKET THICKNESS:", racketThickness, "Thin", 1, "Medium", 3, "Thick", 5),
                createSettingControl("SCORE LIMIT:", scoreLimit));
    }

    private HBox createSettingControl(String label, javafx.scene.Node control) {
        Label settingLabel = new Label(label);
        HBox hbox = new HBox(10, settingLabel, control);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createToggleGroup(String label, ToggleGroup group,
                                   String n1, int n1v,
                                   String n2, int n2v,
                                   String n3, int n3v) {
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

        m.setSelected(true);

        HBox hbox = new HBox(10, settingLabel, s, m, b);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private Button createPlayButton() {
        Button button = new Button("PLAY");
        button.getStyleClass().add("start-game-button");

        button.setOnAction(event -> menuListener.setPlay());

        return button;
    }

    private Label createStyledLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;
    }

    private void styleVBox(VBox vbox, String styleClass) {
        vbox.getStyleClass().add(styleClass);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStylesheets().add("stylesheet.css");
    }

    private void createMainMenu() {
        Label titleLabel = createStyledLabel("PING-PONG", "title-label");
        HBox playerBox = createPlayerBox();
        Button playButton = createPlayButton();

        mainMenu = new VBox(titleLabel, playerBox, settingMenu, playButton);
        mainMenu.setAlignment(Pos.CENTER);
        styleVBox(mainMenu, "main-vbox");
    }

    private HBox createPlayerBox() {
        HBox hbox = new HBox(10,
                createPlayerInput("PLAYER 1", player1Name, colorPicker1),
                createPlayerInput("PLAYER 2", player2Name, colorPicker2));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private VBox createPlayerInput(String label, TextField textField, ColorPicker colorPicker) {
        Label playerLabel = new Label(label);
        textField.getStyleClass().add("player-text-field");
        VBox box = new VBox(5, playerLabel, textField, colorPicker);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void createGameMenu() {
        Button rate = createButtonWithIcon("/star.png");
        Button settings = createButtonWithIcon("/settings.png");
        Button info = createButtonWithIcon("/info.png");
        Button exit = createButtonWithIcon("/exit.png");

        exit.setOnAction(event -> menuListener.setExit());

        info.setOnAction(event -> menuListener.setAbout());

        rate.setOnAction(event -> menuListener.setRate());

        settings.setOnAction(event -> menuListener.toggleSettingMenu(settingMenu));

        gameMenu = new HBox(10, rate, info, settings, exit);
        gameMenu.setAlignment(Pos.TOP_RIGHT);
        gameMenu.setPadding(new Insets(10, 10, 10, 10));
        gameMenu.getStylesheets().add("stylesheet.css");
    }

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
