package Main.MainWindow;

import Main.IO;
import Main.UnrolledLinkedList;
import Main.WordPair;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class dictionaryTest {
    public Label word;
    public Button startTest;
    public VBox buttonsContainer;
    public Button selection0;
    public Button selection1;
    public Button selection2;
    public Button selection3;
    public ProgressBar progressBar;

    private Main.Main main;
    private UnrolledLinkedList wordPairs;
    UnrolledLinkedList wrongSelections;
    private int numberOfOptions;
    private double testLength = 10;
    private int correctPairIndex;
    private int selectedPairIndex;
    private int score;
    private Random random;

    public void setMain(Main.Main main) {
        this.main = main;
    }

    @FXML
    private void startTest(MouseEvent event) {
        wordPairs = IO.ReadXML();
        wrongSelections = new UnrolledLinkedList();
        random = new Random();
        score = 0;

        numberOfOptions = buttonsContainer.getChildren().size();

        if (isTestPossible()) {
            buttonsContainer.setDisable(false);
            startTest.setVisible(false);
            test();
        } else {
            main.switchScene(0);
        }
    }

    private void test() {

        if (progressBar.getProgress() >= 1) {
            finished();
        }

        WordPair[] currentPairs = getRandomPairs(numberOfOptions);
        correctPairIndex = random.nextInt(numberOfOptions);

        selection0.setText(currentPairs[0].Get(1));
        selection1.setText(currentPairs[1].Get(1));
        selection2.setText(currentPairs[2].Get(1));
        selection3.setText(currentPairs[3].Get(1));

        word.setText(currentPairs[correctPairIndex].Get(0));
    }

    private WordPair[] getRandomPairs(int count) {
        Random random = new Random();
        WordPair[] pairs = new WordPair[count];

        UnrolledLinkedList usedPairs = new UnrolledLinkedList();

        while (count > 0) {
            WordPair pair = wordPairs.get(random.nextInt(wordPairs.getSize()));

            if (!usedPairs.contains(pair)) {
                pairs[--count] = pair;
                usedPairs.add(pair);
            }
        }
        return pairs;
    }

    private boolean isTestPossible() {
        int pairCount = wordPairs.getSize();

        if(main.fullTest)
            testLength = pairCount;

        if (pairCount < numberOfOptions) {
            String message = "Testo žodyne nepakanka žodžių!" +
                    "\nPirma įveskite ir išsaugokite bent " + numberOfOptions + " žodžius.";
            main.createDialog(Alert.AlertType.ERROR, message).showAndWait();
            return false;
        } else if (pairCount < testLength) {
            String message = "Žodžių skaičius (" + pairCount + ") mažesnis už testo apimtį (" + (int)testLength +
                    ")\nAtlikti trumpesnį testą?";

            Alert alert = main.createDialog(Alert.AlertType.CONFIRMATION, message);
            alert.setTitle("Not enough words in dictionary");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.CANCEL) {
                return false;
            } else testLength = pairCount;
        }
        return true;
    }

    @FXML
    private void selection(MouseEvent event) {
        progressBar.setProgress((double)Math.round(progressBar.getProgress() * 10) / 10 + (1 / testLength));

        if (event.getSource() == selection0) {
            selectedPairIndex = 0;
        }
        if (event.getSource() == selection1) {
            selectedPairIndex = 1;
        }
        if (event.getSource() == selection2) {
            selectedPairIndex = 2;
        }
        if (event.getSource() == selection3) {
            selectedPairIndex = 3;
        }

        if (selectedPairIndex == correctPairIndex)
            score++;
//        else{
//            String Native = (Button)event.getSource()
//            wrongSelections.add(new WordPair(correctPairIndex));
//        }
        test();
    }

    private void finished() {
        main.getStage(word).close();

        String message = "Points: " + score + " out of " + (int)testLength +
                ".\nMark: " + (double) Math.round(score * 1000 / testLength) / 100 +
                ".\nStart new test?";
        Alert alert = main.createDialog(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle("Finished");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Stage st = main.getStage(word);
            st.setScene(main.createScene(0));
            st.show();
        }
    }


}