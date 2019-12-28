package org.fifthgen.colouringbooks.model.bean;

import java.util.Date;

public class MoodBean {

    private Date date;
    private String mood;
    private String notes;

    public MoodBean() {
    }

    public MoodBean(Date date, String mood, String notes) {
        this.date = date;
        this.mood = mood;
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
