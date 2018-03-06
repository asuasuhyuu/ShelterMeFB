package io.github.seibelsabrina.sheltermefb;

/**
 * Created by seibelsabrina on 2/28/18.
 */

public class Shelter {
    String shelterName;
    String capacity;
    String restrictions;
    Float longitude;
    Float latitude;
    String address;
    String notes;
    String phoneNumber;
    Integer uniqueKey;

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
    }

    public Integer getUniqueKey() {
        return uniqueKey;
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
}
