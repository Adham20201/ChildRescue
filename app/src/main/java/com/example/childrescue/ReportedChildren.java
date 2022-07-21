package com.example.childrescue;

public class ReportedChildren {
    String name,time,place, person, photo, behaviour, additionalInformation;

    public ReportedChildren() {

    }

    public ReportedChildren(String name, String time, String place, String person, String photo, String behaviour, String additionalInformation) {
        this.name = name;
        this.time = time;
        this.place = place;
        this.person = person;
        this.photo = photo;
        this.behaviour = behaviour;
        this.additionalInformation = additionalInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
