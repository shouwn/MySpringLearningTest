package com.banchan.DTO;

import com.banchan.domain.question.DetailType;

import javax.persistence.*;

@Entity
public class QuestionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private char type;
    private String contenxt;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Questions question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getContenxt() {
        return contenxt;
    }

    public void setContenxt(String contenxt) {
        this.contenxt = contenxt;
    }
}
