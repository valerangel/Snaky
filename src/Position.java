public class Position {
    private final int coorX;
    private final int coorY;

    public Position(int x, int y) {
        this.coorX = x;
        this.coorY = y;
    }

    public int getCoorX() {
        return coorX;
    }

    public int getCoorY() {
        return coorY;
    }

    public boolean samePosition(Position pos2) {
        if (pos2 == null) {
            return false;
        }
        if (this.coorX != pos2.getCoorX()) {
            return false;
        }
        return this.coorY == pos2.getCoorY();
    }
}
