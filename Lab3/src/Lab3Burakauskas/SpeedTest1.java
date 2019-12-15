package Lab3Burakauskas;

import laborai.demo.Timekeeper;
import laborai.gui.MyException;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

@SuppressWarnings({"Duplicates", "unchecked", "WeakerAccess", "MismatchedQueryAndUpdateOfCollection"})
public class SpeedTest1 {

    public static final String FINISH_COMMAND = "finish";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages_1");

    private static final String[] TYRIMU_VARDAI = {"addTreeSet", "addHashSet", "remTreeSet", "remHashSet"};
    private static final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 80000};

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] errors;

    private final TreeSet<Integer> aSeries = new TreeSet<>();
    private final HashSet<Integer> a2Series = new HashSet<>();
    private final TreeSet<Integer> a3Series = new TreeSet<>();
    private final HashSet<Integer> a4Series = new HashSet<>();

    public SpeedTest1() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
        errors = new String[]{
                MESSAGES.getString("error1"),
                MESSAGES.getString("error2"),
                MESSAGES.getString("error3"),
                MESSAGES.getString("error4")
        };
    }

    public void beginTest() {
        try {
            systemicTest();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void systemicTest() throws InterruptedException {
        try {
            for (int k : TIRIAMI_KIEKIAI) {
                int[] intMas = Production.generateRandomIntegers(k);

                aSeries.clear();
                a2Series.clear();
                a3Series.clear();
                a4Series.clear();

                for (int a : intMas) {
                    a3Series.add(a);
                    a4Series.add(a);
                }

                Production.shuffleRandomIntegers(intMas);
                tk.startAfterPause();
                tk.start();

                for (int a : intMas) {
                    aSeries.add(a);
                }

                tk.finish(TYRIMU_VARDAI[0]);

                for (int a : intMas) {
                    a2Series.add(a);
                }

                tk.finish(TYRIMU_VARDAI[1]);

                for (int a : intMas) {
                    a3Series.remove(a);
                }

                tk.finish(TYRIMU_VARDAI[2]);

                for (int a : intMas) {
                    a4Series.remove(a);
                }

                tk.finish(TYRIMU_VARDAI[3]);
                tk.seriesFinish();
            }
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                tk.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                tk.logResult(MESSAGES.getString("msg3"));
            } else {
                tk.logResult(e.getMessage());
            }
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
