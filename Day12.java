import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {
    public static void main(String[] args) throws IOException {
        int sum = Files.lines(Paths.get("input12.txt")).mapToInt(line -> {
            String[] split = line.split(" ");
            String str = split[0];
            List<Integer> list = Stream.of(split[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
            int x = solve(str, list);
            return x;
        }).sum();
        System.out.println(sum);
    }

    static int solve(String str, List<Integer> list) {
        int n = list.stream().mapToInt(Integer::intValue).sum();
        int qmark = str.length() - str.replace("?", "").length();
        int sharp = str.length() - str.replace("#", "").length();
        List<List<Integer>> res = combine(qmark, n-sharp);
        List<Integer> qmarkIdx = new ArrayList<>();
        String tmp = str;
        for (int i = 0; i < qmark; i++) {
            qmarkIdx.add(tmp.indexOf('?'));
            tmp = tmp.replaceFirst("\\?", "*");
        }

        int cnt = 0;
        for (List<Integer> comb : res) {
            StringBuilder sb = new StringBuilder(str);
            for (Integer i : comb) {
                int idx = qmarkIdx.get(i);
                sb.replace(idx, idx+1, "#");
            }
            String newStr = sb.toString().replace("?", ".");
            if (isPossible(newStr, list)) cnt++;
        }
        return cnt;
    }

    static boolean isPossible(String str, List<Integer> list) {
        String trim = str.replaceAll("^\\.+|\\.+$", "");
        String[] split = trim.split("\\.+");
        List<Integer> sharp = Stream.of(split).map(String::length).collect(Collectors.toList());
        return sharp.equals(list);
    }

    static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(n, k, 0, new ArrayList<>(), res);
        return res;
    }

    static void backtrack(int n, int k, int start, List<Integer> comb, List<List<Integer>> res) {
        if (comb.size() == k) {
            res.add(new ArrayList<>(comb));
            return;
        }
        for (int i = start; i < n; i++) {
            comb.add(i);
            backtrack(n, k, i+1, comb, res);
            comb.remove(comb.size()-1);
        }
    }
}