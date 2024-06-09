package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

//Used ChatGPT to create reasonable timeStamps
class DataStorageTest {

    @Test
    void testAddAndGetRecords() {

        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testDiastolicIncreasing() {
        DataStorage storage = DataStorage.getInstance();
       storage.addPatientData(96, 85.0, "DiastolicPressure", 1627842123000L);
       storage.addPatientData(96, 87.0, "DiastolicPressure", 1627842723000L);
       storage.addPatientData(96, 89.0, "DiastolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(96, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(85.0, records.get(0).getMeasurementValue());
        assertEquals(87.0, records.get(1).getMeasurementValue());
        assertEquals(89.0, records.get(2).getMeasurementValue());
    }

    @Test
    void testDiastolicDecreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(88, 89.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(88, 87.0, "DiastolicPressure", 1627842723000L);
        storage.addPatientData(88, 85.0, "DiastolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(88, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(89.0, records.get(0).getMeasurementValue());
        assertEquals(87.0, records.get(1).getMeasurementValue());
        assertEquals(85.0, records.get(2).getMeasurementValue());
    }
@Test
    void testSystolicIncreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(33, 120.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(33, 125.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(33, 130.0, "SystolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(33, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(120.0, records.get(0).getMeasurementValue());
        assertEquals(125.0, records.get(1).getMeasurementValue());
        assertEquals(130.0, records.get(2).getMeasurementValue());
    }

    @Test
    void testSystolicDecreasing() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(44, 130.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(44, 125.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(44, 120.0, "SystolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(44, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(130.0, records.get(0).getMeasurementValue());
        assertEquals(125.0, records.get(1).getMeasurementValue());
        assertEquals(120.0, records.get(2).getMeasurementValue());
    }
    @Test
    void testSystolicDrop() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(55, 100.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(55, 88.0, "SystolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(55, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(88.0, records.get(1).getMeasurementValue());
    }
@Test
    void testSystolicPressurePeak() {
    DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(66, 170.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(66, 190.0, "SystolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(66, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(170.0, records.get(0).getMeasurementValue());
        assertEquals(190.0, records.get(1).getMeasurementValue());
    }

    @Test
    void testDiastolicPressurePeak() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(77, 55.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(77, 70.0, "DiastolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(77, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(55.0, records.get(0).getMeasurementValue());
        assertEquals(70.0, records.get(1).getMeasurementValue());
    }
    @Test
    void testDiastolicPressureDrop() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(123, 130.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(123, 100.0, "DiastolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(123, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(130.0, records.get(0).getMeasurementValue());
        assertEquals(100.0, records.get(1).getMeasurementValue());
    }
@Test
    void testBloodSaturationDrop() {
    DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(99, 95.0, "Saturation", 1627842123000L);
        storage.addPatientData(99, 88.0, "Saturation", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(99, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(95.0, records.get(0).getMeasurementValue());
        assertEquals(88.0, records.get(1).getMeasurementValue());
    }
    @Test

    void testBloodSaturationPeak() {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1111, 88.0, "Saturation", 1627842123000L);
        storage.addPatientData(1111, 88.0, "Saturation", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(1111, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(88.0, records.get(0).getMeasurementValue());
        assertEquals(88.0, records.get(1).getMeasurementValue());
    }
@Test
    void testECG() {
    DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(101, 55.0, "ECG", 1627842123000L);
        storage.addPatientData(101, 105.0, "ECG", 1627842723000L);
        storage.addPatientData(101, 60.0, "ECG", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(101, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(55.0, records.get(0).getMeasurementValue());
        assertEquals(105.0, records.get(1).getMeasurementValue());
        assertEquals(60.0, records.get(2).getMeasurementValue());
    }
@Test
    void testHypoxemiaHypotensia() {
    DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(1101, 88.0, "Saturation", 1627842123000L);
        storage.addPatientData(1101, 88.0, "SystolicPressure", 1627842123000L);

        List<PatientRecord> saturationRecords = storage.getRecords(1101, 1627842123000L, 1627842123000L);

        assertEquals(2, saturationRecords.size());
        assertEquals(88.0, saturationRecords.get(0).getMeasurementValue());
        assertEquals(88.0, saturationRecords.get(1).getMeasurementValue());
    }


}
