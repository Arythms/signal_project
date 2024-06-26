package com.data_management.alertStrategy;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class HypotensiveHypoxemiaTracker implements AlertStrategy{
    private Patient patient;
    private List<PatientRecord> systolicPressureRecords;
    private List<PatientRecord> saturationRecords;

    public HypotensiveHypoxemiaTracker(Patient patient) {
        this.patient = patient;
        this.systolicPressureRecords = getSystolicPressureRecords();
        this.saturationRecords = getSaturationRecords();
    }

    /**
     * Retrieves a list of the patient's records that record the systolic pressure
     * @return a list of PatientRecord objects of type "SystolicPressure"
     */
    private List<PatientRecord> getSystolicPressureRecords() {
        List<PatientRecord> systolicPressureRecords = new ArrayList<>();
        for (PatientRecord record : patient.getPatientRecords()) {
            if (record.getRecordType().equals("SystolicPressure")) {
                systolicPressureRecords.add(record);
            }
        }
        return systolicPressureRecords;
    }

    /**
     * Retrieves a list of the patient's records that record the blood saturation
     * @return a list of PatientRecord objects of type "Saturation"
     */
    private List<PatientRecord> getSaturationRecords() {
        List<PatientRecord> saturationRecords = new ArrayList<>();
        for (PatientRecord record : patient.getPatientRecords()) {
            if (record.getRecordType().equals("Saturation")) {
                saturationRecords.add(record);
            }
        }
        return saturationRecords;
    }

    /**
     * Checks for alerts based on hypotensive hypoxemia conditions.
     * @return an Alert if the conditions are met
     */
    public Alert checkHypotensiveHypoxemia() {
        for (PatientRecord systolicRecord : systolicPressureRecords) {
            for (PatientRecord saturationRecord : saturationRecords) {
                if (systolicRecord.getTimestamp() == saturationRecord.getTimestamp() &&
                        systolicRecord.getMeasurementValue() < 90 && saturationRecord.getMeasurementValue() < 92) {
                    Alert alert = new Alert(Integer.toString(patient.getPatientId()), "Hypotensive Hypoxemia", systolicRecord.getTimestamp());
                    return alert;
                }
            }
        }
        return null ;
    }

    @Override
    public Alert checkAlert() {
        if(checkHypotensiveHypoxemia()!=null)return checkHypotensiveHypoxemia();
        return null;
    }
}
