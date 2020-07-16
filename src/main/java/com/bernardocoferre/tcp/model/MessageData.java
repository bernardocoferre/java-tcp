package com.bernardocoferre.tcp.model;

import com.bernardocoferre.tcp.concerns.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "message_data")
public class MessageData {

    @Id
    @Column
    @SequenceGenerator(name = "sq_message_data", sequenceName = "sq_message_data")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_message_data")
    private Integer id;

    @OneToOne(mappedBy = "data", cascade = CascadeType.ALL)
    @JsonIgnore
    private MessageReference messageRef;

    @Column(nullable = false)
    private String init;

    @Column(nullable = false)
    private String bytes;

    @Column(nullable = false)
    private String frame;

    @Column
    private String data;

    @Column(nullable = false)
    private String crc;

    @Column(nullable = false)
    private String end;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MessageReference getMessageRef() {
        return messageRef;
    }

    public void setMessageRef(MessageReference messageRef) {
        this.messageRef = messageRef;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String compileRawHex() {
        StringBuilder hex = new StringBuilder();
        List<String> fields = Arrays.asList(this.init, this.bytes, this.frame, this.data, this.crc, this.end);

        for (String field : fields)
            if (StringUtils.isNotBlank(field))
                hex.append(field).append(" ");

        return hex.deleteCharAt(hex.length() - 1).toString();
    }

    @Override
    public String toString() {
        return this.compileRawHex();
    }

}
