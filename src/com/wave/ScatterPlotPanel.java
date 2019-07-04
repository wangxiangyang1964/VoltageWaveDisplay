package com.wave;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ScatterPlotPanel extends JPanel {


    private DisplayFrame displayFrame;
    XYDataset dataset;
    JFreeChart chart;
    XYPlot xyPlot;
    ChartPanel chartPanel;

    private ValueAxis valueAxis_Y = null;
    private ValueAxis valueAxis_X = null;

    ScatterPlotPanel(Dimension dimension, DisplayFrame displayFrame){
        super();
        this.displayFrame = displayFrame;
        // Create dataset
        dataset = createDataset();

        // Create chart
        chart = ChartFactory.createScatterPlot(
                "防腐蚀电压--采样时间散点图",
                "采样时间顺序值", "防腐蚀电压（毫伏）", dataset);


        xyPlot = chart.getXYPlot();
        valueAxis_Y = xyPlot.getRangeAxis();


        if (displayFrame.getY_Axis_Top_Value() < displayFrame.getY_Axis_Actual_Max_Value()){
            displayFrame.setY_Axis_Top_Value(displayFrame.getY_Axis_Actual_Max_Value());
            displayFrame.setY_Axis_Rage(displayFrame.getY_Axis_Top_Value() - displayFrame.getY_Axis_Lower_Value());
            displayFrame.setY_Axis_Ratio(displayFrame.getY_Axis_Rage()/displayFrame.getY_Axis_Rage_Benchmark());
        }

        valueAxis_Y.setRange(displayFrame.getY_Axis_Lower_Value(), displayFrame.getY_Axis_Top_Value());

        valueAxis_X = xyPlot.getDomainAxis();
        valueAxis_X.setRange(displayFrame.getX_Axis_Lower_Value(), displayFrame.getX_Axis_Top_Value());

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());

        // Create Panel
        chartPanel = new ChartPanel(chart,false);
        chartPanel.setPreferredSize(dimension);
        this.add(chartPanel);
    }


    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        //Boys (Age,weight) series
        XYSeries series1 = new XYSeries("某某编号防腐设备");
        for (int i = 0; i < displayFrame.getAvilableAccount(); i++) {
            series1.add(displayFrame.getDisplayUsageDataUnitArray()[i].index,
                    displayFrame.getDisplayUsageDataUnitArray()[i].voltage);
        }
        dataset.addSeries(series1);
        return dataset;
    }

    public void updateDataset() {
        if (displayFrame.getY_Axis_Top_Value() < displayFrame.getY_Axis_Actual_Max_Value()){
            displayFrame.setY_Axis_Top_Value(displayFrame.getY_Axis_Actual_Max_Value());
            displayFrame.setY_Axis_Rage(displayFrame.getY_Axis_Top_Value() - displayFrame.getY_Axis_Lower_Value());
            displayFrame.setY_Axis_Ratio(displayFrame.getY_Axis_Rage()/displayFrame.getY_Axis_Rage_Benchmark());
        }
        valueAxis_Y.setRange(displayFrame.getY_Axis_Lower_Value(), displayFrame.getY_Axis_Top_Value());

        valueAxis_X.setRange(displayFrame.getX_Axis_Lower_Value(), displayFrame.getX_Axis_Top_Value());

        ((XYSeriesCollection)dataset).removeSeries(0);

        XYSeries series1 = new XYSeries("某某编号防腐设备");
        for (int i = 0; i < displayFrame.getAvilableAccount(); i++) {
            series1.add(displayFrame.getDisplayUsageDataUnitArray()[i].index, displayFrame.getDisplayUsageDataUnitArray()[i].voltage);
        }
        ((XYSeriesCollection)dataset).addSeries(series1);

    }

    public void Y_EnLarge() {
        double temp_ratioY = displayFrame.getY_Axis_Ratio() * 0.8;
        double temp_Y_Axis_Range = displayFrame.getY_Axis_Rage_Benchmark() * temp_ratioY;
        double temp_Y_Axis_Top_Value = displayFrame.getY_Axis_Lower_Value() + temp_Y_Axis_Range;
        if ((temp_Y_Axis_Top_Value > displayFrame.getY_Axis_Actual_Max_Value()) &&
                (temp_Y_Axis_Top_Value >= (displayFrame.getY_Axis_Lower_Value() + DisplayFrame.DISPLAY_Y_AXIS_MINIMUM_RANGE))) {
            valueAxis_Y.setRange(displayFrame.getY_Axis_Lower_Value(), temp_Y_Axis_Top_Value);
            displayFrame.setY_Axis_Top_Value(temp_Y_Axis_Top_Value);
            displayFrame.setY_Axis_Rage(temp_Y_Axis_Range);
            displayFrame.setY_Axis_Ratio(temp_ratioY);

        }

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());

    }

    public void Y_Shrink(){
        double temp_ratioY = displayFrame.getY_Axis_Ratio() * 1.2;
        double temp_Y_Axis_Range = displayFrame.getY_Axis_Rage_Benchmark() * temp_ratioY;
        double temp_Y_Axis_Top_Value = displayFrame.getY_Axis_Lower_Value() + temp_Y_Axis_Range;
        if (( temp_Y_Axis_Top_Value > displayFrame.getY_Axis_Actual_Max_Value()) &&
                (temp_Y_Axis_Top_Value <= DisplayFrame.DISPLAY_Y_AXIS_MAXIMUM_RANGE)) {
            valueAxis_Y.setRange(displayFrame.getY_Axis_Lower_Value(), temp_Y_Axis_Top_Value);
            displayFrame.setY_Axis_Top_Value(temp_Y_Axis_Top_Value);
            displayFrame.setY_Axis_Rage(temp_Y_Axis_Range);
            displayFrame.setY_Axis_Ratio(temp_ratioY);
        }

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());

    }

    public void X_Enlargy(){
        double temp_ratioX = displayFrame.getX_Axis_Ratio() * 0.8;
        double temp_X_Axis_Range = displayFrame.getX_Axis_Rage_Benchmark() * temp_ratioX;
        double temp_X_Axis_Top_Value = displayFrame.getX_Axis_Lower_Value() + temp_X_Axis_Range;
        if (temp_X_Axis_Range >= DisplayFrame.DISPLAY_X_AXIS_MINIMUM_RANGE) {
            valueAxis_X.setRange(displayFrame.getX_Axis_Lower_Value(), temp_X_Axis_Top_Value);
            displayFrame.setX_Axis_Top_Value(temp_X_Axis_Top_Value);
            displayFrame.setX_Axis_Rage(temp_X_Axis_Range);
            displayFrame.setX_Axis_Ratio(temp_ratioX);
            displayFrame.createDisplayUsageDataUnitArray();
            updateDataset();
        }

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());

    }

    public void X_Shrink(){

        double temp_ratioX = displayFrame.getX_Axis_Ratio() * 1.2;
        double temp_X_Axis_Range = displayFrame.getX_Axis_Rage_Benchmark() * temp_ratioX;
        double temp_X_Axis_Top_Value = displayFrame.getX_Axis_Lower_Value() + temp_X_Axis_Range;
        if (temp_X_Axis_Range <= DisplayFrame.DISPLAY_X_AXIS_MAXIMUM_RANGE) {
            valueAxis_X.setRange(displayFrame.getX_Axis_Lower_Value(), temp_X_Axis_Top_Value);
            displayFrame.setX_Axis_Top_Value(temp_X_Axis_Top_Value);
            displayFrame.setX_Axis_Rage(temp_X_Axis_Range);
            displayFrame.setX_Axis_Ratio(temp_ratioX);
            displayFrame.createDisplayUsageDataUnitArray();
            updateDataset();
        }

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());

    }

    public void X_Left_Shift(int n){
        double temp_X_Axis_Lower_Value = displayFrame.getX_Axis_Lower_Value() - n;
        double temp_X_Axis_Top_Value = temp_X_Axis_Lower_Value + displayFrame.getX_Axis_Rage();
        if ((temp_X_Axis_Lower_Value >= DisplayFrame.DISPLAY_X_AXIS_START_POINT_VALUE) &&
                (temp_X_Axis_Top_Value <= displayFrame.getActualDataCount())){
            valueAxis_X.setRange(temp_X_Axis_Lower_Value, temp_X_Axis_Top_Value);
            displayFrame.setX_Axis_Lower_Value(temp_X_Axis_Lower_Value);
            displayFrame.setX_Axis_Top_Value(temp_X_Axis_Top_Value);
            displayFrame.createDisplayUsageDataUnitArray();
            updateDataset();
        }

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //       + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());
    }

    public void X_Right_Shift(int n){
        double temp_X_Axis_Lower_Value = displayFrame.getX_Axis_Lower_Value() + n;
        double temp_X_Axis_Top_Value = temp_X_Axis_Lower_Value + displayFrame.getX_Axis_Rage();
        if ((temp_X_Axis_Lower_Value >= DisplayFrame.DISPLAY_X_AXIS_START_POINT_VALUE) &&
                (temp_X_Axis_Top_Value <= displayFrame.getActualDataCount())){
            valueAxis_X.setRange(temp_X_Axis_Lower_Value, temp_X_Axis_Top_Value);
            displayFrame.setX_Axis_Lower_Value(temp_X_Axis_Lower_Value);
            displayFrame.setX_Axis_Top_Value(temp_X_Axis_Top_Value);
            displayFrame.createDisplayUsageDataUnitArray();
            updateDataset();
        }

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());
    }

}
