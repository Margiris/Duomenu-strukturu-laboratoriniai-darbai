package Lab3Burakauskas;

import laborai.demo.Timekeeper;
import laborai.gui.MyException;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.BstSetKTUx;
import laborai.studijosktu.BstSetKTUx2;
import laborai.studijosktu.SortedSetADTx;

import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

@SuppressWarnings({"unchecked", "WeakerAccess", "SpellCheckingInspection", "Duplicates"})
public class SpeedTest {

    public static final String FINISH_COMMAND = "finish";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages_1");

    private static final String[] TYRIMU_VARDAI = {"addBstRec", "addBstIte", "addAvlRec", "removeBst"};
    private static final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 80000};

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] errors;

    private final SortedSetADTx<Smartphone> aSeries = new BstSetKTUx(new Smartphone(), Smartphone.byCPUClockSpeedRAMSize);
    private final SortedSetADTx<Smartphone> aSeries2 = new BstSetKTUx2(new Smartphone());
    private final SortedSetADTx<Smartphone> aSeries3 = new AvlSetKTUx(new Smartphone());

    public SpeedTest() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
    }

    public void pradetiTyrima() {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void SisteminisTyrimas() throws InterruptedException {
        try {
            for (int k : TIRIAMI_KIEKIAI) {
                Smartphone[] smartphonesArray = Production.generateAndShuffleSmartphones(k, k, 1.0);
                aSeries.clear();
                aSeries2.clear();
                aSeries3.clear();
                tk.startAfterPause();
                tk.start();
                for (Smartphone s : smartphonesArray) {
                    aSeries.add(s);
                }
                tk.finish(TYRIMU_VARDAI[0]);
                for (Smartphone s : smartphonesArray) {
                    aSeries2.add(s);
                }
                tk.finish(TYRIMU_VARDAI[1]);
                for (Smartphone s : smartphonesArray) {
                    aSeries3.add(s);
                }
                tk.finish(TYRIMU_VARDAI[2]);
                for (Smartphone s : smartphonesArray) {
                    aSeries.remove(s);
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
