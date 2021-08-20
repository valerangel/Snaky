public class Main {
    public static void main (String[] args){
        Board board = new Board(15, 15);
        Screen screen = new Screen(board);

        while (true){
            board.move();
            //board.paintBoard();
            screen.paintScreen();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
