package com.alerts;

import com.data_management.*;
import com.data_management.trackers.BloodPressureTracker;
import com.data_management.trackers.BloodSaturationTracker;
import com.data_management.trackers.ECGTracker;
import com.data_management.trackers.HypotensiveHypoxemiaTracker;


import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    public boolean alertTriggered;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alertTriggered = false;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        trackBloodPressure(patient);
        trackBloodSaturation(patient);
        trackHypotensiveHypoxemia(patient);
        trackECG(patient);
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        if (alert !=null){
            System.out.println("Alert at " +alert.getTimestamp()+" : Patient "+ alert.getPatientId()+", Condition : " + alert.getCondition());
            alertTriggered = true;
        }
    }

    public boolean isAlertTriggered() {
        return alertTriggered; // Return the flag value
    }

    /**
     * Not implemented but used for when alerts are resolved
     */
    public void alertsResolved(){
        alertTriggered= false;
    }


    private void trackBloodPressure(Patient patient){
        BloodPressureTracker bloodPressureTracker = new BloodPressureTracker(patient);
        triggerAlert(bloodPressureTracker.systolicChange());
        triggerAlert(bloodPressureTracker.diastolicChange());
        triggerAlert(bloodPressureTracker.checkSystolicThresholds());
        triggerAlert(bloodPressureTracker.checkDiastolicThresholds());

    }

    private void trackBloodSaturation(Patient patient){
        BloodSaturationTracker saturationTracker = new BloodSaturationTracker(patient);
        triggerAlert(saturationTracker.checkLowSaturation());
        triggerAlert(saturationTracker.checkRapidDrop());
    }

    private void trackHypotensiveHypoxemia(Patient patient){
        HypotensiveHypoxemiaTracker tracker = new HypotensiveHypoxemiaTracker(patient);
        triggerAlert(tracker.checkHypotensiveHypoxemia());
    }

    private void trackECG(Patient patient){
        ECGTracker tracker= new ECGTracker(patient);
        triggerAlert(tracker.checkAbnormalECG());
    }

    public static void main(String args[]){
        DataStorage storage = new DataStorage();
        storage.addPatientData(4, 130.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(4, 125.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(4, 110.0, "SystolicPressure", 1627843323000L);
        storage.addPatientData(6, 170.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(6, 190.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(10, 55.0, "ECG", 1627842123000L);
        storage.addPatientData(10, 105.0, "ECG", 1627842723000L);
        storage.addPatientData(10, 60.0, "ECG", 1627843323000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(10);
        alert.evaluateData(tested);

    }

}
