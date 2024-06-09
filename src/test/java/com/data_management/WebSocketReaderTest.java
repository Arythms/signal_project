package com.data_management;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketReaderTest {

    @Test
    void connectToValidAddress() throws IOException, URISyntaxException {
        String address = "ws://localhost:8080";
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);
        DataStorage dataStorage = DataStorage.getInstance();
        WebSocketReader webSocket = new WebSocketReader(dataStorage);
        WebSocketClientDS client = new WebSocketClientDS(new URI(address), dataStorage);
        webSocket.connectToWebSocketServer(address);
    }

    @Test
    void connectToInvalidAddress() {
        String invalidAddress = "notValid!";
        WebSocketReader webSocket = new WebSocketReader(DataStorage.getInstance());
        // Use assertThrows to verify that URISyntaxException is thrown when connecting to an invalid address
        assertThrows(IOException.class, () -> {
            webSocket.connectToWebSocketServer(invalidAddress);
        });

    }
}