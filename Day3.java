import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> map = new ArrayList<>();
        while (sc.hasNextLine()) {
            map.add(sc.nextLine());
        }
        sc.close();
        ArrayList<Point> symbols = getSymbolPoints(map);
        boolean[][] numbers = getIsNumberMatrix(map);
        ArrayList<Integer> a = getNumberList(map, symbols, numbers);
        int sum = 0;
        for (Integer i : a) {
            sum += i;
        }
        System.out.println(sum);
    }

    static ArrayList<Point> getSymbolPoints(ArrayList<String> map) {
        ArrayList<Point> p = new ArrayList<>();
        int m = map.size();
        int n = map.get(0).length();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!String.valueOf(map.get(i).charAt(j)).matches("\\d|\\.")) {
                    p.add(new Point(j, i));
                }
            }
        }
        return p;
    }

    static boolean[][] getIsNumberMatrix(ArrayList<String> map) {
        int m = map.size();
        int n = map.get(0).length();
        boolean[][] mat = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isNumber(map.get(i).charAt(j))) {
                    mat[i][j] = true;
                }
            }
        }
        return mat;
    }

    static boolean isNumber(char c) {
        return String.valueOf(c).matches("\\d");
    }

    static void printMatrix(boolean[][] mat) {
        for (boolean[] row : mat) {
            System.out.println(Arrays.toString(row));
        }
    }

    static ArrayList<Integer> getNumberList(ArrayList<String> map, ArrayList<Point> symbols, boolean[][] number) {
        ArrayList<Integer> a = new ArrayList<>();
        for (Point p : symbols) {
            label:
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int x = p.x + j;
                    int y = p.y + i;
                    if (y == -1 || y == number.length || x == -1 || x == number[0].length)
                        break label;
                    if (number[y][x]) {
                        int num = getNumber(map.get(y), number, new Point(x, y));
                        a.add(num);
                    }
                }
            }
        }
        return a;
    }

    static int getNumber(String s, boolean[][] numbers, Point p) {
        StringBuilder sb = new StringBuilder();
        int i = p.x;
        char c = s.charAt(i);
        while (isNumber(c)) {
            numbers[p.y][i] = false;
            sb.insert(0, c);
            i--;
            if (i == -1) break;
            c = s.charAt(i);
        }
        i = p.x + 1;
        c = s.charAt(i);
        while (isNumber(c)) {
            numbers[p.y][i] = false;
            sb.append(c);
            i++;
            if (i == numbers.length) break;
            c = s.charAt(i);
        }
        return Integer.parseInt(sb.toString());
    }
}