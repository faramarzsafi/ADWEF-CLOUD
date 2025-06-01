package com.jrefinery.chart.demo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
/**
 * A simple demonstration application showing how to create a line chart using data from an
 * XYDataset.
 */
public class LineChartDemo2 extends ApplicationFrame {

	/** The data. */
	protected XYDataset data;
	/**
	 * Default constructor.
	 */
	public LineChartDemo2(String title) {
		super(title);
		// create a dataset...
		XYSeries series1 = new XYSeries("First");
		series1.add(1.0, 1.0);
		series1.add(2.0, 4.0);
		series1.add(3.0, 3.0);
		series1.add(4.0, 5.0);
		series1.add(5.0, 5.0);
		series1.add(6.0, 7.0);
		series1.add(7.0, 7.0);
		series1.add(8.0, 8.0);
		XYSeries series2 = new XYSeries("Second");
		series2.add(1.0, 5.0);
		series2.add(2.0, 7.0);
		series2.add(3.0, 6.0);
		series2.add(4.0, 8.0);
		series2.add(5.0, 4.0);
		series2.add(6.0, 4.0);
		series2.add(7.0, 2.0);
		series2.add(8.0, 1.0);
		XYSeries series3 = new XYSeries("Third");
		series3.add(3.0, 4.0);
		series3.add(4.0, 3.0);
		series3.add(5.0, 2.0);
		series3.add(6.0, 3.0);
		series3.add(7.0, 6.0);
		series3.add(8.0, 3.0);
		series3.add(9.0, 4.0);
		series3.add(10.0, 3.0);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);
		// create the chart...
		JFreeChart chart = ChartFactory.createXYLineChart(
				"chartTitle", 
				"yLabel",
				"xLabel",
				dataset,// Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.orange);
		// get a reference to the plot for further customisation...
		XYPlot plot = chart.getXYPlot();
		// set the color for each series...
//		plot.setSeriesPaint(new Paint[] { Color.green, Color.orange, Color.red });
		// set the stroke for each series...
		Stroke[] seriesStrokeArray = new Stroke[3];
		seriesStrokeArray[0] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
				1.0f, new float[] { 10.0f, 6.0f }, 0.0f);
		seriesStrokeArray[1] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
				1.0f, new float[] { 6.0f, 6.0f }, 0.0f);
		seriesStrokeArray[2] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
				1.0f, new float[] { 2.0f, 6.0f }, 0.0f);
//		plot.setSeriesStroke(seriesStrokeArray);
		// change the auto tick unit selection to integer units only...
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(TickUnits.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.
		// add the chart to a panel...
		ChartPanel chartPanel = new ChartPanel(chart);
		this.setContentPane(chartPanel);
	}
	/**
	 * Starting point for the demonstration application.
	 */
	public static void main(String[] args) {
		LineChartDemo2 demo = new LineChartDemo2("Line Chart Demo 2");
		demo.pack();
		demo.setVisible(true);
	}
}
