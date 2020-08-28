import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    /**
     * Read input from a file and writes it to a string.
     *
     * @param filename - the name of the file in which the input data is stored
     * @return input string
     */
    public String read(String filename) {
        StringBuilder result = new StringBuilder();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.append(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}