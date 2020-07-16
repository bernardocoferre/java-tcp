package com.bernardocoferre.tcp.model;

import com.bernardocoferre.tcp.concerns.MessageDirection;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "message_reference")
public class MessageReference {

    @Id
    @Column
    @SequenceGenerator(name = "sq_message_reference", sequenceName = "sq_message_reference")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_message_reference")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    @JsonIgnore
    private Message message;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_data_id")
    private MessageData data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "text_message_id")
    private TextMessage textMessage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_message_id")
    private UserMessage userMessage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_message_id")
    private DateMessage dateMessage;

    @Column(nullable = false)
    private String rawHex;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }

    public TextMessage getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(TextMessage textMessage) {
        this.textMessage = textMessage;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public DateMessage getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(DateMessage dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getRawHex() {
        return rawHex;
    }

    public void setRawHex(String rawHex) {
        this.rawHex = rawHex;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public void setDirection(MessageDirection direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MessageReference.class.getSimpleName() + " [", "]")
                .add("id=" + id)
                .add("rawHex=" + rawHex + "")
                .add("direction=" + direction)
                .toString();
    }

}
