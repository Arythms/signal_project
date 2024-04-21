package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient data.
 */

public interface PatientDataGenerator {
    /**
     * Generates data for a specific Patient following the output strategy
     * @param patientId the patient id number
     * @param outputStrategy The strategy used to output the patient data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
