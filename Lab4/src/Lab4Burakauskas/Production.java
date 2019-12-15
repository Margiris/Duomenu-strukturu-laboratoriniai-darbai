package Lab4Burakauskas;

import laborai.gui.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public class Production {

    private static Smartphone[] smartphones;
    private static Random random = new Random();

    public static final String ID_CODE = "SP";      //  ***** nauja
    private static int serNr = 10000;               //  ***** nauja

    private String[] raktai;
    private int kiekis = 0, idKiekis = 0;

    public static Smartphone[] produceSmartphones(int kiekis) {
        Smartphone[] smartphones = generateSmartphones(kiekis);
        Collections.shuffle(Arrays.asList(smartphones));
        return smartphones;
    }

    public static String[] produceIDs(int kiekis) {
        String[] raktai = IntStream.range(0, kiekis)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(raktai));
        return raktai;
    }

    public Smartphone[] produceAndSellSmartphones(int aibesDydis,
                                                  int aibesImtis) throws MyException {
        if (aibesImtis > aibesDydis) {
            aibesImtis = aibesDydis;
        }
        smartphones = produceSmartphones(aibesDydis);
        raktai = produceIDs(aibesDydis);
        this.kiekis = aibesImtis;
        return Arrays.copyOf(smartphones, aibesImtis);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Smartphone sellSmartphone() {
        if (smartphones == null) {
            throw new MyException("smartphonesNotGenerated");
        }
        if (kiekis < smartphones.length) {
            return smartphones[kiekis++];
        } else {
            throw new MyException("allSetStoredToMap");
        }
    }

    public String getFromBaseSmartphoneID() {
        if (raktai == null) {
            throw new MyException("smartphonesIDsNotGenerated");
        }
        if (idKiekis >= raktai.length) {
            idKiekis = 0;
        }
        return raktai[idKiekis++];
    }

    public static Smartphone[] generateSmartphones(int count) {
        String[] Manufacturers = {
                "Samsung", "Apple", "Huawei", "Oppo", "Vivo",
                "OnePlus", "Xiaomi", "Lenovo", "LG", "Sony"
        };
        int[] CoreCounts = {
                1, 2, 4, 6, 8, 10
        };
        double[] CPUClockSpeeds = {
                0.8, 1.2, 1.3, 1.5, 1.8, 2.0, 2.1, 2.5
        };
        int[] RAMSizes = {
                1, 2, 3, 4, 6, 8, 12, 16
        };
        int[] StorageSizes = {
                8, 16, 32, 64, 128, 256
        };
        int[] BatterySizes = {
                1000, 1200, 1500, 2000, 2580,
                3000, 3210, 3540, 4000, 10000
        };

        smartphones = new Smartphone[count];

        while (count-- > 0) {
            Smartphone smartphone = new Smartphone();
            smartphone.setManufacturer(Manufacturers[random.nextInt(Manufacturers.length)]);
            smartphone.setCoreCount(CoreCounts[random.nextInt(CoreCounts.length)]);
            smartphone.setCPUClockSpeed(CPUClockSpeeds[random.nextInt(CPUClockSpeeds.length)]);
            smartphone.setRAMSize(RAMSizes[random.nextInt(RAMSizes.length)]);
            smartphone.setStorageSize(StorageSizes[random.nextInt(StorageSizes.length)]);
            smartphone.setBatterySize(BatterySizes[random.nextInt(BatterySizes.length)]);
            smartphones[count] = smartphone;
        }
        return smartphones;
    }

    public static int[] generateRandomIntegers(int count) {
        int[] integers = new int[count];
        for (int i = 0; i < count; i++) {
            integers[i] = random.nextInt();
        }
        return integers;
    }
}
