/**
 * @author Margiris
 */
package Lab2Burakauskas;

import studijosKTU.Ks;
import studijosKTU.ListKTU;

import java.util.Locale;

public class SmartphonePool {

    public SmartphonePool() {

    }

    private void demo() {
        ListKTU<Smartphone> smartphones = Smartphone.generateSmartphone(10);

        for (Smartphone smartphone : smartphones) {
            Ks.oun(smartphone.toString());
        }
        Ks.oun("");

        ListKTU<Smartphone> found = findByCoreCount(smartphones, 2);
        //ListKTU<Smartphone> found = findByCPUClockSpeed(smartphones, 2.5);

        for (Smartphone smartphone : found) {
            Ks.oun(smartphone.toString());
        }
        Ks.oun("");

        smartphones.sortBubble(Smartphone.byCPUClockSpeedRAMSize);
        Ks.oun("Sorted by number of cores and clock speed");

        for(Smartphone smartphone : smartphones) {
            Ks.oun(smartphone);
        }
        Ks.oun("");
    }

    public ListKTU<Smartphone> findByCoreCount(ListKTU<Smartphone> smartphones, int minCoreCount) {
        ListKTU<Smartphone> found = new ListKTU<>();
        for (Smartphone smartphone : smartphones) {
            if (smartphone.getCoreCount() > minCoreCount) {
                found.add(smartphone);
            }
        }
        return found;
    }

    public ListKTU<Smartphone> findByCPUClockSpeed(ListKTU<Smartphone> smartphones, double minClockSpeed) {
        ListKTU<Smartphone> found = new ListKTU<>();
        for (Smartphone smartphone : smartphones) {
            if (smartphone.getCPUClockSpeed() > minClockSpeed) {
                found.add(smartphone);
            }
        }
        return found;
    }

    public static void main(String... args) {
        Locale.setDefault(new Locale("LT"));
        new SmartphonePool().demo();
    }
}
