package org.example.musicapp.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;

public class JsonDatabase<T> {
    private final String filePath;
    private final Type type;
    private final Gson gson;

    public JsonDatabase(String filePath, Type type) {
        this.filePath = filePath;
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveData(T data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T loadData() {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
