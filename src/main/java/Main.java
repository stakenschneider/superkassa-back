import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ReadFile readFile = new ReadFile();
        String fileStr = readFile.read("input.json");
        JsonParser jsonParser = new JsonParser();

        HashMap<Integer, ArrayList<ArrayList<String>>>  str = jsonParser.parse(fileStr);
        Integer bitSize = jsonParser.bitSize(fileStr);

        Compound compound = new Compound();
        ArrayList<ArrayList<String>> str_2 = compound.compound(str, bitSize);

        WriteToFile writeToFile = new WriteToFile();
        writeToFile.write(str_2, "output_1.json");
    }
}
