import java.util.Scanner;

public class Day15 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        sc.close();
        String[] split = in.split(",");

        int sum = 0;
        for (String str : split) {
            int x = hash(str);
            sum += x;
        }
        System.out.println(sum);
    }

    static int hash(String str) {
        int val = 0;
        for (int i = 0; i < str.length(); i++) {
            int ascii = (int)str.charAt(i);
            val += ascii;
            val *= 17;
            val %= 256;
        }
        return val;
    }
}
