package pack;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pack.DataAnalyzerService.Quartiles;
import static pack.DataAnalyzerService.calculateQuartiles;
import static pack.DataAnalyzerService.isOutlier;

public class Main {

    public static void main(String[] args) {
        try {
            String[] filePaths = getFilePaths();
            if (filePaths == null) {
                System.out.println("File path is empty");
                return;
            }
            XmlParserService parserService = new XmlParserService();
            Map<String, Item> items = parserService.parseFiles(filePaths);
            System.out.println("Parse is done");


            // Анализ выбросов
            List<Integer> revenueValues = items.values().stream()
                    .map(Item::getRevenue)
                    .collect(Collectors.toList());

            List<Integer> taxSumValues = items.values().stream()
                    .flatMap(item -> item.getTaxSums().stream())
                    .collect(Collectors.toList());

            Quartiles revenueQuartiles = calculateQuartiles(revenueValues);
            Quartiles taxSumQuartiles = calculateQuartiles(taxSumValues);

            List<Item> revenueOutliers = items.values().stream()
                    .filter(item -> isOutlier(item.getRevenue(), revenueQuartiles))
                    .collect(Collectors.toList());
            List<Item> taxSumOutliers = items.values().stream()
                    .flatMap(item -> item.getTaxSums().stream()
                            .filter(taxSum -> isOutlier(taxSum, taxSumQuartiles))
                            .map(taxSum -> {
                                Item outlierItem = new Item();
                                outlierItem.setINN(item.getINN());
                                outlierItem.setRevenue(item.getRevenue());
                                outlierItem.addTaxName(item.getTaxNames().get(item.getTaxSums().indexOf(taxSum)));
                                outlierItem.addTaxSum(taxSum);
                                return outlierItem;
                            }))
                    .collect(Collectors.toList());

            System.out.println("Analyze is done");

            // Graphics
            ChartService chartService = new ChartService();
            chartService.generateCharts(revenueOutliers, taxSumOutliers);

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