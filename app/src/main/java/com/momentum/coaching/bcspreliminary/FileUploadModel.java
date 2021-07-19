package com.momentum.coaching.bcspreliminary;

import androidx.annotation.Keep;

@Keep
public class FileUploadModel {

    private String bcsSubjectName;
    private String bcsSubjectURL;

    public FileUploadModel()
    {

    }

    public FileUploadModel(String bcsSubjectName, String bcsSubjectURL) {
        this.bcsSubjectName = bcsSubjectName;
        this.bcsSubjectURL = bcsSubjectURL;
    }

    public String getBcsSubjectName() {
        return bcsSubjectName;
    }

    public void setBcsSubjectName(String bcsSubjectName) {
        this.bcsSubjectName = bcsSubjectName;
    }

    public String getBcsSubjectURL() {
        return bcsSubjectURL;
    }

    public void setBcsSubjectURL(String bcsSubjectURL) {
        this.bcsSubjectURL = bcsSubjectURL;
    }
}
