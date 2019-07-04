package com.wave;



import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;


public class TimeSeriesChartPanel extends JPanel {

    private DisplayFrame displayFrame;
    XYDataset dataset;
    JFreeChart chart;
    XYPlot xyPlot;
    ChartPanel chartPanel;

    private ValueAxis valueAxis_Y = null;
    private ValueAxis valueAxis_X = null;


    public TimeSeriesChartPanel(Dimension dimension, DisplayFrame displayFrame) {
        super();
        this.displayFrame = displayFrame;

        dataset = createDataset();

        chart = createChart(dataset);

        xyPlot = chart.getXYPlot();
        valueAxis_Y = xyPlot.getRangeAxis();

        if (displayFrame.getY_Axis_Top_Value() < displayFrame.getY_Axis_Actual_Max_Value()){
            displayFrame.setY_Axis_Top_Value(displayFrame.getY_Axis_Actual_Max_Value());
            displayFrame.setY_Axis_Rage(displayFrame.getY_Axis_Top_Value() - displayFrame.getY_Axis_Lower_Value());
            displayFrame.setY_Axis_Ratio(displayFrame.getY_Axis_Rage()/displayFrame.getY_Axis_Rage_Benchmark());
        }

        valueAxis_Y.setRange(displayFrame.getY_Axis_Lower_Value(), displayFrame.getY_Axis_Top_Value());

        valueAxis_X = xyPlot.getDomainAxis();

        //System.out.println("Y:" + displayFrame.getY_Axis_Lower_Value() + "---" + displayFrame.getY_Axis_Top_Value()
        //        + "rageY:"+ displayFrame.getY_Axis_Rage() + "ratioY:" + displayFrame.getY_Axis_Ratio());
        //System.out.println("X:" + displayFrame.getX_Axis_Lower_Value() + "---" + displayFrame.getX_Axis_Top_Value()
        //        + "rageX:" + displayFrame.getX_Axis_Rage() + "ratioX:" + displayFrame.getX_Axis_Ratio());

        chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(dimension);
        chartPanel.setMouseZoomable(true, false);
        this.add(chartPanel);
    }


    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "防腐蚀电压--采样时间时序图",  // title
                "采样时间",             // x-axis label
                "防腐蚀电压(毫伏)",   // y-axis label
                dataset,            // data
                true,               // create legend?
                true,               // generate tooltips?
                false               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        xyPlot = (XYPlot) chart.getPlot();
        xyPlot.setBackgroundPaint(Color.lightGray);
        xyPlot.setDomainGridlinePaint(Color.white);
        xyPlot.setRangeGridlinePaint(Color.white);
        xyPlot.setAxisOffset(new RectangleInsets(10, 10, 10, 10));
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);

        XYItemRenderer r = xyPlot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }

        DateAxis axis = (DateAxis) xyPlot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return chart;

    }


    private XYDataset createDataset() {

        TimeSeries s1 = new TimeSeries("A编号防腐蚀设备", Second.class);


        for (int i = 0; i < displayFrame.getAvilableAccount(); i++) {
            DisplayUsageDataUnit displayUsageDataUnit = displayFrame.getDisplayUsageDataUnitArray()[i];
            Calendar calendar = displayUsageDataUnit.calendar;
            int voltage =  displayUsageDataUnit.voltage;
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            Day  day_obj   = new Day(day, month, year);
            Hour hour_obj = new Hour(hour, day_obj);
            Minute minute_obj = new Minute(minute, hour_obj);
            Second second_obj = new Second(second, minute_obj);
            s1.add(second_obj, voltage);
        }


        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);

        dataset.setDomainIsPointsInTime(true);

        return dataset;

    }


    public void updateDataset() {

        if (displayFrame.getY_Axis_Top_Value() < displayFrame.getY_Axis_Actual_Max_Value()){
            displayFrame.setY_Axis_Top_Value(displayFrame.getY_Axis_Actual_Max_Value());
            displayFrame.setY_Axis_Rage(displayFrame.getY_Axis_Top_Value() - displayFrame.getY_Axis_Lower_Value());
            displayFrame.setY_Axis_Ratio(displayFrame.getY_Axis_Rage()/displayFrame.getY_Axis_Rage_Benchmark());
        }
        valueAxis_Y.setRange(displayFrame.getY_Axis_Lower_Value(), displayFrame.getY_Axis_Top_Value());

        ((TimeSeriesCollection)dataset).removeSeries(0);
        TimeSeries s1 = new TimeSeries("A编号防腐蚀设备", Second.class);

        for (int i = 0; i < displayFrame.getAvilableAccount(); i++) {
            DisplayUsageDataUnit displayUsageDataUnit = displayFrame.getDisplayUsageDataUnitArray()[i];
            Calendar calendar = displayUsageDataUnit.calendar;
            int voltage =  displayUsageDataUnit.voltage;
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            //System.out.println("year=" + year + ", month=" + month + ", day=" + day + ", hour=" + hour + ", minute=" + minute + ", second=" + second);
            Day  day_obj   = new Day(day, month, year);
            Hour hour_obj = new Hour(hour, day_obj);
            Minute minute_obj = new Minute(minute, hour_obj);
            Second second_obj = new Second(second, minute_obj);
            s1.add(second_obj, voltage);
        }


        ((TimeSeriesCollection)dataset).addSeries(s1);

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
            //valueAxis_X.setRange(displayFrame.getX_Axis_Lower_Value(), temp_X_Axis_Top_Value);
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
            //valueAxis_X.setRange(displayFrame.getX_Axis_Lower_Value(), temp_X_Axis_Top_Value);
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
            //valueAxis_X.setRange(temp_X_Axis_Lower_Value, temp_X_Axis_Top_Value);
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

    public void X_Right_Shift(int n){
        double temp_X_Axis_Lower_Value = displayFrame.getX_Axis_Lower_Value() + n;
        double temp_X_Axis_Top_Value = temp_X_Axis_Lower_Value + displayFrame.getX_Axis_Rage();
        if ((temp_X_Axis_Lower_Value >= DisplayFrame.DISPLAY_X_AXIS_START_POINT_VALUE) &&
                (temp_X_Axis_Top_Value <= displayFrame.getActualDataCount())){
            //valueAxis_X.setRange(temp_X_Axis_Lower_Value, temp_X_Axis_Top_Value);
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
