package Main;

import Main.DictionaryWindow.dictionaryWindow;
import Main.MainWindow.dictionaryTest;
import Main.MainWindow.start;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String[] SCENERESOURCES = {
            "MainWindow/start.fxml",
            "MainWindow/dictionaryTest.fxml",
            "dictionaryWindow/dictionaryWindow.fxml"
    };

    private FXMLLoader fxmlLoader;

    private Stage mainWindow;

    public boolean fullTest;

    @Override
    public void start(Stage primaryStage) {
        Scene startScene = createScene(0);

        fullTest = false;

        mainWindow = primaryStage;
        mainWindow.setTitle("Language Dictionary Test");
        mainWindow.setScene(startScene);
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void switchScene(int sceneIndex) {
        mainWindow.setScene(createScene(sceneIndex));
    }

    public Stage getStage(Control control) {
        return (Stage) control.getScene().getWindow();
    }

    public Scene createScene(int sceneIndex) {
        Scene scene;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(SCENERESOURCES[sceneIndex]));
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            scene = null;
            e.printStackTrace();
        }

        Object controller = fxmlLoader.getController();

        switch (sceneIndex) {
            case 0:
                start.class.cast(controller).setMain(this);
                break;
            case 1:
                dictionaryTest.class.cast(controller).setMain(this);
                break;
            case 2:
                dictionaryWindow.class.cast(controller).setMain(this);
        }
        return scene;
    }

    public Alert createDialog(Alert.AlertType type, String message) {
        Alert alert;
        switch (type) {
            case CONFIRMATION:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                break;
            case ERROR:
                alert = new Alert(Alert.AlertType.ERROR);
                break;
            default:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
        alert.setHeaderText(message);
        return alert;
    }
}
