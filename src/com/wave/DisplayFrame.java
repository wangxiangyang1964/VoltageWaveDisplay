package com.wave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class DisplayFrame extends JFrame {

    private int width;
    private int height;

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JTabbedPane tabbedPane;
    private JPanel scatterPlotPanel;
    private JPanel timeSeriesPanel;
    private JPanel dataTablePanel;
    private JMenuBar menubar;
    private JToolBar toolbar;
    private JMenu menuFile;
    private JMenu menuHelp;
    private JMenuItem menuItemOpenDataFile;
    private JMenuItem menuItemExit;
    private JButton buttonYEnlarge;
    private JButton buttonYShrink;
    private JButton buttonXEnlarge;
    private JButton buttonXShrink;
    //private JButton buttonOverview;
    private JButton buttonLeftShift;
    private JButton buttonRightShift;
    private TabbedPanel currentViewPanel;

    private int actualDataCount;
    private DataUnit[] dataUnitArray;
    private DisplayUsageDataUnit[] displayUsageDataUnitArray;

    private int avilableAccount;

    public static double DISPLAY_Y_AXIS_NORMAL_RANGE = 500000;
    public static double DISPLAY_Y_AXIS_MAXIMUM_RANGE = 1000000;
    public static double DISPLAY_Y_AXIS_MINIMUM_RANGE = 10;
    public static double DISPLAY_Y_AXIS_BENCHMARK_RANGE = DISPLAY_Y_AXIS_NORMAL_RANGE;
    public static double DISPLAY_Y_AXIS_START_POINT_VALUE = 0;
    public static double DISPLAY_Y_AXIS_TOP_POINT_VALUE = DISPLAY_Y_AXIS_START_POINT_VALUE +
            DISPLAY_Y_AXIS_BENCHMARK_RANGE;

    public static double DISPLAY_X_AXIS_NORMAL_RANGE = 20;
    public static double DISPLAY_X_AXIS_MAXIMUM_RANGE = 50;
    public static double DISPLAY_X_AXIS_MINIMUM_RANGE = 10;
    public static double DISPLAY_X_AXIS_BENCHMARK_RANGE = DISPLAY_X_AXIS_NORMAL_RANGE;
    public static double DISPLAY_X_AXIS_START_POINT_VALUE = 0;
    public static double DISPLAY_X_AXIS_TOP_POINT_VALUE = DISPLAY_X_AXIS_START_POINT_VALUE +
            DISPLAY_X_AXIS_BENCHMARK_RANGE;

    private double Y_Axis_Lower_Value;
    private double Y_Axis_Top_Value;
    private double Y_Axis_Ratio;
    private double Y_Axis_Actual_Max_Value;
    private double Y_Axis_Rage;
    private double Y_Axis_Rage_Benchmark;

    private double X_Axis_Lower_Value;
    private double X_Axis_Top_Value;
    private double X_Axis_Ratio;
    private double X_Axis_Actual_Max_Value;
    private double X_Axis_Rage;
    private double X_Axis_Rage_Benchmark;


    //private JButton buttonCreateData;
    //private JLabel testEventLabel;

    public  DisplayFrame() {
        frame = new JFrame("防腐蚀恒压仪输出电压显示程序");
        currentViewPanel = TabbedPanel.SCATTER_PLOT_PANEL;
        actualDataCount = 0;
        avilableAccount = 0;
        displayUsageDataUnitArray = new DisplayUsageDataUnit[((int)DISPLAY_X_AXIS_MAXIMUM_RANGE)];

        Y_Axis_Lower_Value = DISPLAY_Y_AXIS_START_POINT_VALUE;
        Y_Axis_Top_Value   = DISPLAY_Y_AXIS_TOP_POINT_VALUE;
        Y_Axis_Actual_Max_Value = 0;
        Y_Axis_Rage = Y_Axis_Top_Value - Y_Axis_Lower_Value;
        Y_Axis_Rage_Benchmark = DISPLAY_Y_AXIS_BENCHMARK_RANGE;
        Y_Axis_Ratio = Y_Axis_Rage / Y_Axis_Rage_Benchmark;

        X_Axis_Lower_Value = DISPLAY_X_AXIS_START_POINT_VALUE;
        X_Axis_Top_Value   = DISPLAY_X_AXIS_TOP_POINT_VALUE;
        X_Axis_Actual_Max_Value = 0;
        X_Axis_Rage = X_Axis_Top_Value - X_Axis_Lower_Value;
        X_Axis_Rage_Benchmark = DISPLAY_X_AXIS_BENCHMARK_RANGE;
        X_Axis_Ratio = X_Axis_Rage / X_Axis_Rage_Benchmark;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        width = (screenSize.width * 4) / 5;
        height = (screenSize.height * 4) / 5;
        int x = width / 10;
        int y = height / 10;
        frame.setLocation(x, y);

        BorderLayout bord = new BorderLayout();
        mainPanel = new JPanel();
        frame.setContentPane(mainPanel);
        mainPanel.setLayout(bord);

        southPanel = new JPanel();
        eastPanel = new JPanel();
        westPanel = new JPanel();

        menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        toolbar = new JToolBar();
        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);


        Dimension eastDimension = eastPanel.getSize();
        Dimension westDimension = westPanel.getSize();
        Dimension southDimension = southPanel.getSize();
        Dimension centerDimenstion = tabbedPane.getSize();

        eastPanel.setPreferredSize(new Dimension(0, eastDimension.height));
        westPanel.setPreferredSize(new Dimension(0, westDimension.height));
        southPanel.setPreferredSize(new Dimension(southDimension.width, 0));

        int centerTabbedPanelWidth = centerDimenstion.width + eastDimension.width + westDimension.width;
        int centerTabbedPanelHeight = centerDimenstion.height + southDimension.height;

        Dimension chartDimension = new Dimension(width - 50, height - 150);


        scatterPlotPanel = new ScatterPlotPanel(chartDimension, this);
        timeSeriesPanel = new TimeSeriesChartPanel(chartDimension, this);
        dataTablePanel = new DataTableShow(chartDimension, this);

        tabbedPane.add("scatterPlotPanel", scatterPlotPanel);
        tabbedPane.setEnabledAt(0, true);
        tabbedPane.setTitleAt(0, "散点图");

        tabbedPane.add("timeSeriesPanel", timeSeriesPanel);
        tabbedPane.setEnabledAt(1, true);
        tabbedPane.setTitleAt(1, "时序图");

        tabbedPane.add("dataTablePanel", dataTablePanel);
        tabbedPane.setEnabledAt(2, true);
        tabbedPane.setTitleAt(2, "数据表");

        menuFile = new JMenu("文件");
        menuHelp = new JMenu("帮助");
        menubar.add(menuFile);
        menubar.add(menuHelp);

        menuItemOpenDataFile = new JMenuItem("打开数据文件");
        menuItemExit = new JMenuItem("退出程序");
        menuFile.add(menuItemOpenDataFile);
        menuFile.addSeparator();
        menuFile.add(menuItemExit);

        buttonYEnlarge = new JButton("纵向放大");
        buttonYShrink = new JButton("纵向缩小");
        buttonXEnlarge = new JButton("横向放大");
        buttonXShrink = new JButton("横向缩小");
        //buttonOverview = new JButton("全貌");
        buttonLeftShift = new JButton("水平左移");
        buttonRightShift = new JButton("水平右移");
        //buttonCreateData = new JButton("创建数据文件");

        toolbar.add(buttonYEnlarge);
        toolbar.add(buttonYShrink);
        toolbar.add(buttonXEnlarge);
        toolbar.add(buttonXShrink);
        //toolbar.add(buttonOverview);
        toolbar.add(buttonLeftShift);
        toolbar.add(buttonRightShift);
        //toolbar.add(buttonCreateData);

        //testEventLabel = new JLabel("test event");
        //toolbar.add(testEventLabel);

        ActionListener displayActionListener = new DisplayActionListner(this);
        menuItemExit.addActionListener(displayActionListener);
        menuItemOpenDataFile.addActionListener(displayActionListener);
        buttonYEnlarge.addActionListener(displayActionListener);
        buttonYShrink.addActionListener(displayActionListener);
        buttonXEnlarge.addActionListener(displayActionListener);
        buttonXShrink.addActionListener(displayActionListener);
        buttonLeftShift.addActionListener(displayActionListener);
        buttonRightShift.addActionListener(displayActionListener);
        //buttonOverview.addActionListener(displayActionListener);
        //buttonCreateData.addActionListener(displayActionListener);
        scatterPlotPanel.addAncestorListener(new TabbedPanelAncestorListener(this,TabbedPanel.SCATTER_PLOT_PANEL));
        timeSeriesPanel.addAncestorListener(new TabbedPanelAncestorListener(this, TabbedPanel.TIME_SERIES_CHART_PANEL));
        dataTablePanel.addAncestorListener(new TabbedPanelAncestorListener(this,TabbedPanel.DATA_TABLE_PANEL));

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public JFrame getFrame() {
        return frame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getScatterPlotPanel() {
        return scatterPlotPanel;
    }

    public JPanel getTimeSeriesPanel() {
        return timeSeriesPanel;
    }

    public JPanel getDataTablePanel() {
        return dataTablePanel;
    }

    public JMenu getMenuFile() {
        return menuFile;
    }

    public JMenu getMenuHelp() {
        return menuHelp;
    }

    public JMenuItem getMenuItemOpenDataFile() {
        return menuItemOpenDataFile;
    }

    public JMenuItem getMenuItemExit() {
        return menuItemExit;
    }

    public JButton getButtonYEnlarge() {
        return buttonYEnlarge;
    }

    public JButton getButtonYShrink() {
        return buttonYShrink;
    }

    public JButton getButtonXEnlarge() {
        return buttonXEnlarge;
    }

    public JButton getButtonXShrink() {
        return buttonXShrink;
    }

    public JButton getButtonLeftShift() {
        return buttonLeftShift;
    }

    public JButton getButtonRightShift() {
        return buttonRightShift;
    }

    //public JButton getButtonOverview() {
    //    return buttonOverview;
    //}

    //public JButton getButtonCreateData() {
    //    return buttonCreateData;
    //}

    //public JLabel getTestEventLabel() {
    //    return testEventLabel;
    //}

    public TabbedPanel getCurrentViewPanel(){ return currentViewPanel;}

    public void setCurrentViewPanel(TabbedPanel tabbedPanel) {
        currentViewPanel = tabbedPanel;
    }

    public void setDataUnitArray(DataUnit[] dataUnitArray){
        this.dataUnitArray = dataUnitArray;
    }

    public DataUnit[] getDataUnitArray(){
        return dataUnitArray;
    }

    public void setActualDataCount(int count){
        this.actualDataCount = count;
    }

    public int getActualDataCount(){
        return actualDataCount;
    }

    public void setDisplayUsageDataUnitArray(DisplayUsageDataUnit[] displayUsageDataUnitArray){
        this.displayUsageDataUnitArray = displayUsageDataUnitArray;
    }

    public DisplayUsageDataUnit[] getDisplayUsageDataUnitArray() {
        return displayUsageDataUnitArray;
    }


    public int createDisplayUsageDataUnitArray(){
        if (X_Axis_Lower_Value >= actualDataCount){
            avilableAccount = 0;
        } else {
            avilableAccount = Math.min(actualDataCount - (int) X_Axis_Lower_Value, (int)X_Axis_Rage);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
            Y_Axis_Actual_Max_Value = dataUnitArray[(int) X_Axis_Lower_Value].voltage;
            for (int i = 0; i < avilableAccount; i++) {
                int actualIndex = (int) X_Axis_Lower_Value + i;
                DisplayUsageDataUnit tempDisplayUsageDataUnit = new DisplayUsageDataUnit();
                tempDisplayUsageDataUnit.index = actualIndex;
                tempDisplayUsageDataUnit.timePoint = dataUnitArray[actualIndex].timePoint;
                tempDisplayUsageDataUnit.voltage = dataUnitArray[actualIndex].voltage;
                tempDisplayUsageDataUnit.stringTimePoint = dateFormat.format(tempDisplayUsageDataUnit.timePoint * 1000);
                tempDisplayUsageDataUnit.timePointToCalendar();
                //System.out.println(tempDisplayUsageDataUnit.stringTimePoint);
                displayUsageDataUnitArray[i] = tempDisplayUsageDataUnit;
                if (displayUsageDataUnitArray[i].voltage > Y_Axis_Actual_Max_Value) {
                    Y_Axis_Actual_Max_Value = displayUsageDataUnitArray[i].voltage;
                }
            }
            X_Axis_Actual_Max_Value = (int)X_Axis_Lower_Value + avilableAccount -1;
        }
        return avilableAccount;
    }

    public double getY_Axis_Lower_Value() {
        return Y_Axis_Lower_Value;
    }

    public void setY_Axis_Lower_Value(double y_Axis_Lower_Value) {
        Y_Axis_Lower_Value = y_Axis_Lower_Value;
    }

    public double getY_Axis_Top_Value() {
        return Y_Axis_Top_Value;
    }

    public void setY_Axis_Top_Value(double y_Axis_Top_Value) {
        Y_Axis_Top_Value = y_Axis_Top_Value;
    }

    public double getY_Axis_Ratio() {
        return Y_Axis_Ratio;
    }

    public void setY_Axis_Ratio(double y_Axis_Ratio) {
        Y_Axis_Ratio = y_Axis_Ratio;
    }

    public double getY_Axis_Actual_Max_Value() {
        return Y_Axis_Actual_Max_Value;
    }

    public void setY_Axis_Actual_Max_Value(double y_Axis_Actual_Max_Value) {
        Y_Axis_Actual_Max_Value = y_Axis_Actual_Max_Value;
    }

    public double getY_Axis_Rage() {
        return Y_Axis_Rage;
    }

    public void setY_Axis_Rage(double y_Axis_Rage) {
        Y_Axis_Rage = y_Axis_Rage;
    }

    public double getY_Axis_Rage_Benchmark() {
        return Y_Axis_Rage_Benchmark;
    }

    public void setY_Axis_Rage_Benchmark(double y_Axis_Rage_Benchmark) {
        Y_Axis_Rage_Benchmark = y_Axis_Rage_Benchmark;
    }

    public double getX_Axis_Lower_Value() {
        return X_Axis_Lower_Value;
    }

    public void setX_Axis_Lower_Value(double x_Axis_Lower_Value) {
        X_Axis_Lower_Value = x_Axis_Lower_Value;
    }

    public double getX_Axis_Top_Value() {
        return X_Axis_Top_Value;
    }

    public void setX_Axis_Top_Value(double x_Axis_Top_Value) {
        X_Axis_Top_Value = x_Axis_Top_Value;
    }

    public double getX_Axis_Ratio() {
        return X_Axis_Ratio;
    }

    public void setX_Axis_Ratio(double x_Axis_Ratio) {
        X_Axis_Ratio = x_Axis_Ratio;
    }

    public double getX_Axis_Actual_Max_Value() {
        return X_Axis_Actual_Max_Value;
    }

    public void setX_Axis_Actual_Max_Value(double x_Axis_Actual_Max_Value) {
        X_Axis_Actual_Max_Value = x_Axis_Actual_Max_Value;
    }

    public double getX_Axis_Rage() {
        return X_Axis_Rage;
    }

    public void setX_Axis_Rage(double x_Axis_Rage) {
        X_Axis_Rage = x_Axis_Rage;
    }

    public double getX_Axis_Rage_Benchmark() {
        return X_Axis_Rage_Benchmark;
    }

    public void setX_Axis_Rage_Benchmark(double x_Axis_Rage_Benchmark) {
        X_Axis_Rage_Benchmark = x_Axis_Rage_Benchmark;
    }

    public void setAvilableAccount(int avilableAccount){
        this.avilableAccount = avilableAccount;
    }

    public int getAvilableAccount(){
        return avilableAccount;
    }
}


