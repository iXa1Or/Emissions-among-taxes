package pack;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.List;

public class ChartService {

    public void generateCharts(List<Item> taxSumOutliers) {
        JFrame frame = new JFrame("Outliers Charts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        XYSeries taxSumSeries = new XYSeries("Tax Sum Outliers");

        for (Item item : taxSumOutliers) {
            long inn = Long.parseLong(item.getINN());
            for (int i = 0; i < item.getTaxNames().size(); i++) {
                taxSumSeries.add(inn, item.getTaxSums().get(i));
            }
        }

        XYSeriesCollection taxSumDataset = new XYSeriesCollection(taxSumSeries);


        JFreeChart taxSumChart = ChartFactory.createScatterPlot(
                "Tax Sum Outliers",
                "INN",
                "Tax Sum",
                taxSumDataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel taxSumChartPanel = new ChartPanel(taxSumChart);

        frame.add(taxSumChartPanel);

        frame.pack();
        frame.setVisible(true);
    }
}