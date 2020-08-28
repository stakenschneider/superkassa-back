import java.util.*;
import java.util.stream.Collectors;

public class Compound {
    /**
     * The function recursively iterates over all possible
     * combinations of masks and matches them all possible strings that match this mask.
     *
     * @param input - HashMap<BitKey, All rows matching the key>
     * @return the final solution to the problem
     */
    protected List<List<String>> compound(final HashMap<Integer, List<List<String>>> input) {
        List<List<String>> result = new ArrayList<>();
        final List<List<Integer>> combinationList = findCombinations(input);

        for (List<Integer> row : combinationList) {
            List<List<String>> first = input.get(row.get(0));
            if (row.size() > 1) {
                row.remove(0);
                for (List<String> strings : first) {
                    result.addAll(mergeLines(strings, row, input));
                }
            } else {
                result.addAll(first);
            }
        }
        return result;
    }

    /**
     * Finds all possible combinations going through masks.
     *
     * ((a|b)^a)^b - return 0 if merging is valid
     * (a|b) - return result of merging two mask
     *
     * F.e. we have two mask 1001 and 0100
     * (1001 | 0100) = 1101
     * ((1001|0100)^1001)^0100 = 0
     *
     *
     * @param input - HashMap<BitKey, All rows matching the key>
     * @return combinations
     */
    private List<List<Integer>> findCombinations(final HashMap<Integer, List<List<String>>> input) {
        List<List<Integer>> combinationList = new ArrayList<>();
        int bitSize;

        for (Integer key : input.keySet()) {
            bitSize = maxBitSize(input.get(key).get(0).size());
            List<Integer> compoundList = new ArrayList<>();
            compoundList.add(key);

            if (key.equals(bitSize)) {
                combinationList.add(compoundList);
                continue;
            }

            Integer compoundBits = key;
            List<Integer> keys_2 = new ArrayList<>(input.keySet());
            int index = 0;
            for (int i = index; i < keys_2.size(); i++) {
                if ((((compoundBits | keys_2.get(i)) ^ compoundBits) ^ keys_2.get(i)) == 0) {
                    compoundBits = compoundBits | keys_2.get(i);
                    compoundList.add(keys_2.get(i));

                    if (compoundBits.equals(bitSize)) {
                        i = keys_2.indexOf(compoundList.get(1))+1;

                        Collections.sort(compoundList);

                        if (uniq(combinationList, compoundList)) {
                            combinationList.add(compoundList);
                        }
                        compoundList = new ArrayList<>();
                        compoundList.add(key);
                        compoundBits = key;
                    }
                }
            }
        }
        return combinationList;
    }

    /**
     * The function check, before writing a new found combination
     * to the result, whether it already exists?
     *
     * @param combinationList - result combination list
     * @param compoundList - new variant
     * @return - such a combination already exists?
     */
    private boolean uniq(List<List<Integer>> combinationList, List<Integer> compoundList) {
        for (List<Integer> q : combinationList) {
            if (q.equals(compoundList)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A recursive function that iterates over the masks in combination
     * and retrieves all the lines. Consistently combining the lines into the final.
     *
     * @param res - list of all lines matching the mask
     * @param rest - the rest of the array of masks
     * @param input - HashMap<BitKey, All rows matching the key>
     * @return list of merged lines
     */
    private List<List<String>> mergeLines(List<String> res, List<Integer> rest, final HashMap<Integer, List<List<String>>> input) {
        List<List<String>> result = new ArrayList<>();
        Integer key = rest.get(0);
        List<List<String>> first = input.get(key);
        List<Integer> indexes = getIndexes(key, input.get(key).get(0).size() - 1);

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

    /**
     * The function calculates from which positions to pick up
     * non-empty elements.
     *
     * @param key - mask
     * @param max - number of items in a line
     * @return list of indexes on which elements not null
     */
    private List<Integer> getIndexes(final Integer key, final Integer max) {
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

    /**
     * Calculates the mask of a full-filled line by its size.
     *
     * @param size - size of line
     * @return mask of full-filled line
     */
    private int maxBitSize(final int size) {
        int res = 0;
        for (int i = 0; i < size; i++) {
            res = (res << 1) + 1;
        }
        return res;
    }
}
