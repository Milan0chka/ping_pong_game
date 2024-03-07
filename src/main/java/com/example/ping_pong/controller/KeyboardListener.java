package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;
import com.example.ping_pong.view.LabCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardListener implements EventHandler<KeyEvent> {
        private Game game;
        private LabCanvas canvas;
        public KeyboardListener(Game game2, LabCanvas canvas) {
            this.game =game2;
            this.canvas =canvas;
        }
        @Override
        public void handle(KeyEvent keyEvent) {
            System.out.println(keyEvent);
            KeyCode key = keyEvent.getCode();
            double height = game.getHeigh();
            switch (key) {
                case W -> game.getPlayer1().getRacket().moveUp(height);
                case S -> game.getPlayer1().getRacket().moveDown(height);
                case UP -> game.getPlayer2().getRacket().moveUp(height);
                case DOWN -> game.getPlayer2().getRacket().moveDown(height);
            }
            canvas.drawGame(game);
        }


}
