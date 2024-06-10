/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Richiamo Librerie.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
public interface ClimateInterface extends Remote {
    
    /**
     * Effettua la connessione al database.
     * 
     * @throws SQLException Se si verifica un errore di accesso al database.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    void dbConnection() throws SQLException, RemoteException;

    /**
     * Effettua la disconnessione dal database.
     * 
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     * @throws SQLException Se si verifica un errore di accesso al database.
     */
    void dbDisconnection() throws RemoteException, SQLException;

    /**
     * Cerca un'area geografica nel database in base al nome.
     * 
     * @param nome Il nome dell'area geografica da cercare.
     * @return Una lista di mappe con i dettagli dell'area geografica trovata.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<Map<String, String>> cercaAreaGeograficaDB(String nome) throws RemoteException;

    /**
     * Cerca un'area geografica nel database in base alle coordinate.
     * 
     * @param lat La latitudine dell'area.
     * @param lon La longitudine dell'area.
     * @param offset L'offset per la ricerca.
     * @return Una lista di mappe con i dettagli dell'area geografica trovata.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<Map<String, String>> cercaAreaGeograficaDB(double lat, double lon, int offset) throws RemoteException;

    /**
     * Ottiene le informazioni di un utente in base a username e password.
     * 
     * @param username Il nome utente.
     * @param password La password dell'utente.
     * @return Una lista di stringhe con i dettagli dell'utente.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<String> getUtente(String username, String password) throws RemoteException;

    /**
     * Registra un nuovo utente.
     * 
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param codFisc Il codice fiscale dell'utente.
     * @param email L'email dell'utente.
     * @param username Il nome utente scelto dall'utente.
     * @param password La password scelta dall'utente.
     * @param centroNome Il nome del centro di monitoraggio associato all'utente.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    void registrazione(String nome, String cognome, String codFisc, String email, String username, String password, String centroNome) throws RemoteException;

    /**
     * Ottiene l'elenco dei centri di monitoraggio.
     * 
     * @return Una lista di nomi dei centri di monitoraggio.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<String> getCentriMonitoraggio() throws RemoteException;

    /**
     * Inserisce un nuovo centro di monitoraggio.
     * 
     * @param nome Il nome del centro.
     * @param indirizzo L'indirizzo del centro.
     * @param elencoAree L'elenco delle aree monitorate dal centro.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    void inserisciCentroMonitoraggio(String nome, String indirizzo, String elencoAree) throws RemoteException;

    /**
     * Inserisce nuovi parametri climatici nel database.
     * 
     * @param nomeCentro Il nome del centro di monitoraggio.
     * @param nomeArea Il nome dell'area di interesse.
     * @param vento Il valore del vento.
     * @param umidita Il valore dell'umidità.
     * @param pressione Il valore della pressione.
     * @param temperatura Il valore della temperatura.
     * @param precipitazioni Il valore delle precipitazioni.
     * @param alt L'altitudine.
     * @param mass La massa.
     * @param note Eventuali note aggiuntive.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    void inserisciParametriClimatici(String nomeCentro, String nomeArea, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int alt, int mass, String note) throws RemoteException;

    /**
     * Inserisce una nuova area di interesse nel database.
     * 
     * @param citta La città dell'area.
     * @param code Il codice dell'area.
     * @param country Il paese dell'area.
     * @param lat La latitudine dell'area.
     * @param lon La longitudine dell'area.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    void inserisciAreaDB(String citta, String code, String country, String lat, String lon) throws RemoteException;

    /**
     * Visualizza i parametri climatici di una specifica area geografica.
     * 
     * @param geoNameID L'ID geografico dell'area.
     * @return Una lista di mappe con i dettagli dei parametri climatici.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<Map<String, String>> visualizzaParametriClimaticiDB(String geoNameID) throws RemoteException;

    /**
     * Ottiene l'elenco dei centri di monitoraggio associati a un utente.
     * 
     * @param codFisc Il codice fiscale dell'utente.
     * @return Una lista di nomi dei centri di monitoraggio.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<String> getCentriMonitoraggio(String codFisc) throws RemoteException;

    /**
     * Ottiene l'elenco delle aree di interesse associate a un centro di monitoraggio.
     * 
     * @param nomeCentro Il nome del centro di monitoraggio.
     * @return Una lista di nomi delle aree di interesse.
     * @throws RemoteException Se si verifica un errore di comunicazione RMI.
     */
    List<String> getAreeInteresse(String nomeCentro) throws RemoteException;
}
