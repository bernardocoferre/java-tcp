package com.bernardocoferre.tcp.server;

import com.bernardocoferre.tcp.concerns.MessageType;
import com.bernardocoferre.tcp.model.Message;
import com.bernardocoferre.tcp.model.MessageReference;
import com.bernardocoferre.tcp.repositories.MessageRepository;
import com.bernardocoferre.tcp.support.Str;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Autowired
    private MessageRepository repository;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Message request = (Message) message;

        if (request.getReference() == null)
            throw new IllegalArgumentException("Unexpected message received - messageReference cannot be null!");

        String hex = request.getReference().get(0).getData().compileRawHex();
        LOGGER.info(new Str.Builder("Message request successfully received: [{}]").build(hex));

        this.repository.save(request);
        LOGGER.info(new Str.Builder("Message saved with: ID #{}").build(request.getId()));

        ResponseBuilder responseBuilder = this.findBuilder(request.getType());
        MessageReference responseReference = responseBuilder.createResponse(request);
        request.getReference().add(responseReference);

        this.repository.save(request);

        session.write(responseReference.getData());
    }

    public ResponseBuilder findBuilder(MessageType type) {
        switch (type) {
            case TEXT:
            case USER:
                return new AckResponseBuilder();

            case DATE:
                return new DateResponseBuilder();

            default:
                LOGGER.warn(new Str.Builder("Response builder not mapped for type: {}").build(type));
                LOGGER.warn(new Str.Builder("Default {} will be used!").build(AckResponseBuilder.class.getName()));
                return new AckResponseBuilder();
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        LOGGER.error(new Str.Builder("Error while processing message: {}").build(cause.getLocalizedMessage()), cause);
    }

}
