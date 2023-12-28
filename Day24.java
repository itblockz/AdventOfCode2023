import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day24 {
    public static void main(String[] args) throws IOException {
        List<List<Long>> list = Files.lines(Paths.get("input24.txt"))
            .map(line -> Arrays.stream(line.split(",\\s+| @\\s+"))
                .map(Long::parseLong)
                .collect(Collectors.toList())
            ).collect(Collectors.toList());

        long lowerBound = 200000000000000L;
        long upperBound = 400000000000000L;

        int cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i+1; j < list.size(); j++) {
                double xA = list.get(i).get(0);
                double yA = list.get(i).get(1);
                double dxA = list.get(i).get(3);
                double dyA = list.get(i).get(4);
                double mA = dyA / dxA;
                double bA = yA - mA*xA;

                double xB = list.get(j).get(0);
                double yB = list.get(j).get(1);
                double dxB = list.get(j).get(3);
                double dyB = list.get(j).get(4);
                double mB = dyB / dxB;
                double bB = yB - mB*xB;

                double xCross = (bA - bB) / (mB - mA);
                double yCross = (bA*mB - bB*mA) / (mB - mA);
                if (xCross < lowerBound || xCross > upperBound || yCross < lowerBound || yCross > upperBound)
                    continue;

                double tA = (xCross - xA) / dxA;
                double tB = (xCross - xB) / dxB;
                if (tA > 0 && tB > 0) cnt++;
            }
        }
        System.out.println(cnt);
    }
}
