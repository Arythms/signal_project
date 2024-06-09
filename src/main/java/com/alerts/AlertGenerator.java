package com.alerts;

import com.data_management.*;
import com.data_management.alertStrategy.BloodPressureTracker;
import com.data_management.alertStrategy.BloodSaturationTracker;
import com.data_management.alertStrategy.ECGTracker;
import com.data_management.alertStrategy.HypotensiveHypoxemiaTracker;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;


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
        if (alert != null) {
            System.out.println("Alert at " + alert.getTimestamp() + " : Patient " + alert.getPatientId() + ", Condition : " + alert.getCondition());
        }
    }

    /**
     * Given a patient Track if any alert was generated regarding Blood Pressure
     *
     * @param patient
     */
    private void trackBloodPressure(Patient patient) {
        BloodPressureTracker bloodPressureTracker = new BloodPressureTracker(patient);
        triggerAlert(bloodPressureTracker.checkAlert());
        /**
         triggerAlert(bloodPressureTracker.systolicChange());
         triggerAlert(bloodPressureTracker.diastolicChange());
         triggerAlert(bloodPressureTracker.checkSystolicThresholds());
         triggerAlert(bloodPressureTracker.checkDiastolicThresholds());
         */

    }

    /**
     * Given a patient, track if any blood saturation alerts have occurred
     *
     * @param patient
     */

    private void trackBloodSaturation(Patient patient) {
        BloodSaturationTracker saturationTracker = new BloodSaturationTracker(patient);
        triggerAlert(saturationTracker.checkAlert());
        /** old implementation
         triggerAlert(saturationTracker.checkLowSaturation());
         triggerAlert(saturationTracker.checkRapidDrop());
         */
    }

    /**
     * Given a Patient Track if there is a case of hypotensive hypoxemia
     *
     * @param patient
     */
    private void trackHypotensiveHypoxemia(Patient patient) {
        HypotensiveHypoxemiaTracker tracker = new HypotensiveHypoxemiaTracker(patient);
        triggerAlert(tracker.checkAlert());
    }

    /**
     * Given a patient track if any EGC issues have occured
     *
     * @param patient
     */
    private void trackECG(Patient patient) {
        ECGTracker tracker = new ECGTracker(patient);
        triggerAlert(tracker.checkAlert());
        //triggerAlert(tracker.checkAbnormalECG());
    }

    /** check manually
    public static void main(String args[]) {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(490, 130.0, "SystolicPressure", 1627842123000L);
        storage.addPatientData(490, 125.0, "SystolicPressure", 1627842723000L);
        storage.addPatientData(490, 110.0, "SystolicPressure", 1627843323000L);

        AlertGenerator alert = new AlertGenerator(storage);
        Patient tested = storage.getPatient(490);
        alert.evaluateData(tested);

    }
     */
}

