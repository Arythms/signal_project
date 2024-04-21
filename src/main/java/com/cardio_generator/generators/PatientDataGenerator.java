package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient data, this interface will create objects that will be generating the data for the simulator.
 */

public interface PatientDataGenerator {
    /**
     * Generates data for a specific patient and outputs it using the corresponding output Strategy for that data
     * @param patientId the patient id number that was generated
     * @param outputStrategy The strategy used to output the specific type of data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
