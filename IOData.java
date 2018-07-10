package Paperchase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class IOData {

    private ArrayList<String> profiles = new ArrayList<>();
    private final String fileLocation = "Paperchase/Data/profiles.txt";

    IOData() {
        try {
            profiles = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> readFile() throws IOException {
        String line;
        ArrayList<String> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
        while((line = reader.readLine())!= null) {
            result.add(line);
        }
        return result;
    }

    String isAlreadyRegistered(String email) {
        for (String profile : profiles) {
            int seperatorIndex = profile.indexOf(",,");
            if (profile.substring(0, seperatorIndex).equals(email)) return profile.substring(seperatorIndex+2, profile.length());
        }
        return "false";
    }
}
