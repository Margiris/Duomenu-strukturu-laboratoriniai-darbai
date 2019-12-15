package Lab4Burakauskas;

import laborai.gui.swing.Lab4Window;

import java.util.Hashtable;
import java.util.Locale;

/*
 * Darbo atlikimo tvarka - čia yra pradinė klasė.
 */
public class VykdymoModulis {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
//        SmartphoneTests.atvaizdzioTestas();
        Lab4Window.createAndShowGUI();
    }
}
