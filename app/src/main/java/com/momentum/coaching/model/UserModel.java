package com.momentum.coaching.model;

import androidx.annotation.Keep;

@Keep
public class UserModel {
    public String displayName;
    public String email;
    public long createdAt;
    public int physicMarksB = 0;
    public int physicMarksI = 0;
    public int physicMarksE = 0;
    public int chemistryMarksB = 0;
    public int chemistryMarksI = 0;
    public int chemistryMarksE = 0;
    public int englishMarksB = 0;
    public int englishMarksI = 0;
    public int englishMarksE = 0;
    public int finalMarks = 0;
    public String mRecipientId;

    public UserModel() {
    }

    public UserModel(String displayName, String email, long createdAt, int physicMarksB, int physicMarksI, int physicMarksE, int chemistryMarksB, int chemistryMarksI, int chemistryMarksE, int englishMarksB, int englishMarksI, int englishMarksE, int finalMarks) {
        this.displayName = displayName;
        this.email = email;
        this.createdAt = createdAt;
        this.physicMarksB = physicMarksB;
        this.physicMarksI = physicMarksI;
        this.physicMarksE = physicMarksE;
        this.chemistryMarksB = chemistryMarksB;
        this.chemistryMarksI = chemistryMarksI;
        this.chemistryMarksE = chemistryMarksE;
        this.englishMarksB = englishMarksB;
        this.englishMarksI = englishMarksI;
        this.englishMarksE = englishMarksE;
        this.finalMarks = finalMarks;
    }

    public String getmRecipientId() {
        return mRecipientId;
    }

    public void setmRecipientId(String mRecipientId) {
        this.mRecipientId = mRecipientId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getPhysicMarksB() {
        return physicMarksB;
    }

    public void setPhysicMarksB(int physicMarksB) {
        this.physicMarksB = physicMarksB;
    }

    public int getPhysicMarksI() {
        return physicMarksI;
    }

    public void setPhysicMarksI(int physicMarksI) {
        this.physicMarksI = physicMarksI;
    }

    public int getPhysicMarksE() {
        return physicMarksE;
    }

    public void setPhysicMarksE(int physicMarksE) {
        this.physicMarksE = physicMarksE;
    }

    public int getChemistryMarksB() {
        return chemistryMarksB;
    }

    public void setChemistryMarksB(int chemistryMarksB) {
        this.chemistryMarksB = chemistryMarksB;
    }

    public int getChemistryMarksI() {
        return chemistryMarksI;
    }

    public void setChemistryMarksI(int chemistryMarksI) {
        this.chemistryMarksI = chemistryMarksI;
    }

    public int getChemistryMarksE() {
        return chemistryMarksE;
    }

    public void setChemistryMarksE(int chemistryMarksE) {
        this.chemistryMarksE = chemistryMarksE;
    }

    public int getEnglishMarksB() {
        return englishMarksB;
    }

    public void setEnglishMarksB(int englishMarksB) {
        this.englishMarksB = englishMarksB;
    }

    public int getEnglishMarksI() {
        return englishMarksI;
    }

    public void setEnglishMarksI(int englishMarksI) {
        this.englishMarksI = englishMarksI;
    }

    public int getEnglishMarksE() {
        return englishMarksE;
    }

    public void setEnglishMarksE(int englishMarksE) {
        this.englishMarksE = englishMarksE;
    }

    public int getFinalMarks() {
        return finalMarks;
    }

    public void setFinalMarks(int finalMarks) {
        this.finalMarks = finalMarks;
    }
}