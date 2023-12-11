import java.util.Scanner;

public class Day2_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while (sc.hasNextLine()) {
            // input
            String input = sc.nextLine();
            String[] subsets = input.split(":")[1].split(";");
            int r, g, b;
            r = g = b = 0;
            for (String s : subsets) {
                int[] rgb = new int[3];
                String[] sets = s.split(",");
                for (String e : sets) {
                    String[] details = e.trim().split(" ");
                    if (details[1].equals("red")) rgb[0] = Integer.parseInt(details[0]);
                    else if (details[1].equals("green")) rgb[1] = Integer.parseInt(details[0]);
                    else rgb[2] = Integer.parseInt(details[0]);
                }

                // process
                if (rgb[0] > r) r = rgb[0];
                if (rgb[1] > g) g = rgb[1];
                if (rgb[2] > b) b = rgb[2];
            }
            // process
            int ans = r * g * b;
            sum += ans;
        }
        System.out.println(sum);
        sc.close();
    }
}
