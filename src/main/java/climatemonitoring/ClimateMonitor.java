/**
 * <strong>Classe Main</strong> 
 */
/**
 * Richiamo origine progetto.
 */
package climatemonitoring;
/**
 * Richiamo Librerie di Java
 */
import java.io.File;
/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 * @version Software 1.0.0
 * @version JDK 17 
 */
public class ClimateMonitor {
    /**
     *Dichiarazione variabile 'sep' (separatore) di tipo stringa, uso il metodo 'File.separator' per recuperarlo dal sistema operativo corrente
     */
    static String sep = File.separator;
     /**
      * Metodo 'main' che crea la prima finestra del programma.
      * @param args di tipo String, unica accettato dal metodo 'main' per debug
      */
    public static void main(String[] args){
        Home a = new Home();   
    }
}
