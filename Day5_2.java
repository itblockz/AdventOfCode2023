import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] in1 = sc.nextLine().split(":")[1].trim().split(" ");
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
        
        List<Long> ranges = List.of(in1).stream().map(e -> Long.parseLong(e)).collect(Collectors.toList());
        int size = ranges.size();
        long ans = Long.MAX_VALUE;
        for (int i = 0; i < size; i+=2) {
            long base = ranges.get(i);
            long limit = ranges.get(i+1);
            for (long j = base; j < base+limit; j++) {
                ArrayList<Long> seeds = new ArrayList<>();
                seeds.add(j);
                long subAns = min(seeds, map);
                ans = Math.min(ans, subAns);
            }
        }
        System.out.println(ans);
    }
    
    static long min(List<Long> seeds, ArrayList<ArrayList<long[]>> map) {
        int n = map.size();
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
        long min = Collections.min(list);
        return min;
    }
}
