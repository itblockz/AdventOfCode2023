import java.util.Scanner;

public class Day1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while (sc.hasNextLine()) {
            // input
            String str = sc.nextLine();
            System.out.println(str);

            // process
            int ans = Integer.parseInt(firstDigit2(str) + lastDigit2(str));
            sum += ans;
        }
        sc.close();

        // output
        System.out.println(sum);
    }

    static String firstDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (isDigit(s.charAt(i)))
                return String.valueOf(s.charAt(i));
        }
        return null;
    }

    static String firstDigit2(String s) {
        int[] a = new int[10];
        int[] b = new int[10];
        String[] label = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        int strlen = s.length();
        for (int i = 0; i < 10; i++) {
            int p = s.indexOf(String.valueOf(i));
            if (p == -1) a[i] = strlen;
            else a[i] = p;
        }
        for (int i = 0; i < 10; i++) {
            int p = s.indexOf(label[i]);
            if (p == -1) b[i] = strlen;
            else b[i] = p;
        }
        int fa = indexOfMin(a);
        int fb = indexOfMin(b);
        if (a[fa] < b[fb]) return String.valueOf(fa);
        return String.valueOf(fb);
    }
    
    static String lastDigit(String s) {
        for (int i = s.length()-1; i >= 0; i--) {
            if (isDigit(s.charAt(i)))
            return String.valueOf(s.charAt(i));
        }
        return null;
    }
    
    static String lastDigit2(String s) {
        int[] a = new int[10];
        int[] b = new int[10];
        String[] label = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 9; i >= 0; i--) {
            int p = s.lastIndexOf(String.valueOf(i));
            if (p == -1) a[i] = -1;
            else a[i] = p;
        }
        for (int i = 9; i >= 0; i--) {
            int p = s.lastIndexOf(label[i]);
            if (p == -1) b[i] = -1;
            else b[i] = p;
        }
        int fa = indexOfMax(a);
        int fb = indexOfMax(b);
        if (a[fa] > b[fb]) return String.valueOf(fa);
        return String.valueOf(fb);
    }

    static int indexOfMin(int[] a) {
        int min = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[min])
                min = i;
        }
        return min;
    }

    static int indexOfMax(int[] a) {
        int max = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[max])
                max = i;
        }
        return max;
    }
    
    static boolean isDigit(char c) {
        return String.valueOf(c).matches("[0-9]");
    }
}