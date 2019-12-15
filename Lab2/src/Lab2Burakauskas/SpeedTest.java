/**
 * @author Margiris
 */
package Lab2Burakauskas;

import studijosKTU.Ks;
import studijosKTU.Timekeeper;

import java.util.*;

@SuppressWarnings("ALL")
public class SpeedTest {
    Random random = new Random();
    final int MAX_NUMBER = 1_000_000;
    int[] numbersCount = {8_000, 16_000, 32_000, 64_000, 128_000};

    public SpeedTest() {

    }

    public List<Integer> generateRandomIntegers(List<Integer> list, int count) {
        for (int i = 0; i < count; i++) {
            list.add(1 + random.nextInt(MAX_NUMBER));
        }
        return list;
    }

    private void demo() {
        long memTotal = Runtime.getRuntime().totalMemory();

        Ks.oun("memTotal= " + memTotal);
        Ks.oun("1 - Generating data");
        Ks.oun("2 - Garbage collection");
        Ks.oun("3 - Executing Math.sqrt(x)");
        Ks.oun("4 - Executing Math.cbrt(x)");
        Ks.oun("5 - indexOf(Object o) using ArrayList<Integer>");
        Ks.oun("6 - lastIndexOf(Object o) using ArrayList<Integer>");

        Ks.ouf("%6d %7d %7d %7d %7d %7d %7d \n", 0, 1, 2, 3, 4, 5, 6);

        for (int count : numbersCount) {
            test(count);
        }

        systemicTest();
    }

    private void test(int count) {
        long t0 = System.nanoTime();

        List<Integer> list = new ArrayList<>();
        list = generateRandomIntegers(list, count);

        int object = list.get(count / 2);

        //List<Integer> o = new ArrayList<>();
        //o = generateRandomIntegers(o, 1000);

        long t1 = System.nanoTime();

        // Garbage collection
        System.gc();
        System.gc();
        System.gc();

        long t2 = System.nanoTime();

        // Math.sqrt(x)
        for (int x : list) {
            Math.sqrt(x);
        }

        long t3 = System.nanoTime();

        // Math.cbrt(x);
        for (int x : list) {
            Math.cbrt(x);
        }

        long t4 = System.nanoTime();

        // indexOf(Object o) with ArrayList<Integer>
        list.indexOf(object);
        long t5 = System.nanoTime();

        // lastIndexOf(Object o) with ArrayList<Integer>
        list.lastIndexOf(object);
        long t6 = System.nanoTime();

        Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n", count,
                (t1 - t0) / 1e9, (t2 - t1) / 1e9, (t3 - t2) / 1e9,
                (t4 - t3) / 1e9, (t5 - t4) / 1e9, (t6 - t5) / 1e9);
    }

    private void systemicTest() {
        Timekeeper tk = new Timekeeper(numbersCount);

        for (int count : numbersCount) {
            List<Integer> list = new ArrayList<>();
            list = generateRandomIntegers(list, count);

            int object = list.get(count / 2);

            //List<Integer> o = new ArrayList<>();
            //o = generateRandomIntegers(o, 1000);

            tk.start();

            for (int x : list) {
                Math.sqrt(x);
            }
            tk.finish("sqrt");

            for (int x : list) {
                Math.cbrt(x);
            }
            tk.finish("cbrt");

            list.indexOf(object);
            tk.finish("indexOf");

            list.lastIndexOf(object);
            tk.finish("lastIndexOf");

            tk.seriesFinish();
        }
    }

    public static void main(String... args) {
        Locale.setDefault(new Locale("LT"));
        new SpeedTest().demo();
    }
}
