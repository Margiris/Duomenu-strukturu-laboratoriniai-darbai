package Lab3Burakauskas;

import laborai.gui.fx.KsFX;
import laborai.gui.fx.Lab3WindowFX_1;
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Margiris
 */
@SuppressWarnings({"unused", "WeakerAccess", "unchecked", "EqualsWhichDoesntCheckParameterClass"})
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
        setManufacturer(Manufacturer);
        setCoreCount(CoreCount);
        setCPUClockSpeed(CPUClockSpeed);
        setRAMSize(RAMSize);
        setStorageSize(StorageSize);
        setBatterySize(BatterySize);
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
        return Manufacturer.equals(s.Manufacturer) &&
                CoreCount == s.CoreCount &&
                CPUClockSpeed == s.CPUClockSpeed &&
                RAMSize == s.RAMSize &&
                StorageSize == s.StorageSize &&
                BatterySize == s.BatterySize;
    }

    @Override
    public Smartphone create(String dataString) {
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

    public static boolean validateInput(String dataString) {
        Smartphone s = new Smartphone();
        try {
            parse(dataString, s);
        } catch (NoSuchElementException e) {
            return false;
        }

        return true;
    }

    private static void parse(String dataString, Smartphone s) {
        Scanner scanner = new Scanner(dataString);
        s.setManufacturer(scanner.next());
        s.setCoreCount(scanner.nextInt());
        s.setCPUClockSpeed(scanner.nextDouble());
        s.setRAMSize(scanner.nextInt());
        s.setStorageSize(scanner.nextInt());
        s.setBatterySize(scanner.nextInt());
    }

    @Override
    public void parse(String dataString) {
        try {
            parse(dataString, Smartphone.this);
        } catch (InputMismatchException e) {
            Ks.ern("Bad data format for Smartphone: " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Insufficient data about Smartphone: " + dataString);
        }
    }

    @Override
    public int compareTo(Smartphone s) {
        if (this.equals(s))
                return -2;

        if (BatterySize > s.getBatterySize()) return 1;
        if (BatterySize < s.getBatterySize()) return -1;
        else return Manufacturer.compareTo(s.getManufacturer());
    }

    public final static Comparator<Smartphone> byCPUClockSpeedRAMSize = (Comparator) (o1, o2) -> {
        Smartphone s1 = (Smartphone) o1;
        Smartphone s2 = (Smartphone) o2;

        // by CPUClockSpeed, if it's the same then by RAMSize
        if (s1.getCPUClockSpeed() < s2.getCPUClockSpeed()) return 1;
        if (s1.getCPUClockSpeed() > s2.getCPUClockSpeed()) return -1;
        return Double.compare(s2.getRAMSize(), s1.getRAMSize());
    };
}
