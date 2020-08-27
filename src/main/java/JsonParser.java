import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonParser {
    Integer bitSize(String jsonString){
        int size = new JSONArray(jsonString).getJSONArray(0).length();
        int res = 0;
        for (int i = 0 ; i<size;i++){
            res = (res << 1) + 1;
        }
        return res;
    }

    HashMap<Integer, ArrayList<ArrayList<String>>> parse(String jsonString) {
        JSONArray jsonArray = new JSONArray(jsonString);
        HashMap<Integer, ArrayList<ArrayList<String>>> result = new HashMap<>();

        for (int k = 0; k < jsonArray.length(); k++) {
            JSONArray jsonArr = jsonArray.getJSONArray(k);
            ArrayList<String> list = new ArrayList<>();

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

            ArrayList<ArrayList<String>> set = new ArrayList<>();

            if (result.containsKey(bit)){
                set = result.get(bit);
            }

            set.add(list);
            result.put(bit,set);
        }

        return result;
    }
}
