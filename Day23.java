import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day23 {
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input23.txt"))
            .map(String::toCharArray)
            .toArray(char[][]::new);

        int xin = String.valueOf(map[0]).indexOf(".");
        int yin = 0;
        int xout = String.valueOf(map[map.length - 1]).indexOf(".");
        int yout = map.length - 1;

        char[][] mapCopy = Arrays.stream(map).map(c -> c.clone()).toArray(char[][]::new);
        int x = traverse(mapCopy, xin, yin, xout, yout);
        System.out.println(x);
    }

    static int traverse(char[][] map, int x, int y, int xout, int yout) {
        if (x == -1 || y == -1 || x == map[0].length || y == map.length) return 0;
        if (map[y][x] == '#' || map[y][x] == 'O') return 0;

        char cur = map[y][x];
        map[y][x] = 'O';
        if (x == xout && y == yout) return 0;

        char[][][] mapCopyArr = IntStream.range(0, 4).mapToObj(i -> {
            return Arrays.stream(map).map(c -> c.clone()).toArray(char[][]::new);
        }).toArray(char[][][]::new);
        int[] a = new int[4];
        switch (cur) {
            case '^':
                a[0] = traverse(mapCopyArr[0], x, y-1, xout, yout);
                break;
            case 'v':
                a[1] = traverse(mapCopyArr[1], x, y+1, xout, yout);
                break;
            case '<':
                a[2] = traverse(mapCopyArr[2], x-1, y, xout, yout);
                break;
            case '>':
                a[3] = traverse(mapCopyArr[3], x+1, y, xout, yout);
                break;
            default:
                a[0] = traverse(mapCopyArr[0], x, y-1, xout, yout);
                a[1] = traverse(mapCopyArr[1], x, y+1, xout, yout);
                a[2] = traverse(mapCopyArr[2], x-1, y, xout, yout);
                a[3] = traverse(mapCopyArr[3], x+1, y, xout, yout);
                break;
        }
        
        int max = Arrays.stream(a).max().getAsInt() + 1;
        return max;
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
