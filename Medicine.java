package MP4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The prupose is to create and manage medeince is the parent class of overthecounter and prescirption
 * @author Brandon Foley
 * @version 1.0
 */

public abstract class Medicine implements Counter{
    public int id;
    public String name;
    public String expirationDate;
    public String chemicalCompound;
    public String drugInteractions;

    /**
     * Overloaded constuctor to be implemented from the children class
     * @param id int id of the medicine
     * @param name String of the name of the medicine
     * @param expirationDate String of when it expires
     * @param chemicalCompound String of the chemical compound of the medicine
     * @param drugInteractions String of what other interactions it could have
     */

    public Medicine(int id, String name, String expirationDate, String chemicalCompound, String drugInteractions) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.chemicalCompound = chemicalCompound;
        this.drugInteractions = drugInteractions;
    }

    /**
     * absract method
     * @return String
     */
    public abstract String getDetails();

    /**
     * Takes in a String and compares it to the current date
     * @param dateExpires the input of the date we are checking
     * @return true if it is expired
     */

    public static boolean isExpired(String dateExpires) {
        return dateExpires.compareTo(getCurrentDate()) < 0;
    }

    /**
     * Gets todays date from java.time
     * @return the String of the current date in the correct format
     */
    private static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

    /**
     * Getter gfor the medince
     * @return the String of the medicine
     */
    public String getMedicine(){
        return name;
    }

    /**
     * Adds data to the CSV from an Array
     * @param data takes in array and parses it so it can be added to a csv
     */
    public static void addDataToCSV(String[] data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.MEDICINE_CSV, true))) {
            // Join the data with commas to form a CSV row
            String csvRow = String.join(",", data);

            // Add a new line character after each row
            csvRow += System.lineSeparator();

            // Write the row to the CSV file
            writer.write(csvRow);

            System.out.println("Data added to CSV file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in the id of the medicine that we want to remove
     * @param idToRemove id of the medince to be removed
     */
    public static void removeRowFromCSV(int idToRemove) {
        // Read the existing CSV file
        List<String> csvData = readCSV();

        // Identify and remove the row with the specified ID
        for (int i = 1; i < csvData.size(); i++) {
            String[] rowData = csvData.get(i).split(",");
            int id = Integer.parseInt(rowData[0].trim()); // Assuming ID is the first column

            if (id == idToRemove) {
                csvData.remove(i);
                System.out.println("Row with ID " + idToRemove + " removed from CSV file.");
                break; // Stop searching once the row is removed
            }
        }

        // Write the updated data back to the CSV file
        writeCSV(csvData);
    }

    /**
     * Reads the current file
     * @return 1D array of the csv
     */
    private static List<String> readCSV() {
        List<String> csvData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Main.MEDICINE_CSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                csvData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvData;
    }

    /**
     * Adds the new data to the csv
     * @param data the new data to be added
     */
    private static void writeCSV(List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.MEDICINE_CSV))) {
            for (String row : data) {
                writer.write(row);
                writer.newLine();
            }
            System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

