import java.util.Scanner;

public class Day6_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in1 = sc.nextLine();
        String in2 = sc.nextLine();
        sc.close();
        String[] str1 = in1.split(":")[1].trim().split("\\s+");
        String[] str2 = in2.split(":")[1].trim().split("\\s+");
        long time = Long.parseLong(String.join("", str1));
        long dist = Long.parseLong(String.join("", str2));
        long ans = way(time, dist);
        System.out.println(ans);
    }

    static long way(long time, long dist) {
        long mid = time/2;
        long minTime = 0;
        for (long i = 0; i <= mid; i++) {
            long myDist = i * (time-i);
            if (myDist > dist) {
                minTime = i;
                break;
            }
        }
        long ans = time+1 - minTime*2;
        return ans;
    }
}
