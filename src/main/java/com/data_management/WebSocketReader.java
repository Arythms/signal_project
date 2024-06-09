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

    /**
     * Conects the reacer to a Websocket
     * @param serverAddress String indicating the adress of the Websocket input
     * @throws IOException if the address is not valid
     */

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

    /**
     * Disconnects
     */

    @Override
    public void disconnect() {
        if (client != null && client.isOpen()) {
            client.close();
        }
    }


        /**
         * Class to test manually the outputs
        public static void main(String[] args) throws IOException, URISyntaxException {
            String invalidAddress = "WWWW";
            String valid ="ws://localhost:8080";
            WebSocketOutputStrategy server = new WebSocketOutputStrategy(8080);
            DataStorage dataStorage = DataStorage.getInstance();
            WebSocketReader webSocket = new WebSocketReader(dataStorage);
            webSocket.connectToWebSocketServer(valid);

        }
         */


}
