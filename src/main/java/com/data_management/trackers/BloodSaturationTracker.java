package com.data_management.trackers;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodSaturationTracker {
    private Patient patient;
    private List<PatientRecord> bloodSaturationRecord;
    private List<PatientRecord> patientRecord;

    public BloodSaturationTracker(Patient patient) {
        this.patient = patient;
        this.patientRecord = patient.getPatientRecords();
        this.bloodSaturationRecord =getOxygenSaturationRecords();
    }

    /**
     * Retrieves a list of PatientRecord objects for this patient that are of type "OxygenSaturation"
     * @return a list of PatientRecord objects of type "OxygenSaturation"
     */
    public List<PatientRecord> getOxygenSaturationRecords() {
        List<PatientRecord> oxygenSaturationRecords = new ArrayList<>();
        for (PatientRecord record : patientRecord) {
            if (record.getRecordType().equals("Saturation")) {
                oxygenSaturationRecords.add(record);
            }
        }
        return oxygenSaturationRecords;
    }

    /**
     * Checks if blood saturation in any record drops below 92 and returns an alert if the condition is met.
     * @return an Alert if the condition is met, null otherwise
     */
    public Alert checkLowSaturation() {
        for (PatientRecord record : bloodSaturationRecord) {
            if (record.getMeasurementValue() < 92) {
                return new Alert(Integer.toString(patient.getPatientId()), "Blood Saturation Drops Below 92", record.getTimestamp());
            }
        }
        return null;
    }

    /**
     * Checks if blood saturation drops 5% or more within a 10-minute interval and returns an alert if the condition is met.
     * @return an Alert if the condition is met, null otherwise
     */
    public Alert checkRapidDrop() {
        long interval = 600000;
        if (bloodSaturationRecord.isEmpty()) {
            return null;
        }
        long startTime = bloodSaturationRecord.get(0).getTimestamp();
        long endTime = bloodSaturationRecord.get(bloodSaturationRecord.size() - 1).getTimestamp();

        for (long currentTime = startTime; currentTime < endTime; currentTime += interval) {
            long intervalStart = currentTime;
            long intervalEnd = currentTime + interval;

            List<PatientRecord> tempList = new ArrayList<>();
            for (PatientRecord record : bloodSaturationRecord) {
                if (record.getTimestamp() >= intervalStart && record.getTimestamp() <= intervalEnd) {
                    tempList.add(record);
                }
            }

            if (!tempList.isEmpty()) {
                double min = tempList.stream().mapToDouble(PatientRecord::getMeasurementValue).min().orElse(Double.POSITIVE_INFINITY);
                double max = tempList.stream().mapToDouble(PatientRecord::getMeasurementValue).max().orElse(Double.NEGATIVE_INFINITY);

                if ((max - min) >= 5) {
                    return new Alert(Integer.toString(patient.getPatientId()), "Saturation Drops Rapidly", intervalStart);
                }
            }
        }
        return null;
    }
}
