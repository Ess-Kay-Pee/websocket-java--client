package com.example.websockets;

import jakarta.websocket.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@ClientEndpoint
public class WebSocketClient {
    private final Logger log = LoggerFactory.getLogger(WebSocketClient.class);
    private static String URL = "ws://localhost:6565/ws-demo";
    private Session session;

   /* @OnMessage
    public void onMessage(String message){
        log.info("Received: "+message);
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public void connect() throws URISyntaxException, DeploymentException, IOException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, new URI(URL));

    }

    public void close() throws IOException {
        session.close();
    }*/

    public static void main(String[] args) throws DeploymentException, URISyntaxException, IOException {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(URL, sessionHandler);

        new Scanner(System.in).nextLine(); // Don't close immediately.

       /*WebSocketClient client = new WebSocketClient();
       client.connect();
       client.sendMessage("Hi! from client");
       client.close();*/
    }
}
