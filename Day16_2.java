import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day16_2 {
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input16.txt")).map(String::toCharArray).toArray(char[][]::new);
        
        int max = 0;
        int m = map.length;
        int n = map[0].length;
        for (int y = 0; y < m; y++) {
            char[][] mark = new char[m][n];
            Stream.of(mark).forEach(row -> Arrays.fill(row, '.'));
            List<Character>[][] visited = new List[m][n];
            beam(map, mark, visited, 0, y, 'R');
            int cnt = Stream.of(mark).map(String::valueOf)
                .mapToInt(str -> str.replace(".", "").length())
                .sum();
            max = Math.max(max, cnt);
        }
        for (int y = 0; y < m; y++) {
            char[][] mark = new char[m][n];
            Stream.of(mark).forEach(row -> Arrays.fill(row, '.'));
            List<Character>[][] visited = new List[m][n];
            beam(map, mark, visited, n-1, y, 'L');
            int cnt = Stream.of(mark).map(String::valueOf)
                .mapToInt(str -> str.replace(".", "").length())
                .sum();
            max = Math.max(max, cnt);
        }
        for (int x = 0; x < m; x++) {
            char[][] mark = new char[m][n];
            Stream.of(mark).forEach(row -> Arrays.fill(row, '.'));
            List<Character>[][] visited = new List[m][n];
            beam(map, mark, visited, x, 0, 'D');
            int cnt = Stream.of(mark).map(String::valueOf)
                .mapToInt(str -> str.replace(".", "").length())
                .sum();
            max = Math.max(max, cnt);
        }
        for (int x = 0; x < m; x++) {
            char[][] mark = new char[m][n];
            Stream.of(mark).forEach(row -> Arrays.fill(row, '.'));
            List<Character>[][] visited = new List[m][n];
            beam(map, mark, visited, x, m-1, 'U');
            int cnt = Stream.of(mark).map(String::valueOf)
                .mapToInt(str -> str.replace(".", "").length())
                .sum();
            max = Math.max(max, cnt);
        }
        System.out.println(max);
    }

    static void beam(char[][] map, char[][] mark, List<Character>[][] visited, int x, int y, char dir) {
        if (y == -1 || y == map.length) return;
        if (x == -1 || x == map[0].length) return;
        if (visited[y][x] == null) visited[y][x] = new ArrayList<>();
        if (visited[y][x].contains(dir)) return;

        mark[y][x] = '#';

        char dir2 = dir;
        boolean split = false;

        char tile = map[y][x];
        if ((dir == 'L' || dir == 'R') && tile == '|') {
            visited[y][x].add('L');
            visited[y][x].add('R');
            dir = 'U';
            dir2 = 'D';
            split = true;
        } else if ((dir == 'U' || dir == 'D') && tile == '-') {
            visited[y][x].add('U');
            visited[y][x].add('D');
            dir = 'L';
            dir2 = 'R';
            split = true;
        } else if (tile == '/') {
            visited[y][x].add(dir);
            if (dir == 'U') dir = 'R';
            else if (dir == 'D') dir = 'L';
            else if (dir == 'L') dir = 'D';
            else if (dir == 'R') dir = 'U';
        } else if (tile == '\\') {
            visited[y][x].add(dir);
            if (dir == 'U') dir = 'L';
            else if (dir == 'D') dir = 'R';
            else if (dir == 'L') dir = 'U';
            else if (dir == 'R') dir = 'D';
        }
        
        int dx, dy;
        dx = dy = 0;
        switch (dir) {
            case 'U':
                dy = -1;
                break;
            case 'D':
                dy = 1;
                break;
            case 'L':
                dx = -1;
                break;
            case 'R':
                dx = 1;
                break;
        }

        beam(map, mark, visited, x+dx, y+dy, dir);
        if (split) {
            beam(map, mark, visited, x-dx, y-dy, dir2);
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
}
