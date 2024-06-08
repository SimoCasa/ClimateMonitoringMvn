/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Importazione del separatore dalla classe main 'ClimateMonitor'
 */
import static climatemonitoring.ClimateMonitor.sep;
import java.awt.Color;
import java.awt.Component;
/**
 * Richiamo Librerie.
 */
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public class AreaParametri extends javax.swing.JDialog {
    /**
      * Creo oggetto di nome 'hh' di tipo 'Home' 
      * Creo una finistra speculare alla Home, in versione 'Operatore' con privilegi e funzioni aggiuntive.
      */
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";
    private static  String note = null;
    /**
     * Variabile oggetto di tipo 'Home'
     */
    static Home hh;
    /**
     * Variabile che contiene il 'GeoNameID'
     */
    static long geo;
    /**
     * Variabile per controllare se sono presenti parametri climatici nel file
     */
    static boolean ck=false;
    /**
     * Variabile per memorizzare le note di ogni riga
     */
    private List<String> noteList = new ArrayList<>();
    /**
     * Costruttore <strong>base</strong> (senza parametri)
     */
    public AreaParametri(){}
    /**
     * Costruttore <strong>parametrizzato</strong>
     * @param hh oggetto, di tipo 'Home'
     * @param cs boleean, del costuttore
     * @param geo long, indica il GeoNameID
    */
    public AreaParametri(Home hh, boolean cs, long geo) {
        /**
         * 'Super' per puntare alla classe genitore, da cui eredito metodi e parametri
         */
        super(hh, cs);
        /**
         * 'This' per puntare al riferimento di una oggetto presente nella classe corrente
         */
        this.hh = hh;
        this.geo=geo;
        /**
         * 'This' per puntare al riferimento di una metodo presente nella classe corrente (setto titolo)
         */
        this.setTitle("Accedi");
        /**
         * Metodo base di Netbeans (Swing designer, parte grafica) per inizializzare il componente
         */
        initComponents();
        /**
         * Richiamo funzione per Visualizzare i Parametri Climatici
         */
        visualizzaParametriClimatici();
        /**
         * Controllo per vedere se sono presenti parametri climatici nel file
         */
        if(!ck){
            /**
             * Metodo per rendere non visibile la finestra ed pulirla
             */ 
            setVisible(false);dispose();
        }else{
            /**
             * Metodo per recuperare la dimensione del display, per creare una finestra coerente
             */
            Dimension dim = getToolkit().getScreenSize();
            /**
             * Metodo per posizione la finestra
             */
            this.setLocation(dim.width / 2 - this.getWidth() / 2, dim.height / 2 - this.getHeight() / 2);
            /**
             * Metodo per rendere visibile la finestra
             */ 
            setVisible(true);
            /**
             * Metodo per bloccare la possibilità di ridimensionare la finestra
             */
            setResizable(false);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        paramTable = new javax.swing.JTable();
        titolo = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        paramTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vento", "Umidit�", "Pressione", "Temperatura", "Precipitazioni", "Altitudine Ghiacciai", "Massa Ghiacciai"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableColumnModel columnModel = paramTable.getColumnModel();

        // Rendere le colonne non ridimensionabili
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setResizable(false);
        }

        // Centrare orizzontalmente il testo nelle celle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < paramTable.getColumnCount(); i++) {
            paramTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Bloccare lo spostamento delle colonne
        paramTable.getTableHeader().setReorderingAllowed(false);
        paramTable.setEnabled(false);
        paramTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paramTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(paramTable);

        titolo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titolo.setText("Parametri Climatici:");
        titolo.setText("Parametri Climatici ");

        cityLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cityLabel.setForeground(new java.awt.Color(0, 153, 153));
        cityLabel.setText(hh.citta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(titolo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cityLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titolo)
                    .addComponent(cityLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void paramTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paramTableMouseClicked
        int row = paramTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < noteList.size()) {
                    String note = noteList.get(row);
                    mostraFinestraNote(note);
                }
    }//GEN-LAST:event_paramTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AreaParametri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AreaParametri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AreaParametri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AreaParametri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /**
         * Creazione e visualizzazione della pagina dei Parametri Climatici
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /**
                 * Metodo per rendere visibile la finestra di visualizzazione
                 */
                new AreaParametri().setVisible(true);
            }
        });
    }
    
    
     /**
     * Metodo per la ricerca dei parametri climatici dato il GeoID
     * Senza parametri perch� recuperati dalle TextField
     * Gestita eccezione: IOException eccezione per mancanza file, directory errata
     */
    public void visualizzaParametriClimatici(){
        /*
         * Imposto la connessione e la query su valore 'nullo' iniziale
         */
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            /**
             * Connessione al database
             * Sostituire 'db_url', 'db_user', 'db_password' con le credenziali del proprio database
             */
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            /**
             * Query per recuperare i parametri climatici in base al GeoNameID
             */
            String query = "SELECT vento, umidita, pressione, temperatura, precipitazione, altitudineghiacciai, massaghiacciai, note FROM ParametriClimatici WHERE GeoNameID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, geo);

            /**
             * Esecuzione della query
             */
            rs = pstmt.executeQuery();

            /**
             * Se la query restituisce risultati, popola la tabella
             */
            boolean hasData = false;
            while (rs.next()) {
                addRowTable(new String[]{
                    rs.getString("vento"),
                    rs.getString("umidita"),
                    rs.getString("pressione"),
                    rs.getString("temperatura"),
                    rs.getString("precipitazione"),
                    rs.getString("altitudineghiacciai"),
                    rs.getString("massaghiacciai")
                });
                noteList.add(rs.getString("note"));
                hasData = true;
            }

            if (!hasData) {
                /**
                 * Generazione finestra di errore con specifica dell'errore (Parametri climatici assenti)
                 */
                JOptionPane.showMessageDialog(null, "Non sono disponibili parametri climatici per la seguente citt�!", "Avvertenza!!", JOptionPane.WARNING_MESSAGE);
                dispose();
            } else {
                ck = true;
            }
        } catch (SQLException e) {
            /**
             * Cattura errore in caso di mancato funzionamento del metodo 'visualizzaParametriClimatici'
             * Scrittura su riga di comando dell'errore per debug
             */
            System.out.print(e);
        } finally {
            /**
             * Chiusura delle risorse
             */
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.print(e);
            }
        }
    }
    /**
     * Metodo aggiunta righe alla tabella dei risultati della ricerca
     * @param dataRow string, array di Stringhe passate dall'estrazione da file
     * Nessuna eccezione gestita
     */
    public static void addRowTable(String[] dataRow){
        model = (DefaultTableModel)paramTable.getModel();
        model.addRow(dataRow);
    }
    /**
     * Metodo per svuotare la tabella
     * Senza parametri perché recuperati dalle TextField
     * Nessuna eccezione gestita
     */
    public static void clearTable(){
        model = (DefaultTableModel)paramTable.getModel();
        model.setRowCount(0);
    }
    /**
     * Metodo per mostrare una finestra di dialogo con le note
     * @param note Il testo delle note da visualizzare
     */
    private void mostraFinestraNote(String note) {
        JTextArea textArea = new JTextArea(note);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Note", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cityLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable paramTable;
    private javax.swing.JLabel titolo;
    // End of variables declaration//GEN-END:variables
    private static DefaultTableModel model;
}
