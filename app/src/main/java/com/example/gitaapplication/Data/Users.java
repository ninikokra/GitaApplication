package com.example.gitaapplication.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Users implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fullName")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "dateBirth")
    private String dateBirth;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "mobNum")
    private String mobNum;

    @ColumnInfo(name = "gender")
    private  String gender;

    @ColumnInfo(name = "career")
    private  String career;

    public String getCareer() {
        return career;
    }

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte [] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    /*    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
