package pack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class DataAnalyzerService {

//    public List<Item> detectOutliers(Map<String, Item> itemsMap) {
//        List<Integer> revenueValues = itemsMap.values().stream()
//                .map(Item::getRevenue)
//                .collect(Collectors.toList());
//
//        List<Integer> taxSumValues = itemsMap.values().stream()
//                .flatMap(item -> item.getTaxSums().stream())
//                .collect(Collectors.toList());
//
//        Quartiles revenueQuartiles = calculateQuartiles(revenueValues);
//        Quartiles taxSumQuartiles = calculateQuartiles(taxSumValues);
//
//        return itemsMap.values().stream()
//                .filter(item -> isOutlier(item.getRevenue(), revenueQuartiles) ||
//                        item.getTaxSums().stream().anyMatch(taxSum -> isOutlier(taxSum, taxSumQuartiles)))
//                .collect(Collectors.toList());
//    }

    // Выбросы во внутренних границах
    public static boolean isOutlier(int value, Quartiles quartiles) {
        double innerLowerBound = quartiles.q1 - 1.5 * quartiles.iqr;
        double innerUpperBound = quartiles.q3 + 1.5 * quartiles.iqr;
        return value < innerLowerBound || value > innerUpperBound;
    }

    public static Quartiles calculateQuartiles(List<Integer> values) {
        Collections.sort(values);
        int size = values.size();
        int middle1 = size == 1 ? 1 : size / 2;
        int middle2 = size == 1 ? 0 : (size % 2 == 0 ? 0 : 1);
        double q1 = findMedian(values.subList(0, middle1));
        double q3 = findMedian(values.subList(middle2, size));
        double iqr = q3 - q1;
        return new Quartiles(q1, q3, iqr);
    }

    private static double findMedian(List<Integer> values) {
        if (values.isEmpty()) {
            return 0.0;
        }
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
        } else {
            return values.get(size / 2);
        }
    }

    public static class Quartiles {
        final double q1;
        final double q3;
        final double iqr;

        Quartiles(double q1, double q3, double iqr) {
            this.q1 = q1;
            this.q3 = q3;
            this.iqr = iqr;
        }
    }

    public void printOutliers(List<Item> outliers) {
        System.out.println("Detected Outliers:");
        for (Item item : outliers) {
            System.out.println("INN: " + item.getINN());
            System.out.println("Revenue: " + item.getRevenue());
            System.out.println("Tax Names: " + item.getTaxNames());
            System.out.println("Tax Sums: " + item.getTaxSums());
            System.out.println("-----------------------------");
        }
    }
}