package Lab3Burakauskas;

import javafx.application.Application;
import javafx.stage.Stage;
import laborai.gui.fx.Lab3WindowFX_1;

import java.util.Locale;

/*
 * Darbo atlikimo tvarka - čia yra JavaFX pradinė klasė.
 */
public class VykdymoModulisFX extends Application {

    public static void main(String [] args) {
        VykdymoModulisFX.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
//        SmartphoneTest.test();
        Lab3WindowFX_1.createAndShowFXGUI(primaryStage);
    }
}