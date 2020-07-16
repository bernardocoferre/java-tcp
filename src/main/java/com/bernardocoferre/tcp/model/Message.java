package com.bernardocoferre.tcp.model;

import com.bernardocoferre.tcp.concerns.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column
    @SequenceGenerator(name = "sq_messages", sequenceName = "sq_messages")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_messages")
    private Integer id;

    @Column(nullable = false)
    private Long sessionId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "message")
    private List<MessageReference> reference;

    @Column
    private String ipAddress;

    @Column
    private Date receivedAt;

    @Column(nullable = false)
    private Date sentAt = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public List<MessageReference> getReference() {
        return reference;
    }

    public void setReference(List<MessageReference> reference) {
        this.reference = reference;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + " [", "]")
                .add("id=" + id)
                .add("sessionId=" + sessionId)
                .add("type=" + type)
                .add("reference=" + reference)
                .add("ipAddress=" + ipAddress + "")
                .add("receivedAt=" + receivedAt)
                .add("sentAt=" + sentAt)
                .toString();
    }

}
