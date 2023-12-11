import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] in1 = sc.nextLine().split(":")[1].trim().split(" ");
        List<Long> seeds = List.of(in1).stream().map(e -> Long.parseLong(e)).collect(Collectors.toList());
        sc.nextLine();
        int n = 7;
        ArrayList<ArrayList<long[]>> map = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            map.add(new ArrayList<>());
            sc.nextLine();
            String in2 = sc.nextLine();
            while (!in2.isEmpty()) {
                String[] d = in2.split(" ");
                long[] details = {
                    Long.parseLong(d[0]),
                    Long.parseLong(d[1]),
                    Long.parseLong(d[2])
                };
                map.get(i).add(details);
                if (!sc.hasNextLine()) break;
                in2 = sc.nextLine();
            }
        }
        sc.close();
        
        Stream<Long> s = seeds.stream();
        for (int i = 0; i < n; i++) {
            int j = i;
            Stream<Long> newS = s.map(source -> {
                for (long[] details : map.get(j)) {
                    if (source >= details[1] && source < details[1]+details[2]) {
                        return source + details[0] - details[1];
                    }
                }
                return source;
            });
            s = newS;
        }
        List<Long> list = s.collect(Collectors.toList());
        long ans = Collections.min(list);
        System.out.println(ans);
    }
}
