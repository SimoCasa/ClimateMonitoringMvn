/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Richiamo Librerie.
 */
import customzation.TableColumnAdjuster;
import java.awt.Dimension;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
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
public final class AreaParametri extends javax.swing.JDialog {
    /**
     * Dichiarazione variabili per collegamento al server RMI
     */
    static Registry registry;
    static ClimateInterface stub;
    /**
     * Variabile oggetto di tipo 'Home'
     */
    static ClientCM hh;
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
    private List<Map<String, String>> noteList = new ArrayList<>();
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
    public AreaParametri(ClientCM hh, boolean cs, long geo) {
        /**
         * 'Super' per puntare alla classe genitore, da cui eredito metodi e parametri
         */
        super(hh, cs);
        /**
         * 'This' per puntare al riferimento di una oggetto presente nella classe corrente
         */
        AreaParametri.hh = hh;
        AreaParametri.geo=geo;
        /**
         * 'This' per puntare al riferimento di una metodo presente nella classe corrente (setto titolo)
         */
        this.setTitle("Parametri Area");
        /**
         * Metodo base di Netbeans (Swing designer, parte grafica) per inizializzare il componente
         */
        initComponents();
        setClient();
        try {
            /**
             * Richiamo funzione per Visualizzare i Parametri Climatici
             */
            visualizzaParametriClimatici();
        } catch (RemoteException ex) {
            Logger.getLogger(AreaParametri.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        paramTable = new javax.swing.JTable();
        titolo = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        modaTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        medianaTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        mediaTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        paramTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Vento", "Umidità", "Pressione", "Temperatura", "Precipitazioni", "Altitudine Ghiacciai", "Massa Ghiacciai"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JTableHeader header;
        header = paramTable.getTableHeader();
        // Crea un renderer personalizzato per centrare i titoli delle colonne
        DefaultTableCellRenderer renderer;
        renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        paramTable.setEnabled(false);
        paramTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paramTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(paramTable);
        if (paramTable.getColumnModel().getColumnCount() > 0) {
            paramTable.getColumnModel().getColumn(0).setHeaderValue("Data");
        }

        titolo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        titolo.setText("Parametri Climatici:");
        titolo.setText("Parametri Climatici ");

        cityLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        cityLabel.setForeground(new java.awt.Color(0, 153, 153));
        cityLabel.setText(hh.citta);

        jLabel2.setText("Media:");

        modaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vento", "Umidità", "Pressione", "Temperatura", "Precipitazioni", "Altitudine Ghiacciai", "Massa Ghiacciai"
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
        modaTable.setEnabled(false);
        jScrollPane2.setViewportView(modaTable);
        header = modaTable.getTableHeader();
        // Crea un renderer personalizzato per centrare i titoli delle colonne
        renderer= (DefaultTableCellRenderer) header.getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        medianaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vento", "Umidità", "Pressione", "Temperatura", "Precipitazioni", "Altitudine Ghiacciai", "Massa Ghiacciai"
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
        medianaTable.setEnabled(false);
        header = medianaTable.getTableHeader();
        // Crea un renderer personalizzato per centrare i titoli delle colonne
        renderer= (DefaultTableCellRenderer) header.getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jScrollPane3.setViewportView(medianaTable);

        mediaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vento", "Umidità", "Pressione", "Temperatura", "Precipitazioni", "Altitudine Ghiacciai", "Massa Ghiacciai"
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
        mediaTable.setEnabled(false);
        mediaTable.setFocusable(false);
        jScrollPane4.setViewportView(mediaTable);
        TableColumnModel columnModel = paramTable.getColumnModel();
        TableColumnModel columnModelMedia = mediaTable.getColumnModel();
        TableColumnModel columnModelModa = modaTable.getColumnModel();
        TableColumnModel columnModelMediana = medianaTable.getColumnModel();

        // Rendere le colonne non ridimensionabili
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setResizable(false);
        }
        for (int i = 0; i < columnModelMedia.getColumnCount(); i++) {
            columnModelMedia.getColumn(i).setResizable(false);
        }
        for (int i = 0; i < columnModelModa.getColumnCount(); i++) {
            columnModelModa.getColumn(i).setResizable(false);
        }
        for (int i = 0; i < columnModelMediana.getColumnCount(); i++) {
            columnModelMediana.getColumn(i).setResizable(false);
        }

        // Centrare orizzontalmente il testo nelle celle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < paramTable.getColumnCount(); i++) {
            paramTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        for (int i = 0; i < mediaTable.getColumnCount(); i++) {
            mediaTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        for (int i = 0; i < modaTable.getColumnCount(); i++) {
            modaTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        for (int i = 0; i < medianaTable.getColumnCount(); i++) {
            medianaTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Bloccare lo spostamento delle colonne
        paramTable.getTableHeader().setReorderingAllowed(false);
        mediaTable.getTableHeader().setReorderingAllowed(false);
        modaTable.getTableHeader().setReorderingAllowed(false);
        medianaTable.getTableHeader().setReorderingAllowed(false);

        header = mediaTable.getTableHeader();
        // Crea un renderer personalizzato per centrare i titoli delle colonne
        renderer= (DefaultTableCellRenderer) header.getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel3.setText("Moda:");

        jLabel4.setText("Mediana:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(titolo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cityLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1025, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titolo)
                    .addComponent(cityLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void paramTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paramTableMouseClicked
        // Verifica che l'evento sia per la tua tabella specifica
        if (evt.getSource() != paramTable) {
            return;
        }
        
        int row = paramTable.rowAtPoint(evt.getPoint());
        int col = paramTable.columnAtPoint(evt.getPoint());

        if (row != -1 && col != -1) {
            // Ottieni il nome della colonna
            String columnName = paramTable.getColumnName(col);
            // Mappa di colonne e note corrispondenti
            Map<String, String> noteMap = noteList.get(row);
            // Ottieni la nota corrispondente alla colonna selezionata
            String note = noteMap.get(columnName);
            // Selezione della nota corretta
            switch (columnName) {
                case "Vento":
                    note = noteMap.get("notevento");
                    break;
                case "Umidità":
                    note = noteMap.get("noteumidita");
                    break;
                case "Pressione":
                    note = noteMap.get("notepressione");
                    break;
                case "Temperatura":
                    note = noteMap.get("notetemperatura");
                    break;
                case "Precipitazioni":
                    note = noteMap.get("noteprecipitazioni");
                    break;
                case "Altitudine Ghiacciai":
                    note = noteMap.get("notealtitudineghiacciai");
                    break;
                case "Massa Ghiacciai":
                    note = noteMap.get("notemassaghiacciai");
                    break;
                default:
                    note = null;
                    break;
            }
            if(note!=null){
                // Visualizza la nota in una finestra di dialogo
                JOptionPane.showMessageDialog(this, note, "Nota", JOptionPane.INFORMATION_MESSAGE);
            }
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AreaParametri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /**
         * Creazione e visualizzazione della pagina dei Parametri Climatici
         */
        java.awt.EventQueue.invokeLater(() -> {
            /**
             * Metodo per rendere visibile la finestra di visualizzazione
             */
            new AreaParametri().setVisible(true);
        });
    }
    
    
     /**
     * Metodo per la ricerca dei parametri climatici dato il GeoID
     * verifica la presenza dei parametri usando il metodo 'visualizzaParametriClimaticiDB' presente su 'ServerCM' 
     * Senza parametri perchè recuperati dalle TextField
     * @throws java.rmi.RemoteException
     */
    public void visualizzaParametriClimatici() throws RemoteException{
        List<Map<String, String>> listaParametri = stub.visualizzaParametriClimaticiDB(Long.toString(geo));
        model = (DefaultTableModel) paramTable.getModel();
        model.setRowCount(0);
        noteList.clear();

        if (listaParametri.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non sono presenti parametri climatici per l'area selezionata.", "Info", JOptionPane.INFORMATION_MESSAGE);
            ck = false;
        } else {
            for (Map<String, String> parametri : listaParametri) {
                addRowTable(new String[]{
                    addAsteriskIfNoteExists(parametri, "data"),
                    addAsteriskIfNoteExists(parametri, "vento"),
                    addAsteriskIfNoteExists(parametri, "umidita"),
                    addAsteriskIfNoteExists(parametri, "pressione"),
                    addAsteriskIfNoteExists(parametri, "temperatura"),
                    addAsteriskIfNoteExists(parametri, "precipitazioni"),
                    addAsteriskIfNoteExists(parametri, "altitudineghiacciai"),
                    addAsteriskIfNoteExists(parametri, "massaghiacciai")
                },paramTable);

                Map<String, String> noteMap = new HashMap<>();
                    noteMap.put("notedata", parametri.get("notedata"));
                    noteMap.put("notevento", parametri.get("notevento"));
                    noteMap.put("noteumidita", parametri.getOrDefault("noteumidita", "Nessuna nota"));
                    noteMap.put("notepressione", parametri.getOrDefault("notepressione", "Nessuna nota"));
                    noteMap.put("notetemperatura", parametri.getOrDefault("notetemperatura", "Nessuna nota"));
                    noteMap.put("noteprecipitazioni", parametri.getOrDefault("noteprecipitazioni", "Nessuna nota"));
                    noteMap.put("notealtitudineghiacciai", parametri.getOrDefault("notealtitudineghiacciai", "Nessuna nota"));
                    noteMap.put("notemassaghiacciai", parametri.getOrDefault("notemassaghiacciai", "Nessuna nota"));
                    
                     // Debugging statements
            System.out.println("Adding notes for data: " + parametri.get("notedata"));
            System.out.println("Note for vento: " + noteMap.get("notevento"));
            System.out.println("Note for umidita: " + noteMap.get("noteumidita"));
            System.out.println("Note for pressione: " + noteMap.get("notepressione"));
            System.out.println("Note for temperatura: " + noteMap.get("notetemperatura"));
            System.out.println("Note for precipitazioni: " + noteMap.get("noteprecipitazioni"));
            System.out.println("Note for altitudineghiacciai: " + noteMap.get("notealtitudineghiacciai"));
            System.out.println("Note for massaghiacciai: " + noteMap.get("notemassaghiacciai"));
                noteList.add(noteMap);
            }
            ck=true;
            visualizzaMedia();
            visualizzaModa();
            visualizzaMediana();
            // Adjust the width of the first column (column index 0)
            TableColumnAdjuster tca = new TableColumnAdjuster(paramTable);
            tca.adjustColumn(0);
        }
    }
    
     /**
     * Metodo per la visualizzazione e calcolo della media dei parametri climatici
     * verifica la presenza dei parametri usando il metodo 'visualizzaParametriClimaticiDB' presente su 'ServerCM' 
     * Senza parametri perchè recuperati dalle TextField
     * @throws java.rmi.RemoteException
     */
    private void visualizzaMedia() throws RemoteException{
        // Chiamata remota al server per ottenere i parametri climatici
        List<Map<String, String>> parametriClimatici = stub.visualizzaMediaParametriDB(Long.toString(geo));
        for (Map<String, String> riga : parametriClimatici) {
            addRowTable(new String[]{
                riga.get("vento"),
                riga.get("umidita"),
                riga.get("pressione"),
                riga.get("temperatura"),
                riga.get("precipitazioni"),
                riga.get("altitudineghiacciai"),
                riga.get("massaghiacciai")
            },mediaTable);
        }
    }
    /**
     * Metodo per la visualizzazione e calcolo della moda dei parametri climatici
     * verifica la presenza dei parametri usando il metodo 'visualizzaParametriClimaticiDB' presente su 'ServerCM' 
     * Senza parametri perchè recuperati dalle TextField
     * @throws java.rmi.RemoteException
     */
    private void visualizzaModa() throws RemoteException{
        // Chiamata remota al server per ottenere i parametri climatici
        List<Map<String, String>> parametriClimatici = stub.visualizzaModaParametriDB(Long.toString(geo));
        for (Map<String, String> riga : parametriClimatici) {
            addRowTable(new String[]{
                riga.get("vento"),
                riga.get("umidita"),
                riga.get("pressione"),
                riga.get("temperatura"),
                riga.get("precipitazioni"),
                riga.get("altitudineghiacciai"),
                riga.get("massaghiacciai")
            },modaTable);
        }
    }
    /**
     * Metodo per la visualizzazione e calcolo della mediana dei parametri climatici
     * verifica la presenza dei parametri usando il metodo 'visualizzaParametriClimaticiDB' presente su 'ServerCM' 
     * Senza parametri perchè recuperati dalle TextField
     * @throws java.rmi.RemoteException
     */
    private void visualizzaMediana() throws RemoteException{
        // Chiamata remota al server per ottenere i parametri climatici
        List<Map<String, String>> parametriClimatici = stub.visualizzaMedianaParametriDB(Long.toString(geo));
        for (Map<String, String> riga : parametriClimatici) {
            addRowTable(new String[]{
                riga.get("vento"),
                riga.get("umidita"),
                riga.get("pressione"),
                riga.get("temperatura"),
                riga.get("precipitazioni"),
                riga.get("altitudineghiacciai"),
                riga.get("massaghiacciai")
            },medianaTable);
        }
    }
    /**
     * Metodo aggiunta righe alla tabella dei risultati della ricerca
     * @param dataRow string, array di Stringhe passate dall'estrazione da file
     * Nessuna eccezione ges
     * @param customTable
     */
    public static void addRowTable(String[] dataRow, JTable customTable){
        model = (DefaultTableModel)customTable.getModel();
        model.addRow(dataRow);
    }
    /**
     * Metodo per svuotare la tabella
     * Senza parametri perché recuperati dalle TextField
     * Nessuna eccezione gestita
     * @param customTable
     */
    public static void clearTable(JTable customTable){
        model = (DefaultTableModel)paramTable.getModel();
        model.setRowCount(0);
    }
    /**
     * Metodo per settare il 'Client' che accede ai metodi del 'ServerCM'
     * Indirizzo: localHost Porta: 1099
     * con implementazione dell'eccezioni (se presenti)
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    void setClient(){
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            stub = (ClimateInterface) registry.lookup("ClimateMonitoring");
            System.out.println("Stub inizializzato con successo.");
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(this, "Errore di connessione al server RMI: \n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo aggiunta '*' alla righe della tabella dei parametri, che hanno un commento
     * @param parametri string, array di Stringhe passate dall'estrazione da file
     * @param paramName string, nome del parametro da controllare
     * Nessuna eccezione ges
     */
    private String addAsteriskIfNoteExists(Map<String, String> parametri, String paramName) {
        String value = parametri.get(paramName);
        String note = parametri.get("note" + paramName.toLowerCase());
        if (note != null && !note.isEmpty()) {
            return "*"+value + "*";
        } else {
            return value;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cityLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private static javax.swing.JTable mediaTable;
    private static javax.swing.JTable medianaTable;
    private static javax.swing.JTable modaTable;
    private static javax.swing.JTable paramTable;
    private javax.swing.JLabel titolo;
    // End of variables declaration//GEN-END:variables
    private static DefaultTableModel model;
}
