import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22 {
    public static void main(String[] args) throws IOException {
        List<int[]> brickList = Files.lines(Paths.get("input22.txt"))
            .map(s -> Arrays.stream(s.split("~|,"))
                .mapToInt(Integer::parseInt)
                .toArray()
            ).sorted(Comparator.comparing(a -> a[a.length - 1]))
            .collect(Collectors.toList());

        int m, n;
        m = n = 0;
        for (int[] brick  : brickList) {
            int x = Math.max(brick[0], brick[3]);
            int y = Math.max(brick[1], brick[4]);
            n = Math.max(n, x);
            m = Math.max(m, y);
        }
        
        int[][] level = new int[m+1][n+1];
        int[][][] top = new int[m+1][n+1][6];
        Set<int[]> supportByOne = new HashSet<>();
        for (int[] brick : brickList) {
            int dz = brick[5] - brick[2] + 1;
            int h = getHeight(level, brick);
            int[] s = h == 0 ? null : supportByOne(top, level, brick, h);
            if (s != null) supportByOne.add(s);
            fill(top, level, brick, h+dz);
        }

        int x = brickList.size() - supportByOne.size();
        System.out.println(x);
    }

    static int[] supportByOne(int[][][] top, int[][] level, int[] brick, int h) {
        int[] support = null;
        for (int row = brick[1]; row <= brick[4]; row++) {
            for (int col = brick[0]; col <= brick[3]; col++) {
                if (level[row][col] == h) {
                    if (support == null) support = top[row][col];
                    else if (support != top[row][col]) return null;
                }
            }
        }
        return support;
    }

    static int getHeight(int[][] level, int[] brick) {
        int max = 0;
        for (int row = brick[1]; row <= brick[4]; row++) {
            for (int col = brick[0]; col <= brick[3]; col++) {
                max = Math.max(max, level[row][col]);
            }
        }
        return max;
    }

    static void fill(int[][][] top, int[][] level, int[] brick, int val) {
        for (int row = brick[1]; row <= brick[4]; row++) {
            for (int col = brick[0]; col <= brick[3]; col++) {
                top[row][col] = brick;
                level[row][col] = val;
            }
        }
    }

    static void print(int[][] a) {
        for (int[] row : a) {
            for (int i : row) {
                System.out.print(i);
            }
            System.out.println();
        }
    }

    static void print(int[][][] a) {
        for (int[][] row : a) {
            for (int[] i : row) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }
}