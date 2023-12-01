package MP4;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Person {
    int clearance; // 1 = patient, 2 = pharmacy 3 = doctor
    String name;
    String verificationKey;
    ArrayList<Person> access = new ArrayList<Person>();
    HR hr; // only for patients
    static int counter = 1;
    File HRFile = new File(Main.HR_CSV);
    List<List<String>> HRData = HRFile.getData();
    public Person(){
        clearance = 1;
        name = "User" + counter;
        counter++;
        hr = new HR(HRData);
        verificationKey = generateVerificationKey();
        System.out.println(name + " your verification key is: " + verificationKey + ". Make sure to write it down and keep it safe");
    }

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

    public void addHR(HR r){
        hr = r;
    }

    public void addPatient(Person p){
        access.add(p);
    }

    public int numPatients(){
        return access.size();
    }

    public int getClearance(){
        return clearance;
    }

    public String getName(){
        return name;
    }

    public String getVerification(){
        return verificationKey;
    }

    public ArrayList<Person> getArrayList(){
        return access;
    }


    public void printPatients(){
        if(!access.isEmpty()){
            for(int i = 0; i < access.size(); i++){
                System.out.println("\t->" + access.get(i).getName());
            }
        }
    }



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

    public String toString(){
        return name + "\nSecurity Clearance level: " + clearance;
    }

}
