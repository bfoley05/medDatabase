package MP4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
/**
 * The prupose is to run the database
 * @author Brandon Foley
 * @version 1.0
 */

public class Main {
    public static final String MASTER_KEY = "test";
    // adjust file path to your own path. Below is an exmaple for macs. Otherwise use C:\Users\name\Documents\CPSC_Courses\MP4\Medicines.csv
    public static final String MEDICINE_CSV = "/Users/brandonfoley/Documents/CPSC_Courses/MP4/Medicines.csv";
    public static final String HR_CSV = "/Users/brandonfoley/Documents/CPSC_Courses/MP4/FakeHRs.csv";
    public static File medicinesFile = new File(MEDICINE_CSV);
    public static List<List<String>> data = medicinesFile.getData();
    public static void main(String[] args){
        System.out.println("\n\n\n");
        File HRFile = new File(HR_CSV);
        List<List<String>> HRData = HRFile.getData();
        Users u = new Users();
        displayLoadingScreen();
        simulateDatabaseLoading();
        removeExpiredMeds();
        System.out.print("\033[H\033[2J");
        System.out.println("Expired meds removed\n");

        System.out.println("Medical database loaded successfully!\n");

        int input = 1;
        while(input > 0){
            try{
                System.out.println("What would you like to do:\n\t1. Add patient\n\t2. Add pharmasist\n\t3. Add doctor\n\t4. Access our portal\n\t0. To quit");
                Scanner sc = new Scanner(System.in);
                input = sc.nextInt();
                if(input <= 3){
                    if(input > 1){
                        System.out.println("To create a employee please enter the company key: ");
                        String key1 = sc.nextLine();
                        String key = sc.nextLine();
                        if(key.equals(MASTER_KEY)){
                            System.out.println("To create your account we will need your name and a password to keep your account secure.");
                            System.out.println("First what is your name: ");
                            String name = sc.nextLine();
                            System.out.println("Welcome " + name + "!\nPlease choose a strong password: ");
                            String password = sc.nextLine();
                            u.addPerson(new Person(input, name, password, new HR(HRData)));
                        }else{
                            System.out.println("Access Denied");
                        }
                    }else if(input == 1){
                        System.out.println("To create your account we will need your name and a password to keep your account secure.");
                        System.out.println("First what is your name: ");
                        String name1 = sc.nextLine();
                        String name = sc.nextLine();
                        System.out.println("Welcome " + name + "!\nPlease choose a strong password: ");
                        String password = sc.nextLine();
                        u.addPerson(new Person(input, name, password, new HR(HRData)));
                        u.printDoctors();
                        System.out.println("Please enter the name of the doctor you would like: ");
                        String nameOfDoc = sc.nextLine();
                        try{
                            u.getPerson(nameOfDoc).addPatient(u.getPerson(name));
                        } catch (NullPointerException e){
                            System.out.println("There are no doctors at this time, please come back and register at a later date");
                        }
                    }
                }else if(input == 0){
                    break;
                }else if(input == 123456789){
                    System.out.print("\033[H\033[2J");
                    System.out.println("Welcome back boss 🫡🫡🫡");
                    u.printAll();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\033[H\033[2J");
                }else if(input == 4){
                    u.getAccess();
                }
            }catch(InputMismatchException e){
                System.out.println("Enter a number 0-4");
            }
        }

    }

    /**
     * Gets the last ID of medicines
     * @return interger of the id
     */
    public static int lastID(){
        int index = Integer.parseInt(data.get(countLinesInCSV()-1).get(0));
        return index;
    }

    /**
     * gets experation date from the id of the medicine
     * @param i id to find
     * @return String of the experation date
     */
    public static String experiationDate(int i){
        String expires = data.get(i).get(2);
        return expires;
    }

    /**
     * Gets the id based of the index
     * @param i index to find
     * @return id of the given index
     */
    public static int getID(int i){
        return Integer.parseInt(data.get(i).get(0));
    }

    /**
     * counts the amount of lines in the medicine csv
     * @return how many lines there are
     */
    public static int countLinesInCSV() {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(MEDICINE_CSV))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    /**
     * removes all expired meds at start up
     * @return true when finished
     */
    public static boolean removeExpiredMeds(){
        try{
            for(int i = 0; i < countLinesInCSV(); i++){
                String expires = experiationDate(i);
                if(Medicine.isExpired(expires)){
                    int id = getID(i);
                    Medicine.removeRowFromCSV(id);
                }
            }
            if(Medicine.isExpired(experiationDate(countLinesInCSV()))){
                int id = getID(countLinesInCSV()+1);
                Medicine.removeRowFromCSV(id);
            }
        }catch (IndexOutOfBoundsException e){
        }
        return true;
    }

    /**
     * Our loading screen
     */
    private static void displayLoadingScreen() {
        System.out.print("\033[H\033[2J");
        printLogo();
        System.out.println("Intializing...");
        try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        // Simulate loading animation with a progress bar
        int totalProgressBars = 60;
        for (int i = 0; i < totalProgressBars; i++) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J");
            printLogo();
            System.out.print("Loading [");
            int progress = (int) (((double) i / (totalProgressBars - 1)) * 100);
            int barCount = (int) (((double) i / (totalProgressBars - 1)) * totalProgressBars);
            for (int j = 0; j < barCount; j++) {
                System.out.print("=");
            }
            for (int j = barCount; j < totalProgressBars; j++) {
                System.out.print(" ");
            }
            System.out.print("] " + progress + "%");
        }
        System.out.println();
        try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.print("\033[H\033[2J");
    }

    /**
     * Loading point
     */
    private static void simulateDatabaseLoading() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * our logo
     */
    public static void printLogo(){
        System.out.println("\n\n");
        System.out.println("                             &&&&&&&&&&&&&&&&&&                                 ");
        System.out.println("                             &&&&&&&&&&&&&&&&&&                                 ");
        System.out.println("                             &&&&&&&&&&&&&&&&&&                                 ");
        System.out.println("                             &&&&&&&&&&&&&&&&&&                                 ");
        System.out.println("                             &&&&&&&&&&&&&&&&&&                                 ");
        System.out.println("                             &&&&&&&&&&&&&&&&&&       /&                        ");
        System.out.println("                             &&&&&&&&&&&&&&(       #&&&                         ");
        System.out.println("          &&&&&&&&&&&&&&&&&&&&&&&&&&&&&,       #&&&&&   (&&&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&&&&&&&&&&&&       &&&&&&&    (((&&&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&&&&&&&&&      (&&&&&&&  *((((((&&&&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&&&&&&(     #&&&&&&((((((((((((&&&&&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&&&&#     &&&&&%(((((((((((((&&&&&&&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&&&     &&&&&((((((((((((((&&&&&& &&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&&    &&&&%(((((((((((((&&&&&&&  %&&&&&&&&              ");
        System.out.println("          &&&&&&&&&&&&&&&    &&&&((((((((((((&&&&&&&    &&&&&&&&&&              ");
        System.out.println("                            &&&#(((((((((&&&&&&&%                               ");
        System.out.println("                           ,&&(((((((#&&&&&&*                                   ");
        System.out.println("                           &&%(((((&&&&&.                                       ");
        System.out.println("                           &&((((&&&&      .&&&                                 ");
        System.out.println("                           .%((&&&      &&&&&&&                                 ");
        System.out.println("                            ((&&.    &&&&&&&&&&                                 ");
        System.out.println("                            .%&    %&&&&&&&&&&&                                 ");
        System.out.println("                             &    @&&&&&&&&&&&&                                 ");
        System.out.println("\n\n");
    }

}
