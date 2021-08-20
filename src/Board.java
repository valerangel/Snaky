

public class Board {

    private Cells[][] map;
    private int sizeX;
    private int sizeY;
    private Snake[] snakes;
    private int numberOfSnakes;

    private Directions direction;

    public Board(int sizeX, int sizeY, int numberOfSnakes) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfSnakes = numberOfSnakes;
        this.map = new Cells[sizeX][sizeY];
        this.direction = Directions.RIGHT;

        fillEmptyMap();
        createSnakes();
        createNewFood();
    }


    private void fillEmptyMap() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.map[i][j] = Cells.NOFOOD;
            }
        }
    }

    private void createSnakes() {
        snakes = new Snake[numberOfSnakes];
        for (int i = 0; i < numberOfSnakes; i++) {
            this.snakes[i] = new Snake(this, i);
        }
    }

    public void iterate() {
        Cells[][] mapCopy = copyTheMap();

    }


    private Cells[][] copyTheMap() {
        Cells[][] mapCopy = new Cells[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                mapCopy[i][j] = map[i][j];
            }
        }
        return mapCopy;
    }

    public boolean isFood(Position position) {
        int x = position.getCoorX();
        int y = position.getCoorY();
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
            return false;
        }
        return this.map[x][y] == Cells.FOOD;
    }

    public boolean avaliableCell(Position position) {
        int x = position.getCoorX();
        int y = position.getCoorY();
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
            return false;
        }
        if (alreadyASnake(position)) {
            return false;
        }
        return true;
    }

    private boolean alreadyASnake(Position position) {
        for (int j = 0; j < numberOfSnakes; j++) {
            for (int i = 0; i < this.snakes[j].getLength(); i++) {
                if (position.samePosition(this.snakes[j].getPosition()[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void createNewFood() {
        int cellsNoSnake = sizeX * sizeY;
        for (int i = 0; i < numberOfSnakes; i++) {
            cellsNoSnake = cellsNoSnake - this.snakes[i].getLength();
        }
        Position[] positionsWithoutSnakes = new Position[cellsNoSnake];
        int k = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Position pos = new Position(i, j);
                if (!alreadyASnake(pos)) {
                    positionsWithoutSnakes[k] = pos;
                    k++;
                }
            }
        }

        Position pos2 = positionsWithoutSnakes[(int) (Math.random() * cellsNoSnake)];
        this.map[pos2.getCoorX()][pos2.getCoorY()] = Cells.FOOD;
    }

    public Snake[] getSnakes() {
        return this.snakes;
    }

    public void deleteFood(Position position) {
        this.map[position.getCoorX()][position.getCoorY()] = Cells.NOFOOD;
    }

    public void move() {
        for (int i = 0; i < numberOfSnakes; i++) {
            this.snakes[i].moveSnake();
        }
    }

    public boolean allSnakesAlive() {
        for (int i = 0; i < numberOfSnakes; i++) {
            if (!snakes[i].isAlive()) return false;
        }
        return true;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getNumberOfSnakes() {
        return numberOfSnakes;
    }
}
