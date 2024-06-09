package com.data_management.alertStrategy;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralized Class that is encharged of tracking the Blood Pressure of a Patient.
 * It calls alert when there is a condition.
 */

public class BloodPressureTracker implements AlertStrategy {
    private Patient patient;
    private List<PatientRecord> systolicRecord;
    private List<PatientRecord> diastolicRecord;
    private List<PatientRecord> patientRecord;
    public BloodPressureTracker(Patient patient) {
        this.patient = patient;
        this.patientRecord = patient.getPatientRecords();
        this.systolicRecord = getSystolicBloodPressureRecords();
        this.diastolicRecord =getDiastolicBloodPressureRecords();

    }

    /**
     * Retrieves a list of PatientRecords that track systolic blood pressure.
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
     * Retrieves a list of PatientRecord that record the Diastolic Blood Pressure.
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
     * @return a personalized Alert (if needed) that indicates if its decreasing or increasing
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
     * Checks if the diastolic blood pressure is changing
     * @return a costumized Alert ( if needed) that explains if it's increasing or decreasing
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

    /**
     * Checks that the systolic pressure is within normal values
     * @return a personalized Alert (if needed) that indicates if the systolic pressure
     * has gone out of normal parameters.
     */

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

    /**
     * Checks that the diastolic pressure is within normal values
     * @return a personalized Alert (if needed) that indicates if the diastolic pressure
     * has gone out of normal parameters.
     */

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

    @Override
    public Alert checkAlert() {
        if (systolicChange() != null) return systolicChange();
        if (diastolicChange() != null) return diastolicChange();
        if (checkSystolicThresholds() != null) return checkSystolicThresholds();
        if (checkDiastolicThresholds()!=null) return checkDiastolicThresholds();
        return null;
    }
}





