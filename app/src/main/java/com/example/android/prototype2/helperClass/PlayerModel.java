package com.example.android.prototype2.helperClass;

//This will store the data for the players
public class PlayerModel {

    //Declare variables of the information to store in the database
    private String name;
    private String email;
    private String phoneNo;
    private String emergencyContact;
    private String contactNumber;
    private String dob;
    private String password;
    private String uid;



    //Empty constructor
    public PlayerModel() {
    }

    //Constructor
    public PlayerModel(String name, String email, String phoneNo, String dob, String emergencyContact,
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

}




