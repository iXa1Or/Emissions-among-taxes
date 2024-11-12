package pack;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import pack.DataAnalyzerService.Quartiles;
import static pack.DataAnalyzerService.calculateQuartiles;
import static pack.DataAnalyzerService.isOutlier;
import static pack.XmlParserService.parseFiles;

public class Main {

    public static void main(String[] args) {
        try {
            String[] filePaths = getFilePaths();
            if (filePaths == null) {
                System.out.println("File path is empty");
                return;
            }
            Map<String, Item> items = parseFiles(filePaths);
            System.out.println("Parse is done");

            Quartiles taxSumQuartiles = calculateQuartiles(
                    items.values().stream()
                            .flatMap(item -> item.getTaxSums().stream())
                            .collect(Collectors.toList())
            );

            // добавляет в объекты только те значения налогов, которые выбросы остальные отбрасывает
            List<Item> taxSumOutliers = items.values().stream()
                    .filter(item -> item.getTaxSums().stream()
                            .anyMatch(taxSum -> isOutlier(taxSum, taxSumQuartiles, 1.5)))
                    .peek(item -> {
                        List<Integer> outlierTaxSums = new ArrayList<>();
                        List<String> outlierTaxNames = new ArrayList<>();

                        for (int i = 0; i < item.getTaxSums().size(); i++) {
                            int taxSum = item.getTaxSums().get(i);
                            if (isOutlier(taxSum, taxSumQuartiles, 2)) {
                                outlierTaxSums.add(taxSum);
                                outlierTaxNames.add(item.getTaxNames().get(i));
                            }
                        }

                        item.setTaxSums(outlierTaxSums);
                        item.setTaxNames(outlierTaxNames);
                    })
                    .collect(Collectors.toList());

            System.out.println("Analyze is done."
                    + "\nTotal items: " + items.size()
                    + "\nTax Sum Outliers size = " + taxSumOutliers.size()
            );

            String pathToInnOutliers = Objects.requireNonNull(Main.class.getResource("/inn_outliers.txt")).getPath();
            BufferedWriter innOutliersFile = new BufferedWriter(new FileWriter(pathToInnOutliers));
            for (Item item : taxSumOutliers) {
                innOutliersFile.write(item.getINN());
                innOutliersFile.newLine();
            }
            innOutliersFile.close();
            System.out.println("The data has been uploaded to a file");

            // Graphics
            ChartService chartService = new ChartService();
            chartService.generateCharts(taxSumOutliers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String[] getFilePaths() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("~/Downloads"));
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