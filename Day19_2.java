import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day19_2 {
    static Map<String, Integer> categories = new HashMap<>();
    static final int N = 4000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Paths.get("input19.txt"))
            .collect(Collectors.toList());

        Map<String, List<String>> workflows = new HashMap<>();
        boolean beforeBlank = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                beforeBlank = false;
                break;
            }
            if (beforeBlank) {
                String[] split = line.split("\\{|\\}");
                String name = split[0];
                List<String> rules = Arrays.asList(split[1].split(","));
                workflows.put(name, rules);
            }
        }

        List<int[]> ratings = IntStream.range(0, 4)
            .mapToObj(i -> {
                int[] arr = new int[N];
                Arrays.fill(arr, 1);
                return arr;
            }).collect(Collectors.toList());

        categories.put("x", 0);
        categories.put("m", 1);
        categories.put("a", 2);
        categories.put("s", 3);

        long x = combination(workflows, ratings, "in");
        System.out.println(x);
    }
    
    static long combination(Map<String, List<String>> workflow, List<int[]> ratings, String name) {
        if (name.equals("A")) {
            long total = 1;
            for (int[] arr : ratings) {
                long x = Arrays.stream(arr).sum();
                total *= x;
            }
            return total;
        }
        if (name.equals("R")) return 0;

        List<String> rules = workflow.get(name);
        String sendTo;
        long sum = 0;
        for (int i = 0; i < rules.size()-1; i++) {
            String[] split = rules.get(i).split(":");
            sendTo = split[1];
            String[] split2 = split[0].split("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)");
            int cate = categories.get(split2[0]);
            int x = Integer.parseInt(split2[2]) - 1;
            
            int[] arr;
            List<int[]> alt;

            String oper = split2[1];
            switch (oper) {
                case "<":
                    alt = ratings.stream().map(a -> a.clone())
                        .collect(Collectors.toList());
                    
                    arr = ratings.get(cate);
                    Arrays.fill(arr, x, N, 0);
                    sum += combination(workflow, ratings, sendTo);

                    ratings = alt;
                    arr = ratings.get(cate);
                    Arrays.fill(arr, 0, x, 0);
                    break;
                case ">":
                    alt = ratings.stream().map(a -> a.clone())
                        .collect(Collectors.toList());

                    arr = ratings.get(cate);
                    Arrays.fill(arr, 0, x+1, 0);
                    sum += combination(workflow, ratings, sendTo);

                    ratings = alt;
                    arr = ratings.get(cate);
                    Arrays.fill(arr, x+1, N, 0);
                    break;
            }
        }
        sendTo = rules.get(rules.size()-1);
        return sum + combination(workflow, ratings, sendTo);
    }
}