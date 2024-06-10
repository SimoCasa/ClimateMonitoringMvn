/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Richiamo Librerie di Java
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.util.List;


/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public final class Registrazione extends JDialog {
    /**
     * Dichiarazione variabili per collegamento al server RMI
     */
    static Registry registry;
    static ClimateInterface stub;
    /**
     * Costruttore <strong>base</strong> (senza parametri)
     */
    public Registrazione(){}
    /**
     * Costruttore <strong>parametrizzato</strong> per bloccare la finestra sottostante
     * @param reg oggetto, di tipo 'Home'
     * @param ck boolean, da classe finestra home 'base'
     */
    public Registrazione(ClientCM reg, boolean ck){
        /**
         * 'Super' per puntare alla classe genitore, da cui eredito metodi e parametri
         */
        super(reg,ck);
        /**
         * 'This' per puntare al riferimento di una metodo presente nella classe corrente (setto titolo)
         */
        this.setTitle("Registrati");
        /**
         * Metodo base di Netbeans (Swing designer, parte grafica) per inizializzare il componente
         */
        initComponents();
        /**
         * Metodi per eseguire il setting del client e visualizzare gli elementi della dropdown
         */
        try {
            setClient();
            centriDropSelector();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Registrazione.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Registrati = new javax.swing.JButton();
        nome = new javax.swing.JTextField();
        cognome = new javax.swing.JTextField();
        codFisc = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        centriDrop = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 102));
        jLabel1.setText("REGISTRAZIONE");

        jLabel2.setText("Nome");

        jLabel3.setText("Cognome");

        jLabel4.setText("Codice Fiscale");

        jLabel5.setText("Email");

        jLabel6.setForeground(new java.awt.Color(102, 255, 0));
        jLabel6.setText("Username");

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Password");

        jLabel8.setText("Centro Monitoraggio");

        Registrati.setBackground(new java.awt.Color(255, 153, 102));
        Registrati.setForeground(new java.awt.Color(51, 51, 51));
        Registrati.setText("Registrati");
        Registrati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistratiActionPerformed(evt);
            }
        });

        centriDrop.addItem("");
        //centriDrop.setSelectedIndex(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(centriDrop, 0, 138, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGap(43, 43, 43)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nome)
                                .addComponent(cognome)
                                .addComponent(codFisc)
                                .addComponent(email)
                                .addComponent(username)
                                .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(168, Short.MAX_VALUE)
                .addComponent(Registrati)
                .addContainerGap(153, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cognome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(codFisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(centriDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Registrati)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //ArrayList model<String>=new ArrayList<String>();

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metodo che al click del bottone esegue la registrazione dell'operatore (richiama metodo 'Registrazione')
     * verifica se mancano parte delle credenziali richieste
     * @param evt click del bottone
     */
    private void RegistratiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistratiActionPerformed
        try {
            reg();
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(Registrazione.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RegistratiActionPerformed
    

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
            java.util.logging.Logger.getLogger(Registrazione.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /**
         * Creazione e visualizzazione del form di inserimento (per registrazione) 
         */
        java.awt.EventQueue.invokeLater(() -> {
            /**
             * Metodo per rendere visibile il form d'inserimento
             */
            new Registrazione().setVisible(true);
        });
    }
    /**
     * Metodo per la Registrazione, forniti i parametri dall'utente
     * inserisce utilizzando il metodo 'registrazione' presente su 'ServerCM'
     * con implementazione dell'eccezioni (se presenti)
     * Senza parametri perch� recuperati dalle TextField
     * @throws IOException eccezione per mancanza file, directory errata
     * @throws java.rmi.RemoteException
    */
    public void reg() throws IOException{
        
        boolean check = true;
        ArrayList<String> errore = new ArrayList<>();
        int c = 0;

        if (nome.getText().equals("")) { check = false; errore.add("Nome"); c++; }
        if (cognome.getText().equals("")) { check = false; errore.add("Cognome"); c++; }
        if (email.getText().equals("")) { check = false; errore.add("Email"); c++; }
        if (username.getText().equals("")) { check = false; errore.add("Username"); c++; }
        if (codFisc.getText().equals("")) { check = false; errore.add("Codice Fiscale"); c++; }
        if (password.getText().equals("")) { check = false; errore.add("Password"); c++; }
        if (centriDrop.getSelectedItem().equals("")) { check = false; errore.add("Centro Monitoraggio"); c++; }

        if (!check) {
            String f = "";
            for (int i = 0; i < c; i++) {
                f += errore.get(i) + "\n";
            }
            JOptionPane.showMessageDialog(null, "Dati Mancanti : \n" + f, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String centroNome = centriDrop.getSelectedItem().toString();
                stub.registrazione(nome.getText(), cognome.getText(), codFisc.getText(), email.getText(), username.getText(), password.getText(), centroNome);
            } catch (RemoteException e) {
                // Gestione dell'eccezione remota
                Logger.getLogger(Registrazione.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(this, "Errore durante la registrazione: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Metodo per settare la DropDown 'CentriDrop' sui valori presenti in DB
     * verifica la presenza dei centri usando il metodo 'getCentriMonitoraggio' presente su 'ServerCM'
     * @throws java.rmi.RemoteException
     */
    private void centriDropSelector() throws RemoteException {
        List<String> centri = stub.getCentriMonitoraggio();
        centriDrop.removeAllItems();
        for (String centro : centri) {
            centriDrop.addItem(centro);
            System.out.println("Client: "+centro);
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
    private javax.swing.JButton Registrati;
    private javax.swing.JComboBox<String> centriDrop;
    private javax.swing.JTextField codFisc;
    private javax.swing.JTextField cognome;
    private javax.swing.JTextField email;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField nome;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

}
