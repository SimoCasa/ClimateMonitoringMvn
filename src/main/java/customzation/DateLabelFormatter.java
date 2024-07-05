/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Richiamo origine progetto.
 */
package customzation;
/**
 * Richiamo Librerie.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;
/**
 * @author 753546 Badrous Giorgio William
 * @author 753540 Casati Simone
 * @author 754772 Biavaschi Raffaele
 * @author 755531 Bonacina Davide
 */
 public class DateLabelFormatter extends AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        /**
        * Converte una stringa di testo in un oggetto di tipo {@code Object}.
        *
        * <p>Questo metodo utilizza un formatter di data per analizzare la stringa di testo 
        * fornita e convertirla in un oggetto {@code Calendar}. Il formato della stringa 
        * deve corrispondere a quello previsto dal formatter.</p>
        *
        * @param text la {@code String} da convertire in un oggetto {@code Object}.
        * @return l'oggetto convertito dalla stringa di testo. In questo caso, si tratta di un oggetto {@code Calendar}.
        * @throws ParseException se la stringa di testo non può essere analizzata correttamente
        *         secondo il formato previsto dal formatter.
        */
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }
        /**
        * Converte un oggetto in una stringa di testo.
        *
        * <p>Questo metodo converte un oggetto {@code Calendar} in una stringa di testo 
        * utilizzando un formatter di data. Se l'oggetto fornito è {@code null}, viene restituita
        * una stringa vuota.</p>
        *
        * @param value l'oggetto da convertire in una stringa di testo. Si presume che 
        *              l'oggetto sia di tipo {@code Calendar}.
        * @return la rappresentazione sotto forma di stringa dell'oggetto. Se l'oggetto è {@code null}, 
        *         viene restituita una stringa vuota.
        * @throws ParseException se l'oggetto non può essere formattato correttamente.
        */
        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }
