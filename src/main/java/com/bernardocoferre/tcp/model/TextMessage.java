package com.bernardocoferre.tcp.model;

import javax.persistence.*;

@Entity
@Table(name = "text_messages")
public class TextMessage {

    @Id
    @Column
    @SequenceGenerator(name = "sq_text_messages", sequenceName = "sq_text_messages")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_text_messages")
    private Integer id;

    @Column
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
