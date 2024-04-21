package com.cardio_generator.outputs;

/**
 * Interface to create an Output strategy that displays the data generated of the different data generators of the simulation.
 */
public interface OutputStrategy {
    /**
     * Outputs data for a patient using the Output Strategy created
     * @param patientId The identification number for a patient, this data is generated in PatientDataGenerator
     * @param timestamp Time stamp of the data
     * @param label Data label of the generator
     * @param data The data produced by the generator
     */
    void output(int patientId, long timestamp, String label, String data);
}
