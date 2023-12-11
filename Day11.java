import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    public static void main(String[] args) throws IOException {
        List<List<Character>> map = Files.lines(Paths.get("input11.txt")) 
            .map(str -> {
                return str.chars()
                    .mapToObj(c -> (char)c)
                    .collect(Collectors.toList());
            }).collect(Collectors.toList());
        expand(map);

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
        
        int sum = 0;
        for (int i = 0; i < x.size()-1; i++) {
            for (int j = i+1; j < x.size(); j++) {
                sum += dist(map, x.get(i), y.get(i), x.get(j), y.get(j));
            }
        }
        System.out.println(sum);
    }

    static int dist(List<List<Character>> map, int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
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
        for (int i = rows-1; i >= 0; i--) {
            if (rowContains(map, i, '.')) {
                insertRow(map, i, '.');
            }
        }
        for (int i = cols-1; i >= 0; i--) {
            if (colContains(map, i, '.')) {
                insertCol(map, i, '.');
            }
        }
    }

    static void insertRow(List<List<Character>> map, int idx, char c) {
        int size = map.get(0).size();
        ArrayList<Character> row = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            row.add(c);
        }
        map.add(idx, row);
    }

    static void insertCol(List<List<Character>> map, int idx, char c) {
        int size = map.size();
        for (int i = 0; i < size; i++) {
            map.get(i).add(idx, c);
        }
    }

    static boolean rowContains(List<List<Character>> map, int idx, char c) {
        int size = map.get(0).size();
        for (int i = 0; i < size; i++) {
            if (map.get(idx).get(i) != c) return false;
        }
        return true;
    }

    static boolean colContains(List<List<Character>> map, int idx, char c) {
        int size = map.size();
        for (int i = 0; i < size; i++) {
            if (map.get(i).get(idx) != c) return false;
        }
        return true;
    }
}