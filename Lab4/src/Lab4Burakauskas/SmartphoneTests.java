package Lab4Burakauskas;

import laborai.studijosktu.MapKTUx;
import laborai.studijosktu.Ks;

import java.util.Locale;

import laborai.studijosktu.HashType;

@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public class SmartphoneTests {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        atvaizdzioTestas();
        greitaveikosTestas();
    }

    public static void atvaizdzioTestas() {
        Smartphone a1 = new Smartphone("Xiaomi", 6, 4.5, 8, 128, 4000);
        Smartphone a2 = new Smartphone("Samsung", 2, 1.1, 3, 25, 5000);
        Smartphone a3 = new Smartphone("Nokia 6 6.6 6 66 6666");
        Smartphone a4 = new Smartphone("HTC 8 7.6 5 43 2100");
        Smartphone a5 = new Smartphone("HTC 2 1.5 4 69 780");
        Smartphone a6 = new Smartphone("asus 5 5.4 2 64 5000");
        Smartphone a7 = Production.produceSmartphones(1)[0];
        Smartphone a8 = Production.produceSmartphones(1)[0];

        // Raktų masyvas
        String[] smartphoneIDs = {"TK156", "TK102", "TK178", "TK171", "TK105", "TK106", "TK107", "TK108"};
        int id = 0;
        MapKTUx<String, Smartphone> atvaizdis
                = new MapKTUx("", new Smartphone(), HashType.DIVISION);
        // Reikšmių masyvas
        Smartphone[] smartphones = {a1, a2, a3, a4, a5, a6, a7, a8};
        for (Smartphone a : smartphones) {
            atvaizdis.put(smartphoneIDs[id++], a);
        }
        atvaizdis.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje?");
        Ks.oun(atvaizdis.contains(smartphoneIDs[6]));
        Ks.oun(atvaizdis.contains(smartphoneIDs[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:");
        Ks.oun(atvaizdis.remove(smartphoneIDs[1]));
        Ks.oun(atvaizdis.remove(smartphoneIDs[7]));
        atvaizdis.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(atvaizdis.get(smartphoneIDs[2]));
        Ks.oun(atvaizdis.get(smartphoneIDs[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(atvaizdis);
        atvaizdis.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(atvaizdis.get(smartphoneIDs[2]));
        Ks.oun(atvaizdis.get(smartphoneIDs[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(atvaizdis);
    }

    //Konsoliniame režime
    private static void greitaveikosTestas() {
        System.out.println("Greitaveikos tyrimas:\n");
        SpeedTest gt = new SpeedTest();
        //Šioje gijoje atliekamas greitaveikos tyrimas
        new Thread(gt::pradetiTyrima,
                "Greitaveikos_tyrimo_gija").start();
        try {
            String result;
            while (!(result = gt.getResultsLogger().take())
                    .equals(SpeedTest.FINISH_COMMAND)) {
                System.out.println(result);
                gt.getSemaphore().release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gt.getSemaphore().release();
    }
}