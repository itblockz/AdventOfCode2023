import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day15_2 {
    public static void main(String[] args) throws IOException {
        
        String in = Files.lines(Paths.get("input15.txt")).collect(Collectors.joining());
        String[] split = in.split(",");

        HashMap<Integer, HashMap<String, Integer>> hmBox = new HashMap<>();
        HashMap<Integer, List<String>> hmLabel = new HashMap<>();
        for (String str : split) {
            String label = str.replaceAll("[^a-z]", "");
            String oper = str.replaceAll("\\w", "");
            
            int x = hash(label);
            if (!hmBox.containsKey(x)) {
                hmBox.put(x, new HashMap<>());
                hmLabel.put(x, new ArrayList<>());
            }
            
            HashMap<String, Integer> lens = hmBox.get(x);
            List<String> labelList = hmLabel.get(x);
            if (oper.equals("=")) {
                int val = Integer.parseInt(str.replaceAll("\\D", ""));
                if (!labelList.contains(label)) {
                    labelList.add(label);
                }
                lens.put(label, val);
            } else {
                labelList.remove(label);
                lens.remove(label);
            }
        }
        
        int sum = 0;
        for (var entry : hmLabel.entrySet()) {
            int box = entry.getKey();
            List<String> boxList = entry.getValue();
            for (int i = 0; i < boxList.size(); i++) {
                String label = boxList.get(i);
                int slot = i;
                int val = hmBox.get(box).get(label);
                int x = (box + 1)*(slot + 1)*val;
                sum += x;
            }
        }
        System.out.println(sum);
    }

    static int hash(String str) {
        int val = 0;
        for (int i = 0; i < str.length(); i++) {
            int ascii = (int)str.charAt(i);
            val += ascii;
            val *= 17;
            val %= 256;
        }
        return val;
    }
}
