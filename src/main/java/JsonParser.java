import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    /**
     * Parses a input string into a HashMap<mask , all lines matching the mask>.
     * The mask is created using bitwise shift.
     * <p>
     * F.e. we have input string
     * [[ null,   "c2",   "c3",   null   ],
     * [ null,   "e2",   "e3",   null   ],
     * [ "b1",   null,   null,   "b4"   ]]
     * <p>
     * First and second line have same bitMask 0110 (or 6 in decimal system)
     * Third line bitMask 1001 (or 9 in decimal system)
     * <p>
     * This structure allows me not to work with strings and does not
     * go over all possible solutions. Subsequently, I first iterate
     * over all the combinations of masks, and then match the lines to them.
     *
     * @param jsonString - input string
     * @return HashMap<key, all lines matching the mask>
     */
    protected HashMap<Integer, List<List<String>>> parse(String jsonString) {
        JSONArray jsonArray = new JSONArray(jsonString);
        HashMap<Integer, List<List<String>>> result = new HashMap<>();

        for (int k = 0; k < jsonArray.length(); k++) {
            JSONArray jsonArr = jsonArray.getJSONArray(k);
            List<String> list = new ArrayList<>();

            int bit = 0;
            for (int i = 0; i < jsonArr.length(); i++) {
                String element = jsonArr.get(i).toString();
                if (element.equals("null")) {
                    bit = (bit << 1);
                } else {
                    bit = (bit << 1) + 1;
                }
                list.add(element);
            }

            List<List<String>> set = new ArrayList<>();

            if (result.containsKey(bit)) {
                set = result.get(bit);
            }

            set.add(list);
            result.put(bit, set);
        }
        return result;
    }
}
