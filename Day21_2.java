import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day21_2 {
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input21.txt"))
            .map(String::toCharArray)
            .toArray(char[][]::new);
        
        int m = map.length;
        int n = map[0].length;
        int sx, sy;
        sx = sy = 0;
        label:
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'S') {
                    sx = j;
                    sy = i;
                    break label;
                }
            }
        }

        char[][] mem = new char[m][n];
        Arrays.stream(mem).forEach(row -> Arrays.fill(row, ' '));

        long x = step2(map, sx, sy, 26501365);
        System.out.println(x);
    }

    static long step2(char[][] map, int sx, int sy, int steps) {
        int r = map.length / 2;
        int fullOdd = step(map, sx, sy, r*2);
        int fullEven = step(map, sx, sy, r*2 - 1);
        int halfOdd = step(map, sx, sy, r);
        int halfEven = step(map, sx, sy, r-1);
        int cornerOdd = fullOdd - halfOdd;
        int cornerEven = fullEven - halfEven;
        
        long n = (steps - r) / map.length;

        long x = fullOdd*((n+1)*(n+1)) + fullEven*(n*n) - cornerOdd*(n+1) + cornerEven*n;
        return x;
    }

    static int step(char[][] map, int sx, int sy, int n) {
        char[][] mem = new char[map.length][map[0].length];
        Arrays.stream(mem).forEach(row -> Arrays.fill(row, ' '));

        List<Integer> xq = new ArrayList<>();
        List<Integer> yq = new ArrayList<>();
        List<Integer> stepQ = new ArrayList<>();

        xq.add(sx);
        yq.add(sy);
        stepQ.add(0);

        int cnt = 0;
        while (!xq.isEmpty()) {
            int x = xq.remove(0);
            int y = yq.remove(0);
            int step = stepQ.remove(0);
            if (x == -1 || x == map[0].length || y == -1 || y == map.length) continue;
            if (map[y][x] == '#') continue;
            if (mem[y][x] == 'O') continue;
            int dx = x - sx;
            int dy = y - sy;
            if (n%2 == 0 && Math.abs((dx+dy)%2) == 0)  {
                mem[y][x] = 'O';
                cnt++;
            }
            else if (n%2 == 1 && Math.abs((dx+dy)%2) == 1) {
                mem[y][x] = 'O';
                cnt++;
            }
            if (step == n) continue;

            xq.add(x); yq.add(y-1); stepQ.add(step+1);
            xq.add(x); yq.add(y+1); stepQ.add(step+1);
            xq.add(x-1); yq.add(y); stepQ.add(step+1);
            xq.add(x+1); yq.add(y); stepQ.add(step+1);
        }
        return cnt;
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
