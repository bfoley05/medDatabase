package MP4;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The prupose is to have all the users of our medical database in one spot
 * @author Brandon Foley
 * @version 1.0
 */

public class Users {
    static HashMap<String, Person> hm = new HashMap<String, Person>();
    private static final List<String> CHEMICAL_COMPOUNDS = Arrays.asList(
            "Acetaminophen", "Ibuprofen", "Aspirin", "Omeprazole", "Simvastatin",
            "Lisinopril", "Levothyroxine", "Metformin", "Atorvastatin", "Amlodipine",
            "Albuterol", "Hydrochlorothiazide", "Gabapentin", "Losartan", "Aripiprazole",
            "Citalopram", "Fluoxetine", "Metoprolol", "Escitalopram", "Sertraline",
            "Furosemide", "Tramadol", "Amitriptyline", "Metoprolol", "Cephalexin",
            "Ciprofloxacin", "Amoxicillin", "Clarithromycin", "Doxycycline", "Prednisone",
            "Celecoxib", "Warfarin", "Clopidogrel", "Duloxetine", "Venlafaxine",
            "Pregabalin", "Methotrexate", "Insulin", "Lansoprazole", "Tamsulosin",
            "Ranitidine", "Esomeprazole", "Montelukast", "Sildenafil", "Tadalafil",
            "Risperidone", "Quetiapine", "Hydroxyzine", "Diazepam", "Olanzapine",
            "Lorazepam", "Cyclobenzaprine", "Meloxicam", "Carvedilol", "Valacyclovir",
            "Famotidine", "Ezetimibe", "Sitagliptin", "Raloxifene", "Pioglitazone",
            "Bupropion", "Budesonide", "Doxazosin", "Alendronate", "Loratadine",
            "Amiodarone", "Diltiazem", "Fenofibrate", "Nifedipine", "Sotalol",
            "Trazodone", "Desvenlafaxine", "Naproxen", "Venlafaxine", "Perindopril",
            "Triamterene", "Nortriptyline", "Hydralazine", "Cyclosporine", "Mirtazapine",
            "Nabumetone", "Terbinafine", "Bimatoprost", "Diphenhydramine", "Ergocalciferol",
            "Potassium", "Vitamin C", "Vitamin D", "Calcium", "Iron"
    );
    private static final List<String> DRUG_INTERACTIONS = Arrays.asList(
    "Warfarin","Ibuprofen","Naproxen","Alcohol","Aliskiren","Lithium","Grapefruit juice","Amiodarone","CNS depressants","Clopidogrel","Ketoconazole","Cimetidine","Furosemide","Probenecid","Methotrexate","Cyclosporine","MAOIs","Rivaroxaban","Procainamide","Calcium supplements","Antacids","Bleeding disorders","NSAIDs","Sertraline","Lisinopril","Verapamil","Thioridazine","Potassium supplements","Pimozide","Morphine","Nitrates","Riociguat","Simvastatin","Theophylline","Carbamazepine","Doxazosin","Ritonavir","Serotonin reuptake inhibitors","Clopidogrel","Digoxin","Aspirin","Ciprofloxacin","Statins","NSAIDs","Oxycodone","None"
    );

    /**
     * adds person to list of users
     * @param p Person to be added
     */
    public void addPerson(Person p){
        hm.put(p.getName(), p);
    }

    /**
     * to get access to our portal
     * @param n String name to verify your identity
     * @return true if the verifcation key is correct
     */
    public boolean requestAccess(String n){
        Scanner sc = new Scanner(System.in);
        Person p = hm.get(n);
        System.out.println("Enter your verification key");
        String key = sc.nextLine();
        if(key.equals(p.getVerification())){
            return true;
        }else{
            System.out.println("Wrong key");
            return false;
        }
    }

