package io.github.seibelsabrina.sheltermefb;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Shelter> searchGender(List<Shelter> list, String gender) {
        List<Shelter> toReturn = new ArrayList<>();
        String gender2;
        if (gender.equals("Female")) {
            gender2 = "Women";
        } else {
            gender2 = "Men";
        }
        for (Shelter shelter : list) {
            // shelter.restrictions
            if (shelter.getRestrictions().contains(gender) ||
                    shelter.getRestrictions().contains(gender2)) {
                toReturn.add(shelter);
            }
        }
        return toReturn;
    }

    public static List<Shelter> searchAgeRange(List<Shelter> list, String age) {
        List<Shelter> toReturn = new ArrayList<>();
        for (Shelter shelter: list) {
            if (shelter.getRestrictions().contains(age)) {
                toReturn.add(shelter);
            }
        }
        return toReturn;
    }

    public static List<Shelter> searchShelterName(List<Shelter> list, String name) {
        List<Shelter> toReturn = new ArrayList<>();
        for (Shelter shelter: list) {
            if (shelter.getShelterName().contains(name)) {
                toReturn.add(shelter);
            }
        }
        return toReturn;
    }

    public static List<Shelter> searchCharacteristic(List<Shelter> list, String characteristic) {
        List<Shelter> toReturn = new ArrayList<>();
        for (Shelter shelter: list) {
            if (shelter.getNotes().contains(characteristic)) {
                toReturn.add(shelter);
            }
        }
        return toReturn;
    }

    public static List<Shelter> searchAddress(List<Shelter> list, String address) {
        List<Shelter> toReturn = new ArrayList<>();
        for (Shelter shelter: list) {
            if (shelter.getAddress().contains(address)) {
                toReturn.add(shelter);
            }
        }
        return toReturn;
    }
}
