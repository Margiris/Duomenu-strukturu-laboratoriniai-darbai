package snake;

import studijosKTU.ScreenKTU;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Snake implements KeyListener, Runnable {
    // Directions, window properties, length of the snake and framerate
    private static final int CELL_SIZE_MAIN = 15, SCREEN_SIZE_MAIN = 31,
            UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3,
            MIN_LENGTH = 5, FRAMERATE = 10;

    // Score and time accumulators
    private int direction, score;
    private double time;

    public boolean isOver, isPaused;

    public ScreenKTU mainWindow, statsTable;

    private ArrayList<Point> snakeParts;

    private Random random;
    private Point head, cherry;

    // Class constructor
    public Snake() {
        mainWindow = new ScreenKTU(cellSize(), SCREEN_SIZE_MAIN, ScreenKTU.Grid.OFF);
        mainWindow.setTitle("Snake \u00a9 Margiris Burakauskas, IFF-6/10");
        mainWindow.addKeyListener(this);

        statsTable = new ScreenKTU(30, 18, 3, 17);
        statsTable.setColors(Color.BLACK, Color.WHITE);
        statsTable.setTitle("Game Statistics");

        mainWindow.requestFocus();

        drawSnakeAndCherry();
        isPaused = true;
    }

    // Returns cell size according to screen resolution which in turn scales the window.
    private int cellSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int height = gd.getDisplayMode().getHeight();
        return height * CELL_SIZE_MAIN / 768;
    }

    // Sets all the necessary values for the game to start.
    public void run() {
        isOver = false;
        isPaused = false;
        direction = RIGHT;
        score = 0;
        time = 0;
        random = new Random();

        head = new Point(SCREEN_SIZE_MAIN / 2, SCREEN_SIZE_MAIN / 2);
        cherry = new Point(random.nextInt(SCREEN_SIZE_MAIN),
                random.nextInt(SCREEN_SIZE_MAIN));

        snakeParts = new ArrayList<>();

        for (int i = head.y - MIN_LENGTH + 1; i <= head.y; i++)
            snakeParts.add(new Point(head.x, i));

        // Main cycle of the game thread.
        while (!isOver) {
            refreshStats();
            if (!isPaused) {
                checkCherry();
                drawSnakeAndCherry();
                increaseTime();
                makeNewHead();
            }
        }

        refreshStats();

        return;
    }

    // Creates new head point according to direction of the movement.
    // If snake reaches the end of the window moves it to the other side.
    private void makeNewHead() {
        switch (direction) {
            case LEFT: {
                if (head.y - 1 < 0)
                    head = new Point(head.x, SCREEN_SIZE_MAIN - 1);
                else head = new Point(head.x, head.y - 1);
                break;
            }
            case RIGHT: {
                if (head.y + 1 > SCREEN_SIZE_MAIN - 1)
                    head = new Point(head.x, 0);
                else head = new Point(head.x, head.y + 1);
                break;
            }
            case UP: {
                if (head.x - 1 < 0)
                    head = new Point(SCREEN_SIZE_MAIN - 1, head.y);
                else head = new Point(head.x - 1, head.y);
                break;
            }
            case DOWN: {
                if (head.x + 1 > SCREEN_SIZE_MAIN - 1)
                    head = new Point(0, head.y);
                else head = new Point(head.x + 1, head.y);
                break;
            }
        }

        if (noTailAt(head))
            snakeParts.add(head);
        else isOver = true;
    }

    // If snake eats cherry creates new cherry where's no tail and increases tail length,
    // if doesn't eat it - keeps tail the same length.
    private void checkCherry() {
        if (cherry != null)
            if (head.equals(cherry)) {
                score++;
                Point cherryNew = new Point(random.nextInt(SCREEN_SIZE_MAIN),
                        random.nextInt(SCREEN_SIZE_MAIN));
                while (!noTailAt(cherryNew))
                    cherryNew.setLocation(random.nextInt(SCREEN_SIZE_MAIN),
                            random.nextInt(SCREEN_SIZE_MAIN));
                cherry.setLocation(cherryNew);
            }
            else if (snakeParts.size() > MIN_LENGTH)
                snakeParts.remove(0);
    }

    // Checks if there is no tail at given point
    private boolean noTailAt(Point point) {
        for (Point p : snakeParts)
            if (p.equals(point))
                return false;
        return true;
    }

    // Repaints main (game) window every cycle.
    private void drawSnakeAndCherry() {
        mainWindow.forEachCell((int r, int c) -> mainWindow.print(r, c, Color.BLACK));

        if (snakeParts != null)
            if (snakeParts.size() > 0)
                for (Point point : snakeParts)
                    mainWindow.print(point.x, point.y, Color.GREEN);
        if (cherry != null)
            mainWindow.print(cherry.x, cherry.y, Color.YELLOW);
        mainWindow.refresh(1);
    }

    // Repaints statistics window every cycle.
    private void refreshStats() {
        statsTable.forEachCell((int r, int c) -> statsTable.print(r, c, Color.black));
        statsTable.print(0, 0, " Score: " + score);
        statsTable.print(1, 0, "  Time: " + (int) time);

        if (isPaused)
            statsTable.print(2, 0, "Status: Paused");

        else if (isOver)
            statsTable.print(2, 0, "Status: Game over");

        else statsTable.print(2, 0, "Status: Running");
        statsTable.refresh(1000 / FRAMERATE);
    }

    // Time is not accurate due to delays in methods' execution.
    // Higher the framerate - bigger the error.
    // TODO fix time calculation
    private void increaseTime() {
        time += 1.0 / FRAMERATE;
    }

    //region Keyboard Controls
    public void keyPressed(KeyEvent k) {
        int j = k.getKeyCode();

        if (!isPaused) {
            if ((j == KeyEvent.VK_W || j == KeyEvent.VK_UP) && direction != DOWN)
                direction = UP;

            if ((j == KeyEvent.VK_D || j == KeyEvent.VK_RIGHT) && direction != LEFT)
                direction = RIGHT;

            if ((j == KeyEvent.VK_S || j == KeyEvent.VK_DOWN) && direction != UP)
                direction = DOWN;

            if ((j == KeyEvent.VK_A || j == KeyEvent.VK_LEFT) && direction != RIGHT)
                direction = LEFT;
        }

        // Starts new game if over, else pauses.
        if (j == KeyEvent.VK_SPACE) {
            if (isOver)
                (new Thread(this)).start();

            else
                isPaused = !isPaused;
        }
    }

    public void keyReleased(KeyEvent k) {
    }

    public void keyTyped(KeyEvent k) {
    }
    //endregion
}
