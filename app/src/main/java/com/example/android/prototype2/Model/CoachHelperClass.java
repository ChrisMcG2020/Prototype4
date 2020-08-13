package com.example.android.prototype2.Model;

//Class to store the data for the Coaches
public class CoachHelperClass {
    //Declare variables of the information to store in the database
    String coachID, coachName, coachEmail, coachPhoneNumber, coachPassword, teamCoached, coachUID;

    //Empty constructor
    public CoachHelperClass() {

    }


    public void setCoachUID(String coachUID) {
        this.coachUID = coachUID;
    }

    //Constructor
    public CoachHelperClass(String coachID, String coachName, String coachEmail, String coachPhoneNumber, String teamCoached, String coachPassword, String coachUID) {
        this.coachID = coachID;
        this.coachName = coachName;
        this.coachEmail = coachEmail;
        this.coachPhoneNumber = coachPhoneNumber;
        this.teamCoached = teamCoached;
        this.coachPassword = coachPassword;
        this.coachUID=coachUID;
    }


    //Getters and setters
    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachEmail() {
        return coachEmail;
    }

    public void setCoachEmail(String coachEmail) {
        this.coachEmail = coachEmail;
    }

    public String getCoachPhoneNumber() {
        return coachPhoneNumber;
    }

    public void setCoachPhoneNumber(String coachPhoneNumber) {
        this.coachPhoneNumber = coachPhoneNumber;
    }

    public String getCoachPassword() {
        return coachPassword;
    }

    public void setCoachPassword(String coachPassword) {
        this.coachPassword = coachPassword;
    }

    public String getTeamCoached() {
        return teamCoached;
    }

    public String getCoachID() {
        return coachID;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public void setTeamCoached(String teamCoached) {
        this.teamCoached = teamCoached;
    }

    public String getCoachUID() {
        return coachUID;
    }

    public void setUid(String coachUID) { this.coachUID=coachUID;}
    }




