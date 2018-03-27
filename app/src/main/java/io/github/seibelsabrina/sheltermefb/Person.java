package io.github.seibelsabrina.sheltermefb;

import java.io.Serializable;

/**
 * Created by seibelsabrina on 2/24/18.
 */

public class Person implements Serializable {
    String name;
    String username;
    String password;
    String mode;
    String reservation;
    Integer numReservations;


    public Person() {

    }

    public Person(String name, String username, String password, String mode, String reservation, Integer numReservations) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mode = mode;
        this.reservation = reservation;
        this.numReservations = numReservations;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMode() {
        return mode;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public Integer getNumReservations() {
        return numReservations;
    }

    public void setNumReservations(Integer numReservations) {
        this.numReservations = numReservations;
    }
}
