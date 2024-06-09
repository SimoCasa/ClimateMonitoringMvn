/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climatemonitoring;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simoc
 */
public class ServerCM extends UnicastRemoteObject implements ClimateInterface{
    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;
    private Connection conn=null;
    private PreparedStatement pstmt=null;
    
    public ServerCM() throws RemoteException, SQLException{
        DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
        DB_USER = "postgres";
        DB_PASS = "password";
    }
    
    public void dbConnection() throws SQLException{
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    public void dbDisconnection() throws SQLException{
        conn.close();
    }
    
    @Override
    public synchronized List<Map<String, String>> cercaAreaGeograficaDB(String nome) throws RemoteException {
        List<Map<String, String>> results = new LinkedList<>();
        String sql = "SELECT GeoNameID, Name, CountryName, CountryCode FROM CoordinateMonitoraggio WHERE LOWER(Name) LIKE LOWER(?)";
        ResultSet rs = null;
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
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) dbDisconnection();
            } catch (SQLException e) {
                throw new RemoteException("Database error: " + e.getMessage(), e);
            }
        }

        return results;
    }

    @Override
    public synchronized  List<Map<String, String>> cercaAreaGeograficaDB(double lat, double lon, int offset) throws RemoteException {
        List<Map<String, String>> results = new LinkedList<>();
        ResultSet rs = null;
        
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
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) dbDisconnection();
            } catch (SQLException e) {
                throw new RemoteException("Database error: " + e.getMessage(), e);
            }
        }

        return results;
    }

    @Override
    public synchronized void registrazioneDB() throws IOException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized void registraCentroAreeDB() throws IOException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized void inserisciParametriClimatici() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized void inserisciAreaInteresseDB() throws IOException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized void visualizzaParametriClimaticiDB() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public synchronized static void main(String[] args) throws RemoteException, SQLException {
        ServerCM server = new ServerCM(); 
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ClimateMonitoring", server);
        System.out.println("Server avviato e registrato nel registry RMI.");
    }
    
}
