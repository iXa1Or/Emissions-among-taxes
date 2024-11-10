package pack;
import pack.DataAnalyzerService.*;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import static pack.DataAnalyzerService.isOutlier;

public class Main {

    public static int sumOfArray(Vector<Integer> vec) {
        return vec.stream().reduce(0, Integer::sum);
    }
    public static void main(String[] args) {
        try {
            String[] filePaths = getFilePaths();

            XmlParserService parserService = new XmlParserService();
            Map<String, Item> items = parserService.parseFiles(filePaths);
            System.out.println("Parse is done");

            // Анализ выбросов
            DataAnalyzerService analyzerService = new DataAnalyzerService();

            List<Integer> revenueValues = items.values().stream()
                    .map(Item::getRevenue)
                    .collect(Collectors.toList());

            List<Integer> taxSumValues = items.values().stream()
                    .flatMap(item -> item.getTaxSums().stream())
                    .collect(Collectors.toList());

            Quartiles revenueQuartiles = analyzerService.calculateQuartiles(revenueValues);
            Quartiles taxSumQuartiles = analyzerService.calculateQuartiles(taxSumValues);

            List<Item> revenueOutliers = items.values().stream()
                    .filter(item -> isOutlier(item.getRevenue(), revenueQuartiles))
                    .collect(Collectors.toList());
            List<Item> taxSumOutliers = items.values().stream()
                    .filter(item -> item.getTaxSums().stream()
                    .anyMatch(taxSum -> isOutlier(taxSum, taxSumQuartiles)))
                    .collect(Collectors.toList());

            List<Item> outliers = analyzerService.detectOutliers(items);
            System.out.println("Analyze is done");

            List<Item> revMoreTaxSum = new ArrayList<>();
            List<Item> revLessTaxSum = new ArrayList<>();
            for (Item item : outliers) {
                if (sumOfArray(item.getTaxSums()) > item.getRevenue()) {
                    revMoreTaxSum.add(item);
                }
                else {
                    revLessTaxSum.add(item);
                }
            }
//            analyzerService.printOutliers(outliers);

            ChartService chartService = new ChartService();
            chartService.generateCharts(outliers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String[] getFilePaths() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("/Users/arseniy/Downloads/"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);

        int flagChooseOkay = fileChooser.showOpenDialog(null);
        if (flagChooseOkay == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            String[] filePaths = new String[selectedFiles.length];
            int i = 0;
            for (File file : selectedFiles) {
                filePaths[i] = file.getAbsolutePath();
                ++i;
            }
            return filePaths;
        }
        return null;
    }
}