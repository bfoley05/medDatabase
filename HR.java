package MP4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The prupose is to create and store new HR's
 * @author Brandon Foley
 * @version 1.0
 */

public class HR {
    ArrayList<String> medicationsOn;
    ArrayList<String> allergies;
    ArrayList<String> immunizations;
    boolean insurance;
    int age;
    List<List<String>> HRData;

    /**
     * Overloaded constructor that takes in a 2D array and sets the variables of this class to those inputs
     * @param HRData 2D array of HR data
     */
    public HR(List<List<String>> HRData){
        this.HRData = HRData;
        Random randy = new Random();
        int index = randy.nextInt(HRData.size()-1) + 1;
        List<String> medicationList = Arrays.asList((HRData.get(index).get(0)).split(";"));
        medicationsOn = new ArrayList<>(medicationList);
        List<String> allergiesList = Arrays.asList((HRData.get(index).get(1)).split(";"));
        allergies = new ArrayList<>(allergiesList);
        List<String> immunizationsList = Arrays.asList((HRData.get(index).get(2)).split(";"));
        immunizations = new ArrayList<>(immunizationsList);
        insurance = Boolean.parseBoolean(HRData.get(index).get(3));
        age = Integer.parseInt(HRData.get(index).get(4));
    }

    /**
     * ToString to output everythin in a organized way
     */
    public String toString(){
        String output = "\n---------------------------------------------------------------------------------------";
        output += "\nMedications on:\n";
        for(int i = 0; i < medicationsOn.size(); i++){
            output+= "\t->" + medicationsOn.get(i) + "\n";
        }
        output += "Allergies:\n";
        for(int i = 0; i < allergies.size(); i++){
            output+= "\t->" + allergies.get(i) + "\n";
        }
        output += "Immunizations:\n";
        for(int i = 0; i < immunizations.size(); i++){
            output+= "\t->" + immunizations.get(i) + "\n";
        }
        output += "Insurance: " + insurance + "\n";
        output += "Age: " + age + "\n";
        output += "---------------------------------------------------------------------------------------";
        return output;
    }
}
