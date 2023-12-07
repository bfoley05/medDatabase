package MP4;

/**
 * The prupose is to make prescription medince is the child
 * @author Brandon Foley
 * @version 1.0
 */

public class Prescription extends Medicine {

    /**
     * ovearloaded constructor connects to parent
     * @param id
     * @param name
     * @param expirationDate
     * @param chemicalCompound
     * @param drugInteractions
     */
    public Prescription(int id, String name, String expirationDate, String chemicalCompound, String drugInteractions) {
        super(id, name, expirationDate, chemicalCompound, drugInteractions);
    }

    @Override
    public String getDetails() {
        return "Prescription medicine: " + getMedicine();
    }

    /**
     * adds medicine to the csv
     * @return boolean when added
     */
    public boolean addMedicine(){
        //id,medicine_name,expiration_date,over_the_counter,chemical_compound,drug_interactions,additional_info
        String[] data = new String[7];
        data[0] = Integer.toString(id);
        data[1] = name;
        data[2] = expirationDate;
        data[3] = "false";
        data[4] = chemicalCompound;
        data[5] = drugInteractions;
        data[6] = "\"None\"";
        addDataToCSV(data);
        return true;
    }

    /**
     * method to remove medicne from csv
     * @param id int id to be removed
     * @return true when removed
     */
    public static boolean removeMedicine(int id){
        removeRowFromCSV(id);
        return true;
    }
}
