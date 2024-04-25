package com.example.ping_pong.controller;

import com.example.ping_pong.model.GameSave;
import com.example.ping_pong.model.GameSettings;

import java.io.*;

/**
 * Class for serializing and deserializing GameSettings objects.
 * Allows saving game settings to a file and loading them from a file.
 */
public class GameSerializer {

    private static GameSerializer instance;

    private GameSerializer() {}

    public static GameSerializer getInstance(){
        if(instance == null){
            instance = new GameSerializer();
        }
        return instance;
    }

    /**
     * Serializes the given GameSettings object and saves it to a file.
     *
     * @param settings the GameSettings object to be serialized
     */
    public void serializeSettings(GameSettings settings) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/settings.txt"))) {
            out.writeObject(settings);
        } catch (IOException e) {
            System.out.println("Error serializing GameSettings: " + e.getMessage());
        }
    }

    public boolean serializeSave(GameSave save, String saveName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/saves/"+saveName+".txt"))) {
            out.writeObject(save);
            return true;
        } catch (IOException e) {
            System.out.println("Error serializing GameSave: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deserializes GameSettings from a file.
     *
     * @return the deserialized GameSettings object, or a new GameSettings object if deserialization fails
     */
    public GameSettings deserializeSettings() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/settings.txt"))) {
            return (GameSettings) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing GameSettings: " + e.getMessage());
            return new GameSettings();
        }
    }

    public GameSave deserializeSave(String saveName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/saves/"+saveName+".txt"))) {
            return (GameSave) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing GameSettings: " + e.getMessage());
            return null;
        }
    }
}

