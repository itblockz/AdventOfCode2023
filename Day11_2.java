import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11_2 {
    public static void main(String[] args) throws IOException {
        List<List<Character>> map = Files.lines(Paths.get("input11.txt")) 
            .map(str -> {
                return str.chars()
                    .mapToObj(c -> (char)c)
                    .collect(Collectors.toList());
            }).collect(Collectors.toList());
        expand(map);
        // print(map);

        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        int rows = map.size();
        int cols = map.get(0).size();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map.get(i).get(j) == '#') {
                    x.add(j);
                    y.add(i);
                }
            }
        }
        
        long sum = 0;
        for (int i = 0; i < x.size()-1; i++) {
            for (int j = i+1; j < x.size(); j++) {
                long dist = dist(map, x.get(i), y.get(i), x.get(j), y.get(j));
                sum += dist;
            }
        }
        System.out.println(sum);

    }

    static long dist(List<List<Character>> map, int x1, int y1, int x2, int y2) {
        final int times = 1_000_000;
        long cnt = 0;
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        for (int i = x1; i < x2; i++) {
            if (map.get(y1).get(i) == '*') cnt += times;
            else cnt++;
        }
        for (int i = y1; i < y2; i++) {
            if (map.get(i).get(x2) == '*') cnt += times;
            else cnt++;
        }
        return cnt;
    }

    static void print(List<List<Character>> map) {
        for (List<Character> row : map) {
            for (Character c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    static void expand(List<List<Character>> map) {
        int rows = map.size();
        int cols = map.get(0).size();
        char replace = '*';
        String regex = "\\.|\\*";
        for (int i = rows-1; i >= 0; i--) {
            if (rowContains(map, i, regex)) {
                replaceRow(map, i, replace);
            }
        }
        for (int i = cols-1; i >= 0; i--) {
            if (colContains(map, i, regex)) {
                replaceCol(map, i, replace);
            }
        }
    }

    static void replaceRow(List<List<Character>> map, int idx, char c) {
        int cols = map.get(0).size();
        for (int i = 0; i < cols; i++) {
            map.get(idx).set(i, c);
        }
    }

    static void replaceCol(List<List<Character>> map, int idx, char c) {
        int rows = map.size();
        for (int i = 0; i < rows; i++) {
            map.get(i).set(idx, c);
        }
    }

    static void insertRow(List<List<Character>> map, int idx, char c) {
        int cols = map.get(0).size();
        map.add(idx, new ArrayList<>());
        for (int i = 0; i < cols; i++) {
            map.get(idx).add(c);
        }
    }

    static void insertCol(List<List<Character>> map, int idx, char c) {
        int rows = map.size();
        for (int i = 0; i < rows; i++) {
            map.get(i).add(idx, c);
        }
    }

    static boolean rowContains(List<List<Character>> map, int idx, String regex) {
        int cols = map.get(0).size();
        for (int i = 0; i < cols; i++) {
            if (!String.valueOf(map.get(idx).get(i)).matches(regex)) return false;
        }
        return true;
    }

    static boolean colContains(List<List<Character>> map, int idx, String regex) {
        int rows = map.size();
        for (int i = 0; i < rows; i++) {
            if (!String.valueOf(map.get(i).get(idx)).matches(regex)) return false;
        }
        return true;
    }
}