import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day19 {
    static Map<String, Integer> categories = new HashMap<>();
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Paths.get("input19.txt"))
            .collect(Collectors.toList());

        Map<String, List<String>> workflows = new HashMap<>();
        List<List<Integer>> ratings = new ArrayList<>();
        boolean beforeBlank = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                beforeBlank = false;
                continue;
            }
            if (beforeBlank) {
                String[] split = line.split("\\{|\\}");
                String name = split[0];
                List<String> rules = Arrays.asList(split[1].split(","));
                workflows.put(name, rules);
            } else {
                String[] split = line.split("\\D+");
                List<Integer> list = IntStream.range(1, split.length)
                    .mapToObj(i -> split[i])
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());
                ratings.add(list);
            }
        }

        categories.put("x", 0);
        categories.put("m", 1);
        categories.put("a", 2);
        categories.put("s", 3);

        int sum = ratings.stream()
            .mapToInt(list -> accept(workflows, list, "in"))
            .sum();
        
        System.out.println(sum);
    }
    
    static int accept(Map<String, List<String>> workflow, List<Integer> rating, String name) {
        if (name.equals("A"))
            return rating.stream()
                .mapToInt(Integer::valueOf)
                .sum();
        if (name.equals("R"))
            return 0;

        List<String> rules = workflow.get(name);
        String sendTo;
        for (int i = 0; i < rules.size()-1; i++) {
            String[] split = rules.get(i).split(":");
            sendTo = split[1];
            String[] split2 = split[0].split("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)");
            int a = rating.get(categories.get(split2[0]));
            int b = Integer.parseInt(split2[2]);

            String oper = split2[1];
            switch (oper) {
                case "<":
                    if (a < b) return accept(workflow, rating, sendTo);
                    break;
                case ">":
                    if (a > b) return accept(workflow, rating, sendTo);
                    break;
            }
        }
        sendTo = rules.get(rules.size()-1);
        return accept(workflow, rating, sendTo);
    }
}