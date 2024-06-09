package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest {


    @Test
    void testDiastolicIncreasing() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 85.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(1, 90.0, "DiastolicPressure", 1627842723000L);
        storage.addPatientData(1, 96.0, "DiastolicPressure", 1627843323000L);
        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(1);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());


    }

    @Test
    void testDiastolicDecreasing() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(2, 90.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(2, 80.0, "DiastolicPressure", 1627842723000L);
        storage.addPatientData(2, 75.0, "DiastolicPressure", 1627843323000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(2);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());
    }
    @Test
    void testSystolicIncreasing() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(3, 120.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(3, 130.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(3, 135.0, "SystolicPressure", 1627843323000L);
        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(3);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());
    }

    @Test
    void testSystolicDecreasing() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(4, 130.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(4, 120.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(4, 119.0, "SystolicPressure", 1627843323000L);
        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(4);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());
    }
    @Test
    void testSystolicDrop() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(5, 100.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(5, 88.0, "SystolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(5);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());


    }
    @Test
    void testSystolicPressurePeak() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(6, 170.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(6, 190.0, "SystolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(6);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());

    }

    @Test
    void testDiastolicPressurePeak() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(7, 190.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(7, 195.0, "DiastolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(7);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());

    }
    @Test
    void testDiastolicPressureDrop() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(8, 50.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(8, 40.0, "DiastolicPressure", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(8);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());

    }
    @Test
    void testBloodSaturationDrop() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(9, 95.0, "Saturation", 1627842123000L);
        storage.addPatientData(9, 88.0, "Saturation", 1627842723000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(9);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());

    }

    @Test
    void testECG() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();
        storage.addPatientData(10, 55.0, "ECG", 1627842123000L);
        storage.addPatientData(10, 105.0, "ECG", 1627842723000L);
        storage.addPatientData(10, 60.0, "ECG", 1627843323000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(10);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());
    }
    @Test
    void testHypoxemiaHypotensia() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        DataStorage storage = new DataStorage();

        storage.addPatientData(11, 88.0, "Saturation", 1627842123000L);
        storage.addPatientData(11, 88.0, "SystolicPressure", 1627842123000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(11);
        alert.evaluateData(tested);
        assertTrue(alert.isAlertTriggered());


    }



}