    /**
     * runs through the proccess of getting access
     */
    public void getAccess(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name");
            String name = sc.nextLine();
            boolean isAllowed = requestAccess(name);
            Person p = hm.get(name);
            if(isAllowed){
                if(p.getClearance() == 1){
                    System.out.println("Your HR is: " + p.hr);
                }else if(p.getClearance() == 2){
                    System.out.println("Welcome back pharmacist!");
                    addRemoveMedicine();
                }else{
                    System.out.print("\033[H\033[2J");
                    System.out.println("Welcome back doctor! You have " + p.numPatients() + " Patients");
                    p.printPatients();
                    if(p.numPatients() > 0){
                        System.out.println("What patient would you like to look at?");
                        name = sc.nextLine();
                        Person patient = hm.get(name);
                        System.out.println("The patients HR is: " + patient.hr);
                    }
                }
            }
        }catch (NullPointerException e){
            System.out.println("Sorry either you dont have an account with us or entered your name wrong\nIf issue persists please contact your doctor");
        }
    }


    /**
     * For pharmacists to add and remove medicine
     */
    public void addRemoveMedicine(){
        try{
            String input = "";
            while(!input.equals("q")){
                System.out.println("Do you want to:\n1. Add medicine\n2. Remove medicine\nq to quit");
                Scanner sc = new Scanner(System.in);
                input = sc.nextLine();
                if(input.equals("q")){
                    System.out.println("Exiting...");
                }else if(Integer.parseInt(input) == 1){
                    System.out.println("Is this medication over the counter? (y/n)");
                    String input2 = sc.nextLine();
                    if(input2.equals("y")){
                        System.out.println("Enter the name: ");
                        String name = sc.nextLine();
                        System.out.println("Enter the experation date(yyyy-mm-dd): ");
                        String expires = sc.nextLine();
                        Random randy = new Random();
                        int index = randy.nextInt(CHEMICAL_COMPOUNDS.size());
                        int index2 = randy.nextInt(DRUG_INTERACTIONS.size()-1)+1;
                        String drugInteractions = DRUG_INTERACTIONS.get(index2) + ";" + DRUG_INTERACTIONS.get(index2-1);
                        new OverTheCounter(Main.lastID()+1, name, expires, CHEMICAL_COMPOUNDS.get(index), drugInteractions).addMedicine();
                    }else if(input2.equals("n")){
                        System.out.println("Enter the name: ");
                        String name = sc.nextLine();
                        System.out.println("Enter the experation date(yyyy-mm-dd): ");
                        String expires = sc.nextLine();
                        Random randy = new Random();
                        int index = randy.nextInt(CHEMICAL_COMPOUNDS.size());
                        int index2 = randy.nextInt(DRUG_INTERACTIONS.size()-1)+1;
                        String drugInteractions = DRUG_INTERACTIONS.get(index2) + ";" + DRUG_INTERACTIONS.get(index2-1);
                        new Prescription(Main.lastID()+1, name, expires, CHEMICAL_COMPOUNDS.get(index), drugInteractions).addMedicine();
                    }
                }else if(Integer.parseInt(input) == 2){
                    System.out.println("What is the id number of the medicine to remove?");
                    int index = sc.nextInt();
                    Medicine.removeRowFromCSV(index);
                }
            }
        } catch (NumberFormatException e){
            System.out.println("You did not input one of the correct values");
        }
    }

    /**
     * gets the person from their name that is inputed
     * @param name String to get person
     * @return the person with the name
     */
    public Person getPerson(String name){
        return hm.get(name);
    }

    /**
     * Prints all doctors
     */
    public void printDoctors(){
        Collection<Person> doctors = hm.values();

        // Print all values
        System.out.println("All doctors:");
        for (Person doctor : doctors) {
            if(doctor.getClearance() == 3){
                System.out.println("\t->" + doctor.getName());
            }
        }
    }

    /**
     * prints all users
     */
    public void printAll(){
        Collection<Person> allPeople = hm.values();
        System.out.println("---------------------------------------------------------------------");

        // Print all values
        for (Person person : allPeople) {
            System.out.println("\t->" + person.getName() + ", Clearance: " + person.getClearance());
            if(person.getClearance() == 3){
                System.out.println("Patients: ");
                person.printPatients();
            }
            System.out.println("---------------------------------------------------------------------");
        }
    }
}
