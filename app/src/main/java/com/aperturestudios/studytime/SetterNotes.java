package com.aperturestudios.studytime;

public class SetterNotes {

    public SetterNotes() {
    }

    private String key, phoneNumber, studentName, localUrl, onlineUrl, subject, chapter, topic;
    private long timestamp;

    public SetterNotes(String key, String phoneNumber, String studentName, String localUrl,
                       String onlineUrl, String subject, String chapter, String topic, long timestamp) {
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.studentName = studentName;
        this.localUrl = localUrl;
        this.onlineUrl = onlineUrl;
        this.subject = subject;
        this.chapter = chapter;
        this.topic = topic;
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
