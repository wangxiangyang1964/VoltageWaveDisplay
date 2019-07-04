package com.wave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DisplayActionListner implements ActionListener {

    private DisplayFrame displayFrame;

    DisplayActionListner(DisplayFrame displayFrame)
    {
        this.displayFrame = displayFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (displayFrame.getMenuItemOpenDataFile() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("打开文件");
            OpenFileHandler openFileHandler = new OpenFileHandler(displayFrame);
            openFileHandler.actionPerformed();
            return;
        }

        if (displayFrame.getMenuItemExit() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("退出程序");
            ExitHandler exitHandler = new ExitHandler(displayFrame);
            exitHandler.actionPerformed();
            return;
        }

        if (displayFrame.getButtonYEnlarge() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("纵向放大按钮");
            if (displayFrame.getAvilableAccount() != 0) {
                if (displayFrame.getCurrentViewPanel() == TabbedPanel.SCATTER_PLOT_PANEL) {
                    ((ScatterPlotPanel) displayFrame.getScatterPlotPanel()).Y_EnLarge();
                } else if (displayFrame.getCurrentViewPanel() == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                    ((TimeSeriesChartPanel) displayFrame.getTimeSeriesPanel()).Y_EnLarge();
                }
            }
            return;
        }

        if (displayFrame.getButtonYShrink() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("纵向缩小按钮");
            if (displayFrame.getAvilableAccount() != 0) {
                if (displayFrame.getCurrentViewPanel() == TabbedPanel.SCATTER_PLOT_PANEL) {
                    ((ScatterPlotPanel) displayFrame.getScatterPlotPanel()).Y_Shrink();
                } else if (displayFrame.getCurrentViewPanel() == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                    ((TimeSeriesChartPanel) displayFrame.getTimeSeriesPanel()).Y_Shrink();
                }
            }
            return;
        }

        if (displayFrame.getButtonXEnlarge() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("横向放大按钮");
            if (displayFrame.getAvilableAccount() != 0) {
                if (displayFrame.getCurrentViewPanel() == TabbedPanel.SCATTER_PLOT_PANEL) {
                    ((ScatterPlotPanel) displayFrame.getScatterPlotPanel()).X_Enlargy();
                    ;
                } else if (displayFrame.getCurrentViewPanel() == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                    ((TimeSeriesChartPanel) displayFrame.getTimeSeriesPanel()).X_Enlargy();
                }
            }
            return;
        }

        if (displayFrame.getButtonXShrink() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("横向缩小按钮");
            if (displayFrame.getAvilableAccount() != 0) {
                if (displayFrame.getCurrentViewPanel() == TabbedPanel.SCATTER_PLOT_PANEL) {
                    ((ScatterPlotPanel) displayFrame.getScatterPlotPanel()).X_Shrink();
                    ;
                } else if (displayFrame.getCurrentViewPanel() == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                    ((TimeSeriesChartPanel) displayFrame.getTimeSeriesPanel()).X_Shrink();
                }
            }
            return;
        }

        if (displayFrame.getButtonLeftShift() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("水平左移按钮");
            if (displayFrame.getAvilableAccount() != 0) {
                if (displayFrame.getCurrentViewPanel() == TabbedPanel.SCATTER_PLOT_PANEL) {
                    ((ScatterPlotPanel) displayFrame.getScatterPlotPanel()).X_Left_Shift((int) displayFrame.getX_Axis_Rage());
                } else if (displayFrame.getCurrentViewPanel() == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                    ((TimeSeriesChartPanel) displayFrame.getTimeSeriesPanel()).X_Left_Shift((int) displayFrame.getX_Axis_Rage());
                }
            }
            return;
        }

        if (displayFrame.getButtonRightShift() == e.getSource())
        {
            //displayFrame.getTestEventLabel().setText("水平右移按钮");
            if (displayFrame.getAvilableAccount() != 0) {
                if (displayFrame.getCurrentViewPanel() == TabbedPanel.SCATTER_PLOT_PANEL) {
                    ((ScatterPlotPanel) displayFrame.getScatterPlotPanel()).X_Right_Shift((int) displayFrame.getX_Axis_Rage());
                } else if (displayFrame.getCurrentViewPanel() == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                    ((TimeSeriesChartPanel) displayFrame.getTimeSeriesPanel()).X_Right_Shift((int) displayFrame.getX_Axis_Rage());
                }
            }
            return;
        }

        //if (displayFrame.getButtonOverview() == e.getSource())
        //{
        //    displayFrame.getTestEventLabel().setText("全貌");
        //    return;
        //}

        //if (displayFrame.getButtonCreateData() == e.getSource())
        //{
        //    String path = System.getProperty("usr.dir") + "testData.dat";
        //    displayFrame.getTestEventLabel().setText("开始创建数据文件" + path);
        //    CreateTestDataFile createTestDataFile = new CreateTestDataFile(displayFrame);
        //    try {
        //        createTestDataFile.writeBasicTypesToFile(path);
        //    } catch (IOException e1 ) {
        //        e1.printStackTrace();
        //        displayFrame.getTestEventLabel().setText("开始创建数据文件" + path + "出错！");
        //    }

        //    displayFrame.getTestEventLabel().setText("结束创建数据文件" + path);

        //}

    }

}
