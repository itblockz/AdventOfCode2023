import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day20_2 {
    static Map<String, String> typeMap = new HashMap<>();
    static Map<String, List<String>> listMap = new HashMap<>();
    static Map<String, Map<String, Boolean>> conj = new HashMap<>();
    static Map<String, Boolean> ffON = new HashMap<>();
    static int low = 0;
    static int high = 0;
    static boolean end;
    
    public static void main(String[] args) throws IOException {
        Files.lines(Paths.get("input20.txt")).forEach(line -> {
            String[] split = line.split(" -> ");
            String[] split2 = split[0].split("(?<=[%&])(?=\\w)");
            String type = split2[0];
            String name = split2[0];
            if (split2.length == 2) name = split2[1];
            List<String> list = Arrays.stream(split[1].split(", "))
            .collect(Collectors.toList());
            
            typeMap.put(name, type);
            listMap.put(name, list);
        });
        List<String> list = new ArrayList<>(Arrays.asList("broadcaster"));
        typeMap.put("button", "button");
        listMap.put("button", list);

        initFF();
        initConj();
        
        String[] label = { "xj", "qs", "kz", "km" };
        int[] arr = new int[label.length];
        for (int i = 0; i < label.length; i++) {
            int k = 0;
            int diff = 0;
            for (int j = 0; j < 2; j++) {
                end = false;
                while (!end) {
                    push(label[i]);
                    k++;
                }
                diff = k - diff;
            }
            arr[i] = diff;
        }

        long total = 1;
        for (int i = 0; i < label.length; i++) {
            total = lcm(total, arr[i]);
        }
        System.out.println(total);
    }

    static long lcm(long a, long b) {
        return a*b / gcd(a, b);
    }
    static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a%b);
    }

    static void push(String target) {
        List<String> qName = new ArrayList<>();
        List<Boolean> qHigh = new ArrayList<>();
        qName.add("button");
        qHigh.add(false);
        while (!qName.isEmpty()) {
            String name = qName.remove(0);
            boolean highIn = qHigh.remove(0);
            if (name.equals(target) && highIn) {
                end = true;
            }
            List<String> list = listMap.get(name);
            for (String dest : list) {
                if (listMap.containsKey(dest)) {
                    boolean highOut = highIn;
                    String type = typeMap.get(dest);
                    switch (type) {
                        case "%":
                            if (!highIn) {
                                highOut = ff(dest, highIn);
                                qName.add(dest);
                                qHigh.add(highOut);
                            }
                            break;
                        case "&":
                            conj.get(dest).put(name, highOut);
                            highOut = conj(dest, highIn);
                            qName.add(dest);
                            qHigh.add(highOut);
                            break;
                        default:
                            qName.add(dest);
                            qHigh.add(highOut);
                            break;
                    }
                }
                if (highIn) high++;
                else low++;
            }
        }
    }

    static boolean ff(String name, boolean highIn) {
        if (!highIn) {
            if (ffON.get(name)) {
                ffON.put(name, false);
                return false;
            } else {
                ffON.put(name, true);
                return true;
            }
        }
        return highIn;
    }

    static boolean conj(String name, boolean highIn) {
        Map<String, Boolean> map = conj.get(name);
        boolean allHigh = map
            .keySet()
            .stream()
            .allMatch(map::get);
        if (allHigh) return false;
        return true;
    }

    static void initFF() {
        typeMap.keySet()
            .stream()
            .filter(name -> typeMap.get(name).equals("%"))
            .forEach(ff -> ffON.put(ff, false));
    }

    static void initConj() {
        typeMap.keySet()
            .stream()
            .filter(name -> typeMap.get(name).equals("&"))
            .forEach(cj -> {
                Map<String, Boolean> isHigh = new HashMap<>();
                listMap.keySet()
                    .stream()
                    .filter(list -> listMap.get(list).contains(cj))
                    .forEach(name -> {
                        isHigh.put(name, false);
                    });
                
                conj.put(cj, isHigh);
            });
    }
}