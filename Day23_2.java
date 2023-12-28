import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day23_2 {
    static int max = 0;
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input23.txt"))
            .map(String::toCharArray)
            .toArray(char[][]::new);

        int xin = 0;
        int yin = String.valueOf(map[0]).indexOf(".");
        int xout = map.length - 1;

        dfs(map, xin, yin, xout, 0);
        System.out.println(max);
    }

    static void dfs(char[][] map, int x, int y, int xout, int steps) {
        if (!isValidMove(map, x, y)) return;
        if (x == xout) {
            max = Math.max(max, steps);
            return;
        }

        char original = map[x][y];
        map[x][y] = 'O';

        int[] dx = { 0, 0, -1, 1 };
        int[] dy = { -1, 1, 0, 0 };

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            dfs(map, nx, ny, xout, steps + 1);
        }

        map[x][y] = original;
    }

    static boolean isValidMove(char[][] map, int x, int y) {
        return x >= 0 && x < map[0].length && y >= 0 && y < map.length && map[x][y] != '#' && map[x][y] != 'O';
    }

    static void print(char[][] map) {
        for (char[] row : map) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    static void print(int[][] a) {
        for (int[] row : a) {
            for (int i : row) {
                System.out.printf("%3s ", i);
            }
            System.out.println();
        }
        System.out.println();
    }
}
