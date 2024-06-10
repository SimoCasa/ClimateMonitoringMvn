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
// Definizione interfaccia per RMI.
public interface ClimateInterface extends Remote{
    void dbConnection() throws SQLException,RemoteException;        //Connessione al Database
    void dbDisconnection() throws RemoteException, SQLException;    //Disconnessione al Database
    List<Map<String, String>> cercaAreaGeograficaDB(String nome) throws RemoteException;    //Ricerca per nome
    List<Map<String, String>> cercaAreaGeograficaDB(double lat, double lon, int offset)throws RemoteException;  //Ricerca per Coordinate
    List<String> getUtente(String username, String password) throws RemoteException;    //Accesso utente
    void registrazione(String nome, String cognome, String codFisc, String email, String username, String password, String centroNome) throws RemoteException;  //Registrazione utente
    List<String> getCentriMonitoraggio() throws RemoteException;   //Dropdown per la Registrazione dei Centri di Monitoraggio
    void inserisciCentroMonitoraggio(String nome, String indirizzo, String elencoAree) throws RemoteException;  //Inserimento Centri Monitoraggio
    void inserisciParametriClimatici(String nomeCentro, String nomeArea, int vento, int umidita, int pressione, int temperatura, int precipitazioni, int alt, int mass, String note) throws RemoteException; 
    void inserisciAreaDB(String citta, String code, String country, String lat, String lon) throws RemoteException;     //Inserimento area di interesse
    List<Map<String, String>> visualizzaParametriClimaticiDB(String geoNameID) throws RemoteException;  //Visualizza i parametri inseriti dagli operatori
    List<String> getCentriMonitoraggio(String codFisc) throws RemoteException;  //Dropdown dei centri nell'inserimento parametri
    List<String> getAreeInteresse(String nomeCentro) throws RemoteException;    //Dropdown delle aree di interesse nell'inserimento parametri
}
