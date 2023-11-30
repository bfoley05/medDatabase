package MP4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Medicine implements Counter{
    public int id;
    public String name;
    public String expirationDate;
    public String chemicalCompound;
    public String drugInteractions;

    public Medicine(int id, String name, String expirationDate, String chemicalCompound, String drugInteractions) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.chemicalCompound = chemicalCompound;
        this.drugInteractions = drugInteractions;
    }

    public abstract String getDetails();

    public boolean isExpired() {
        // Implement the logic to check if the medicine is expired based on the current date
        // You can use the java.util.Date or java.time.LocalDate classes for this purpose.
        // For simplicity, let's assume expirationDate is a string in the format "yyyy-MM-dd".
        return expirationDate.compareTo(getCurrentDate()) < 0;
    }

    private String getCurrentDate() {
        // Implement the logic to get the current date as a string.
        // For simplicity, let's use the format "yyyy-MM-dd".
        // You can use the java.util.Date or java.time.LocalDate classes for this purpose.
        return "2023-01-01"; // Replace with actual code to get the current date.
    }

    public String getMedicine(){
        return name;
    }

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

