package com.data_management;

import java.io.IOException;
import java.net.URISyntaxException;

public interface DataReader {
    void connectToWebSocketServer(String serverAddress) throws IOException, URISyntaxException;
    void receiveRealTimeData();
    void disconnect();
}