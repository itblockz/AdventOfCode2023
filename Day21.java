import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Day21 {
    public static void main(String[] args) throws IOException {
        char[][] map = Files.lines(Paths.get("input21.txt"))
            .map(String::toCharArray)
            .toArray(char[][]::new);
        
        int sx, sy;
        sx = sy = 0;
        label:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'S') {
                    sx = j;
                    sy = i;
                    break label;
                }
            }
        }

        int x = step(map, sx, sy, '#', 64);
        System.out.println(x);
    }

    static int step(char[][] map, int sx, int sy, char rock, int n) {
        Set<Pair<Integer, Point>> q = new LinkedHashSet<>();
        q.add(new Pair<>(0, new Point(sx, sy)));

        int cnt = 0;
        while (!q.isEmpty()) {
            Iterator<Pair<Integer, Point>> iter = q.iterator();
            Pair<Integer, Point> pair = iter.next();
            iter.remove();

            int i = pair.getKey();
            Point p = pair.getValue();
            int x = p.x;
            int y = p.y;
            if (x == -1 || x == map[0].length || y == -1 || y == map.length) continue;
            if (map[y][x] == rock) continue;
            if (i == n) {
                cnt++;
                continue;
            }
            q.add(new Pair<>(i+1, new Point(x, y-1)));
            q.add(new Pair<>(i+1, new Point(x, y+1)));
            q.add(new Pair<>(i+1, new Point(x-1, y)));
            q.add(new Pair<>(i+1, new Point(x+1, y)));
        }
        return cnt;
    }
}
