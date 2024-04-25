package com.example.ping_pong;

import com.example.ping_pong.controller.*;
import com.example.ping_pong.view.LabCanvas;
import com.example.ping_pong.view.Menu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the Ping-Pong game application.
 */
public class HelloApplication extends Application implements SceneSwitcher {

    private LabCanvas canvas = new LabCanvas(650, 500);
    private GameController gameController = new GameController(canvas);
    private MenuListener menuListener = new MenuListener(gameController.getGame(), this, gameController);
    private Menu menu = new Menu(menuListener);
    private StackPane rootPane;
    private Thread gameThread;

    /**
     * The main entry point for the JavaFX application.
     * Initializes the stage and sets up the main scene.
     *
     * @param primaryStage The primary stage of the application.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Initializes the window resize listeners.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void initializeResizeListeners(Stage primaryStage) {
        addWidthListener(primaryStage);
        addHeightListener(primaryStage);
    }

    /**
     * Adds a listener to handle changes in the width of the primaryStage.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void addWidthListener(Stage primaryStage) {
        primaryStage.widthProperty().addListener(observable -> {
            double factor = primaryStage.getWidth() / gameController.getGame().getWidth();
            System.out.println("Width changed " + primaryStage.getWidth() + " " + factor);
            gameController.getGame().setWidth(primaryStage.getWidth());
            gameController.getGame().resizeX(factor);
            canvas.drawGame(gameController.getGame());
        });
    }

    /**
     * Adds a listener to handle changes in the height of the primaryStage.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void addHeightListener(Stage primaryStage) {
        primaryStage.heightProperty().addListener(observable -> {
            double factor = primaryStage.getHeight() / gameController.getGame().getHeigh();
            System.out.println("Height changed " + primaryStage.getHeight() + " " + factor);
            gameController.getGame().setHeigh(primaryStage.getHeight());
            gameController.getGame().resizeY(factor);
            canvas.drawGame(gameController.getGame());
        });
    }

    /**
     * Switches the scene to the main menu.
     */
    @Override
    public void switchToMainMenu() {
        rootPane.getChildren().clear();
        VBox root = menu.getMainMenu();

        if (!root.getChildren().contains(menu.getSettingMenu()))
            menu.resetMainMenu();

        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        }

        rootPane.getChildren().add(root);
    }

    /**
     * Switches the scene to the game.
     */
    @Override
    public void switchToGame() {
        rootPane.getChildren().clear();

        StackPane.setAlignment(canvas, Pos.CENTER);
        rootPane.getChildren().add(canvas);
        canvas.drawGame(gameController.getGame());

        HBox gameMenu = menu.getGameMenu();
        StackPane.setAlignment(gameMenu, Pos.TOP_CENTER);
        rootPane.getChildren().add(gameMenu);

        VBox settingOverlay = menu.getSettingMenu();
        settingOverlay.setVisible(false);
        StackPane.setAlignment(settingOverlay, Pos.CENTER);
        rootPane.getChildren().add(settingOverlay);

        KeyboardListener keyboardListener = new KeyboardListener(gameController.getGame(), gameController);
        canvas.setOnKeyPressed(keyboardListener);
        canvas.setOnKeyTyped(keyboardListener);
        canvas.setFocusTraversable(true);

        BallManager ballManager = new BallManager(gameController.getGame(), canvas, gameController);
        gameThread = new Thread(ballManager);
        gameThread.start();
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
