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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public class ServerCM extends UnicastRemoteObject implements ClimateInterface{
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;
    private Connection conn=null;
    private PreparedStatement pstmt=null;
    private ResultSet rs = null;
    private JFrame frame;
    /**
     * Costruttore <strong>base</strong> (senza parametri)
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     */
    public ServerCM() throws RemoteException, SQLException{
        DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
        DB_USER = "postgres";
        DB_PASS = "password";
    }
     /**
     * Metodo Connessione al DB
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     */
    @Override
    public synchronized void dbConnection() throws RemoteException,SQLException{
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
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
        } catch (SQLException e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        }
    }
    /**
     * Metodo per la ricerca dell'area geografica dato il nome
     * @param nome, tipo 'String' è il nome dell'area geografica inserita dall'utente
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized List<Map<String, String>> cercaAreaGeograficaDB(String nome) throws RemoteException {
        List<Map<String, String>> results = new LinkedList<>();
        String sql = "SELECT GeoNameID, Name, CountryName, CountryCode FROM CoordinateMonitoraggio WHERE LOWER(Name) LIKE LOWER(?)";
        try {
            dbConnection();
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
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            dbConnection();
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
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                dbConnection();
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
                dbConnection();

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
                try {
                    dbDisconnection();
                } catch (SQLException ex) {
                    Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
                }
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
     * @param alt, tipo 'Int' è il valore dell'altitudine
     * @param mass, tipo 'Int' è il valore della massa
     * @param note, tipo 'String' èuna nota generica sulle condizioni meteo
     * @throws java.rmi.RemoteException
     */  
    @Override
    public synchronized void inserisciParametriClimatici(String nomeCentro, String nomeArea, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int alt, int mass, String note) throws RemoteException {
        int IDCentro = 0;
        long GeoID = 0;

        try {
            dbConnection();

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
            String sqlInsert = "INSERT INTO ParametriClimatici (GeonameID, IDCentro, Vento, Umidita, Pressione, Temperatura, Precipitazione, Altitudineghiacciai, Massaghiacciai, Note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.setString(10, note.isEmpty() ? "Nessun commento disponibile!" : note);

            pstmt.executeUpdate();
            System.out.println("Inserimento effettuato con successo!");
        } catch (SQLException e) {
            throw new RemoteException("Errore durante l'inserimento dei dati nel database: " + e.getMessage(), e);
        } finally {
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            dbConnection();

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
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
         * Metodo  per visualizzare i parametriclimatici dato il 'GeoNameID' della località
         * @param geoNameID, tipo 'String' è l'ID della località
         * @throws java.rmi.RemoteException
         */  
    @Override
    public synchronized List<Map<String, String>> visualizzaParametriClimaticiDB(String geoNameID) throws RemoteException {
        List<Map<String, String>> parametri = new ArrayList<>();

        try {
            dbConnection();

            String query = "SELECT vento, umidita, pressione, temperatura, precipitazione, altitudineghiacciai, massaghiacciai, note FROM ParametriClimatici WHERE GeoNameID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(geoNameID));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, String> parametriMap = new HashMap<>();
                parametriMap.put("vento", rs.getString("vento"));
                parametriMap.put("umidita", rs.getString("umidita"));
                parametriMap.put("pressione", rs.getString("pressione"));
                parametriMap.put("temperatura", rs.getString("temperatura"));
                parametriMap.put("precipitazione", rs.getString("precipitazione"));
                parametriMap.put("altitudineghiacciai", rs.getString("altitudineghiacciai"));
                parametriMap.put("massaghiacciai", rs.getString("massaghiacciai"));
                parametriMap.put("note", rs.getString("note"));
                parametri.add(parametriMap);
            }
        } catch (SQLException e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        } finally {
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

        return parametri;
    }

    // MAIN
    public synchronized static void main(String[] args) throws RemoteException, SQLException {
        ServerCM server = new ServerCM(); 
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ClimateMonitoring", server);
        System.out.println("Server avviato e registrato nel registry RMI.");
        ServerCM obj = new ServerCM();
        obj.initializeGUI();
    }
    /**
         * Metodo  per recuperare l'Utente e quindi eseguire il login dato username e password
         * @param username, tipo 'String' è lo username dell'utente
         * @param password, tipo 'String' è la password dell'utente
         * @throws java.rmi.RemoteException
         */  
    @Override
    public List<String> getUtente(String username, String password) throws RemoteException {
        String sql = "SELECT nome, cognome, codfisc FROM operatori WHERE userid = ? AND password = ?";
        List<String> userInfo = new ArrayList<>();
        try {
            dbConnection();
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
            throw new RemoteException("Database error: " + e.getMessage(), e);
        } finally {
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userInfo;
    }
    
     /**
         * Metodo  per recuperare i centri di monitoraggio e popolare quindi la DropDown
         * @throws java.rmi.RemoteException
         */ 
    @Override
    public synchronized List<String> getCentriMonitoraggio() throws RemoteException {
        List<String> res = new ArrayList<>();
        String sql = "SELECT nome FROM centromonitoraggio";
        try {
            dbConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("nome"));
                System.out.println("Server: "+rs.getString("nome"));
            }
        } catch (SQLException e) {
            throw new RemoteException("Database error: " + e.getMessage(), e);
        } finally {
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }
     /**
         * Metodo  per recuperare i centri di monitoraggio dato il codice fiscale dell'utente
         * @param codFisc, tipo 'String' è il codice fiscale dell'utente
         * @throws java.rmi.RemoteException
         * @throws java.rmi.SQLException
         */  
    @Override
    public List<String> getCentriMonitoraggio(String codFisc) throws RemoteException {
        List<String> centri = new ArrayList<>();
        try {
            dbConnection();

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
                            centri.add(nomeCentro);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("Errore durante il recupero dei centri di monitoraggio", e);
        } finally {
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return centri;
    }
    /**
         * Metodo  per recuperare l'area di interesse dato il nome del centro
         * @param nomeCentro, tipo 'String' è il nome del centro
         * @throws java.rmi.RemoteException
         * @throws java.rmi.SQLException
         */  
    @Override
    public List<String> getAreeInteresse(String nomeCentro) throws RemoteException {
        List<String> aree = new ArrayList<>();
        try {
            dbConnection();

            String query = "SELECT areaInteresse FROM CentroMonitoraggio WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nomeCentro);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String[] areeInteresse = rs.getString("areaInteresse").split(", ");
                Collections.addAll(aree, areeInteresse);
            }
        } catch (SQLException e) {
            throw new RemoteException("Errore durante il recupero delle aree di interesse", e);
        } finally {
            try {
                dbDisconnection();
            } catch (SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    public void initializeGUI() {
        frame = new JFrame("Server Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());

        JButton disconnectButton = new JButton("Disconnessione");
        disconnectButton.addActionListener((ActionEvent e) -> {
            try {
                dbDisconnection();
                frame.dispose();
                System.exit(0); 
            } catch (RemoteException | SQLException ex) {
                Logger.getLogger(ServerCM.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        frame.add(disconnectButton);
        frame.setVisible(true);
    }
}
