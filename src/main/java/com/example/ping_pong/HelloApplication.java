package com.example.ping_pong;

import com.example.ping_pong.controller.*;
import com.example.ping_pong.view.LabCanvas;
import com.example.ping_pong.view.Menu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements SceneSwitcher {

    private LabCanvas canvas = new LabCanvas(650, 500);
    private LabController labController = new LabController();
    private MenuListener menuListener = new MenuListener(labController.getGame(), this);
    private Menu menu = new Menu(menuListener);
    private StackPane rootPane;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.rootPane = new StackPane();
        this.rootPane.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(rootPane, 650, 550);
        primaryStage.setTitle("PING-PONG");
        primaryStage.getIcons().add(new Image("/icon.jpg"));

        switchToMainMenu(); // Initialize with the main menu

        primaryStage.setMinHeight(550);
        primaryStage.setMinWidth(650);

        // Initialize window resize listeners
        initializeResizeListeners(primaryStage);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void initializeResizeListeners(Stage primaryStage) {
        addWidthListener(primaryStage);
        addHeightListener(primaryStage);
    }

    private void addWidthListener(Stage primaryStage) {
        primaryStage.widthProperty().addListener(observable -> {
            double factor = primaryStage.getWidth() / labController.getGame().getWidth();
            System.out.println("Width changed " + primaryStage.getWidth() + " " + factor);
            labController.getGame().setWidth(primaryStage.getWidth());
            labController.getGame().resizeX(factor);
            canvas.drawGame(labController.getGame());
        });
    }

    private void addHeightListener(Stage primaryStage) {
        primaryStage.heightProperty().addListener(observable -> {
            double factor = primaryStage.getHeight() / labController.getGame().getHeigh();
            System.out.println("Height changed " + primaryStage.getHeight() + " " + factor);
            labController.getGame().setHeigh(primaryStage.getHeight());
            labController.getGame().resizeY(factor);
            canvas.drawGame(labController.getGame());
        });
    }

    @Override
    public void switchToMainMenu() {
        rootPane.getChildren().clear();
        VBox root = menu.getMainMenu();
        rootPane.getChildren().add(root);
    }

    @Override
    public void switchToGame() {
        rootPane.getChildren().clear();

        StackPane.setAlignment(canvas, Pos.CENTER);
        rootPane.getChildren().add(canvas);
        canvas.drawGame(labController.getGame());

        HBox gameMenu = menu.getGameMenu();
        StackPane.setAlignment(gameMenu, Pos.TOP_RIGHT);
        rootPane.getChildren().add(gameMenu);

        VBox settingOverlay = menu.getSettingMenu();
        settingOverlay.setVisible(false);
        StackPane.setAlignment(settingOverlay, Pos.CENTER);
        rootPane.getChildren().add(settingOverlay);

        KeyboardListener keyboardListener = new KeyboardListener( labController.getGame(), canvas);
        canvas.setOnKeyPressed(keyboardListener );
        canvas.setOnKeyTyped(keyboardListener);
        canvas.setFocusTraversable(true);

//        BallManager ballManager= new BallManager(labController.getGame(), canvas);
//        Thread thread = new Thread(ballManager);
//        thread.start();
//        Thread.yield();
    }

    public LabCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(LabCanvas canvas) {
        this.canvas = canvas;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public static void main(String[] args) {
        launch();
    }
}
