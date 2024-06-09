package com.data_management;

import com.alerts.AlertGenerator;
import com.cardio_generator.outputs.WebSocketOutputStrategy;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketClientDSTest {
    private WebSocketClientDS client;
    private static DataStorage dataStorage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() throws URISyntaxException, InterruptedException {
        dataStorage = new DataStorage();
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);
        client = new WebSocketClientDS(new URI("ws://localhost:8080"), dataStorage);
        client.connect();
        // Add a delay to ensure the connection is established
        Thread.sleep(1000); // Adjust delay as needed
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void connectionTest() {
        assertTrue(client.isOpen(), "Client should be connected to the server");
    }



    @Test
    void testInvalidMessageFormat() {
        String invalidMessage = "1,1,1,1,1"; // Invalid message format with too many parts

        // Use assertThrows to verify that a RuntimeException is thrown when processing an invalid message format
        assertThrows(RuntimeException.class, () -> {
            client.onMessage(invalidMessage);
        });
    }



    @Test
    public void testIntegration() throws InterruptedException {
        client.onMessage("8,88,Saturation,1627842123000"); // Removed extra spaces
        client.onMessage("8,88,SystolicPressure,1627842223000"); // Removed extra spaces

        Thread.sleep(1000); // Adjust delay as needed to allow processing

        List<PatientRecord> records = dataStorage.getRecords(8, 1627842123000L, 1627842223000L);
        assertEquals(2, records.size(), "Should store 2 records");
        assertEquals(88.0, records.get(0).getMeasurementValue(), "First record value should be 88.0");
        assertEquals(88.0, records.get(1).getMeasurementValue(), "Second record value should be 88.0");

        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        for (Patient patient : dataStorage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
        assertTrue(outContent.toString().contains("Alert"), "Should print alert message");
    }
}
