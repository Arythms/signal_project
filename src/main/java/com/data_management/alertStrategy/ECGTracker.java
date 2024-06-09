package com.data_management.alertStrategy;

import com.alerts.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class ECGTracker implements AlertStrategy{
    private Patient patient;
    private List<PatientRecord> ecgRecords;

    public ECGTracker(Patient patient) {
        this.patient = patient;
        this.ecgRecords = getECGRecords();
    }

    /**
     * Retrieves a list of the records of Patient that record ECG readings
     * @return a list of PatientRecord objects of type "ECG"
     */
    private List<PatientRecord> getECGRecords() {
        List<PatientRecord> ecgRecords = new ArrayList<>();
        for (PatientRecord record : patient.getPatientRecords()) {
            if (record.getRecordType().equals("ECG")) {
                ecgRecords.add(record);
            }
        }
        return ecgRecords;
    }

    /**
     * Checks for alerts based on abnormal ECG readings.
     * @return an Alert if the conditions are met
     */
    public Alert checkAbnormalECG() {
        List<Alert> alerts = new ArrayList<>();
        for (PatientRecord ecgRecord : ecgRecords) {
            if (ecgRecord.getMeasurementValue() < 50 || ecgRecord.getMeasurementValue() > 100) {
                Alert alert = new Alert(Integer.toString(patient.getPatientId()), "Abnormal Heart Rate Alert", ecgRecord.getTimestamp());
                return alert;
            }
        }
        return null;
    }

    @Override
    public Alert checkAlert() {
        if(checkAbnormalECG()!=null) return checkAbnormalECG();
        return null;
    }
}