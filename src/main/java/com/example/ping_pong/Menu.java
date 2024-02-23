package com.example.ping_pong;

import com.example.ping_pong.controller.MenuListener;
import com.example.ping_pong.controller.SceneSwitcher;
import com.example.ping_pong.model.Racket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static java.lang.Math.floor;
import static java.lang.System.exit;

public class Menu {
    private VBox mainMenu;
    private VBox settingMenu;
    private HBox gameMenu;
    private TextField player1Name, player2Name, speedChangeRate, scoreLimit;
    private Slider ballSpeed;
    private ToggleGroup racketWidth, racketThickness;
    private SceneSwitcher sceneSwitcher;
    private MenuListener menuListener;

    public Menu(MenuListener menuListener) {
        this.menuListener = menuListener;
        initializeFields();
        createSettingMenu();
        createMainMenu();
        createGameMenu();
    }

    private void initializeFields() {
        player1Name = new TextField();
        player2Name = new TextField();
        speedChangeRate = new TextField("10");
        scoreLimit = new TextField("10");
        ballSpeed = new Slider(1,10,5);
        racketWidth = new ToggleGroup();
        racketThickness = new ToggleGroup();
     }

    private void createSettingMenu() {
        Label settingsLabel = createStyledLabel("GAME SETTINGS", "settings-label");
        VBox settingsBox = createSettingsBox();
        Button playButton = createPlayButton();

        settingMenu = new VBox(10, settingsLabel, settingsBox,playButton);
        styleVBox(settingMenu, "main-vbox");
        this.settingMenu.setMaxWidth(450);
        this.settingMenu.setMaxHeight(450);
    }

    private VBox createSettingsBox() {
        return new VBox(5,
                createSettingControl("BALL SPEED :", ballSpeed),
                createSettingControl("BALL SPEED CHANGE:", speedChangeRate),
                createToggleGroup("RACKET WIDTH :", this.racketWidth, "Small", 10, "Medium", 20, "Big", 30),
                createToggleGroup("RACKET THICKNESS :", this.racketThickness, "Thin", 3, "Medium", 5, "Thick", 7),
                createSettingControl("SCORE LIMIT :", scoreLimit));
    }

    private HBox createSettingControl(String label, javafx.scene.Node control) {
        Label settingLabel = new Label(label);
        HBox hbox = new HBox(10, settingLabel, control);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createToggleGroup( String label, ToggleGroup group,
                                    String n1,int n1v,
                                    String n2,int n2v,
                                    String n3, int n3v){
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

        HBox hbox = new HBox(10,settingLabel,s,m,b);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private Button createPlayButton() {
        Button button = new Button("PLAY");
        button.getStyleClass().add("start-game-button");

        button.setOnAction(event -> {
            if (menuListener.getGame().getPlayer1().getName().isEmpty()) {
                applyPlayerNames();
                applySettings();
                sceneSwitcher.switchToGame();
            } else if (settingMenu.isVisible()){
                applySettings();
                settingMenu.setVisible(false);
                sceneSwitcher.switchToGame();
            } else {
                settingMenu.setVisible(true);
            }
        });

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

        this.mainMenu = new VBox(10, titleLabel, playerBox, this.settingMenu);
        this.mainMenu.setAlignment(Pos.CENTER);
        styleVBox(mainMenu, "main-vbox");
    }

    private HBox createPlayerBox() {
        HBox hbox = new HBox(10,
                createPlayerInput("PLAYER 1", player1Name),
                createPlayerInput("PLAYER 2", player2Name));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private VBox createPlayerInput(String label, TextField textField) {
        Label playerLabel = new Label(label);
        textField.getStyleClass().add("player-text-field");
        VBox box = new VBox(playerLabel, textField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void createGameMenu(){
        Button rate = createButtonWithIcon("/star.png");
        Button settings = createButtonWithIcon("/settings.png");
        Button info = createButtonWithIcon("/info.png");
        Button exit = createButtonWithIcon("/exit.png");


        exit.setOnAction(event -> {
           menuListener.setExit();
        });

        info.setOnAction(event -> {
            menuListener.setAbout();
        });

        rate.setOnAction(event -> {
            menuListener.setRate();
        });

        settings.setOnAction(event -> {
            settingMenu.setVisible(!settingMenu.isVisible());
        });


        this.gameMenu = new HBox(10,rate,info,settings,exit);
        this.gameMenu.setAlignment(Pos.TOP_RIGHT);
        this.gameMenu.setPadding(new Insets(10,10,10,10));
        this.gameMenu.getStylesheets().add("stylesheet.css");
    }

    private Button createButtonWithIcon(String pathName){
        Image image = new Image(pathName);
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(15);
        imageView.setFitHeight(15);

        Button buttonWithIcon = new Button();
        buttonWithIcon.setGraphic(imageView);
        buttonWithIcon.getStyleClass().add("menu_button");

        return buttonWithIcon;
    }

    public void applyPlayerNames(){
        String p1Name = player1Name.getText().isEmpty() ? "Player 1" : player1Name.getText();
        String p2Name = player2Name.getText().isEmpty() ? "Player 2" : player2Name.getText();

        menuListener.setPlayerNames(p1Name,p2Name);
    }

    public void applySettings() {
        // Retrieve values from the UI components
        int speedRate = Integer.parseInt(speedChangeRate.getText());
        int scoreLim = Integer.parseInt(scoreLimit.getText());
        int bSpeed = (int)ballSpeed.getValue();
        int rWidth = (int) racketWidth.getSelectedToggle().getUserData();
        int rThickness = (int) racketThickness.getSelectedToggle().getUserData();;

        // Call the method on the menu listener
        menuListener.updateSettings(speedRate, scoreLim, bSpeed, rWidth, rThickness);
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

    public void setSceneSwitcher(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;
    }
}
