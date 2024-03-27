package com.example.ping_pong.controller;

import com.example.ping_pong.model.GameSettings;

import java.io.*;

/**
 * Class for serializing and deserializing GameSettings objects.
 * Allows saving game settings to a file and loading them from a file.
 */
public class GameSettingsSerializer {

    /**
     * Serializes the given GameSettings object and saves it to a file.
     *
     * @param settings the GameSettings object to be serialized
     */
    public static void serialize(GameSettings settings) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/setting.txt"))) {
            out.writeObject(settings);
        } catch (IOException e) {
            System.out.println("Error serializing GameSettings: " + e.getMessage());
        }
    }

    /**
     * Deserializes GameSettings from a file.
     *
     * @return the deserialized GameSettings object, or a new GameSettings object if deserialization fails
     */
    public static GameSettings deserialize() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/setting.txt"))) {
            return (GameSettings) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing GameSettings: " + e.getMessage());
            return new GameSettings();
        }
    }
}

