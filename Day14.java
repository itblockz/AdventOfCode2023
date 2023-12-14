import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day14 {
    public static void main(String[] args) throws IOException {
        List<String> map = Files.lines(Paths.get("input14.txt")).collect(Collectors.toList());

        int sum = 0;
        for (int i = 0; i < map.get(0).length(); i++) {
            String col = col(map, i);
            List<Integer> hash = indicesOf(col, '#');
            List<Integer> ball =  Stream.of(col.replace(".", "")
                .split("#"))
                .map(String::length)
                .collect(Collectors.toList());

            int k = 0;
            for (int j = 0; j < ball.size(); j++) {
                int a1, n, x;
                if (j == 0) {
                    a1 = map.size();
                } else {
                    a1 = map.size() - (hash.get(k++)+1);
                }
                n = ball.get(j);
                x = n * (2*a1 - (n-1)) / 2;
                sum += x;
            }
        }
        System.out.println(sum);
    }

    static String col(List<String> map, int i) {
        return map.stream()
            .map(e -> String.valueOf(e.charAt(i)))
            .collect(Collectors.joining());
    }

    static List<Integer> indicesOf(String str, char target) {
        return IntStream.range(0, str.length())
            .filter(i -> str.charAt(i) == target)
            .boxed()
            .collect(Collectors.toList());
    } 
}
