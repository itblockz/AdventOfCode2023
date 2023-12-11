import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while (sc.hasNextLine()) {
            // input
            String input = sc.nextLine();
            int id = Integer.parseInt(input.split(":")[0].split(" ")[1]);
            String[] subsets = input.split(":")[1].split(";");
            int[] rgb = new int[3];
            boolean isPos = true;
            for (String s : subsets) {
                String[] sets = s.split(",");
                for (String e : sets) {
                    String[] details = e.trim().split(" ");
                    if (details[1].equals("red")) rgb[0] = Integer.parseInt(details[0]);
                    else if (details[1].equals("green")) rgb[1] = Integer.parseInt(details[0]);
                    else rgb[2] = Integer.parseInt(details[0]);
                }

                //process
                int r = 12;
                int g = 13;
                int b = 14;
                if (rgb[0] > r || rgb[1] > g || rgb[2] > b) {
                    isPos = false;
                    break;
                }
            }
            if (isPos) {
                sum += id;
            }
        }
        System.out.println(sum);
        sc.close();

    }
}
