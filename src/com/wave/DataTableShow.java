package com.wave;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class DataTableShow extends JPanel {

    DisplayFrame displayFrame;
    JTable dataTable;
    JScrollPane scrollPane;

    String[][] rawData;
    String[] columnNames = { "序号", "采样时间","防腐蚀电压值（毫伏）"};

    DataTableShow(Dimension dimension, DisplayFrame displayFrame) {
        super();
        this.displayFrame = displayFrame;

        rawData = createRawData();

        dataTable = new MyTable(new DefaultTableModel(rawData, columnNames));
        dataTable.setFillsViewportHeight(true);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(dataTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setPreferredSize(new Dimension(dimension.width * 2 / 3, dimension.height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(10));
        this.add(scrollPane);

    }

    public String [][] createRawData(){

        String[][] tempRawData = new String[displayFrame.getAvilableAccount()][];
        for (int i = 0; i < displayFrame.getAvilableAccount(); i++){
            DisplayUsageDataUnit displayUsageDataUnit = displayFrame.getDisplayUsageDataUnitArray()[i];
            Integer index = displayUsageDataUnit.index;
            String timePoint = displayUsageDataUnit.stringTimePoint;
            Integer voltage = displayUsageDataUnit.voltage;
            String[] rows = {index.toString(), timePoint, voltage.toString() };
            tempRawData[i] = rows;
        }
        return tempRawData;
    }

    public void updateDataTable(){

        rawData = createRawData();

        dataTable = new MyTable(new DefaultTableModel(rawData, columnNames));
        dataTable.setFillsViewportHeight(true);
        scrollPane.setViewportView(dataTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    class MyTable extends JTable {

        public MyTable(DefaultTableModel defaultTableModel) {
            super(defaultTableModel);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (getFillsViewportHeight()) {
                paintEmptyRows(g);
            }
        }

        /**
         * Paints the backgrounds of the implied empty rows when the table model is
         * insufficient to fill all the visible area available to us. We don't
         * involve cell renderers, because we have no data.
         */
        protected void paintEmptyRows(Graphics g) {
            final int rowCount = getRowCount();
            final Rectangle clip = g.getClipBounds();
            if (rowCount * rowHeight < clip.height) {
                for (int i = rowCount; i <= clip.height / rowHeight; ++i) {
                    g.setColor(colorForRow(i));
                    g.fillRect(clip.x, i * rowHeight, clip.width, rowHeight);
                }
            }
        }

        /**
         * Returns the appropriate background color for the given row.
         */
        protected Color colorForRow(int row) {
            return (row % 2 == 0) ? Color.lightGray : Color.GRAY;
        }


        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (isCellSelected(row, column) == false) {
                c.setBackground(colorForRow(row));
                c.setForeground(UIManager.getColor("Table.foreground"));
            } else {
                c.setBackground(UIManager.getColor("Table.selectionBackground"));
                c.setForeground(UIManager.getColor("Table.selectionForeground"));
            }
            return c;
        }
    }
}
