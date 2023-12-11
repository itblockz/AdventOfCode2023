import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    static ArrayList<String> map;
    static int sx, sy;
    static ArrayList<Character> north, west, east, south;
    static int m, n;
    public static void main(String[] args) throws IOException {
        map = new ArrayList<>();
        Files.lines(Paths.get("input10.txt")).forEach(line -> {
            System.out.println(line);
            map.add(line);
        });

        sx = sy = -1;
        m = map.size();
        n = map.get(0).length();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map.get(i).charAt(j) == 'S') {
                    sx = j;
                    sy = i;
                }
            }
        }
        
        north = new ArrayList<>(Arrays.asList('|', 'L', 'J'));
        west = new ArrayList<>(Arrays.asList('-', '7', 'J'));
        east = new ArrayList<>(Arrays.asList('-', 'L', 'F'));
        south = new ArrayList<>(Arrays.asList('|', '7', 'F'));

        int[] steps = new int[4];
        steps[0] = step(sx, sy-1);
        steps[1] = step(sx-1, sy);
        steps[2] = step(sx+1, sy);
        steps[3] = step(sx, sy+1);
        List<Integer> list = Arrays.stream(steps).boxed().collect(Collectors.toList());
        int max = Collections.max(list);
        int ans = max / 2;
        System.out.println(ans);
    }

    static int step(int xNext, int yNext) {
        if (xNext == -1 || yNext == -1 || xNext == n || yNext == m) return 0;
        int x = sx;
        int y = sy;
        int cnt = 1;
        char next = map.get(yNext).charAt(xNext);
        boolean fromNorth = yNext == y+1;
        boolean fromWest = xNext == x+1;
        boolean fromEast = xNext == x-1;
        boolean fromSouth = yNext == y-1;
        if (fromNorth && !north.contains(next)) return 0;
        if (fromWest && !west.contains(next)) return 0;
        if (fromEast && !east.contains(next)) return 0;
        if (fromSouth && !south.contains(next)) return 0;
        while (next != 'S') {
            x = xNext;
            y = yNext;
            if (!fromNorth && north.contains(next)) yNext--;
            if (!fromWest && west.contains(next)) xNext--;
            if (!fromEast && east.contains(next)) xNext++;
            if (!fromSouth && south.contains(next)) yNext++;
            if (xNext == -1 || yNext == -1 || xNext == n || yNext == m) break;
            fromNorth = yNext == y+1;
            fromWest = xNext == x+1;
            fromEast = xNext == x-1;
            fromSouth = yNext == y-1;
            next = map.get(yNext).charAt(xNext);
            cnt++;
        }
        return cnt;
    }
}
