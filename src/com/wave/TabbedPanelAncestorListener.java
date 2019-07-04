package com.wave;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;

public class TabbedPanelAncestorListener implements AncestorListener {

    private DisplayFrame displayFrame;
    private TabbedPanel tabbedPanel;

    TabbedPanelAncestorListener(DisplayFrame displayFrame, TabbedPanel tabbedPanel) {
        super();

        this.displayFrame = displayFrame;
        this.tabbedPanel = tabbedPanel;
    }

        @Override
        public void ancestorAdded(AncestorEvent event) {

            //System.out.println("Added");
            //dumpInfo(event);
            this.displayFrame.setCurrentViewPanel(tabbedPanel);
            if (tabbedPanel == TabbedPanel.SCATTER_PLOT_PANEL){
                ((ScatterPlotPanel)displayFrame.getScatterPlotPanel()).updateDataset();
            } else if (tabbedPanel == TabbedPanel.TIME_SERIES_CHART_PANEL) {
                ((TimeSeriesChartPanel)displayFrame.getTimeSeriesPanel()).updateDataset();
            } else if (tabbedPanel == TabbedPanel.DATA_TABLE_PANEL) {
                ((DataTableShow)displayFrame.getDataTablePanel()).updateDataTable();
            }

            //dumpInfo(event);
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
        }

        @Override
        public void ancestorRemoved(AncestorEvent event ){
        }

        private void dumpInfo(AncestorEvent event) {

            System.out.println(displayFrame.getCurrentViewPanel() + "   Ancestor: " + name(event.getAncestor()));
            System.out.println(displayFrame.getCurrentViewPanel() + "   AncestorParent: "
                    + name(event.getAncestorParent()));
            System.out.println(displayFrame.getCurrentViewPanel() + "   Component: " + name(event.getComponent()));
        }

        private String name(Container container) {
            return (container == null) ? null : container.getName();
        }
    };

