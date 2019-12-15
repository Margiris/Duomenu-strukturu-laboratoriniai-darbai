package snake;

import studijosKTU.ScreenKTU;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        (new Thread(new Snake())).start();
    }
}
