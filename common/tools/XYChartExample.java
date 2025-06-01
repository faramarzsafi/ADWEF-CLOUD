package common.tools;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;

import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.XValueOutsideUODException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYChartExample {
	double step = 1; 
	String chartTitle; 
	String xLabel;
	String yLabel;
	JFreeChart chart;
	XYSeriesCollection datasets;
	
	public XYChartExample(String frameTitle,
			String chartTitle, 
			String xLabel, 
			String yLabel){
		chartTitle = chartTitle;
		xLabel = xLabel; 
		yLabel = yLabel;
	    datasets = new XYSeriesCollection();
	}
	
	public XYChartExample plotFuzzyValues(FuzzyValue fv) 
		throws IOException, XValueOutsideUODException{
		
		XYSeries xySeries = new XYSeries(fv.getLinguisticExpression());
		for (double j= fv.getMinUOD(); j<=fv.getMaxUOD();j=j+step)
			xySeries.add(j, fv.getMembership(j));
		
		datasets.addSeries(xySeries);
		
	    chart = ChartFactory.createXYLineChart(
				chartTitle, 
				yLabel,
				xLabel,
                datasets,// Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
	    
	    return this;
	}
	
	public XYChartExample saveChartValuesToFile(FuzzyVariable fv, File path){ 
		
	    // Create the list
	    LinkedList series = new LinkedList();    // Doubly-linked list
	    int elem = 0;
		try {
		    Enumeration en = fv.findTerms();
		    while(en.hasMoreElements()){
	
		    	FuzzyValue tfValue = (FuzzyValue)en.nextElement();
		    	series.add(new XYSeries(tfValue.getLinguisticExpression()));
		    	(new File(path.getPath())).mkdirs();
				File f = new File (path.getPath()+"/Series_"+elem+".csv");
				f.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(f));
	    		for (double j= 0; j<=tfValue.getMaxUOD();j= j+step){
	    			System.out.println("j-> "+j+","+tfValue.getMembership(j));
	    			if(j>=0) out.write(((j==99.9)?100:j)+","+tfValue.getMembership(j)+"\n");
	    			((XYSeries)series.get(elem)).add(j, tfValue.getMembership(j));
	    		}
			    out.close();
		        //Add the series to your data set
		    	datasets.addSeries((XYSeries) series.get(elem));
		    	elem++;
	    	}
    	}catch (XValueOutsideUODException e) {
			System.out.println("Faramarz: in the catch: XValueOutsideUODException: "+e.getCause()+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	    chart = ChartFactory.createXYLineChart(
				chartTitle, 
				yLabel,
				xLabel,
                datasets,// Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
	    return this;
    }

	
	public XYChartExample plotFuzzyValues(FuzzyVariable fv){ 
		
	    // Create the list
	    LinkedList series = new LinkedList();    // Doubly-linked list
	    int elem = 0;
		try {
		    Enumeration en = fv.findTerms();
		    while(en.hasMoreElements()){
	
		    	FuzzyValue tfValue = (FuzzyValue)en.nextElement();
		    	series.add(new XYSeries(tfValue.getLinguisticExpression()));
		    	for(int i=0; i<tfValue.size(); i++){
		    		for (double j= tfValue.getMinUOD(); j<=tfValue.getMaxUOD();j= j+step){
		    			System.out.println("j-> "+j);
		    			((XYSeries)series.get(elem)).add(j, tfValue.getMembership(j));
		    		}
				}
		        //Add the series to your data set
		    	datasets.addSeries((XYSeries) series.get(elem));
		    	elem++;
	    	}
		} catch (XValueOutsideUODException e) {
			System.out.println("Faramarz: in the catch: XValueOutsideUODException: "+e.getCause()+e.getMessage());
			e.printStackTrace();
		}

	    chart = ChartFactory.createXYLineChart(
				chartTitle, 
				yLabel,
				xLabel,
                datasets,// Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
	    return this;
    }
	public void showChart(String frameTitle){	 // create and display a frame...
	    ChartFrame frame = new ChartFrame(frameTitle, chart);
	    frame.pack();
	    frame.setVisible(true);
	    
	}
	public void seveToFile(String path) throws IOException{
		
		ChartUtilities.saveChartAsJPEG(new File(path), chart, 500, 300);
	}
	
    public static void main(String[] args) {
        //         Create a simple XY chart
        XYSeries series = new XYSeries("XYGraph");
        series.add(1, 1);
        series.add(1, 2);
        series.add(2, 1);
        series.add(3, 9);
        series.add(4, 10);
        //         Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        //         Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart("XY Chart", // Title
            "x-axis", // x-axis Label
            "y-axis", // y-axis Label
            dataset, // Dataset
            PlotOrientation.VERTICAL, // Plot Orientation
            true, // Show Legend
            true, // Use tooltips
            false // Configure chart to generate URLs?
        );
//        XYItemRenderer rend = chart.getXYPlot().getRenderer();
//        StandardXYItemRenderer rr = (StandardXYItemRenderer)rend;
//        rr.setPlotImages(true);
        
     // create and display a frame...
        ChartFrame frame = new ChartFrame("First", chart);
        frame.pack();
        frame.setVisible(true);
        try {
        	
            ChartUtilities.saveChartAsJPEG(new File("C:\\chart.jpg"), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }
}
