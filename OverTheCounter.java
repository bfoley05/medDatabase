package MP4;

public class OverTheCounter extends Medicine {
    public OverTheCounter(int id, String name, String expirationDate, String chemicalCompound, String drugInteractions) {
        super(id, name, expirationDate, chemicalCompound, drugInteractions);
    }

    @Override
    public String getDetails() {
        return "Over-the-counter medicine: " + getMedicine();
    }

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

    public static boolean removeMedicine(int id){
        removeRowFromCSV(id);
        return true;
    }

}
