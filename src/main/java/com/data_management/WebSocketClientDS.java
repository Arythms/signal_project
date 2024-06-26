package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientDS extends WebSocketClient {
    private DataStorage storage;
    public WebSocketClientDS(URI serverUri, DataStorage storage) throws URISyntaxException {
        super(serverUri);
        this.storage = storage;
    }

    /**
     * Indicated that there has been a connection to the server
     * @param serverHandshake
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to Server");
    }

    /**
     * Indicated that the server connection has ended
     * @param i
     * @param s
     * @param b
     */

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Server connection ended");

    }

    /**
     * Indicates there has been an error
     * @param e
     */

    @Override
    public void onError(Exception e) {
        System.out.println("Error on Websocket");
        e.printStackTrace();

    }

    /**
     * Send the messague to the DataStorage class
     * @param message String indicating the Data for the PatientRecord
     */

    @Override
    public void onMessage(String message) {
        try {
            parseWebSocketData(message);
        } catch (Exception e) {
            throw new RuntimeException("Error Parsing: " + e.getMessage());
        }
    }

    /**
     * Given a String that contains the information of a Patient Record It parses the data and adds it into the storage.
     * @param message  contains the information for the Patient Record
     * @throws Exception if the format of the string is not the one accepted
     */

    public void parseWebSocketData(String message) throws Exception {
        // Parse the message into respective data types
        String[] parts = message.split(",");
        if (parts.length != 4) {
            throw new Exception("Invalid message format: " + message);
        }

        try {
            int patientId = Integer.parseInt(parts[0]);
            double measurementValue = Double.parseDouble(parts[1]);
            String recordType = parts[2];
            long timestamp = Long.parseLong(parts[3]);
            storage.addPatientData(patientId, measurementValue, recordType, timestamp);
        } catch (NumberFormatException e) {
            throw new Exception("Error parsing message: " + message, e);
        }
    }

}
