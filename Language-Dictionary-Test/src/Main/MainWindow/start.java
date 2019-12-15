package Main.MainWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class start {
    public CheckBox fullTest;
    public javafx.scene.control.Button dictionaryTest;
    public javafx.scene.control.Button listeningTest;
    public javafx.scene.control.Button dictionary;
    public Button listeningDictionary;

    private Main.Main main;

    public void setMain(Main.Main main) {
        this.main = main;
    }

    @FXML
    private void startDictionaryTest(MouseEvent event) {
        main.switchScene(1);
    }

    @FXML
    private void openDictionary(MouseEvent event) {
        Stage dictionaryWindow = new Stage();
        dictionaryWindow.setTitle("Dictionary");
        dictionaryWindow.setScene(main.createScene(2));
        dictionaryWindow.show();
    }

    @FXML
    private void startListeningTest() {
        String message = "Not implemented yet.";
        main.createDialog(Alert.AlertType.ERROR, message).showAndWait();
    }

    @FXML
    private void openListeningDictionary() {
        String message = "Not implemented yet.";
        main.createDialog(Alert.AlertType.ERROR, message).showAndWait();
    }

    @FXML
    private void checkBoxClick() {
        main.fullTest = fullTest.selectedProperty().getValue();
    }
}
