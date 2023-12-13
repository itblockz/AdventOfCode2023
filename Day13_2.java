import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13_2 {
    static final int SMUDGE = 1;
    public static void main(String[] args) throws IOException {
        List<List<String>> list = new ArrayList<>();
        List<String> in = Files.lines(Paths.get("input13.txt")).collect(Collectors.toList());
        List<String> map = new ArrayList<>();
        list.add(map);
        for (String line : in) {
            if (!line.isEmpty()) {
                map.add(line);
            } else {
                map = new ArrayList<>();
                list.add(map);
            }
        }

        int sum = 0;
        for (List<String> m : list) {
            for (int i = 1; i < m.get(0).length(); i++) {
                if (colReflect(m, i)) {
                    int x = i;
                    sum += x;
                }
            }
            for (int i = 1; i < m.size(); i++) {
                if (rowReflect(m, i)) {
                    int x = 100*i;
                    sum += x;
                }
            }
        }
        System.out.println(sum);
    }

    static int diff(String s1, String s2) {
        int cnt = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) cnt++;
        }
        return cnt;
    }

    static boolean colReflect(List<String> map, int c) {
        int min = Math.min(c, map.get(0).length()-c);
        int cnt = 0;
        for (String row : map) {
            String left = row.substring(c-min, c);
            String right = new StringBuilder(row.substring(c, c+min)).reverse().toString();
            cnt += diff(left, right);
        }
        if (cnt != SMUDGE) return false;
        return true;
    }

    static boolean rowReflect(List<String> map, int r) {
        int min = Math.min(r, map.size()-r);
        int cols = map.get(0).length();
        int cnt = 0;
        for (int i = 0; i < cols; i++) {
            int j = i;
            String col = map.stream().map(e -> String.valueOf(e.charAt(j)))
                .collect(Collectors.joining());
            String up = col.substring(r-min, r);
            String down = new StringBuilder(col.substring(r, r+min)).reverse().toString();
            cnt += diff(up, down);
        }
        if (cnt != SMUDGE) return false;
        return true;
    }
}
