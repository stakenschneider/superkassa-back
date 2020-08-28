import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToFile {
    /**
     * Creates a file and writes the result to it. If the
     * file exists, it will overwrite the previous one.
     *
     * @param result   - solutions
     * @param filename - the name of the file in which the response will be recorded
     */
    public void write(List<List<String>> result, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            JSONArray mJSONArray = new JSONArray(result);
            mJSONArray.write(myWriter);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}