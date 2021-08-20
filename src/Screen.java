import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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


        this.jframe = new JFrame("Game of Life");
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
                System.out.println("Typed " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Released " + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Pressed " + e.getKeyChar());
                System.out.println("Entra a lo de key type");
                Directions direction;
                if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    direction = Directions.LEFT;
                } else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
                    direction = Directions.RIGHT;
                } else if (e.getKeyCode()==KeyEvent.VK_UP){
                    direction = Directions.UP;
                } else if (e.getKeyCode()==KeyEvent.VK_DOWN){
                    direction = Directions.DOWN;
                } else {
                    direction= null;
                    System.out.println("Has pulsado una tecla que no vale");
                }
                board.getSnake().setDirection(direction);
            }
        });

        jframe.setFocusable(true);

    }

    public void paintScreen() {
        allBlack();
        paintMap();
        paintSnake();
    }

    private void allBlack() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,
                this.sizeX * LENGTH,
                this.sizeY * LENGTH);
    }

    private void paintMap() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (board.isFood(new Position(i, j))) {
                    g.setColor(Color.RED);
                    g.fillOval((int) ((i + 0.33) * LENGTH),
                            (int) ((j + 0.33) * LENGTH),
                            (int) (0.67 * LENGTH),
                            (int) (0.67 * LENGTH));
                }
            }
        }
    }

    private void paintSnake() {
        int length = this.board.getSnake().getLength();
        for (int i = 0; i < length - 1; i++) {
            int x = this.board.getSnake().getPosition()[i].getCoorX();
            int y = this.board.getSnake().getPosition()[i].getCoorY();
            g.setColor(Color.WHITE);
            g.fillOval((int) ((x + 0.2) * LENGTH),
                    (int) ((y + 0.2) * LENGTH),
                    (int) (0.8 * LENGTH),
                    (int) (0.8 * LENGTH));
        }

        int x = this.board.getSnake().getPosition()[length - 1].getCoorX();
        int y = this.board.getSnake().getPosition()[length - 1].getCoorY();
        g.setColor(Color.BLUE);
        g.fillRect((int) ((x + 0.2) * LENGTH),
                (int) ((y + 0.2) * LENGTH),
                (int) (0.8 * LENGTH),
                (int) (0.8 * LENGTH));
    }
}
