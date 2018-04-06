package io.github.seibelsabrina.sheltermefb;

/**
 * Created by seibelsabrina on 4/3/18.
 */


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DataManager {
    List<DataElement> theData;

    DataManager() {
        theData = new ArrayList<>();
    }

    DataManager(ArrayList<Shelter> sl) {
        theData = new ArrayList<>();
        for (Shelter shelter : sl) {
            if (shelter != null) {
                makeSomeData(shelter);
            }
        }
    }

    private void makeSomeData(Shelter shelter) {
       //addReport(new DataElement("Coke Zero", "Sam's Deli", new Location(33.749, -84.388)));
      // addReport(new DataElement("Pepsi", "Grandma Garage", new Location(33.8, -84.5)));
       addReport(new DataElement(shelter.getShelterName(), shelter.getRestrictions(),
                        new Location(shelter.getLatitude(), shelter.getLongitude())));
    }

    void addReport(DataElement de) {
        theData.add(de);
    }


    List<DataElement> getData() { return theData; }



}
