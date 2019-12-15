package Main.DictionaryWindow;

import Main.UnrolledLinkedList;
import Main.WordPair;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;


import static Main.IO.ReadXML;

public class dictionaryWindow {
    public Label labelNative;
    public Label labelForeign;
    public TextArea textAreaNative;
    public TextArea textAreaForeign;
    public Button closeButton;

    private Main.Main main;

    public void setMain(Main.Main main) {
        this.main = main;
    }

    public void initialize() {
        StringBuilder nativeBuilder = new StringBuilder();
        StringBuilder foreignBuilder = new StringBuilder();
        UnrolledLinkedList unrolledLinkedList = ReadXML();

        for (WordPair wordPair : (unrolledLinkedList.toArray())) {
            nativeBuilder.append(wordPair.Get(0)).append("\n");
            foreignBuilder.append(wordPair.Get(1)).append("\n");
        }

        textAreaNative.setText(nativeBuilder.toString());
        textAreaForeign.setText(foreignBuilder.toString());

        textAreaNative.scrollTopProperty().bindBidirectional(textAreaForeign.scrollTopProperty());
    }

    @FXML
    private void closeButtonClick(MouseEvent event) {
        main.getStage(closeButton).close();
    }
}
