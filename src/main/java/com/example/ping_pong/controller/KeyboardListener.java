package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Listens for keyboard events and handles player movements and game start actions.
 */
public class KeyboardListener implements EventHandler<KeyEvent> {
    private Game game;
    private GameController gameController;

    /**
     * Constructs a new KeyboardListener with the specified game and game controller.
     *
     * @param game           the Game object representing the game state
     * @param gameController the LabController object for controlling the game
     */
    public KeyboardListener(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
    }

    /**
     * Handles keyboard events by processing player movements and game start actions.
     *
     * @param keyEvent the KeyEvent object representing the keyboard event
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        System.out.println(keyEvent);
        KeyCode key = keyEvent.getCode();
        double height = game.getHeigh();

        if(key == KeyCode.ENTER){
            if(game.isPaused()){
                try {
                    gameController.startGame();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                game.setPaused(!game.isPaused());
            }
        }
        else if (!game.isPaused()) {
                switch (key) {
                    case W -> game.getPlayer1().getRacket().moveUp(height);
                    case S -> game.getPlayer1().getRacket().moveDown(height);
                    case UP -> game.getPlayer2().getRacket().moveUp(height);
                    case DOWN -> game.getPlayer2().getRacket().moveDown(height);
                }
        }

    }
}
