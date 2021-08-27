

public class Board {

    private Cells[][] map;
    private int sizeX;
    private int sizeY;
    private Snake[] snakes;
    private int numberOfSnakes;
    private int numberOfFruitsEaten;
    private boolean gameIsPlaying;


    public Board(int sizeX, int sizeY, int numberOfSnakes) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfSnakes = numberOfSnakes;
        this.map = new Cells[sizeX][sizeY];
        this.gameIsPlaying = true;

        fillEmptyMap();
        createSnakes();
        createNewFood();
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
            if (this.snakes[j].isAlive()) {
                for (int i = 0; i < this.snakes[j].getLength(); i++) {
                    if (position.samePosition(this.snakes[j].getPosition()[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createNewFood() {
        int cellsNoSnake = sizeX * sizeY;
        for (int i = 0; i < numberOfSnakes; i++) {
            if (this.snakes[i].isAlive()) {
                cellsNoSnake = cellsNoSnake - this.snakes[i].getLength();
            }
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

    public int getTimeOfGame() {
        if (this.numberOfSnakes == 1) {
            return 180 - 5 * this.numberOfFruitsEaten;
        }

        return Math.min(180 - 5 * this.snakes[0].getPoints(), 180 - 5 * this.snakes[1].getPoints());
    }

    public void eatAFruit() {
        this.numberOfFruitsEaten++;
        this.createNewFood();
    }

    public Snake[] getSnakes() {
        return this.snakes;
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

    public boolean isGamePlaying() {
        if (!this.snakes[0].isAlive()) {

            if (this.numberOfSnakes == 1) {
                return false;
            }

            if (!this.snakes[1].isAlive()) {
                return false;
            }

            if (snakes[0].getPoints() <= snakes[1].getPoints()) {
                return false;
            }
        }

        if (numberOfSnakes >= 1) {
            return this.snakes[1].isAlive();
        }

        return true;
    }
}
