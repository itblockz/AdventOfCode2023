import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> bidMap = new HashMap<>();
        ArrayList<String> hands = new ArrayList<>();
        ArrayList<Integer> bids = new ArrayList<>();
        while (sc.hasNextLine()) {
            String[] in = sc.nextLine().split(" ");
            hands.add(in[0]);
            bids.add(Integer.parseInt(in[1]));
            bidMap.put(in[0], Integer.parseInt(in[1]));
        }
        sc.close();
        
        int n_card = 5;
        ArrayList<Pair<String, Integer>> a = new ArrayList<>();
        int size =  hands.size();
        for (int i = 0; i < size; i++) {
            HashMap<Character, Integer> hm = new HashMap<>();
            for (int j = 0; j < n_card; j++) {
                char card = hands.get(i).charAt(j);
                if (!hm.containsKey(card)) {
                    hm.put(card, 1);
                } else {
                    hm.put(card, hm.get(card)+1);
                }
            }
            a.add(new Pair<String,Integer>(hands.get(i), type(hm)));
        }

        HashMap<Character, Integer> strength = new HashMap<>();
        char[] labels = { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A' };
        for (int i = 0; i < labels.length; i++) {
            strength.put(labels[i], i+2);
        }

        Comparator<Pair<String,Integer>> c1 = Comparator.comparing(Pair::getValue);
        Comparator<Pair<String,Integer>> c2 = (p1, p2) -> {
            String s1 = p1.getKey();
            String s2 = p2.getKey();
            for (int i = 0; i < n_card; i++) {
                int v1 = strength.get(s1.charAt(i));
                int v2 = strength.get(s2.charAt(i));
                if (v1 != v2) {
                    return v1 - v2;
                }
            }
            return 0;
        };
        a.sort(c1.thenComparing(c2));

        int total = 0;
        int size_a = a.size();
        for (int i = 0; i < size_a; i++) {
            int bid = bidMap.get(a.get(i).getKey());
            int rank = i + 1;
            int winning = bid * rank;
            total += winning;
        }
        System.out.println(total);
    }

    static int type(HashMap<Character, Integer> hm) {
        if (hm.size() == 1) return 7; // 5
        if (hm.containsValue(4)) return 6; // 4 1
        if (hm.size() == 2) return 5; // 3 2
        if (hm.containsValue(3)) return 4; // 3 1 1
        if (hm.size() == 3) return 3; // 2 2 1
        if (hm.size() == 4) return 2; // 2 1 1 1
        return 1; // 1 1 1 1 1
    }
}
