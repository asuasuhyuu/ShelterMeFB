package io.github.seibelsabrina.sheltermefb;

/**
 * Created by seibelsabrina on 4/3/18.
 */
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class DataServiceFacade {
    private static DataServiceFacade INSTANCE = new DataServiceFacade();
    public static DataServiceFacade getInstance() { return INSTANCE; }

    private DataManager theData;

    private DataElement theLastAddedElement;


    private DataServiceFacade() {
        theData = new DataManager();
    }

    public void findShelters(ArrayList<Shelter> sl) {
        theData = new DataManager(sl);
    }
    /**
     * get a list of all the data
     * @return  the full list of data
     */
    public List<DataElement> getData() { return theData.getData();}

    /**
     * Add a new data element to the model
     * @param name   the name of the element
     * @param desc   textual description of the element
     * @param loc    location of the element
     */
    public void addDataElement(String name, String desc, Location loc) {
        DataElement de = new DataElement(name, desc, loc);
        theData.addReport(de);
        theLastAddedElement = de;
    }

    /**
     * Return the last element added.  This method is mainly to support UI
     * @return the last element added to the model.
     */
    public DataElement getLastElementAdded() {
        return theLastAddedElement;
    }
}
