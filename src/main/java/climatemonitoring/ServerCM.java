/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Richiamo Librerie.
 */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public class ServerCM extends UnicastRemoteObject implements ClimateInterface{
    private String DB_URL = null;
    private String DB_USER = null;
    private String DB_PASS = null;
    private Connection conn=null;
    private PreparedStatement pstmt=null;
    private ResultSet rs = null;
    private JFrame frame;
    private JTextField dbUrlField;
    private JTextField dbUserField;
    private JTextField dbPassField;
    private JButton connectButton;
    private JButton disconnectButton;
    /**
     * Costruttore <strong>base</strong> (senza parametri)
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     */
    public ServerCM() throws RemoteException, SQLException{
        //DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
        //DB_USER = "postgres";
        //DB_PASS = "password";
    }
     /**
     * Metodo Connessione al DB
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized void dbConnection() throws RemoteException {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Connessione avvenuta con successo!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
                }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore nella connessione: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new RemoteException("Errore durante la connessione al database", ex);
        }
    }
    /**
     * Metodo disconnessione dal DB
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     */
    @Override
    public synchronized void dbDisconnection() throws RemoteException, SQLException{
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
            connectButton.setEnabled(true);
            disconnectButton.setEnabled(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la disconnessione: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new RemoteException("Database error: " + e.getMessage(), e);
        }
    }
    /**
     * Metodo per la ricerca dell'area geografica dato il nome
     * @param nome, tipo 'String' è il nome dell'area geografica inserita dall'utente
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized List<Map<String, String>> cercaAreaGeograficaDB(String nome) throws RemoteException {
        List<Map<String, String>> results = new LinkedList<>();
        String sql = "SELECT GeoNameID, Name, CountryName, CountryCode FROM CoordinateMonitoraggio WHERE LOWER(Name) LIKE LOWER(?)";
        try {
            //dbConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + nome + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("GeoNameID", rs.getString("GeoNameID"));
                row.put("Name", rs.getString("Name"));
                row.put("CountryName", rs.getString("CountryName"));
                row.put("CountryCode", rs.getString("CountryCode"));
                results.add(row);
            }
        }  catch (SQLException e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        } finally {
            //dbDisconnection();

        }

        return results;
    }
     /**
     * Metodo per la ricerca dell'area geografica date le coordinate e eventuale offset
     * @param lat, tipo 'Double' è la latitudine inserita
     * @param lon, tipo 'Double' è la longitudine inserita
     * @param offset, tipo 'Int' in KM per eventuale ricerca nei dintorni delle coordinate
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized  List<Map<String, String>> cercaAreaGeograficaDB(double lat, double lon, int offset) throws RemoteException {
        List<Map<String, String>> results = new LinkedList<>();
        
        try {
            //dbConnection();
            // Calcola l'offset modificato
            double offset_mod = offset * 0.01;

            // Query SQL per estrarre latitudine e longitudine dalla colonna "coordinates"
            String sql = "SELECT GeoNameID, Name, CountryName, CountryCode, " +
                         "CAST(SPLIT_PART(Coordinates, ', ', 1) AS DOUBLE PRECISION) AS Latitudine, " +
                         "CAST(SPLIT_PART(Coordinates, ', ', 2) AS DOUBLE PRECISION) AS Longitudine " +
                         "FROM CoordinateMonitoraggio " +
                         "WHERE (CAST(SPLIT_PART(Coordinates, ', ', 1) AS DOUBLE PRECISION) BETWEEN ? AND ?) " +
                         "AND (CAST(SPLIT_PART(Coordinates, ', ', 2) AS DOUBLE PRECISION) BETWEEN ? AND ?)";

            pstmt = conn.prepareStatement(sql);

            // Imposta i parametri della query
            pstmt.setDouble(1, lat - offset_mod);
            pstmt.setDouble(2, lat + offset_mod);
            pstmt.setDouble(3, lon - offset_mod);
            pstmt.setDouble(4, lon + offset_mod);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("GeoNameID", rs.getString("GeoNameID"));
                row.put("Name", rs.getString("Name"));
                row.put("CountryName", rs.getString("CountryName"));
                row.put("CountryCode", rs.getString("CountryCode"));
                results.add(row);
            }

        } catch (SQLException e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        } finally {
            //dbDisconnection();
        }

        return results;
    }
     /**
     * Metodo per la registrazione dell'utente dati i dati, email, username, password e il centro di competenza
     * @param nome, tipo 'String' è il nome dell'utente
     * @param cognome, tipo 'String' è il cognome dell'utente
     * @param codFisc, tipo 'String' è il codice fiscale dell'utente
     * @param email, tipo 'String' è la email dell'utente
     * @param username, tipo 'String' è lo username dell'utente
     * @param password, tipo 'String' è la password dell'utente
     * @param centroNome, tipo 'String' sono il/i nomi dei centri di comptenza
     * @throws java.rmi.RemoteException
     */  
    @Override
    public synchronized void registrazione(String nome, String cognome, String codFisc, String email, String username, String password, String centroNome) throws RemoteException{
       boolean check = true;
        ArrayList<String> errore = new ArrayList<>();
        int c = 0;
        //CONTROLLO ERRORI
        if (nome.isEmpty()) { check = false; errore.add("Nome"); c++; }
        if (cognome.isEmpty()) { check = false; errore.add("Cognome"); c++; }
        if (email.isEmpty()) { check = false; errore.add("Email"); c++; }
        if (username.isEmpty()) { check = false; errore.add("Username"); c++; }
        if (codFisc.isEmpty()) { check = false; errore.add("Codice Fiscale"); c++; }
        if (password.isEmpty()) { check = false; errore.add("Password"); c++; }
        if (centroNome.isEmpty()) { check = false; errore.add("Centro Monitoraggio"); c++; }
        if (!check) {
            String f = "";
            for (int i = 0; i < c; i++) {
                f += errore.get(i) + "\n";
            }
            // Gestione errori appropriata per l'applicazione server
            throw new RemoteException("Dati Mancanti:\n" + f);
            
        //DATI CORRETTI
        } else {
            try {
                //dbConnection();
                // Recupero dell'ID del centro monitoraggio
                String getCentroIdSql = "SELECT idcentro FROM centromonitoraggio WHERE nome = ?";
                pstmt = conn.prepareStatement(getCentroIdSql);
                pstmt.setString(1, centroNome);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int centroId = rs.getInt("idcentro");

                    // Inserimento dell'operatore nel database
                    String sql = "INSERT INTO operatori (nome, cognome, codfisc, email, userid, password, id_centro) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, nome);
                    statement.setString(2, cognome);
                    statement.setString(3, codFisc);
                    statement.setString(4, email);
                    statement.setString(5, username);
                    statement.setString(6, password);
                    statement.setInt(7, centroId);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        // Inserimento nella tabella rapporti
                        String insertRapportiSql = "INSERT INTO rapporti (idcentro, codfisc, email, data) VALUES (?, ?, ?, ?)";
                        PreparedStatement insertRapportiStmt = conn.prepareStatement(insertRapportiSql);
                        insertRapportiStmt.setInt(1, centroId);
                        insertRapportiStmt.setString(2, codFisc);
                        insertRapportiStmt.setString(3, email);
                        insertRapportiStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

                        int rapportiRowsInserted = insertRapportiStmt.executeUpdate();
                        if (rapportiRowsInserted > 0) {
                            // Registrazione avvenuta con successo
                            System.out.println("Registrazione avvenuta con successo!");
                        } else {
                            // Errore durante l'inserimento nei rapporti
                            throw new RemoteException("Errore durante l'inserimento nei rapporti.");
                        }
                    } else {
                        // Errore durante la registrazione
                        throw new RemoteException("Errore durante la registrazione.");
                    }
                } else {
                    // Centro di monitoraggio non trovato
                    throw new RemoteException("Centro di monitoraggio non trovato!");
                }
            } catch (SQLException ex) {
                // Errore SQL
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
                throw new RemoteException("Errore durante la registrazione: \n" + ex.getMessage());
            }
        }
    }
    
     /**
     * Metodo per l'inserimento dei centri di monitoraggio dato nome, indirizzo e elenco aree di competenza
     * @param nome, tipo 'String' è il nome del centro
     * @param indirizzo, tipo 'String' è l'indirizzo fisico del centro
     * @param elencoAree, tipo 'String' è l'elenco delle aree di competenza
     * @throws java.rmi.RemoteException
     */  
    @Override
    public void inserisciCentroMonitoraggio(String nome, String indirizzo, String elencoAree) throws RemoteException {
        boolean check = true;
        ArrayList<String> errore = new ArrayList<>();
        int c = 0;

        // Controllo degli errori
        if (nome.isEmpty()) {
            check = false;
            errore.add("Nome Centro Monitoraggio");
            c++;
        }
        if (indirizzo.isEmpty()) {
            check = false;
            errore.add("Indirizzo fisico");
            c++;
        }
        if (elencoAree.isEmpty()) {
            check = false;
            errore.add("Elenco aree di interesse");
            c++;
        }

        // Se ci sono errori, solleva un'eccezione
        if (!check) {
            StringBuilder errorMsg = new StringBuilder();
            for (String s : errore) {
                errorMsg.append("\n-").append(s);
            }
            throw new RemoteException("Non hai inserito:" + errorMsg.toString());
        } else {
            try {
                // Inserimento del centro di monitoraggio nel database
                String query = "INSERT INTO centromonitoraggio (nome, indirizzo, areainteresse) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, nome);
                pstmt.setString(2, indirizzo);
                pstmt.setString(3, elencoAree);
                pstmt.executeUpdate();

                System.out.println("Centro di monitoraggio inserito con successo!");
            } catch (SQLException ex) {
                throw new RemoteException("Errore durante l'inserimento del centro di monitoraggio nel database: " + ex.getMessage(), ex);
            } finally {
                //dbDisconnection();

            }
        }
    }
     /**
     * Metodo  per l'inserimento dei parametri climatici dato nomeCentro, nomeArea e le varie informazioni
     * @param nomeCentro, tipo 'String' è il nome del centro
     * @param nomeArea, tipo 'String' èil nome dell'area 
     * @param vento, tipo 'Int' è il valore del vento presente nell'aerea
     * @param umidita, tipo 'Int' è il valore dell'umidit� presente nell'aerea
     * @param pressione, tipo 'Int' è il valore della pressione presente nell'aerea
     * @param temperatura, tipo 'Int' è il valore della temperatura presente nell'aerea
     * @param precipitazioni, tipo 'Int' è il valore della precipitazioni presenti nell'aerea
     * @param alt, tipo 'Int' è il valore dell'altitudine
     * @param mass, tipo 'Int' è il valore della massa
     * @throws java.rmi.RemoteException
     */  
    @Override
    public synchronized void inserisciParametriClimatici(String nomeCentro, String nomeArea, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int alt, int mass, String noteVento, Timestamp data, String noteUmidita, String notePressione, String noteTemperatura, String notePrecipitazioni, String noteAltitudineGhiacciai, String noteMassaGhiacciai) throws RemoteException {
        int IDCentro = 0;
        long GeoID = 0;

        try {
            //dbConnection();

            // Retrieve IDCentro
            String sqlCentro = "SELECT idcentro FROM CentroMonitoraggio WHERE nome = ?";
            pstmt = conn.prepareStatement(sqlCentro);
            pstmt.setString(1, nomeCentro);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                IDCentro = rs.getInt("idcentro");
            }

            // Retrieve GeoID
            String sqlGeo = "SELECT GeonameID FROM CoordinateMonitoraggio WHERE name = ?";
            pstmt = conn.prepareStatement(sqlGeo);
            pstmt.setString(1, nomeArea);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                GeoID = rs.getLong("geonameid");
            }

            // Insert climatic parameters
            String sqlInsert = "INSERT INTO ParametriClimatici (GeonameID, IDCentro, Vento, Umidita, Pressione, Temperatura, Precipitazioni, Altitudineghiacciai, Massaghiacciai, noteVento, data, noteUmidita, notePressione, noteTemperatura, notePrecipitazioni, noteAltitudineGhiacciai, noteMassaGhiacciai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setLong(1, GeoID);
            pstmt.setInt(2, IDCentro);
            pstmt.setInt(3, vento);
            pstmt.setInt(4, umidita);
            pstmt.setInt(5, pressione);
            pstmt.setInt(6, temperatura);
            pstmt.setInt(7, precipitazioni);
            pstmt.setInt(8, alt);
            pstmt.setInt(9, mass);
            pstmt.setString(10, noteVento.isEmpty() ? null : noteVento);
            pstmt.setTimestamp(11, data);
            pstmt.setString(12, noteUmidita.isEmpty() ? null : noteUmidita);
            pstmt.setString(13, notePressione.isEmpty() ? null : notePressione);
            pstmt.setString(14, noteTemperatura.isEmpty() ? null : noteTemperatura);
            pstmt.setString(15, notePrecipitazioni.isEmpty() ? null : notePrecipitazioni);
            pstmt.setString(16, noteAltitudineGhiacciai.isEmpty() ? null : noteAltitudineGhiacciai);
            pstmt.setString(17, noteMassaGhiacciai.isEmpty() ? null : noteMassaGhiacciai);
            
            System.out.println("Inserimento effettuato con successo!\n"+noteVento);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dei dati nel database:\n"+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new RemoteException("Errore durante l'inserimento dei dati nel database: " + e.getMessage(), e);
        } finally {
            //dbDisconnection();
        }
    }
     /**
     * Metodo  per l'inserimento delle aree climatiche dato citta, code e le varie informazioni
     * @param citta, tipo 'String' è il nome della città
     * @param code, tipo 'String' è il codice dell'area
     * @param country, tipo 'String' è il codice del paese
     * @param lat, tipo 'String' è il valore della latitudine
     * @param lon, tipo 'String' è il valore della longitudine
     * @throws java.rmi.RemoteException
     */  
    @Override
    public synchronized void inserisciAreaDB(String citta, String code, String country, String lat, String lon) throws RemoteException {
        String query = "INSERT INTO coordinatemonitoraggio (name, asciiname, countrycode, countryname, coordinates) VALUES (?, ?, ?, ?, ?)";
        try {
            //dbConnection();

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, citta);
            pstmt.setString(2, toAscii(citta));
            pstmt.setString(3, code);
            pstmt.setString(4, country);
            pstmt.setString(5, lat + ", " + lon);
            pstmt.executeUpdate();
            System.out.println("Dati inseriti con successo!");
        } catch (SQLException ex) {
            Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            throw new RemoteException("Errore durante l'inserimento dei dati nel database.", ex);
        } finally {
            //dbDisconnection();

        }
    }
    // MAIN
    public synchronized static void main(String[] args){
        ServerCM server = null; 
        try {
            server = new ServerCM();
        } catch (RemoteException | SQLException ex) {
            Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Errore durante la connessione: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ClimateMonitoring", server);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Errore durante la connessione: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        System.out.println("Server avviato e registrato nel registry RMI.");
        server.initializeGUI();
    }
    /**
         * Metodo  per recuperare l'Utente e quindi eseguire il login dato username e password
         * @param username, tipo 'String' è lo username dell'utente
         * @param password, tipo 'String' è la password dell'utente
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public List<String> getUtente(String username, String password) throws RemoteException {
        String sql = "SELECT nome, cognome, codfisc FROM operatori WHERE userid = ? AND password = ?";
        List<String> userInfo = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // Aggiungi nome, cognome e codice fiscale alla lista
                userInfo.add(rs.getString("nome"));
                userInfo.add(rs.getString("cognome"));
                userInfo.add(rs.getString("codfisc"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return userInfo;
    }
    
     /**
         * Metodo  per recuperare i centri di monitoraggio e popolare quindi la DropDown
         * @return 
         * @throws java.rmi.RemoteException
         */ 
    @Override
    public synchronized List<String> getCentriMonitoraggio() throws RemoteException {
        List<String> res = new ArrayList<>();
        String sql = "SELECT nome FROM centromonitoraggio";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("nome"));
                System.out.println("Server: "+rs.getString("nome"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return res;
    }
     /**
         * Metodo  per recuperare i centri di monitoraggio dato il codice fiscale dell'utente
         * @param codFisc, tipo 'String' è il codice fiscale dell'utente
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public List<String> getCentriMonitoraggio(String codFisc) throws RemoteException {
        List<String> centri = new ArrayList<>();
        try {
            String queryOperatore = "SELECT c.areainteresse FROM operatori o JOIN rapporti r ON o.codFisc = r.codFisc AND o.email=r.email JOIN centromonitoraggio c ON r.idCentro = c.idcentro WHERE o.codFisc = ?";
            PreparedStatement stmtOperatore = conn.prepareStatement(queryOperatore);
            stmtOperatore.setString(1, codFisc);
            ResultSet rsOperatore = stmtOperatore.executeQuery();

            if (rsOperatore.next()) {
                String[] IdArea = rsOperatore.getString("areaInteresse").split(", ");
                String queryCentro = "SELECT nome, areaInteresse FROM CentroMonitoraggio";
                PreparedStatement stmtCentro = conn.prepareStatement(queryCentro);
                ResultSet rsCentro = stmtCentro.executeQuery();

                while (rsCentro.next()) {
                    String nomeCentro = rsCentro.getString("nome");
                    String[] areeCentro = rsCentro.getString("areaInteresse").split(", ");

                    for (String area : IdArea) {
                        if (Arrays.asList(areeCentro).contains(area)) {
                            System.out.println(nomeCentro);
                            centri.add(nomeCentro);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return centri;
    }
    /**
         * Metodo  per recuperare l'area di interesse dato il nome del centro
         * @param nomeCentro, tipo 'String' è il nome del centro
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public List<String> getAreeInteresse(String nomeCentro) throws RemoteException {
        List<String> aree = new ArrayList<>();
        try {
            String query = "SELECT areaInteresse FROM CentroMonitoraggio WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nomeCentro);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String[] areeInteresse = rs.getString("areaInteresse").split(", ");
                Collections.addAll(aree, areeInteresse);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return aree;
    }
      
    /**
     * Metodo per la conversione del nome inserito in 'ASCII'
     * @param accented nome con accenti/caratteri speciali
     * Senza gestione di eccezioni.
     */
    private static String toAscii(String accented){
        final String normalized = Normalizer.normalize( accented, Normalizer.Form.NFD );
        StringBuilder sb = new StringBuilder( accented.length() );
        for ( int i = 0; i < normalized.length(); i++ )
            {
            char c = normalized.charAt( i );
            if ( Character.getType( c ) != Character.NON_SPACING_MARK )
                {
                sb.append(c);
                }
            }
        return sb.toString();
    }
    
    /**
     * Initializes the GUI.
     */    
    private void initializeGUI() {
        JFrame tryFrame = new JFrame("Server ClimateMonitoring");
        tryFrame.setSize(500, 200);
        tryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tryFrame.setLocationRelativeTo(null);

        // Creazione dei componenti dell'interfaccia
        JLabel dbUrlLabel = new JLabel("Database URL:");
        dbUrlField = new JTextField("jdbc:postgresql://localhost:5432/ClimateMonitoring", 50);
        dbUrlField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                dbUrlField.selectAll();
            }
        });

        JLabel dbUserLabel = new JLabel("Username:");
        dbUserField = new JTextField("postgres", 30);
        dbUserField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                dbUserField.selectAll();
            }
        });

        JLabel dbPassLabel = new JLabel("Password:");
        dbPassField = new JPasswordField("password", 30);
        dbPassField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                dbPassField.selectAll();
            }
        });

        connectButton = new JButton("Connettiti");
        connectButton.setBackground(Color.GREEN);
        disconnectButton = new JButton("Disconnettiti");
        disconnectButton.setBackground(Color.RED);
        disconnectButton.setEnabled(false);

        connectButton.addActionListener((ActionEvent e) -> {
            DB_URL = dbUrlField.getText();
            DB_USER = dbUserField.getText();
            DB_PASS = dbPassField.getText();
            try {
                dbConnection();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(tryFrame, "Errore nel Server RMI: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        disconnectButton.addActionListener((ActionEvent e) -> {
            try {
                dbDisconnection();
                JOptionPane.showMessageDialog(tryFrame, "Disconnessione avvenuta con successo!", "Successo!", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        });
        // Layout dell'interfaccia usando GridBagLayout
        tryFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Margini tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allarga i componenti orizzontalmente

        // Prima colonna (etichette)
        gbc.gridx = 0;
        gbc.gridy = 0;
        tryFrame.add(dbUrlLabel, gbc);

        gbc.gridy = 1;
        tryFrame.add(dbUserLabel, gbc);

        gbc.gridy = 2;
        tryFrame.add(dbPassLabel, gbc);

        // Seconda colonna (campi di testo)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Estende i campi di testo su due colonne
        gbc.weightx = 1.0; // Assegna spazio extra orizzontale
        tryFrame.add(dbUrlField, gbc);

        gbc.gridy = 1;
        tryFrame.add(dbUserField, gbc);

        gbc.gridy = 2;
        tryFrame.add(dbPassField, gbc);

        // Terza colonna (pulsanti)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // Ripristina la larghezza del componente a una colonna
        gbc.weightx = 0.5; // Assegna spazio extra orizzontale ai pulsanti
        gbc.anchor = GridBagConstraints.CENTER; // Centra i pulsanti orizzontalmente
        tryFrame.add(connectButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        
        tryFrame.add(disconnectButton, gbc);
        tryFrame.setVisible(true);
    }
    /**
         * Metodo  per visualizzare i parametriclimatici dato il 'GeoNameID' della località
         * @param geoNameID, tipo 'String' è l'ID della località
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public synchronized List<Map<String, String>> visualizzaParametriClimaticiDB(String geoNameID) throws RemoteException {
        List<Map<String, String>> parametri = new ArrayList<>();

        try {
            //dbConnection();

            String query = "SELECT data, vento, umidita, pressione, temperatura, precipitazioni, altitudineghiacciai, massaghiacciai, notevento, noteumidita, notepressione, notetemperatura, noteprecipitazioni, notealtitudineghiacciai, notemassaghiacciai FROM ParametriClimatici WHERE GeoNameID = ? ORDER BY data DESC";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(geoNameID));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> parametriMap = new HashMap<>();
                parametriMap.put("data", rs.getString("data"));
                parametriMap.put("vento", rs.getString("vento"));
                parametriMap.put("umidita", rs.getString("umidita"));
                parametriMap.put("pressione", rs.getString("pressione"));
                parametriMap.put("temperatura", rs.getString("temperatura"));
                parametriMap.put("precipitazioni", rs.getString("precipitazioni"));
                parametriMap.put("altitudineghiacciai", rs.getString("altitudineghiacciai"));
                parametriMap.put("massaghiacciai", rs.getString("massaghiacciai"));
                parametriMap.put("notevento", rs.getString("notevento"));
                parametriMap.put("noteumidita", rs.getString("noteumidita"));
                parametriMap.put("notepressione", rs.getString("notepressione"));
                parametriMap.put("notetemperatura", rs.getString("notetemperatura"));
                parametriMap.put("noteprecipitazioni", rs.getString("noteprecipitazioni"));
                parametriMap.put("notealtitudineghiacciai", rs.getString("notealtitudineghiacciai"));
                parametriMap.put("notemassaghiacciai", rs.getString("notemassaghiacciai"));
                parametri.add(parametriMap);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            //dbDisconnection();

        }

        return parametri;
    }
     /**
         * Metodo  per visualizzare la media dato il 'GeoNameID' della località
         * @param geoNameID, tipo 'String' è l'ID della località
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public synchronized List<Map<String, String>> visualizzaMediaParametriDB(String geoNameID) throws RemoteException {
        List<Map<String, String>> parametri = new ArrayList<>();

        try {
            //È necessario esplicitamente convertire il risultato della funzione AVG al tipo numeric prima di applicare ROUND
            //perchè la funzione ROUND non accetta direttamente un double precision con un numero intero per il numero di cifre decimali
            String query = "SELECT ROUND(CAST(AVG(vento) AS numeric), 2) AS avg_vento, " +
               "ROUND(CAST(AVG(umidita) AS numeric), 2) AS avg_umidita, " +
               "ROUND(CAST(AVG(pressione) AS numeric), 2) AS avg_pressione, " +
               "ROUND(CAST(AVG(temperatura) AS numeric), 2) AS avg_temperatura, " +
               "ROUND(CAST(AVG(precipitazioni) AS numeric), 2) AS avg_precipitazioni, " +
               "ROUND(CAST(AVG(altitudineghiacciai) AS numeric), 2) AS avg_altitudineghiacciai, " +
               "ROUND(CAST(AVG(massaghiacciai) AS numeric), 2) AS avg_massaghiacciai " +
               "FROM ParametriClimatici WHERE GeoNameID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(geoNameID));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> parametriMap = new HashMap<>();
                parametriMap.put("vento", rs.getString("avg_vento"));
                parametriMap.put("umidita", rs.getString("avg_umidita"));
                parametriMap.put("pressione", rs.getString("avg_pressione"));
                parametriMap.put("temperatura", rs.getString("avg_temperatura"));
                parametriMap.put("precipitazioni", rs.getString("avg_precipitazioni"));
                parametriMap.put("altitudineghiacciai", rs.getString("avg_altitudineghiacciai"));
                parametriMap.put("massaghiacciai", rs.getString("avg_massaghiacciai"));
                parametri.add(parametriMap);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return parametri;
    }
    /**
         * Metodo  per visualizzare la moda dato il 'GeoNameID' della località
         * @param geoNameID, tipo 'String' è l'ID della località
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public synchronized List<Map<String, String>> visualizzaModaParametriDB(String geoNameID) throws RemoteException {
        List<Map<String, String>> parametri = new ArrayList<>();

        try {

             String query = "SELECT " +
                            "(SELECT vento FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY vento ORDER BY COUNT(*) DESC, vento ASC LIMIT 1) AS moda_vento, " +
                            "(SELECT umidita FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY umidita ORDER BY COUNT(*) DESC, umidita ASC LIMIT 1) AS moda_umidita, " +
                            "(SELECT pressione FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY pressione ORDER BY COUNT(*) DESC, pressione ASC LIMIT 1) AS moda_pressione, " +
                            "(SELECT temperatura FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY temperatura ORDER BY COUNT(*) DESC, temperatura ASC LIMIT 1) AS moda_temperatura, " +
                            "(SELECT precipitazioni FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY precipitazioni ORDER BY COUNT(*) DESC, precipitazioni ASC LIMIT 1) AS moda_precipitazioni, " +
                            "(SELECT altitudineghiacciai FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY altitudineghiacciai ORDER BY COUNT(*) DESC, altitudineghiacciai ASC LIMIT 1) AS moda_altitudineghiacciai, " +
                            "(SELECT massaghiacciai FROM ParametriClimatici WHERE GeoNameID = ? GROUP BY massaghiacciai ORDER BY COUNT(*) DESC, massaghiacciai ASC LIMIT 1) AS moda_massaghiacciai";

            pstmt = conn.prepareStatement(query);
            // Imposta il GeoNameID per ciascuna subquery
            for (int i = 1; i <= 7; i++) {
                pstmt.setInt(i, Integer.parseInt(geoNameID));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> parametriMap = new HashMap<>();
                parametriMap.put("vento", rs.getString("moda_vento"));
                parametriMap.put("umidita", rs.getString("moda_umidita"));
                parametriMap.put("pressione", rs.getString("moda_pressione"));
                parametriMap.put("temperatura", rs.getString("moda_temperatura"));
                parametriMap.put("precipitazioni", rs.getString("moda_precipitazioni"));
                parametriMap.put("altitudineghiacciai", rs.getString("moda_altitudineghiacciai"));
                parametriMap.put("massaghiacciai", rs.getString("moda_massaghiacciai"));
                parametri.add(parametriMap);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return parametri;
    }
    /**
         * Metodo  per visualizzare la mediana dato il 'GeoNameID' della località
         * @param geoNameID, tipo 'String' è l'ID della località
         * @return 
         * @throws java.rmi.RemoteException
         */  
    @Override
    public synchronized List<Map<String, String>> visualizzaMedianaParametriDB(String geoNameID) throws RemoteException {
        List<Map<String, String>> parametri = new ArrayList<>();

        try {
            String query = "SELECT "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY vento) AS mediana_vento, "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY umidita) AS mediana_umidita, "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY pressione) AS mediana_pressione, "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY temperatura) AS mediana_temperatura, "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY precipitazioni) AS mediana_precipitazioni, "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY altitudineghiacciai) AS mediana_altitudineghiacciai, "
                                + "    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY massaghiacciai) AS mediana_massaghiacciai "
                                + "FROM ParametriClimatici "
                                + "WHERE GeoNameID = ?;";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(geoNameID));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> parametriMap = new HashMap<>();
                parametriMap.put("vento", rs.getString("mediana_vento"));
                parametriMap.put("umidita", rs.getString("mediana_umidita"));
                parametriMap.put("pressione", rs.getString("mediana_pressione"));
                parametriMap.put("temperatura", rs.getString("mediana_temperatura"));
                parametriMap.put("precipitazioni", rs.getString("mediana_precipitazioni"));
                parametriMap.put("altitudineghiacciai", rs.getString("mediana_altitudineghiacciai"));
                parametriMap.put("massaghiacciai", rs.getString("mediana_massaghiacciai"));
                parametri.add(parametriMap);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel Database: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return parametri;
    }
}