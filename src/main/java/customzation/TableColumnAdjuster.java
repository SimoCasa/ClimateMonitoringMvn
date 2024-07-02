/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customzation;

/**
 *
 * @author simoc
 */
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;

public class TableColumnAdjuster {
    private final JTable table;

    public TableColumnAdjuster(JTable table) {
        this.table = table;
    }

    public void adjustColumn(int col) {
        TableModel model = table.getModel();
        TableColumnModel columnModel = table.getColumnModel();

        if (col < 0 || col >= columnModel.getColumnCount()) {
            throw new IllegalArgumentException("Invalid column index");
        }

        int maxWidth;

        // Calcola la larghezza necessaria per l'intestazione della colonna
        TableColumn column = columnModel.getColumn(col);
        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if (headerRenderer == null) {
            headerRenderer = table.getTableHeader().getDefaultRenderer();
        }
        Component headerComponent = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, col);
        maxWidth = headerComponent.getPreferredSize().width;

        // Calcola la larghezza necessaria per le celle della colonna
        for (int row = 0; row < model.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
            Component cellComponent = cellRenderer.getTableCellRendererComponent(table, model.getValueAt(row, col), false, false, row, col);
            int cellWidth = cellComponent.getPreferredSize().width;
            maxWidth = Math.max(maxWidth, cellWidth);
        }

        // Imposta la larghezza della colonna
        column.setPreferredWidth(maxWidth + 10); // Aggiungi un piccolo margine
    }
}

