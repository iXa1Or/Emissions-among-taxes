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

    public void generateCharts(List<Item> outliers) {
        JFrame frame = new JFrame("Outliers Charts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        XYSeries revenueSeries = new XYSeries("Revenue Outliers");
        XYSeries taxSumSeries = new XYSeries("Tax Sum Outliers");

        for (Item item : outliers) {
            double inn = Double.parseDouble(item.getINN());
            revenueSeries.add(inn, item.getRevenue());
            for (int i = 0; i < item.getTaxNames().size(); i++) {
                taxSumSeries.add(inn, item.getTaxSums().get(i));
            }
        }

        XYSeriesCollection revenueDataset = new XYSeriesCollection(revenueSeries);
        XYSeriesCollection taxSumDataset = new XYSeriesCollection(taxSumSeries);

        JFreeChart revenueChart = ChartFactory.createScatterPlot(
                "Revenue Outliers",
                "INN",
                "Revenue",
                revenueDataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        JFreeChart taxSumChart = ChartFactory.createScatterPlot(
                "Tax Sum Outliers",
                "INN",
                "Tax Sum",
                taxSumDataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel revenueChartPanel = new ChartPanel(revenueChart);
        ChartPanel taxSumChartPanel = new ChartPanel(taxSumChart);

        frame.add(revenueChartPanel);
        frame.add(taxSumChartPanel);

        frame.pack();
        frame.setVisible(true);
    }
}