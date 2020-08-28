import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final ReadFile readFile = new ReadFile();
        final String fileStr = readFile.read("input.json");
        final JsonParser jsonParser = new JsonParser();

        final HashMap<Integer, List<List<String>>> hashMap = jsonParser.parse(fileStr);

        final Compound compound = new Compound();
        final List<List<String>> solution = compound.compound(hashMap);

        final WriteToFile writeToFile = new WriteToFile();
        writeToFile.write(solution, "output.json");
    }
}
