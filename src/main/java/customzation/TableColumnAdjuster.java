/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package customzation;
/**
 * Richiamo Librerie.
 */
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;
/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public class TableColumnAdjuster {
    private final JTable table;
    /**
    * Crea un'istanza di {@code TableColumnAdjuster} per la tabella specificata.
    *
    * <p>Questo costruttore inizializza l'oggetto {@code TableColumnAdjuster} con la tabella
    * fornita, che sarà utilizzata per regolare automaticamente la larghezza delle colonne.</p>
    *
    * @param table la {@code JTable} per la quale regolare la larghezza delle colonne.
    *              Deve essere diversa da {@code null}.
    * @throws IllegalArgumentException se {@code table} è {@code null}.
    */
    public TableColumnAdjuster(JTable table) {
        this.table = table;
    }
    /**
    * Regola automaticamente la larghezza della colonna specificata per adattarsi al contenuto.
    *
    * <p>Questo metodo calcola la larghezza necessaria per visualizzare correttamente
    * il contenuto dell'intestazione e delle celle della colonna specificata.
    * La larghezza della colonna viene impostata per essere sufficientemente ampia
    * a contenere il testo più lungo tra l'intestazione e le celle, con un margine aggiuntivo.</p>
    *
    * @param col l'indice della colonna da regolare. Deve essere compreso tra 0 e 
    *            {@code table.getColumnModel().getColumnCount() - 1}.
    * @throws IllegalArgumentException se {@code col} è minore di 0 o maggiore o uguale al numero totale di colonne.
    */
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

