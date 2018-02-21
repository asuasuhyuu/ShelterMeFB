package cs2340.gatech.edu.lab3.model;

/**
 * Created by Tanya Churaman on 2/4/18.
 */

public enum ClassStanding {
    FRESHMAN("FR"),
    SOPHOMORE("SO"),
    JUNIOR("JR"),
    SENIOR("SR");

    private final String classstanding;

    ClassStanding(String classStanding2) {
        this.classstanding = classStanding2;
    }

    public String toString() {return this.classstanding;}
}
