package com.wave;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class CreateTestDataFile {


    public static double MAX_VOLTAGE = (DisplayFrame.DISPLAY_X_AXIS_START_POINT_VALUE +
            DisplayFrame.DISPLAY_Y_AXIS_BENCHMARK_RANGE / 2);
    public static int MAX_DATA_COUNT = 1000000;

    DisplayFrame displayFrame;
    CreateTestDataFile(DisplayFrame displayFrame){
        this.displayFrame = displayFrame;
    }


    public void readBasicTypesFromFile(String srcPath) throws IOException {
        // 与要读取的文件建立联系
        DataInputStream dis = new DataInputStream(new BufferedInputStream( new FileInputStream(srcPath) ));
        DataUnit[] tempDataUnitArray = new DataUnit[MAX_DATA_COUNT];
        // 读取操作
        int count = 0;
        while (dis.available() > 0)
        {
            DataUnit dataUnit = new DataUnit();
            dataUnit.timePoint = dis.readLong();
            dataUnit.voltage = dis.readInt();

            tempDataUnitArray[count] = dataUnit;
            count += 1;
            if (count >= MAX_DATA_COUNT){
                break;
            }
        }

        //System.out.println("Actual Data:" + count);
        displayFrame.setActualDataCount(count);
        displayFrame.setDataUnitArray(tempDataUnitArray);

        displayFrame.setY_Axis_Lower_Value(DisplayFrame.DISPLAY_Y_AXIS_START_POINT_VALUE);
        displayFrame.setY_Axis_Top_Value(DisplayFrame.DISPLAY_Y_AXIS_TOP_POINT_VALUE);
        displayFrame.setY_Axis_Rage(displayFrame.getY_Axis_Top_Value() - displayFrame.getY_Axis_Lower_Value());
        displayFrame.setY_Axis_Rage_Benchmark(DisplayFrame.DISPLAY_Y_AXIS_BENCHMARK_RANGE);
        displayFrame.setY_Axis_Actual_Max_Value(0);
        displayFrame.setY_Axis_Ratio(displayFrame.getY_Axis_Rage() / displayFrame.getY_Axis_Rage_Benchmark());

        displayFrame.setX_Axis_Lower_Value(DisplayFrame.DISPLAY_X_AXIS_START_POINT_VALUE);
        displayFrame.setX_Axis_Top_Value(DisplayFrame.DISPLAY_X_AXIS_TOP_POINT_VALUE);
        displayFrame.setX_Axis_Rage(displayFrame.getX_Axis_Top_Value() - displayFrame.getX_Axis_Lower_Value());
        displayFrame.setX_Axis_Rage_Benchmark(DisplayFrame.DISPLAY_X_AXIS_BENCHMARK_RANGE);
        displayFrame.setX_Axis_Actual_Max_Value(0);
        displayFrame.setX_Axis_Ratio(displayFrame.getX_Axis_Rage() / displayFrame.getX_Axis_Rage_Benchmark());

        displayFrame.createDisplayUsageDataUnitArray();

        ((ScatterPlotPanel)displayFrame.getScatterPlotPanel()).updateDataset();
        ((TimeSeriesChartPanel)displayFrame.getTimeSeriesPanel()).updateDataset();
        ((DataTableShow)displayFrame.getDataTablePanel()).updateDataTable();

        dis.close();
    }

    /*
    public void writeBasicTypesToFile(String desPath) throws IOException {

        DataUnit[] dataUnitList = new DataUnit[MAX_DATA_COUNT];
        Date date = new Date();
        long initTimePoint = date.getTime() / 1000;
        int currentVoltage = 0;
        long currentTimePoint = initTimePoint;

        for (int i = 0; i < MAX_DATA_COUNT; i++)
        {
            DataUnit dataUnit = new DataUnit();

            currentTimePoint += 1;
            dataUnit.timePoint = currentTimePoint;

            currentVoltage = (int)(Math.random() * (MAX_VOLTAGE - 1000) + 100);
            dataUnit.voltage = currentVoltage;

            dataUnitList[i] = dataUnit;
        }


        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(desPath)));
        for (int i = 0; i < MAX_DATA_COUNT; i++) {
            dos.writeLong(dataUnitList[i].timePoint);
            dos.writeInt(dataUnitList[i].voltage);
        }

        dos.flush();
        dos.close();
    }

    */
}

