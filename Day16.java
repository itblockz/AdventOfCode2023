import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day16 {
    final static int TIME_OUT = 1000;
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input16.txt")).map(String::toCharArray).toArray(char[][]::new);
        int m = map.length;
        int n = map[0].length;
        char[][] mark = new char[m][n];
        Stream.of(mark).forEach(row -> Arrays.fill(row, '.'));
        beam(map, mark, 0, 0, 'R', 0);

        int cnt = Stream.of(mark).map(String::valueOf)
            .mapToInt(str -> str.replace(".", "").length())
            .sum();
        System.out.println(cnt);
    }

    static void beam(char[][] map, char[][] mark, int x, int y, char dir, int time) {
        if (time == TIME_OUT) return;
        if (y == -1 || y == map.length) return;
        if (x == -1 || x == map[0].length) return;

        mark[y][x] = '#';

        char dir2 = dir;
        boolean split = false;

        char tile = map[y][x];
        if ((dir == 'L' || dir == 'R') && tile == '|') {
            dir = 'U';
            dir2 = 'D';
            split = true;
        } else if ((dir == 'U' || dir == 'D') && tile == '-') {
            dir = 'L';
            dir2 = 'R';
            split = true;
        } else if (tile == '/') {
            if (dir == 'U') dir = 'R';
            else if (dir == 'D') dir = 'L';
            else if (dir == 'L') dir = 'D';
            else if (dir == 'R') dir = 'U';
        } else if (tile == '\\') {
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

        beam(map, mark, x+dx, y+dy, dir, time+1);
        if (split) {
            beam(map, mark, x-dx, y-dy, dir2, time+1);
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
