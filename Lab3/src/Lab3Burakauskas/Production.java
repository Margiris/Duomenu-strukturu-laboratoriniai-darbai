package Lab3Burakauskas;

import laborai.gui.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

public class Production {

    private static Smartphone[] smartphones;
    private static int startIndex = 0, endIndex = 0;
    private static boolean isBeginning = true;
    private static Random random = new Random();

    public static Smartphone[] generateAndShuffleSmartphones(int sizeOfSet, int elementsCountInSet, double shuffleCoefficient) {
        return shuffleSmartphones(generateSmartphones(sizeOfSet), elementsCountInSet, shuffleCoefficient);
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

    public static Smartphone[] shuffleSmartphones(Smartphone[] smartphones, int count, double shuffleCoefficient) throws MyException {
        if (smartphones == null) {
            throw new IllegalArgumentException("There are no smartphones");
        }
        if (count <= 0) {
            throw new MyException(String.valueOf(count), 1);
        }
        if (smartphones.length < count) {
            throw new MyException(smartphones.length + " >= " + count, 2);
        }
        if ((shuffleCoefficient < 0) || (shuffleCoefficient > 1)) {
            throw new MyException(String.valueOf(shuffleCoefficient), 3);
        }

        int leftoverCount = smartphones.length - count;
        int indexOfBeginning = (int) (leftoverCount * shuffleCoefficient / 2);

        Smartphone[] initialSmartphones = Arrays.copyOfRange(smartphones, indexOfBeginning, indexOfBeginning + count);
        Smartphone[] leftoverSmartphones = Stream
                .concat(Arrays.stream(Arrays.copyOfRange(smartphones, 0, indexOfBeginning)),
                        Arrays.stream(Arrays.copyOfRange(smartphones, indexOfBeginning + count, smartphones.length)))
                .toArray(Smartphone[]::new);

        Collections.shuffle(Arrays.asList(initialSmartphones)
                .subList(0, (int) (initialSmartphones.length * shuffleCoefficient)));
        Collections.shuffle(Arrays.asList(leftoverSmartphones)
                .subList(0, (int) (leftoverSmartphones.length * shuffleCoefficient)));

        Production.startIndex = 0;
        endIndex = leftoverSmartphones.length - 1;
        Production.smartphones = leftoverSmartphones;
        return initialSmartphones;
    }

    public static Smartphone getSmartphoneFromBase() throws MyException {
        if ((endIndex - startIndex) < 0) {
            throw new MyException(String.valueOf(endIndex - startIndex), 4);
        }
        // One time smartphone is returned from the beginning of the array, second time - from the end.
        isBeginning = !isBeginning;
        return smartphones[isBeginning ? startIndex++ : endIndex--];
    }

    public static int[] generateRandomIntegers(int count) {
        int[] integers = new int[count];
        for (int i = 0; i < count; i++) {
            integers[i] = random.nextInt();
        }
        return integers;
    }

    public static void shuffleRandomIntegers(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }
}
