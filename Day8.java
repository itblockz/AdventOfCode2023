import java.util.HashMap;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ins = sc.nextLine();
        sc.nextLine();
        HashMap<String, String> lmap = new HashMap<>();
        HashMap<String, String> rmap = new HashMap<>();
        while (sc.hasNextLine()) {
            String in = sc.nextLine();
            String[] step1 = in.split(" = ");
            String[] step2 = step1[1].split(", ");
            String n = step1[0];
            String l = step2[0].substring(1);
            String r = step2[1].substring(0, step2[1].length()-1);
            lmap.put(n, l);
            rmap.put(n, r);
        }
        sc.close();

        int n = ins.length();
        int cnt = 0;
        String cur = "AAA";
        int i = 0;
        while (!cur.equals("ZZZ")) {
            cur = ins.charAt(i) == 'L' ? lmap.get(cur) : rmap.get(cur);
            cnt++;
            i = (i+1) % n;
        }
        System.out.println(cnt);
    }
}
