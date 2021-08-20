

public class Board {

    private Cells[][] map;
    private int sizeX;
    private int sizeY;
    private Snake snake;

    private Directions direction;

    public Board(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.map = new Cells[sizeX][sizeY];
        this.direction = Directions.RIGHT;

        fillEmptyMap();
        createSnake();
        createNewFood();
    }


    private void fillEmptyMap() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.map[i][j] = Cells.NOFOOD;
            }
        }
    }

    private void createSnake() {
        this.snake = new Snake(this);
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
        for (int i = 0; i < this.snake.getLength(); i++) {
            if (position.samePosition(this.snake.getPosition()[i])) return true;
        }
        return false;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void createNewFood() {
        int cellsNoSnake = sizeX * sizeY - this.snake.getLength();
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

    public Snake getSnake (){
        return this.snake;
    }

    public void deleteFood(Position position) {
        this.map[position.getCoorX()][position.getCoorY()] = Cells.NOFOOD;
    }

    public void move() {
        this.snake.moveSnake();
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void paintBoard() {
        String[][] boardS = new String[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (this.map[i][j] == Cells.NOFOOD) {
                    boardS[i][j] = "-";
                } else {
                    boardS[i][j] = "O";
                }
            }
        }

        for (int i = 0; i < this.snake.getLength(); i++) {
            if (snake.getPosition()[i] != null) {
                boardS[snake.getPosition()[i].getCoorX()][snake.getPosition()[i].getCoorY()] = "X";
            } else System.out.println("ESto sa roto" + this.snake.getLength() + "   i= " + i);
        }

        System.out.println("\n\tGAME OF THE SNAKE");
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                System.out.print(boardS[i][j]);
            }
            System.out.println();
        }

    }
}
