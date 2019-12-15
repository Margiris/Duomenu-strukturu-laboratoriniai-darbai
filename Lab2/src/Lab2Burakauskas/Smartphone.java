/**
 * @author Margiris
 */
package Lab2Burakauskas;

import studijosKTU.KTUable;
import studijosKTU.Ks;
import studijosKTU.ListKTU;

import java.text.DecimalFormat;
import java.util.*;

@SuppressWarnings("ALL")
public class Smartphone implements KTUable<Smartphone> {
    final private static int MIN_CORE_COUNT = 2;
    final private static int MIN_RAM_SIZE = 1;
    final private static int MIN_BATTERY_SIZE = 1000;

    private static Random random = new Random();

    private String Manufacturer;
    private int CoreCount;
    private double CPUClockSpeed;
    private int RAMSize;
    private int StorageSize;
    private int BatterySize;

    public Smartphone() {
    }

    public Smartphone(String Manufacturer, int CoreCount, double CPUClockSpeed,
                      int RAMSize, int StorageSize, int BatterySize) {
        this.Manufacturer = Manufacturer;
        this.CoreCount = CoreCount;
        this.CPUClockSpeed = CPUClockSpeed;
        this.RAMSize = RAMSize;
        this.StorageSize = StorageSize;
        this.BatterySize = BatterySize;
    }

    //region get set methods
    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String Manufacturer) {
        this.Manufacturer = Manufacturer;
    }

    public int getCoreCount() {
        return CoreCount;
    }

    public void setCoreCount(int CoreCount) {
        this.CoreCount = CoreCount;
    }

    public double getCPUClockSpeed() {
        return CPUClockSpeed;
    }

    public void setCPUClockSpeed(double CPUClockSpeed) {
        this.CPUClockSpeed = CPUClockSpeed;
    }

    public int getRAMSize() {
        return RAMSize;
    }

    public void setRAMSize(int RAMSize) {
        this.RAMSize = RAMSize;
    }

    public int getStorageSize() {
        return StorageSize;
    }

    public void setStorageSize(int StorageSize) {
        this.StorageSize = StorageSize;
    }

    public int getBatterySize() {
        return BatterySize;
    }

    public void setBatterySize(int BatterySize) {
        this.BatterySize = BatterySize;
    }
    //endregion

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return String.format("%-10s %3d %5s %3d %4d %6d %s",
                Manufacturer, CoreCount, df.format(CPUClockSpeed), RAMSize, StorageSize, BatterySize, validate());
    }

    public boolean equals(Object o) {
        Smartphone s = (Smartphone) o;
        return Manufacturer == s.Manufacturer &&
                CoreCount == s.CoreCount &&
                CPUClockSpeed == s.CPUClockSpeed &&
                RAMSize == s.RAMSize &&
                StorageSize == s.StorageSize &&
                BatterySize == s.BatterySize;
    }

    @Override
    public KTUable create(String dataString) {
        Smartphone smartphone = new Smartphone();
        smartphone.parse(dataString);
        return smartphone;
    }

    @Override
    public String validate() {
        String error = "";
        if (CoreCount < MIN_CORE_COUNT)
            error += "Not enough cores, need at least " + MIN_CORE_COUNT + ". ";
        if (RAMSize < MIN_RAM_SIZE)
            error += "Not enough RAM, need at least " + MIN_RAM_SIZE + ". ";
        if (BatterySize < MIN_BATTERY_SIZE)
            error += "Battery too small, need at least " + MIN_BATTERY_SIZE + ". ";
        return error;
    }

    @Override
    public void parse(String dataString) {
        try {
            Scanner scanner = new Scanner(dataString);
            Manufacturer = scanner.next();
            CoreCount = scanner.nextInt();
            CPUClockSpeed = scanner.nextDouble();
            RAMSize = scanner.nextInt();
            StorageSize = scanner.nextInt();
            BatterySize = scanner.nextInt();
        } catch (InputMismatchException e) {
            Ks.ern("Bad data format for Smartphone: " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Insufficient data about Smartphone: " + dataString);
        }
    }

    @Override
    public int compareTo(Smartphone e) {
        return Integer.compare(RAMSize, e.getRAMSize());
    }

    public final static Comparator byCPUClockSpeedRAMSize = (Comparator) (o1, o2) -> {
        Smartphone s1 = (Smartphone) o1;
        Smartphone s2 = (Smartphone) o2;

        // by CPUClockSpeed, if it's the same then by RAMSize
        if (s1.getCPUClockSpeed() < s2.getCPUClockSpeed()) return 1;
        if (s1.getCPUClockSpeed() > s2.getCPUClockSpeed()) return -1;
        return Double.compare(s2.getRAMSize(), s1.getRAMSize());
    };

    public static ListKTU<Smartphone> generateSmartphone(int count) {
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

        ListKTU<Smartphone> smartphones = new ListKTU<>();

        while (count-- > 0) {
            Smartphone smartphone = new Smartphone();
            smartphone.setManufacturer(Manufacturers[random.nextInt(Manufacturers.length)]);
            smartphone.setCoreCount(CoreCounts[random.nextInt(CoreCounts.length)]);
            smartphone.setCPUClockSpeed(CPUClockSpeeds[random.nextInt(CPUClockSpeeds.length)]);
            smartphone.setRAMSize(RAMSizes[random.nextInt(RAMSizes.length)]);
            smartphone.setStorageSize(StorageSizes[random.nextInt(StorageSizes.length)]);
            smartphone.setBatterySize(BatterySizes[random.nextInt(BatterySizes.length)]);
            smartphones.add(smartphone);
        }
        return smartphones;
    }
}
