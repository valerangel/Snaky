import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Screen extends JPanel {

    public static final int LENGTH = 30;


    private JFrame jframe;
    private JPanel jpanel;
    private Graphics g;

    public Board board;
    public int sizeX;
    public int sizeY;

    public Screen(Board board) {
        this.board = board;
        this.sizeX = board.getSizeX();
        this.sizeY = board.getSizeY();


        this.jframe = new JFrame("Snaky");
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setSize(sizeX * LENGTH + 15,
                sizeY * LENGTH + 45);
        this.jframe.setVisible(true);


        this.jpanel = new JPanel(new BorderLayout());
        this.jpanel.setSize(sizeX * LENGTH + 15,
                sizeY * LENGTH + 45);

        this.jframe.add(this.jpanel);
        this.jframe.setResizable(false);
        this.g = jpanel.getGraphics();
        paintScreen();

        this.jframe.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    board.getSnakes()[0].setDirection(Directions.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    board.getSnakes()[0].setDirection(Directions.RIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    board.getSnakes()[0].setDirection(Directions.UP);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    board.getSnakes()[0].setDirection(Directions.DOWN);
                }

                if (board.getNumberOfSnakes() >= 2) {
                    if (e.getKeyCode() == KeyEvent.VK_A) {
                        board.getSnakes()[1].setDirection(Directions.LEFT);
                    } else if (e.getKeyCode() == KeyEvent.VK_D) {
                        board.getSnakes()[1].setDirection(Directions.RIGHT);
                    } else if (e.getKeyCode() == KeyEvent.VK_W) {
                        board.getSnakes()[1].setDirection(Directions.UP);
                    } else if (e.getKeyCode() == KeyEvent.VK_S) {
                        board.getSnakes()[1].setDirection(Directions.DOWN);
                    }
                }
            }
        });

        jframe.setFocusable(true);
        jpanel.setBackground(Color.BLACK);
        jframe.setLocationRelativeTo(null);

    }

    public void paintScreen() {
        allBlack();
        paintMap();
        for (int i = 0; i < board.getNumberOfSnakes(); i++) {
            paintSnake(board.getSnakes()[i]);
        }
        paintPoints();

    }

    private void allBlack() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,
                this.sizeX * LENGTH + 100,
                this.sizeY * LENGTH + 100);
    }

    private void paintMap() {
        ArrayList<Position> cellsAlive = new ArrayList<>();

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (board.isFood(new Position(i, j))) {
                    cellsAlive.add(new Position(i, j));
                }
            }
        }

        for (Position p : cellsAlive) {
            g.setColor(Color.RED);
            g.fillOval((int) ((p.getCoorX() + 0.33) * LENGTH),
                    (int) ((p.getCoorY() + 0.33) * LENGTH),
                    (int) (0.67 * LENGTH),
                    (int) (0.67 * LENGTH)
            );
            g.setColor(Color.GREEN);
            g.fillRect(
                    (int) ((p.getCoorX() + 0.6) * LENGTH),
                    (int) ((p.getCoorY() + 0.2) * LENGTH),
                    (int) (0.1 * LENGTH),
                    (int) (0.3 * LENGTH)
            );
        }
    }

    private void paintSnake(Snake snake) {
        if (!snake.isAlive()) {
            return;
        }

        int length = snake.getLength();
        for (int i = 0; i < length - 1; i++) {
            int x = snake.getPosition()[i].getCoorX();
            int y = snake.getPosition()[i].getCoorY();
            g.setColor(new Color(180,180,180));
            g.fillOval((int) ((x + 0.2) * LENGTH),
                    (int) ((y + 0.2) * LENGTH),
                    (int) (0.8 * LENGTH),
                    (int) (0.8 * LENGTH));
        }

        int x = snake.getPosition()[length - 1].getCoorX();
        int y = snake.getPosition()[length - 1].getCoorY();
        if (snake.getSnakeNumber() == 0) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.GREEN);
        }
        g.fillRect((int) ((x + 0.2) * LENGTH),
                (int) ((y + 0.2) * LENGTH),
                (int) (0.8 * LENGTH),
                (int) (0.8 * LENGTH));
    }

    private void paintPoints() {
        this.g.setColor(Color.WHITE);
        Font font = new Font("Verdana", Font.PLAIN, 18);
        g.setFont(font);
        for (int i = 0; i < board.getNumberOfSnakes(); i++) {
            paintPointsForASnaky(i);
        }
    }

    private void paintPointsForASnaky(int numberOfSnake) {
        int posX = (numberOfSnake == 0) ? 2 * LENGTH : LENGTH * (sizeX - 6);

        String text = String.format("Snaky %d: %d pts",
                (numberOfSnake + 1),
                board.getSnakes()[numberOfSnake].getPoints()
        );

        this.g.drawString(text, posX, LENGTH);
    }
}
