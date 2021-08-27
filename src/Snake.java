public class Snake {
    private final Board board;
    private Position[] position;
    private int length;
    private Direction direction;
    private Direction lastMove;

    private StatusSnake statusSnake;
    private final int snakeNumber;
    private int points;

    //private static int  numberOfSnakes = 0;

    public Snake(Board board, int snakeNumber) {
        this.board = board;
        this.length = 3;
        //Snake.numberOfSnakes++;
        this.direction = Direction.RIGHT;
        this.lastMove = Direction.RIGHT;
        this.statusSnake = StatusSnake.ALIVE;
        this.snakeNumber = snakeNumber;
        this.points = 0;

        this.position = new Position[length];
        if (snakeNumber == 0) {
            for (int i = 0; i < length; i++) {
                this.position[i] = new Position(i, 0);
            }
        } else if (snakeNumber == 1) {
            for (int i = 0; i < length; i++) {
                this.position[i] = new Position(i, board.getSizeY() - 1);
            }
        }

    }

    public void moveSnake() {
        Position[] newPositions;

        if (this.statusSnake == StatusSnake.DEAD) {
            return;
        }
        if (!canMove()) {
            this.statusSnake = StatusSnake.DEAD;
            return;
        }

        if (nexPositionIsFood()) {
            newPositions = new Position[length + 1];
            for (int i = 0; i < length; i++) {
                newPositions[i] = position[i];
            }
            newPositions[length] = nextPosition();
            length++;
            this.position = newPositions;
            this.board.deleteFood(newPositions[length - 1]);
            this.board.eatAFruit();
            this.points++;
        } else {
            newPositions = new Position[length];
            for (int i = 1; i < length; i++) {
                newPositions[i - 1] = position[i];
            }
            newPositions[length - 1] = nextPosition();
        }
        this.position = newPositions;

        this.lastMove = this.direction;
    }

    private boolean nexPositionIsFood() {
        return this.board.isFood(nextPosition());
    }

    private Position nextPosition() {
        Position newPosition;
        if (direction == Direction.RIGHT) {
            newPosition = new Position(position[length - 1].getCoorX() + 1, position[length - 1].getCoorY());
        } else if (direction == Direction.LEFT) {
            newPosition = new Position(position[length - 1].getCoorX() - 1, position[length - 1].getCoorY());

        } else if (direction == Direction.UP) {
            newPosition = new Position(position[length - 1].getCoorX(), position[length - 1].getCoorY() - 1);
        } else {
            newPosition = new Position(position[length - 1].getCoorX(), position[length - 1].getCoorY() + 1);
        }
        return newPosition;
    }

    private boolean canMove() {
        return this.board.avaliableCell(nextPosition());
    }

    public void setDirection(Direction direction) {
        if (direction == this.direction) {
            return;
        }

        if ((direction == Direction.RIGHT && this.lastMove == Direction.LEFT)
                || (direction == Direction.LEFT && this.lastMove == Direction.RIGHT)
                || (direction == Direction.UP && this.lastMove == Direction.DOWN)
                || (direction == Direction.DOWN && this.lastMove == Direction.UP)
        ) {
            return;
        }

        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public Position[] getPosition() {
        return position;
    }

    public int getSnakeNumber() {
        return snakeNumber;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isAlive() {
        return this.statusSnake == StatusSnake.ALIVE;
    }
}
