package com.wave;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class OpenFileHandler {

    DisplayFrame displayFrame;
    File file = null;
    int result;

    OpenFileHandler(DisplayFrame displayFrame)
    {
        this.displayFrame = displayFrame;
    }

    public void actionPerformed() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("确定");

        fileChooser.setDialogTitle("打开数据文件");
        result = fileChooser.showOpenDialog(displayFrame.getFrame());
        if (result == JFileChooser.APPROVE_OPTION)
        {
            file = fileChooser.getSelectedFile();
            //displayFrame.getTestEventLabel().setText(file.getName());
            String filePath = file.getPath();
            CreateTestDataFile createTestDataFile = new CreateTestDataFile(displayFrame);
            try {
                createTestDataFile.readBasicTypesFromFile(filePath);

            //    displayFrame.getTestEventLabel().setText("timePoint=" + displayFrame.getDataUnitArray()[0].timePoint + ", voltage=" + displayFrame.getDataUnitArray()[0].voltage);
            } catch (IOException e) {
            //    displayFrame.getTestEventLabel().setText(file.getName() + " 读数据出错！");
            }

        }
        else if (result == JFileChooser.CANCEL_OPTION)
        {
         //   displayFrame.getTestEventLabel().setText("您没有选择任何文件");
        }
    }
}
