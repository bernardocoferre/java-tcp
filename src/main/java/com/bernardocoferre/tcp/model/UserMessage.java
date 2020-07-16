package com.bernardocoferre.tcp.model;

import javax.persistence.*;

@Entity
@Table(name = "user_messages")
public class UserMessage {

    @Id
    @Column
    @SequenceGenerator(name = "sq_user_messages", sequenceName = "sq_user_messages")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_user_messages")
    private Integer id;

    @Column
    private Integer age;

    @Column
    private Integer weight;

    @Column
    private Integer height;

    @Column
    private Integer nameLength;

    @Column
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getNameLength() {
        return nameLength;
    }

    public void setNameLength(Integer nameLength) {
        this.nameLength = nameLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
