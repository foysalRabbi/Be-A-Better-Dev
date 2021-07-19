package com.momentum.coaching.model;

import androidx.annotation.Keep;

@Keep
public class QuestionEnglish
{
    private int ID4;
    private String QUESTION4;
    private String OPTA4;
    private String OPTB4;
    private String OPTC4;
    private String OPTD4;
    private String ANSWER4;
    private String CATEGORY4;
    public QuestionEnglish()
    {
        ID4 = 0;
        QUESTION4 = "";
        OPTA4 = "";
        OPTB4 = "";
        OPTC4 = "";
        OPTD4 = "";
        CATEGORY4="";
    }
    public QuestionEnglish(String qUESTION, String oPTA, String oPTB, String oPTC, String oPTD,
                           String aNSWER, String cATEGORY) {

        QUESTION4 = qUESTION;
        OPTA4 = oPTA;
        OPTB4 = oPTB;
        OPTC4 = oPTC;
        OPTD4 = oPTD;
        ANSWER4 = aNSWER;
        CATEGORY4=cATEGORY;
    }

    public int getID4() {
        return ID4;
    }

    public void setID4(int ID) {
        this.ID4 = ID;
    }

    public String getQUESTION4() {
        return QUESTION4;
    }

    public void setQUESTION4(String QUESTION) {
        this.QUESTION4 = QUESTION;
    }

    public String getOPTA4() {
        return OPTA4;
    }

    public void setOPTA4(String OPTA) {
        this.OPTA4 = OPTA;
    }

    public String getOPTB4() {
        return OPTB4;
    }

    public void setOPTB4(String OPTB) {
        this.OPTB4 = OPTB;
    }

    public String getOPTC4() {
        return OPTC4;
    }

    public void setOPTC4(String OPTC) {
        this.OPTC4 = OPTC;
    }

    public String getOPTD4() {
        return OPTD4;
    }

    public void setOPTD4(String OPTD) {
        this.OPTD4 = OPTD;
    }

    public String getANSWER4() {
        return ANSWER4;
    }

    public void setANSWER4(String ANSWER) {
        this.ANSWER4 = ANSWER;
    }

    public String getCATEGORY4() {
        return CATEGORY4;
    }

    public void setCATEGORY4(String CATEGORY) {
        this.CATEGORY4 = CATEGORY;
    }
}

