package com.example.websockets;

import org.springframework.messaging.simp.stomp.StompSessionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;

public class MyStompSessionHandler implements StompSessionHandler {
    private final Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//        session = session.
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/messages", this);
        logger.info("Subscribed to /topic/messages");
        session.send("app/message", "Hi! from STOMP client");
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String msg = payload.toString();
        logger.info("Received: "+msg);
    }
}
