package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Week 4: Implementation of Data Reader Interface
 * Given a directory assumed to be a cvs file, the class will store the data
 */
public class FileDataReader  {
    private String outputDir; // Directory where the data files are located

    // Constructor to initialize the output directory
    public FileDataReader(String outputDir) {
        this.outputDir = outputDir;
    }

    // Method to read data from the specified directory and store it in the dataStorage

    public void readData(DataStorage dataStorage) throws IOException {
        // Create a File object for the directory
        File directory = new File(outputDir);

        // Check if the directory exists and is indeed a directory
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Invalid directory: " + outputDir);
        }

        // List all files in the directory that end with ".txt" (assuming text files)
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        // Check if there are no files in the directory
        if (files == null || files.length == 0) {
            throw new IOException("No data files found in directory: " + outputDir);
        }

        // Loop through each file in the directory
        for (File file : files) {
            // Try-with-resources to ensure the BufferedReader is closed after use
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                // Read each line in the file
                while ((line = reader.readLine()) != null) {
                    // Parse the line into respective data types
                    String[] parts = line.split(",");
                    int patientId = Integer.parseInt(parts[0].trim());
                    double measurementValue = Double.parseDouble(parts[1].trim());
                    String recordType = parts[2].trim();
                    long timestamp = Long.parseLong(parts[3].trim());

                    // Store the parsed data in the dataStorage
                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                }
            }
        }
    }
}
