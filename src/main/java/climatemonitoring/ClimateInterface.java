/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package climatemonitoring;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author simoc
 */
public interface ClimateInterface extends Remote{
    List<Map<String, String>> cercaAreaGeograficaDB(String nome) throws RemoteException;
    List<Map<String, String>> cercaAreaGeograficaDB(double lat, double lon, int offset)throws RemoteException;
    void registrazioneDB() throws IOException, RemoteException;
    void registraCentroAreeDB() throws IOException, RemoteException;
    void inserisciParametriClimatici() throws RemoteException; 
    void inserisciAreaInteresseDB() throws IOException, RemoteException;
    void visualizzaParametriClimaticiDB() throws IOException, RemoteException;
    
}
