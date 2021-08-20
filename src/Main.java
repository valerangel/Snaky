public class Main {
    public static void main (String[] args){
        Board board = new Board(10, 10);
        Screen screen = new Screen(board);

        while (true){
            board.move();
            //board.paintBoard();
            screen.paintScreen();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
