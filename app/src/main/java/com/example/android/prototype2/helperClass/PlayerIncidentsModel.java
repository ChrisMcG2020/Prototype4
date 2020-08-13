package com.example.android.prototype2.helperClass;

//Store data for the incidents

public class PlayerIncidentsModel {
    //Declare variables of the information to store in the database
    private String uid;
    private String name;
    private String coachName;
    private String Reports;
    private String Red_FLag_Test;
    private String Observable_Signs_Test;
    private String Memory_Question;
    private String symptoms;
    private String playerEmail;
    private String Date;


    //Empty constructor
    public PlayerIncidentsModel() {
    }

    //Constructor
    public PlayerIncidentsModel(String uid, String name, String coachName, String reports, String red_FLag_Test, String observable_Signs_Test,
                                String memory_Question, String symptoms, String email, String Date) {
        this.uid = uid;
        this.name = name;
        this.coachName = coachName;
        Reports = reports;
        Red_FLag_Test = red_FLag_Test;
        Observable_Signs_Test = observable_Signs_Test;
        Memory_Question = memory_Question;
        this.symptoms = symptoms;
        this.playerEmail = email;
        this.Date = Date;
    }


    //Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getReports() {
        return Reports;
    }

    public void setReports(String reports) {
        Reports = reports;
    }

    public String getRed_FLag_Test() {
        return Red_FLag_Test;
    }

    public void setRed_FLag_Test(String red_FLag_Test) {
        Red_FLag_Test = red_FLag_Test;
    }

    public String getObservable_Signs_Test() {
        return Observable_Signs_Test;
    }

    public void setObservable_Signs_Test(String observable_Signs_Test) {
        Observable_Signs_Test = observable_Signs_Test;
    }

    public String getMemory_Question() {
        return Memory_Question;
    }

    public void setMemory_Question(String memory_Question) {
        Memory_Question = memory_Question;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayerEmail(String playerEmail) {
        this.playerEmail = playerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}