import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day14_2 {
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input14.txt"))
            .map(String::toCharArray)
            .toArray(char[][]::new);

        final int N = 1000;
        for (int i = 0; i < N; i++) {
            tiltNorth(map);
            tiltWest(map);
            tiltSouth(map);
            tiltEast(map);
        }
        int x = eval(map);
        System.out.println(x);

    }

    static int eval(char[][] map) {
        int sum = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'O') {
                    int x = map.length - i;
                    sum += x;
                }
            }
        }
        return sum;
    }

    static void tiltEast(char[][] map) {
        int n_cols = map[0].length;
        int n_rows = map.length;
        for (int row = 0; row < n_rows; row++) {
            String str = row(map, row);
            String rev = new StringBuilder(str)
                .reverse()
                .toString();
            List<Integer> hash = indicesOf(str, '#');
            List<Integer> ball =  ball(rev);

            fillRow(map, row, '.');
            fillRow(map, row, '#', hash);

            int k = hash.size() - 1;
            for (int i = 0; i < ball.size(); i++) {
                int a1, n;
                if (i == 0) {
                    a1 = n_cols - 1;
                } else {
                    a1 = hash.get(k--) - 1;
                }
                n = ball.get(i);
                
                for (int col = a1; col > a1-n; col--) {
                    map[row][col] = 'O';
                }
            }
        }
    }

    static void tiltSouth(char[][] map) {
        int n_cols = map[0].length;
        int n_rows = map.length;
        for (int col = 0; col < n_cols; col++) {
            String str = col(map, col);
            String rev = new StringBuilder(str)
                .reverse()
                .toString();
            List<Integer> hash = indicesOf(str, '#');
            List<Integer> ball =  ball(rev);

            fillCol(map, col, '.');
            fillCol(map, col, '#', hash);

            int k = hash.size() - 1;
            for (int i = 0; i < ball.size(); i++) {
                int a1, n;
                if (i == 0) {
                    a1 = n_rows - 1;
                } else {
                    a1 = hash.get(k--) - 1;
                }
                n = ball.get(i);
                
                for (int row = a1; row > a1-n; row--) {
                    map[row][col] = 'O';
                }
            }
        }
    }

    static void tiltWest(char[][] map) {
        int n_rows = map.length;
        for (int row = 0; row < n_rows; row++) {
            String str = row(map, row);
            List<Integer> hash = indicesOf(str, '#');
            List<Integer> ball =  ball(str);

            fillRow(map, row, '.');
            fillRow(map, row, '#', hash);
            int k = 0;
            for (int i = 0; i < ball.size(); i++) {
                int a1, n;
                if (i == 0) {
                    a1 = 0;
                } else {
                    a1 = hash.get(k++) + 1;
                }
                n = ball.get(i);
                
                for (int col = a1; col < a1+n; col++) {
                    map[row][col] = 'O';
                }
            }
        }
    }

    static void tiltNorth(char[][] map) {
        int n_cols = map[0].length;
        for (int col = 0; col < n_cols; col++) {
            String str = col(map, col);
            List<Integer> hash = indicesOf(str, '#');
            List<Integer> ball =  ball(str);

            fillCol(map, col, '.');
            fillCol(map, col, '#', hash);
            int k = 0;
            for (int i = 0; i < ball.size(); i++) {
                int a1, n;
                if (i == 0) {
                    a1 = 0;
                } else {
                    a1 = hash.get(k++) + 1;
                }
                n = ball.get(i);
                
                for (int row = a1; row < a1+n; row++) {
                    map[row][col] = 'O';
                }
            }
        }
    }

    static void print(char[][] map) {
        for (char[] row : map) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    static void fillRow(char[][] map, int idx, char c, List<Integer> indices) {
        for (Integer i : indices) {
            map[idx][i] = c;
        }
    }

    static void fillRow(char[][] map, int idx, char c) {
        for (int i = 0; i < map[0].length; i++) {
            map[idx][i] = c;
        }
    }

    static void fillCol(char[][] map, int idx, char c, List<Integer> indices) {
        for (Integer i : indices) {
            map[i][idx] = c;
        }
    }

    static void fillCol(char[][] map, int idx, char c) {
        for (int i = 0; i < map.length; i++) {
            map[i][idx] = c;
        }
    }

    static String col(char[][] map, int i) {
        return Stream.of(map)
            .map(c -> String.valueOf(c[i]))
            .collect(Collectors.joining());
    }

    static String row(char[][] map, int i) {
        return new String(map[i]);
    }

    static List<Integer> ball(String row) {
        return Stream.of(row.replace(".", "").split("#"))
            .map(String::length)
            .collect(Collectors.toList());
    }

    static List<Integer> indicesOf(String str, char c) {
        return IntStream.range(0, str.length())
            .filter(i -> str.charAt(i) == c)
            .boxed()
            .collect(Collectors.toList());
    }
}
