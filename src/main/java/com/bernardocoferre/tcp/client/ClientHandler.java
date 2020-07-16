package com.bernardocoferre.tcp.client;

import com.bernardocoferre.tcp.model.Message;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Message response = (Message) message;

        if (response.getReference() == null)
            throw new IllegalArgumentException("Unexpected message received - messageReference cannot be null!");

        String hex = response.getReference().get(0).getData().compileRawHex();
        LOGGER.info(new Str.Builder("Message response successfully received: [{}]").build(hex));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        LOGGER.error(new Str.Builder("Error while processing response message: {}").build(cause.getLocalizedMessage()), cause);
    }

}
