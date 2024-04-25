package com.example.ping_pong.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class GameSave implements Serializable {
    private String player1;
    private int player1Score;
    private transient Color player1Color;
    private long player1ColorValue;
    private String player2;
    private int player2Score;
    private transient Color player2Color;
    private long player2ColorValue;

    public GameSave(String player1, int player1Score, Color player1Color,
                    String player2, int player2Score, Color player2Color) {
        this.player1 = player1;
        this.player1Score = player1Score;
        this.player1Color = player1Color;
        this.player1ColorValue = (long) (player1Color.getRed() * 255) << 16 | (long) (player1Color.getGreen() * 255) << 8 | (long) (player1Color.getBlue() * 255);
        this.player2 = player2;
        this.player2Score = player2Score;
        this.player2Color = player2Color;
        this.player2ColorValue = (long) (player2Color.getRed() * 255) << 16 | (long) (player2Color.getGreen() * 255) << 8 | (long) (player2Color.getBlue() * 255);
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer1Color(Color player1Color) {
        this.player1Color = player1Color;
        this.player1ColorValue = (long) (player1Color.getRed() * 255) << 16 | (long) (player1Color.getGreen() * 255) << 8 | (long) (player1Color.getBlue() * 255);
    }

    public Color getPlayer1Color() {
        if (player1Color == null) {
            player1Color = Color.rgb((int)((player1ColorValue >> 16) & 0xFF), (int)((player1ColorValue >> 8) & 0xFF), (int)(player1ColorValue & 0xFF));
        }
        return player1Color;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public void setPlayer2Color(Color player2Color) {
        this.player2Color = player2Color;
        this.player2ColorValue = (long) (player2Color.getRed() * 255) << 16 | (long) (player2Color.getGreen() * 255) << 8 | (long) (player2Color.getBlue() * 255);
    }

    public Color getPlayer2Color() {
        if (player2Color == null) {
            player2Color = Color.rgb((int)((player2ColorValue >> 16) & 0xFF), (int)((player2ColorValue >> 8) & 0xFF), (int)(player2ColorValue & 0xFF));
        }
        return player2Color;
    }
}
