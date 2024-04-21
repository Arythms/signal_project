package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

//used the camelCase naming convention in all variables
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // Renamed variable to follow camelCase naming convention

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); // Renamed variable to camelCsse

    // Added final keyword to parameter and renamed parameter to camelCase
    public FileOutputStrategy(final String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void output(final int patientId, final long timestamp, final String label, final String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory)); // Renamed variable to follow camelCase naming convention
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // Set the filePath variable
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString()); // Renamed variable to follow camelCase naming convention

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) { // Used Path.of() for file path creation
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}