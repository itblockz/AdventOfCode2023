import java.util.ArrayList;
import java.util.Scanner;

public class Day4_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        ArrayList<String[]> winArr = new ArrayList<>();
        ArrayList<String[]> myArr = new ArrayList<>();
        while (sc.hasNextLine()) {
            // input
            String input = sc.nextLine();
            String[] step1 = input.split(":");
            String[] step2 = step1[1].split("\\|");
            String[] win = step2[0].trim().split("\\s+");
            String[] my = step2[1].trim().split("\\s+");
            winArr.add(win);
            myArr.add(my);
        }
        sc.close();

        // process
        int size = winArr.size();
        for (int i = 0; i < size; i++) {
            int ans = getCardCount(i, winArr, myArr);
            sum += ans;
        }
        System.out.println(sum);
    }

    static int getCardCount(int card, ArrayList<String[]> winArr, ArrayList<String[]> myArr) {
        int cnt = 0;
        for (String w : winArr.get(card)) {
            for (String m : myArr.get(card)) {
                if (w.equals(m)) {
                    cnt++;
                    break;
                }
            }
        }
        int sum = 1;
        for (int i = 1; i <= cnt; i++) {
            sum += getCardCount(card+i, winArr, myArr);
        }
        return sum;
    }
}