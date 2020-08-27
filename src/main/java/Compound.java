import java.util.*;
import java.util.stream.Collectors;

public class Compound {

    ArrayList<ArrayList<String>> compound(HashMap<Integer, ArrayList<ArrayList<String>>> input, Integer bitSize) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<ArrayList<Integer>> preRes = new ArrayList<>();

        for (Integer key : input.keySet()) {
            Integer[] str = new Integer[20];
            str[0] = key;

            if (key.equals(bitSize)) {
                preRes.add(new ArrayList<>(Arrays.asList(str)));
                continue;
            }

            Integer compoundBits = key;
            int index = 0;
            List<Integer> keys_2 = new ArrayList<>(input.keySet());
            for (int i = 0; i < keys_2.size(); i++) {
                if ((((compoundBits | keys_2.get(i)) ^ compoundBits) ^ keys_2.get(i)) == 0) {
                    compoundBits = compoundBits | keys_2.get(i);
                    index++;
                    str[index] = keys_2.get(i);
                }

                if (compoundBits.equals(bitSize)) {
                    preRes.add(new ArrayList<>(Arrays.asList(str)));
                    i = keys_2.indexOf(str[1]) + 1;
                    Arrays.fill(str, null);
                    str[0] = key;
                    compoundBits = key;
                }
            }
        }

        for (ArrayList<Integer> q : preRes){
            q.removeIf(Objects::isNull);
        }

        for (ArrayList<Integer> res : preRes) {
            ArrayList<ArrayList<String>> first = input.get(res.get(0));
            if (res.size() > 1) {
                res.remove(0);
//                по веткам дерева рекурсии
                for (ArrayList<String> strings : first) {
                    result.addAll(mergeLines(strings, res, input));
                }
            } else {
                result.addAll(first);
            }
        }
        return result;
    }

    ArrayList<ArrayList<String>> mergeLines(ArrayList<String> res, ArrayList<Integer> rest, HashMap<Integer, ArrayList<ArrayList<String>>> input) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        Integer key = rest.get(0);
        List<ArrayList<String>> first = input.get(key);
        List<Integer> indexes = getIndexes(key, input.get(key).get(0).size()-1);

        List<ArrayList<String>> mergedLines = first.stream().map(x -> {
            String[] init = new String[res.size()];
            init = res.toArray(init);

            for (Integer element : indexes) {
                init[element] = x.get(element);
            }

            return new ArrayList<>(Arrays.asList(init));
        }).collect(Collectors.toList());

        if (rest.size() > 1) {
            for (ArrayList<String> mergedLine : mergedLines) {
                ArrayList<Integer> newRest = new ArrayList<>(rest);
                newRest.remove(0);
                result.addAll(mergeLines(mergedLine, newRest, input));
            }
        } else {
            result.addAll(mergedLines);
        }
        return result;
    }

    List<Integer> getIndexes(Integer key, Integer max) {
        List<Integer> res = new ArrayList<>();
        int temp = key;
        int index = max;
        while (temp > 0) {
            if (temp % 2 != 0) {
                res.add(index);
            }
            temp >>= 1;
            index--;
        }

        return res;
    }
}
