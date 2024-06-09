package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * To obtain the console output to check if the methods are being called correctly
     */
    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Testing Blood Pressure Issues
     */
    @Test
    void testDiastolicIncreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 85.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(1, 90.0, "DiastolicPressure", 1627842723000L);
        storage.addPatientData(1, 96.0, "DiastolicPressure", 1627843323000L);
        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(1);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");


    }
    /**
     * Testing Blood Pressure Issues
     */

    @Test
    void testDiastolicDecreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(2, 90.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(2, 80.0, "DiastolicPressure", 1627842723000L);
        storage.addPatientData(2, 75.0, "DiastolicPressure", 1627843323000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(2);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }
    /**
     * Testing Blood Pressure Issues
     */
    @Test
    void testSystolicIncreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(3, 120.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(3, 130.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(3, 135.0, "SystolicPressure", 1627843323000L);
        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(3);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }
    /**
     * Testing Blood Pressure Issues
     */

    @Test
    void testSystolicDecreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(4, 130.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(4, 120.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(4, 119.0, "SystolicPressure", 1627843323000L);
        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(4);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }
    /**
     * Testing Blood Pressure Issues
     */
    @Test
    void testSystolicDrop() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(5, 100.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(5, 88.0, "SystolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(5);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }

    /**
     * Testing Blood Pressure Issues
     */
    @Test
    void testSystolicPressurePeak() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(6, 170.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(6, 190.0, "SystolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(6);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");


    }

    /**
     * Testing Blood Pressure Issues
     */

    @Test
    void testDiastolicPressurePeak() {

        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(7, 190.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(7, 195.0, "DiastolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(7);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");


    }

    /**
     * Testing Blood Pressure Issues
     */
    @Test
    void testDiastolicPressureDrop() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(8, 50.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(8, 40.0, "DiastolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(8);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }

    /**
     * Testing Blood Saturation Cases
     */
    @Test
    void testBloodSaturationDrop() {

        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(9, 95.0, "Saturation", 1627842123000L);
        storage.addPatientData(9, 88.0, "Saturation", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(9);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");


    }
    /**
     * Testing Heart Rate
     */

    @Test
    void testECG() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(10, 55.0, "ECG", 1627842123000L);
        storage.addPatientData(10, 105.0, "ECG", 1627842723000L);
        storage.addPatientData(10, 60.0, "ECG", 1627843323000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(10);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }

    /**
     * Testing for hypoxemia Hypotensia
     */
    @Test
    void testHypoxemiaHypotensia() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(11, 88.0, "Saturation", 1627842123000L);
        storage.addPatientData(11, 88.0, "SystolicPressure", 1627842123000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(11);
        alert.evaluateData(tested);
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");

    }



}