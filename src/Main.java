import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        boolean gameIsActive = true;
        while (gameIsActive) {
            gameIsActive = beginGame();
        }
    }

    public static boolean beginGame() {
        Object[] options = {"" + 1, "" + 2};
        int numberOfPlayers = JOptionPane.showOptionDialog(null, "Select number of players", "Snaky, the Game",
                JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                null, options, options[1]);
        System.out.println(numberOfPlayers);

        Board board = new Board(40, 18, numberOfPlayers + 1);
        Screen screen = new Screen(board);

        while (board.isGamePlaying()) {
            board.move();
            screen.paintScreen();
            try {
                Thread.sleep(board.getTimeOfGame());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return screen.gameOver();
    }
}
