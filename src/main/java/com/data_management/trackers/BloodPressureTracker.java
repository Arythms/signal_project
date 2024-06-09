package com.data_management.trackers;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureTracker {
    private Patient patient;
    private List<PatientRecord> systolicRecord;
    private List<PatientRecord> diastolicRecord;
    private List<PatientRecord> patientRecord;
    private List<Alert> alerts;
    public BloodPressureTracker(Patient patient) {
        this.patient = patient;
        this.patientRecord = patient.getPatientRecords();
        this.systolicRecord = getSystolicBloodPressureRecords();
        this.diastolicRecord =getDiastolicBloodPressureRecords();
        List<Alert> alerts1 = new ArrayList<>();
        this.alerts = alerts1;
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that are of type "SystolicPressure"
     * @return a list of PatientRecord objects of type "SystolicPressure"
     */
    public List<PatientRecord> getSystolicBloodPressureRecords() {
        List<PatientRecord> systolicBloodRecords = new ArrayList<>();
        for (PatientRecord record : patientRecord) {
            if (record.getRecordType().equals("SystolicPressure")) {
                systolicBloodRecords.add(record);
            }
        }
        return systolicBloodRecords;
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that are of type "DiastolicPressure".
     * @return a list of PatientRecord objects of type "DiastolicPressure"
     */
    public List<PatientRecord> getDiastolicBloodPressureRecords() {
        List<PatientRecord> diastolicBloodRecords = new ArrayList<>();
        for (PatientRecord record : patientRecord) {
            if (record.getRecordType().equals("DiastolicPressure")) {
                diastolicBloodRecords.add(record);
            }
        }
        return diastolicBloodRecords;
    }

    /**
     * Checks if the systolic blood pressure is changing
     * @return an Alert if needed
     */
    public Alert systolicChange() {
        if (systolicRecord.size() >= 3) {
            for (int i = 0; i < systolicRecord.size() - 2; i++) {
                double currentValue = systolicRecord.get(i + 1).getMeasurementValue();
                double previousValue = systolicRecord.get(i).getMeasurementValue();
                double nextValue = systolicRecord.get(i + 2).getMeasurementValue();

                // Check if systolic blood pressure is increasing
                if (currentValue > previousValue && nextValue > currentValue && (nextValue - previousValue) > 10) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Systolic Blood Pressure is Increasing", systolicRecord.get(i + 2).getTimestamp());
                }

                // Check if systolic blood pressure is decreasing
                if (currentValue < previousValue && nextValue < currentValue && (previousValue - nextValue) > 10) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Systolic Blood Pressure is Decreasing", systolicRecord.get(i + 2).getTimestamp());
                }
            }
        }
        return null;
    }

    /**
     * Checks if the diastolic blood pressure is consistently decreasing.
     * @return an Alert if needed
     */
    public Alert diastolicChange() {
        if (diastolicRecord.size() >= 3) {
            for (int i = 0; i < diastolicRecord.size() - 2; i++) {
                double currentValue = diastolicRecord.get(i + 1).getMeasurementValue();
                double previousValue = diastolicRecord.get(i).getMeasurementValue();
                double nextValue = diastolicRecord.get(i + 2).getMeasurementValue();

                // Check if diastolic blood pressure is increasing
                if (currentValue > previousValue && nextValue > currentValue && (nextValue - previousValue) > 10) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Diastolic Blood Pressure is Increasing", diastolicRecord.get(i + 2).getTimestamp());
                }

                // Check if diastolic blood pressure is decreasing
                if (currentValue < previousValue && nextValue < currentValue && (previousValue - nextValue) > 10) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Diastolic Blood Pressure is Decreasing", diastolicRecord.get(i + 2).getTimestamp());
                }
            }

        }
        return null;
    }

        public Alert checkSystolicThresholds () {
            for (PatientRecord record : systolicRecord) {
                if (record.getMeasurementValue() >= 180) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Systolic Pressure Exceeds 180", record.getTimestamp());
                }
                if (record.getMeasurementValue() <= 90) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Systolic Pressure Drops Below 90", record.getTimestamp());
                }
            }
            return null;
        }

        public Alert checkDiastolicThresholds () {
            for (PatientRecord record : diastolicRecord) {
                if (record.getMeasurementValue() >= 120) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Diastolic Pressure Exceeds 120", record.getTimestamp());
                }
                if (record.getMeasurementValue() <= 60) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Diastolic Pressure Drops Below 60", record.getTimestamp());
                }
            }
            return null;
        }
    }





