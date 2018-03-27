package io.github.seibelsabrina.sheltermefb;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by seibelsabrina on 2/28/18.
 */

public class Shelter implements Serializable {
    String shelterName;
    String capacity;
    String restrictions;
    Float longitude;
    Float latitude;
    String address;
    String notes;
    String phoneNumber;
    Integer vacancies;

    public Shelter() {

    }

    public Shelter(String shelterName, String capacity, String restrictions,
                   float longitude, float latitude, String address, String notes,
                   String phoneNumber) {
        this.shelterName = shelterName;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phoneNumber = phoneNumber;
        this.vacancies = 0;


        String[] cap = capacity.split(",");

        for (int i = 0; i < cap.length; i++) {
            if (cap[i].length() != 0) {
                if (cap[i].contains("families") || cap[i].contains("family")) {
                    String[] famCap = cap[i].trim().split(" ");
                    this.vacancies += Integer.parseInt(famCap[0].trim());
                } else if (cap[i].contains("single")) {
                    String[] singleCap = cap[i].trim().split(" ");
                    this.vacancies += Integer.parseInt(singleCap[0].trim());
                } else if (cap[i].contains("apartment")) {
                    String[] aptCap = cap[i].trim().split(" ");
                    this.vacancies += Integer.parseInt(aptCap[0].trim());
                } else {
                    this.vacancies += Integer.parseInt(cap[i].trim());
                }
            }
        }
    }


    public String getShelterName() {
        return shelterName;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getNotes() {
        return notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getVacancies() {
        return vacancies;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }
}
