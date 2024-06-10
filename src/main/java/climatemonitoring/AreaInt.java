/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Importazione del separatore dalla classe main 'ClimateMonitor'
 */
import java.awt.Dimension;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public class AreaInt extends javax.swing.JDialog {
     /**
      * Creo oggetto di nome 'hh' di tipo 'Home' 
      * Creo una finistra speculare alla Home, in versione 'Operatore' con privilegi e funzioni aggiuntive.
      */
    static ClientCM hh;
    /**
     * Dichiarazione variabili per collegamento al server RMI
     */
    static Registry registry;
    static ClimateInterface stub;
    /**
     * Costruttore <strong>parametrizzato</strong> per bloccare la finestra sottostante
     * @param hh oggetto, di tipo 'Home'
     * @param ck boolean, da classe finestra home 'base'
     */
    public AreaInt(ClientCM hh, boolean ck) {
        /**
         * 'Super' per puntare alla classe genitore, da cui eredito metodi e parametri
         */
        super(hh, ck);
        /**
         * 'This' per puntare al riferimento di una oggetto presente nella classe corrente
         */
        AreaInt.hh=hh;
        /**
         * 'This' per puntare al riferimento di una metodo presente nella classe corrente (setto titolo)
         */
        this.setTitle("Inserisci Area");
        /**
         * Metodo base di Netbeans (Swing designer, parte grafica) per inizializzare il componente
         */
        initComponents();
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
         * Metodo per bloccare la possibilit� di ridimensionare la finestra
         */
        setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        cittaField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        codeField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        inserisci = new javax.swing.JButton();
        countryField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        latField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lonField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Città");

        jLabel3.setText("Sigla Stato");

        jLabel4.setText("Latitudine");

        inserisci.setBackground(new java.awt.Color(255, 153, 0));
        inserisci.setText("Inserisci");
        inserisci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserisciActionPerformed(evt);
            }
        });

        jLabel6.setText("Nome Stato");

        jLabel7.setText("Longitudine");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 102));
        jLabel5.setText("Inserimento Area Interesse");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(lonField, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(codeField, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(cittaField)
                            .addComponent(countryField, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(latField))))
                .addGap(199, 199, 199))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inserisci)
                .addGap(66, 66, 66))
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cittaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(countryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(latField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lonField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(inserisci)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metodo che al click del bottone esegue l'inserimento della localit� (richiama metodo 'inserisciAreaInt')
     * verifica se mancano parte dei parametri richiesti
     * nel caso manchino restituisce un pannello con l'errore
     * @param evt click del bottone
     */
    private void inserisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserisciActionPerformed
        /**
         * Variabili impostate su valore iniziale
         */
        boolean check=true; ArrayList<String> errore=new ArrayList<>();int c=0;
        /**
         * Controlla se cittaField uguale a ' ' (vuoto), se vuoto check su valore 'falso'
         */
        if(cittaField.getText().equals("")){check=false;errore.add("Nome Centro Monitoraggio");c++;}
        /**
         * Controlla se codeField uguale a ' ' (vuoto), se vuoto check su valore 'falso'
         */
        if(codeField.getText().equals("")){check=false;errore.add("Indirizzo fisico");c++;}
        /**
         * Controlla se countryField uguale a ' ' (vuoto), se vuoto check su valore 'falso'
         */
        if(countryField.getText().equals("")){check=false;errore.add("Elenco aree di interesse");c++;}
        /**
         * Controlla se latField uguale a ' ' (vuoto), se vuoto check su valore 'falso'
         */
        if(latField.getText().equals("")){check=false;errore.add("Latitudine");c++;}
        /**
         * Controlla se longField uguale a ' ' (vuoto), se vuoto check su valore 'falso'
         */
        if(lonField.getText().equals("")){check=false;errore.add("Longitudine");c++;}
        /**
             * Se non presenti errori in fase di inserimento, da parte dell'utente
             * Esecuzione funzione per l'inserimento, all'interno di un 'Try' per evitare l'innalzamento di eccezioni
             */
        try{
            /**
             * Verifica che i parametri di latitudine e longitudine siano numeri
             * Risposta con messaggio d'errore nel caso non lo siano
             */
            Double.valueOf(latField.getText()); 
            Double.valueOf(latField.getText());
        }catch(NumberFormatException e){check=false;JOptionPane.showMessageDialog(null, "Inserisci coordinate corrette! ","Errore!", JOptionPane.ERROR_MESSAGE);}
        if(!check){
            String f = "";
            if(!errore.isEmpty()){
                for(String s : errore){ f+="\n-"+s ;}
                 /**
                  * Generazione finestra di errore con specifica dell'errore (non hai inserito: ...)
                  */
                JOptionPane.showMessageDialog(null, "Non hai inserito: " +f,"Errore!", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            try {
                /**
                 * Richiamo funzione d'inserimento dell'Area
                 */
                inserisciAreaInt();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(Registrazione.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_inserisciActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /**
         * Creazione e visualizzazione della pagina di inserimento della località
         */
        java.awt.EventQueue.invokeLater(() -> {
            CentroMonitoraggio dialog = new CentroMonitoraggio(hh, true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
    /**
     * Metodo per l'inserimento, forniti i parametri dall'utente
     * inserisce i valori utilizzando il metodo 'inserisciAreaDB' presente su 'ServerCM' 
     * con implementazione dell'eccezioni (se presenti)
     * Senza parametri perch� recuperati dalle TextField
     * @throws IOException eccezione per mancanza file, directory errata
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    public void inserisciAreaInt() throws IOException{
        try {
            setClient();
            // Chiamata al metodo remoto del server per l'inserimento
            stub.inserisciAreaDB(cittaField.getText(), codeField.getText(), countryField.getText(), latField.getText(), lonField.getText());
            JOptionPane.showMessageDialog(null, "Dati inseriti con successo!");
            this.dispose();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dei dati", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (NotBoundException ex) {
            Logger.getLogger(AreaInt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metodo per settare il 'Client' che accede ai metodi del 'ServerCM'
     * Indirizzo: localHost Porta: 1099
     * con implementazione dell'eccezioni (se presenti)
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    void setClient() throws RemoteException, NotBoundException {
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            stub = (ClimateInterface) registry.lookup("ClimateMonitoring");
            System.out.println("Stub inizializzato con successo.");
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Errore impostando il client RMI: " + e.getMessage());
            throw e; // Rilancia l'eccezione per segnalare il problema
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cittaField;
    private javax.swing.JTextField codeField;
    private javax.swing.JTextField countryField;
    private javax.swing.JButton inserisci;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField latField;
    private javax.swing.JTextField lonField;
    // End of variables declaration//GEN-END:variables
}
