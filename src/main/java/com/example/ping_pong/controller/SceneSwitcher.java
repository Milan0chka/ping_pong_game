package com.example.ping_pong.controller;
/**
 * Interface for switching scenes in the game.
 */
public interface SceneSwitcher {
    /**
     * Switches to the main menu scene.
     */
    void switchToMainMenu();

    /**
     * Switches to the game scene.
     */
    void switchToGame();
}
