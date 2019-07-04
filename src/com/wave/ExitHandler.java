package com.wave;

import java.awt.event.ActionEvent;

public class ExitHandler {
    DisplayFrame displayFrame;

    ExitHandler(DisplayFrame displayFrame)
    {
        this.displayFrame = displayFrame;
    }

    public void actionPerformed() {
        displayFrame.getFrame().dispose();
    }
}
