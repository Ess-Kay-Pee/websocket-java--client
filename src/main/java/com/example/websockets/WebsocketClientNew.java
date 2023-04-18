package com.example.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class WebsocketClientNew {
    public static void main(String[] args) {
        String url = "http://localhost:6565/ws-demo";

        List<Transport> transports = new ArrayList<>();
        transports.add(
                new WebSocketTransport(
                        new StandardWebSocketClient()
                )
        );

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.doHandshake(new MyWebSocketHandler(), url);
    }
}

class MyWebSocketHandler extends AbstractWebSocketHandler{

    private Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established with "+session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Received message: "+message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Transport error: "+exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed. Reason: "+status.getReason());
    }
}
