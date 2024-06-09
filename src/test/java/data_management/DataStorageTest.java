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

        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }
    @Test

    void testDiastolicIncreasing() {
        DataStorage storage = new DataStorage();
       storage.addPatientData(1, 85.0, "DiastolicPressure", 1627842123000L);
       storage.addPatientData(1, 87.0, "DiastolicPressure", 1627842723000L);
       storage.addPatientData(1, 89.0, "DiastolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(1, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(85.0, records.get(0).getMeasurementValue());
        assertEquals(87.0, records.get(1).getMeasurementValue());
        assertEquals(89.0, records.get(2).getMeasurementValue());
    }

    @Test
    void testDiastolicDecreasing() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(2, 89.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(2, 87.0, "DiastolicPressure", 1627842723000L);
        storage.addPatientData(2, 85.0, "DiastolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(2, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(89.0, records.get(0).getMeasurementValue());
        assertEquals(87.0, records.get(1).getMeasurementValue());
        assertEquals(85.0, records.get(2).getMeasurementValue());
    }
@Test
    void testSystolicIncreasing() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(3, 120.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(3, 125.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(3, 130.0, "SystolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(3, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(120.0, records.get(0).getMeasurementValue());
        assertEquals(125.0, records.get(1).getMeasurementValue());
        assertEquals(130.0, records.get(2).getMeasurementValue());
    }

    @Test
    void testSystolicDecreasing() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(4, 130.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(4, 125.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(4, 120.0, "SystolicPressure", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(4, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(130.0, records.get(0).getMeasurementValue());
        assertEquals(125.0, records.get(1).getMeasurementValue());
        assertEquals(120.0, records.get(2).getMeasurementValue());
    }
    @Test
    void testSystolicDrop() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(5, 100.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(5, 88.0, "SystolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(5, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(88.0, records.get(1).getMeasurementValue());
    }
@Test
    void testSystolicPressurePeak() {
    DataStorage storage = new DataStorage();
        storage.addPatientData(6, 170.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(6, 190.0, "SystolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(6, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(170.0, records.get(0).getMeasurementValue());
        assertEquals(190.0, records.get(1).getMeasurementValue());
    }

    @Test
    void testDiastolicPressurePeak() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(7, 70.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(7, 55.0, "DiastolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(7, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(70.0, records.get(0).getMeasurementValue());
        assertEquals(55.0, records.get(1).getMeasurementValue());
    }
    @Test
    void testDiastolicPressureDrop() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(8, 100.0, "DiastolicPressure", 1627842123000L);
        storage.addPatientData(8, 130.0, "DiastolicPressure", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(8, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(130.0, records.get(1).getMeasurementValue());
    }
@Test
    void testBloodSaturationDrop() {
    DataStorage storage = new DataStorage();
        storage.addPatientData(9, 95.0, "Saturation", 1627842123000L);
        storage.addPatientData(9, 88.0, "Saturation", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(9, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(95.0, records.get(0).getMeasurementValue());
        assertEquals(88.0, records.get(1).getMeasurementValue());
    }
    @Test

    void testBloodSaturationPeak() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(11, 88.0, "Saturation", 1627842123000L);
        storage.addPatientData(11, 88.0, "Saturation", 1627842723000L);

        List<PatientRecord> records = storage.getRecords(11, 1627842123000L, 1627842723000L);
        assertEquals(2, records.size());
        assertEquals(88.0, records.get(0).getMeasurementValue());
        assertEquals(88.0, records.get(1).getMeasurementValue());
    }
@Test
    void testECG() {
    DataStorage storage = new DataStorage();
        storage.addPatientData(10, 55.0, "ECG", 1627842123000L);
        storage.addPatientData(10, 105.0, "ECG", 1627842723000L);
        storage.addPatientData(10, 60.0, "ECG", 1627843323000L);

        List<PatientRecord> records = storage.getRecords(10, 1627842123000L, 1627843323000L);
        assertEquals(3, records.size());
        assertEquals(55.0, records.get(0).getMeasurementValue());
        assertEquals(105.0, records.get(1).getMeasurementValue());
        assertEquals(60.0, records.get(2).getMeasurementValue());
    }
@Test
    void testHypoxemiaHypotensia() {
    DataStorage storage = new DataStorage();

        storage.addPatientData(11, 88.0, "Saturation", 1627842123000L);
        storage.addPatientData(11, 88.0, "SystolicPressure", 1627842123000L);

        List<PatientRecord> saturationRecords = storage.getRecords(11, 1627842123000L, 1627842123000L);

        assertEquals(2, saturationRecords.size());
        assertEquals(88.0, saturationRecords.get(0).getMeasurementValue());
        assertEquals(88.0, saturationRecords.get(1).getMeasurementValue());
    }


}
