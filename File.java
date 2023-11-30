package MP4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class File {
    List<List<String>> data;
    public File(String f){
        data = readFile(f);
    }

    public List<List<String>> getData(){
        return data;
    }

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

    public static List<String> parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        String[] tokens = line.split(",", 4);  // Split the line into first 3 values and the rest
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
