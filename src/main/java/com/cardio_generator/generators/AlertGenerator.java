package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class generates alerts for patients based on random conditions that can be resolved on certain conditions.
 * The main purpose of the class is to simulate patients in a health monitoring system.
 * It does so by implementing the Patient Data Generator interface.
 */
public class AlertGenerator implements PatientDataGenerator {

    // Renamed using camelCase
    public static final Random randomGenerator = new Random();
    // Renamed variable to camelCase and added Javadoc comment
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Constructs an object of the Alert generator with the amount of patients.
     * @param patientCount The number of patients for which data will be generated
     */
    // Added: renamed parameter to camelCase, and used final keyword
    public AlertGenerator(final int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    // Added: renamed parameter to camelCase, and used @Override

    /**
     * Generates alerts for a patient. It does so by randomly triggering the alerts and if they are withing the preffered conditions the alert is resolved.
     * @param patientId the patient id number that was generated
     * @param outputStrategy The strategy used to output the specific type of data
     * @throws NullPointerException   if outputStrategy is null.
     * @throws ArrayIndexOutOfBoundsException if patientId is out of bounds of the alertStates array.
     */
    @Override
    public void generate(final int patientId, final OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                final double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                final double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                final boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (final Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
