package Lab3Burakauskas;

import laborai.studijosktu.BstSetKTU;
import laborai.studijosktu.Ks;

import java.util.Locale;

public class SmartphoneTest {
    public static void test() {
        Smartphone[] SMs = Production.generateSmartphones(12);

        Ks.oun(SMs.length);

        BstSetKTU<Smartphone> smartphoneSet = new BstSetKTU<>();

        for (Smartphone s : SMs) {
            smartphoneSet.add(s);
            Ks.oun(s.toString());
        }
        Ks.oun(smartphoneSet.size());
        Ks.oun(smartphoneSet.toVisualizedString(""));


        Ks.oun(smartphoneSet.height());
    }

    public static void main(String... args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        test();
    }
}
