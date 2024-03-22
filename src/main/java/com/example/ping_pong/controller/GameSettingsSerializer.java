package com.example.ping_pong.controller;

import com.example.ping_pong.model.GameSettings;

import java.io.*;

public class GameSettingsSerializer {

    public static void serialize(GameSettings settings) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/setting.txt"))) {
            out.writeObject(settings);
        } catch (IOException e) {
            System.out.println("Error serializing GameSettings: " + e.getMessage());
        }
    }

    // Deserialize GameSettings from a file
    public static GameSettings deserialize(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/setting.txt"))) {
            return (GameSettings) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing GameSettings: " + e.getMessage());
            return new GameSettings();
        }
    }



}
