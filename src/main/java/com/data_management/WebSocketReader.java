package com.data_management;

import com.cardio_generator.outputs.WebSocketOutputStrategy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketReader implements DataReader {
    private WebSocketClientDS client;
    private DataStorage storage;

    public WebSocketReader(DataStorage storage){
        this.storage = storage;

    }

    @Override
    public void connectToWebSocketServer(String serverAddress) throws IOException {
        try {
            URI uri = new URI(serverAddress);
            if (uri.getScheme() == null) {
                throw new URISyntaxException(serverAddress, "Missing URI scheme");
            }
            client = new WebSocketClientDS(uri, storage);
            client.connect();
        } catch (URISyntaxException e) {
            throw new IOException("Invalid WebSocket server address: " + serverAddress, e);
        }
    }
    @Override
    public void receiveRealTimeData() {
        //handled by WebClientDS class
    }

    @Override
    public void disconnect() {
        if (client != null && client.isOpen()) {
            client.close();
        }
    }



        public static void main(String[] args) throws IOException, URISyntaxException {
            String invalidAddress = "WWWW";
            String valid ="ws://localhost:8080";
            WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);
            WebSocketReader webSocket = new WebSocketReader(new DataStorage());
            webSocket.connectToWebSocketServer(valid);

        }


}
