package com.momentum.coaching.model;

import androidx.annotation.Keep;

@Keep

public class ProverbsModel {
    private String question;
    private String ansPart;

    public ProverbsModel() {

    }

    public ProverbsModel(String question, String ansPart) {
        this.question = question;
        this.ansPart = ansPart;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnsPart() {
        return ansPart;
    }

    public void setAnsPart(String ansPart) {
        this.ansPart = ansPart;
    }
}
