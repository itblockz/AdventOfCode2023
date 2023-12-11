import java.util.Scanner;
import java.util.stream.Stream;

public class Day6 {
    static int[] time;
    static int[] dist;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in1 = sc.nextLine();
        String in2 = sc.nextLine();
        sc.close();
        String[] str1 = in1.split(":")[1].trim().split("\\s+");
        String[] str2 = in2.split(":")[1].trim().split("\\s+");
        time = Stream.of(str1).mapToInt(Integer::parseInt).toArray();
        dist = Stream.of(str2).mapToInt(Integer::parseInt).toArray();
        int ans = 1;
        for (int i = 0; i < time.length; i++) {
            int way = way(i);
            ans *= way;
        }
        System.out.println(ans);
        
    }

    static int way(int idx) {
        int mid = time[idx]/2;
        int minTime = 0;
        for (int i = 0; i <= mid; i++) {
            int myDist = i * (time[idx]-i);
            if (myDist > dist[idx]) {
                minTime = i;
                break;
            }
        }
        int ans = time[idx]+1 - minTime*2;
        return ans;
    }
}
