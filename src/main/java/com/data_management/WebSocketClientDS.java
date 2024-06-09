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

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to Server");
    }


    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Server connection ended");

    }

    @Override
    public void onError(Exception e) {
        System.out.println("Error on Websocket");
        e.printStackTrace();

    }

    @Override
    public void onMessage(String message) {
        try {
            parseWebSocketData(message);
        } catch (Exception e) {
            throw new RuntimeException("Error Parsing: " + e.getMessage());
        }
    }

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
