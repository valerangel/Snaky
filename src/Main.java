public class Main {
    public static void main (String[] args){
        Board board = new Board(20, 18, 2);
        Screen screen = new Screen(board);

        while (true){
            board.move();
            screen.paintScreen();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
