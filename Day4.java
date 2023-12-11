import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while (sc.hasNextLine()) {
            // input
            String input = sc.nextLine();
            String[] step1 = input.split(":");
            String[] step2 = step1[1].split("\\|");
            String[] win = step2[0].trim().split("\\s+");
            String[] my = step2[1].trim().split("\\s+");

            // process
            int cnt = 0;
            for (String w : win) {
                for (String m : my) {
                    if (w.equals(m)) {
                        cnt++;
                        break;
                    }
                }
            }
            int ans = 1 << (cnt-1);
            if (cnt == 0) ans = 0;
            sum += ans;
        }
        sc.close();
        System.out.println(sum);
    }
}