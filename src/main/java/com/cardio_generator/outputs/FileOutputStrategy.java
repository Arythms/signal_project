package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class pourpuse is to create a file output strategy for the health data generated.
 * It implements the Output Strategy Interface that provides a mechanism of outputting information.
 * Once this class is initiated it requires a base directory to store the files.
 * This class generates a text file with the information generated of each health data entry with the information of the patient ID, timestamp, label, and data.
 */
//used the camelCase naming convention in all variables
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; // Renamed variable to follow camelCase naming convention

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); // Renamed variable to camelCsse

    // Added final keyword to parameter and renamed parameter to camelCase

    /**
     * Constructs a new FileOutputStrategy instance with the specified base directory.
     *
     * @param baseDirectory The base directory where output files will be stored.
     */
    public FileOutputStrategy(final String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs health data entry by creating a text file based on the specified parameters.
     *
     * @param patientId The ID of the patient that was generated.
     * @param timestamp The timestamp of the health data.
     * @param label     The label of the health data generated.
     * @param data      The health data to be written in the file outputted.
     */
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
