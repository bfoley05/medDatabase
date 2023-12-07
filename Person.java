package MP4;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * The prupose is to make a new person either a docotr patient or pharmasisct
 * @author Brandon Foley
 * @version 1.0
 */

public class Person {
    int clearance; // 1 = patient, 2 = pharmacy 3 = doctor
    String name;
    String verificationKey;
    ArrayList<Person> access = new ArrayList<Person>();
    HR hr; // only for patients
    static int counter = 1;
    File HRFile = new File(Main.HR_CSV);
    List<List<String>> HRData = HRFile.getData();

    /**
     * Constuctor to create a new random user
     */

    public Person(){
        clearance = 1;
        name = "User" + counter;
        counter++;
        hr = new HR(HRData);
        verificationKey = generateVerificationKey();
        System.out.println(name + " your verification key is: " + verificationKey + ". Make sure to write it down and keep it safe");
    }

    /**
     * Overloaded constructor that assigns all variables ot a person
     * @param c int clearance level
     * @param n String name
     * @param key String password
     * @param r HR health record
     */

    public Person(int c, String n, String key, HR r){
        clearance = c;
        name = n; // At this point we are assuming nobody has the same names, will account later
        verificationKey = key;
        hr = r;
        System.out.print("\033[H\033[2J");
        System.out.println(name + " your verification key is: " + verificationKey + ". Make sure to write it down and keep it safe");
        try {
            // Wait for 3 seconds
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\033[H\033[2J");
    }

    /**
     * Adds an HR to a patient
     * @param r HR
     */
    public void addHR(HR r){
        hr = r;
    }

    /**
     * Adds a new patient (for doctors only)
     * @param p Person to be added
     */
    public void addPatient(Person p){
        access.add(p);
    }

    /**
     * returns number of patients for specific doctor
     * @return int for number of patients
     */
    public int numPatients(){
        return access.size();
    }

    /**
     * returns the clearance of a person
     * @return int clearnce
     */
    public int getClearance(){
        return clearance;
    }

    /**
     * returns name of the person
     * @return String name
     */
    public String getName(){
        return name;
    }

    /**
     * returns verification key
     * @return String password
     */
    public String getVerification(){
        return verificationKey;
    }

    /**
     * returns arraylist of patients
     * @return arraylist patients
     */
    public ArrayList<Person> getArrayList(){
        return access;
    }

    /**
     * prints out all patients of a doctor
     */
    public void printPatients(){
        if(!access.isEmpty()){
            for(int i = 0; i < access.size(); i++){
                System.out.println("\t->" + access.get(i).getName());
            }
        }
    }

    /**
     * Generates a random verification key
     * @return String password
     */

    //Code from internet:
    public static String generateVerificationKey() {
        // Define the characters that can be used in the verification key
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()<>_-+=:";

        // Set the length of the verification key
        int keyLength = 12;

        // Create a StringBuilder to store the verification key
        StringBuilder verificationKey = new StringBuilder(keyLength);

        // Create a SecureRandom object for generating random values
        SecureRandom random = new SecureRandom();

        // Generate the verification key
        for (int i = 0; i < keyLength; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            verificationKey.append(randomChar);
        }

        // Convert the StringBuilder to a String and return the verification key
        return verificationKey.toString();
    }
    //End code from internet

    /**
     * To string that will print a persons clearance level
     */
    public String toString(){
        return name + "\nSecurity Clearance level: " + clearance;
    }

}
