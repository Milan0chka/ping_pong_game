package com.example.ping_pong;

import com.example.ping_pong.controller.LabController;
import com.example.ping_pong.controller.MenuListener;
import com.example.ping_pong.controller.SceneSwitcher;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements SceneSwitcher {

    private LabCanvas canvas = new LabCanvas(500,500);
    private LabController labController = new LabController();
    private MenuListener menuListener = new MenuListener(labController.getGame());
    private Menu menu = new Menu(menuListener);
    private StackPane rootPane;
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.rootPane = new StackPane();
        this.rootPane.setAlignment(Pos.CENTER);
        menu.setSceneSwitcher(this);

        Scene mainScene = new Scene(rootPane, 500, 500);
        primaryStage.setTitle("PING-PONG");
        primaryStage.getIcons().add(new Image("/icon.jpg"));

        primaryStage.setScene(mainScene);
        switchToMainMenu(); // Initialize with the main menu
        primaryStage.show();
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);

        primaryStage.widthProperty().addListener(observable -> {
            double factor= primaryStage.getWidth()/labController.getGame().getWidth();
            System.out.println("Width changed " + primaryStage.getWidth()+" "+factor);
            labController.getGame().setWidth(primaryStage.getWidth());
            labController.getGame().resizeX(factor);
            canvas.drawGame(labController.getGame());
        });

        primaryStage.heightProperty().addListener(observable -> {
            double factor= primaryStage.getHeight()/labController.getGame().getHeigh();
            System.out.println("Height changed " + primaryStage.getHeight()+" "+factor);
            labController.getGame().setHeigh(primaryStage.getHeight());
            labController.getGame().resizeY(factor);
            canvas.drawGame(labController.getGame());
        });
    }

    public static void main(String[] args) {
        launch();
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

        StackPane.setAlignment(canvas,Pos.CENTER);
        rootPane.getChildren().add(canvas);
        canvas.drawGame(labController.getGame());

        HBox gameMenu = menu.getGameMenu();
        StackPane.setAlignment(gameMenu,Pos.TOP_RIGHT);
        rootPane.getChildren().add(gameMenu);

        VBox settingOverlay = menu.getSettingMenu();
        settingOverlay.setVisible(false);
        StackPane.setAlignment(settingOverlay, Pos.CENTER);
        rootPane.getChildren().add(settingOverlay);
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
}