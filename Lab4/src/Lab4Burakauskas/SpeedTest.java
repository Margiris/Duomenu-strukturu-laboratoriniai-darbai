package Lab4Burakauskas;

import laborai.studijosktu.HashType;
import laborai.studijosktu.MapKTUx;
import laborai.gui.MyException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * @author eimutis
 */
public class SpeedTest {

    public static final String FINISH_COMMAND = "finishCommand";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("Lab4Burakauskas.messages");

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;

    private final String[] TYRIMU_VARDAI = {"add0.75", "add0.25", "rem0.75", "rem0.25", "get0.75", "get0.25"};
    private final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 80000};

    private final MapKTUx<String, Smartphone> telefonuAtvaizdis
            = new MapKTUx(new String(), new Smartphone(), 10, 0.75f, HashType.DIVISION);
    private final MapKTUx<String, Smartphone> telefonuAtvaizdis1
            = new MapKTUx(new String(), new Smartphone(), 10, 0.25f, HashType.DIVISION);
    private final Queue<String> chainsSizes = new LinkedList<>();

    public SpeedTest() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
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
            chainsSizes.add(MESSAGES.getString("msg4"));
            chainsSizes.add("   kiekis      " + TYRIMU_VARDAI[0] + "   " + TYRIMU_VARDAI[1]);
            for (int k : TIRIAMI_KIEKIAI) {
                Smartphone[] smartphoneArray = Production.produceSmartphones(k);
                String[] smartphoneIDArray = Production.produceIDs(k);
                telefonuAtvaizdis.clear();
                telefonuAtvaizdis1.clear();
                tk.startAfterPause();
                tk.start();

                for (int i = 0; i < k; i++) {
                    telefonuAtvaizdis.put(smartphoneIDArray[i], smartphoneArray[i]);
                }
                tk.finish(TYRIMU_VARDAI[0]);

                String str = "   " + k + "          " + telefonuAtvaizdis.getMaxChainSize();
                for (int i = 0; i < k; i++) {
                    telefonuAtvaizdis1.put(smartphoneIDArray[i], smartphoneArray[i]);
                }
                tk.finish(TYRIMU_VARDAI[1]);

                str += "         " + telefonuAtvaizdis1.getMaxChainSize();
                chainsSizes.add(str);
                for (String s : smartphoneIDArray) {
                    telefonuAtvaizdis.remove(s);
                }
                tk.finish(TYRIMU_VARDAI[2]);

                for (String s : smartphoneIDArray) {
                    telefonuAtvaizdis1.remove(s);
                }
                tk.finish(TYRIMU_VARDAI[3]);

                for (String s : smartphoneIDArray) {
                    telefonuAtvaizdis1.get(s);
                }
                tk.finish(TYRIMU_VARDAI[4]);

                for (String s : smartphoneIDArray) {
                    telefonuAtvaizdis1.get(s);
                }
                tk.finish(TYRIMU_VARDAI[5]);
                tk.seriesFinish();
            }

            StringBuilder sb = new StringBuilder();
            chainsSizes.forEach(p -> sb.append(p).append(System.lineSeparator()));
            tk.logResult(sb.toString());
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            tk.logResult(e.getMessage());
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
