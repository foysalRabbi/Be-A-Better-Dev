package com.momentum.coaching.notification;


import androidx.annotation.Keep;

@Keep
public class NotificationModel {
    private String notificationHeadLine;
    private String notificationDescription;
    private String time;

    public NotificationModel( )
    {

    }

    public NotificationModel(String notificationHeadLine, String notificationDescription, String time) {
        this.notificationHeadLine = notificationHeadLine;
        this.notificationDescription = notificationDescription;
        this.time=time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotificationHeadLine() {
        return notificationHeadLine;
    }

    public void setNotificationHeadLine(String notificationHeadLine) {
        this.notificationHeadLine = notificationHeadLine;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }
}
