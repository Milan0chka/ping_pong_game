package com.example.ping_pong.controller;

import com.example.ping_pong.model.Game;

public class LabController {
    private Game game;

    public LabController(){
        this.game = new Game();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
