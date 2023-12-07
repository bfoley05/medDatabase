package MP4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The prupose is to read data from a file
 * @author Brandon Foley
 * @version 1.0
 */

public class File {
    List<List<String>> data;

    /**
     * Overloaded constructor takes in the file path
     * @param f String of the file path
     */
    public File(String f){
        data = readFile(f);
    }

    /**
     * Getter for the 2D array of data taken from the CSV
     * @return data taken from CSV
     */
    public List<List<String>> getData(){
        return data;
    }

    /**
     * Takes in a String that is the file path and parses through it
     * to put it into a 2D array
     * @param f String of the filepath
     * @return the 2D array taken from the CSV
     */
    public static List<List<String>> readFile(String f) {
        List<List<String>> data = new ArrayList<>();

        try {
            String file = f; // for each user change the username
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                List<String> lineData = parseCSVLine(line);
                data.add(lineData);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Parses through each seperate line of the CSV
     * @param line takes in one line of the CSV to parse through it
     * @return the 1D array of the line that has been parsed
     */
    public static List<String> parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        String[] tokens = line.split(",", 4);
        for (int i = 0; i < 3; i++) {
            values.add(tokens[i].trim());
        }
        if (tokens.length == 4) {
            String[] quotedValues = tokens[3].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            for (String quotedValue : quotedValues) {
                values.add(quotedValue.trim().replaceAll("\"", ""));
            }
        }
        return values;
    }
}
