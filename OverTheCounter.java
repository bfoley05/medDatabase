package MP4;

/**
 * The prupose is to make overthecounter medince is the child
 * @author Brandon Foley
 * @version 1.0
 */

public class OverTheCounter extends Medicine {

    /**
     * Overaloaded constructor that takes in a new overthecounter medicine
     * @param id int id of the medicine
     * @param name String of the name of the medicine
     * @param expirationDate String of when it expires
     * @param chemicalCompound String of the chemical compound of the medicine
     * @param drugInteractions String of what other interactions it could have
     */
    public OverTheCounter(int id, String name, String expirationDate, String chemicalCompound, String drugInteractions) {
        super(id, name, expirationDate, chemicalCompound, drugInteractions);
    }

    @Override
    public String getDetails() {
        return "Over-the-counter medicine: " + getMedicine();
    }

    /**
     * Adds the new overthecounter medicine to the csv
     * @return true if added
     */
    public boolean addMedicine(){
        //id,medicine_name,expiration_date,over_the_counter,chemical_compound,drug_interactions,additional_info
        String[] data = new String[7];
        data[0] = Integer.toString(id);
        data[1] = name;
        data[2] = expirationDate;
        data[3] = "true";
        data[4] = chemicalCompound;
        data[5] = drugInteractions;
        data[6] = "\"None\"";
        addDataToCSV(data);
        return true;
    }
    /**
     * returns the medicine with the id given
     * @param id id to be removed
     * @return true if removed
     */
    public static boolean removeMedicine(int id){
        removeRowFromCSV(id);
        return true;
    }

}
