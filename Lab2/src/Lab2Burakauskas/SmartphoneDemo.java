/**
 * @author Margiris
 */
package Lab2Burakauskas;

import studijosKTU.Ks;
import studijosKTU.ListKTU;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SmartphoneDemo {
    public SmartphoneDemo() {

    }

    public void demo() {
        //test1();
        //test2();
        //test3();
        test4();
    }

    private void test1() {
        Smartphone in1 = new Smartphone("Samsung", 8, 2.3, 4, 64, 3000);
        Smartphone in2 = new Smartphone("Apple", 6, 2.39, 3, 256, 2716);
        Smartphone in3 = new Smartphone("Razer", 8, 2.35, 8, 64, 4000);
        Smartphone in4 = new Smartphone();
        Smartphone in5 = new Smartphone();
        Smartphone in6 = new Smartphone();

        in4.parse("LG 8 2,45 4 128 3300");
        in5.parse("Essential 8 2,45 4 128 3040");
        in6.parse("OnePlus 8 2,45 8 128 3300");

        Ks.oun(in1);
        Ks.oun(in2);
        Ks.oun(in3);
        Ks.oun(in4);
        Ks.oun(in5);
        Ks.oun(in6);
        Ks.oun("");

        int sum = in1.getRAMSize() + in2.getRAMSize() + in3.getRAMSize()
                + in4.getRAMSize() + in5.getRAMSize() + in6.getRAMSize();
        Ks.oun("All phones' RAM sum: " + sum);
        Ks.oun("");
    }

    private void test2() {
        ListKTU<Smartphone> smartphones = Smartphone.generateSmartphone(11);

        for (Smartphone smartphone : smartphones) {
            Ks.oun(smartphone);
        }
        Ks.oun("");

        Map<String, Integer> frequency = new HashMap<>();
        int max = Integer.MIN_VALUE;

        for (Smartphone smartphone : smartphones) {
            if (frequency.get(smartphone.getManufacturer()) == null) {
                frequency.put(smartphone.getManufacturer(), 1);
            } else {
                frequency.put(smartphone.getManufacturer(), frequency.get(smartphone.getManufacturer()) + 1);
                max = frequency.get(smartphone.getManufacturer()) > max ? frequency.get(smartphone.getManufacturer()) : max;
            }
        }

        ListKTU<String> mostPopularManufacturers = new ListKTU<>();
        for (String name : frequency.keySet()) {
            if (frequency.get(name) == max) {
                mostPopularManufacturers.add(name);
            }
        }

        String postFix = mostPopularManufacturers.size() == 1 ? "" : "s";
        Ks.oun("Most popular manufacturer" + postFix + ":");

        for (String name : mostPopularManufacturers) {
            Ks.oun(name);
        }
        Ks.oun("");

    }

    private void test3() {
        ListKTU<Smartphone> smartphones = Smartphone.generateSmartphone(10);

        for (Smartphone smartphone : smartphones) {
            Ks.oun(smartphone);
        }
        Ks.oun("");

        int count = 0, minRAM = 4;

        for (Smartphone Smartphone : smartphones) {
            if (Smartphone.getRAMSize() > minRAM) {
                count++;
            }
        }

        Ks.oun("Number of smartphones that have more than " + minRAM + "GB RAM: " + count);
        Ks.oun("");
    }

    public void test4() {
        Smartphone in1 = new Smartphone("Samsung", 8, 2.3, 4, 64, 3000);
        Smartphone in2 = new Smartphone("Apple", 6, 2.39, 3, 256, 2716);
        Smartphone in3 = new Smartphone("Razer", 8, 2.35, 8, 64, 4000);
        Smartphone in4 = new Smartphone();
        Smartphone in5 = new Smartphone();
        Smartphone in6 = new Smartphone();

        in4.parse("LG 8 2,45 4 128 3300");
        in5.parse("Essential 8 2,45 4 128 3040");
        in6.parse("OnePlus 8 2,45 8 128 3300");

        ListKTU<Smartphone> smartphones = new ListKTU<>();
        smartphones.add(in1);
        smartphones.add(in2);
        smartphones.add(in3);
        smartphones.add(in4);
        smartphones.add(new Smartphone("Apple", 6, 2.39, 3, 256, 2716));
        smartphones.add(in5);
        smartphones.add(new Smartphone("Apple", 6, 2.39, 3, 256, 2716));
        smartphones.add(in6);

        for(Smartphone smartphone : smartphones) {
            Ks.oun(smartphone);
        }
        Ks.oun("");

        smartphones.removeLastOccurrence(smartphones.get(1));

        for(Smartphone smartphone : smartphones) {
            Ks.oun(smartphone);
        }
        Ks.oun("");
    }

    public static void main(String... args) {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)

        Locale.setDefault(new Locale("LT"));
        new SmartphoneDemo().demo();
    }
}
