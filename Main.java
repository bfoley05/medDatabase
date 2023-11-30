package MP4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String MASTER_KEY = "test";
    // adjust file path to your own path. Below is an exmaple for macs. Otherwise use C:\Users\name\Documents\CPSC_Courses\MP4\Medicines.csv
    public static final String MEDICINE_CSV = "/Users/brandonfoley/Documents/CPSC_Courses/MP4/Medicines.csv";
    public static final String HR_CSV = "/Users/brandonfoley/Documents/CPSC_Courses/MP4/FakeHRs.csv";
    public static void main(String[] args){
        System.out.println("\n\n\n");
        File medicinesFile = new File(MEDICINE_CSV);
        File HRFile = new File(HR_CSV);
        List<List<String>> HRData = HRFile.getData();
        List<List<String>> data = medicinesFile.getData();
        System.out.println(data.get(0));
        Users u = new Users();

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
                        u.getPerson(nameOfDoc).addPatient(u.getPerson(name));;
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

}
