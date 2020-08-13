package com.example.android.prototype2.Model;

//This will store the data for the players
public class UserHelperClass {

    //Declare variables of the information to store in the database
    String playerID, name, email, phoneNo, emergencyContact, contactNumber, dob, password, uid;


    //Empty constructor
    public UserHelperClass() {
    }

    //Constructor
    public UserHelperClass( String name, String email, String phoneNo, String dob, String emergencyContact,
                           String contactNumber, String password, String uid) {

        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.emergencyContact = emergencyContact;
        this.contactNumber = contactNumber;
        this.password = password;
        this.uid = uid;
    }

    //Getters and setters
    public String getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}




