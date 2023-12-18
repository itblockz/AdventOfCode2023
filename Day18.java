import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day18 {
    public static void main(String[] args) throws IOException {
        List<Character> dir = new ArrayList<>();
        List<Integer> num = new ArrayList<>();
        List<String> color = new ArrayList<>();
        Files.lines(Paths.get("input18.txt")).forEach(line -> {
            String[] split = line.split(" ");
            dir.add(split[0].charAt(0));
            num.add(Integer.parseInt(split[1]));
            color.add(split[2]);
        });
        
        int midX = IntStream.range(0, dir.size())
            .filter(d -> dir.get(d) == 'R')
            .map(num::get)
            .sum() + 1;
        int midY = IntStream.range(0, dir.size())
            .filter(d -> dir.get(d) == 'D')
            .map(num::get)
            .sum() + 1;

        char[][] map = new char[midY*2 + 3][midX*2 + 3];
        Stream.of(map).forEach(e -> Arrays.fill(e, '.'));
        
        Map<Character, Integer> xMap = new HashMap<>();
        Map<Character, Integer> yMap = new HashMap<>();
        xMap.put('U', 0);
        xMap.put('D', 0);
        xMap.put('L', -1);
        xMap.put('R', 1);
        yMap.put('U', -1);
        yMap.put('D', 1);
        yMap.put('L', 0);
        yMap.put('R', 0);
        
        int x = midX;
        int y = midY;
        for (int i = 0; i < dir.size(); i++) {
            int dx = xMap.get(dir.get(i));
            int dy = yMap.get(dir.get(i));
            int n = num.get(i);
            for (int j = 0; j < n; j++) {
                y += dy;
                x += dx;
                map[y][x] = '#';
            }
        }
        
        fill(map, '#');

        int cnt = Stream.of(map)
            .map(String::valueOf)
            .mapToInt(str -> str.replace(".", "").length())
            .sum();

        System.out.println(cnt);
    }

    static void fill(char[][] map, char replacement) {
        int x = 0;
        int y = 0;
        label:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == replacement) {
                    if (map[i][j+1] != replacement) {
                        y = i;
                        x = j+1;
                        break label;
                    }
                    break;
                }
            }
        }
        // fillRecur(map, x, y, replacement); // stack overflow
        fillIter(map, x, y, replacement);
    }

    static void fillRecur(char[][] map, int x, int y, char replacement) {
        if (x == -1 || x == map[0].length || y == -1 || y == map.length) return;
        if (map[y][x] == replacement) return;

        map[y][x] = replacement;

        fillRecur(map, x, y-1, replacement);
        fillRecur(map, x, y+1, replacement);
        fillRecur(map, x-1, y, replacement);
        fillRecur(map, x+1, y, replacement);
    }


static void fillIter(char[][] map, int x, int y, char replacement) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[] { x, y });
    while (!stack.isEmpty()) {
        int[] cur = stack.pop();
        int curX = cur[0];
        int curY = cur[1];

        if (curX >= 0 && curX < map[0].length && curY >= 0 && curY < map.length && map[curY][curX] != replacement) {
            map[curY][curX] = replacement;

            stack.push(new int[] { curX, curY-1 });
            stack.push(new int[] { curX, curY+1 });
            stack.push(new int[] { curX-1, curY });
            stack.push(new int[] { curX+1, curY });
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
}
