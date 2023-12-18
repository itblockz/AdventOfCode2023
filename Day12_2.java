import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12_2 {
    public static void main(String[] args) throws IOException {
        long sum = Files.lines(Paths.get("input12.txt")).mapToLong(line -> {
            String[] split = line.split(" ");
            String str = split[0];
            List<Integer> num = Stream.of(split[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
            long x = solve(str, num);
            return x;
        }).sum();
        System.out.println(sum);
    }

    static long solve(String strCopy, List<Integer> numCopy) {
        int copy = 5;
        List<Integer> num = new ArrayList<>();
        for (int i = 0; i < copy; i++) num.addAll(numCopy);
        String str = strCopy + ("?"+strCopy).repeat(copy-1);
        long x = recur(str, num, new HashMap<>(), new HashMap<>());
        return x;
    }

    static long recur(String str, List<Integer> num, Map<String, Long> map, Map<String, List<Integer>> listMap) {
        if (map.containsKey(str) && listMap.get(str).equals(num)) return map.get(str);
        if (str.isEmpty() && num.isEmpty()) return 1;
        if (str.isEmpty() && !num.isEmpty()) return 0;

        long x = 0;
        char start = str.charAt(0);
        switch (start) {
            case '.':
                x = recur(str.substring(1), num, map, listMap);
                break;
            case '?':
                long a = recur(str.replaceFirst("\\?", "\\."), num, map, listMap);
                long b = recur(str.replaceFirst("\\?", "#"), num, map, listMap);
                x = a + b;
                break;
            case '#':
                if (num.isEmpty()) {
                    x = 0;
                    break;
                }
                int n = num.get(0);
                if (str.length() >= n && str.substring(0, n).contains(".")){
                    x = 0;
                    break;
                }
                List<Integer> copy = new ArrayList<>(num);
                copy.remove(0);
                if (str.length() == n) {
                    x = recur(str.substring(n), copy, map, listMap);
                } else if (str.length() > n && str.charAt(n) != '#') {
                    x = recur(str.substring(n+1), copy, map, listMap);
                } else {
                    x = 0;
                }
                break;
        }

        map.put(str, x);
        listMap.put(str, num);

        return x;
    }
}