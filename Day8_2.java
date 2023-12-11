import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day8_2 {
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

        ArrayList<String> start = new ArrayList<>();
        for (String node : lmap.keySet()) {
            if (node.matches("..A")){
                start.add(node);
            }
        }
        
        ArrayList<Integer> steps = new ArrayList<>();
        for (int i = 0; i < start.size(); i++) {
            ArrayList<String> run = new ArrayList<>(start);
            int cnt = 0;
            int idx = 0;
            while (!run.get(i).matches("..Z")) {
                String node = run.get(i);
                run.set(i, ins.charAt(idx) == 'L' ? lmap.get(node) : rmap.get(node));
                cnt++;
                idx = (idx+1) % ins.length();
            }
            steps.add(cnt);
        }
        
        long lcm = steps.get(0);
        for (int i = 1; i < steps.size(); i++) {
            lcm = lcm(lcm, steps.get(i));
        }
        System.out.println(lcm);
    }

    static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a%b);
    }

    static long lcm(long a, long b) {
        return Math.abs(a*b) / gcd(a, b);
    }
}
