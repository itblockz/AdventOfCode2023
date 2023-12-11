import java.util.ArrayList;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while (sc.hasNextLine()) {
            String in = sc.nextLine();
            String[] split = in.split(" ");
            ArrayList<Integer> a = new ArrayList<>();
            for (String s : split) {
                a.add(Integer.parseInt(s));
            }
            int x = compute(a);
            sum += x;
        }
        sc.close();
        System.out.println(sum);
    }

    static int compute(ArrayList<Integer> a) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        while (!allZero(a)) {
            ArrayList<Integer> b = new ArrayList<>();
            for (int i = 0; i < a.size()-1; i++) {
                b.add(a.get(i+1) - a.get(i));
            }
            list.add(a);
            a = b;
        }
        
        // return next(list);
        return prev(list);
    }

    static int next(ArrayList<ArrayList<Integer>> list) {
        int x = 0;
        int rows = list.size();
        for (int i = rows-1; i >= 0; i--) {
            int cols = list.get(i).size();
            int last = list.get(i).get(cols-1);
            x += last;
        }
        return x;
    }

    static int prev(ArrayList<ArrayList<Integer>> list) {
        int x = 0;
        int rows = list.size();
        for (int i = rows-1; i >= 0; i--) {
            int first = list.get(i).get(0);
            x = first - x;
        }
        return x;
    }

    static boolean allZero(ArrayList<Integer> a) {
        for (Integer i : a) {
            if (i != 0) return false;
        }
        return true;
    }
}
