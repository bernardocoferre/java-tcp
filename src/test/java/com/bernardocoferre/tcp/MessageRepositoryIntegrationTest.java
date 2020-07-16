package com.bernardocoferre.tcp;

import com.bernardocoferre.tcp.concerns.MessageDirection;
import com.bernardocoferre.tcp.concerns.MessageType;
import com.bernardocoferre.tcp.model.*;
import com.bernardocoferre.tcp.repositories.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryIntegrationTest {

    @Autowired
    private MessageRepository repository;

    @Test
    public void whenCreateTextMessage_thenMessageIdIsNotNull() throws Exception {
        Message message = new Message();
        message.setSessionId(1L);
        message.setType(MessageType.TEXT);
        message.setIpAddress("127.0.0.1");
        message.setReceivedAt(new Date());
        message.setSentAt(new Date());

        MessageData receivedData = new MessageData();
        receivedData.setInit("0A");
        receivedData.setBytes("10");
        receivedData.setFrame("A1");
        receivedData.setData("48 65 6C 6C 6F 20 57 6F 72 6C 64");
        receivedData.setCrc("DC");
        receivedData.setEnd("0D");

        TextMessage receivedTextMessage = new TextMessage();
        receivedTextMessage.setText("Hello World");

        MessageReference receivedMessageReference = new MessageReference();
        receivedMessageReference.setData(receivedData);
        receivedMessageReference.setDirection(MessageDirection.RECEIVED);
        receivedMessageReference.setRawHex("0A 10 A1 48 65 6C 6C 6F 20 57 6F 72 6C 64 DC 0D");
        receivedMessageReference.setTextMessage(receivedTextMessage);
        receivedMessageReference.setMessage(message);

        MessageData sentData = new MessageData();
        sentData.setInit("0A");
        sentData.setBytes("05");
        sentData.setFrame("A0");
        sentData.setData(null);
        sentData.setCrc("28");
        sentData.setEnd("0D");

        TextMessage sentTextMessage = new TextMessage();
        sentTextMessage.setText(null);

        MessageReference sentMessageReference = new MessageReference();
        sentMessageReference.setData(sentData);
        sentMessageReference.setDirection(MessageDirection.RECEIVED);
        sentMessageReference.setRawHex("0A 05 A0 28 0D");
        sentMessageReference.setTextMessage(sentTextMessage);
        sentMessageReference.setMessage(message);

        message.setReference(Arrays.asList(receivedMessageReference, sentMessageReference));

        this.repository.save(message);

        assertThat(message.getId())
                .isNotNull();
    }

}
