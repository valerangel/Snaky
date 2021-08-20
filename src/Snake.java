import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Snake {
    private Position[] position;
    private int length;
    private final Board board;

    private Directions direction;
    private Directions lastMove;

    private StatusSnake statusSnake;
    private int snakeNumber;

    //private static int  numberOfSnakes = 0;

    public Snake(Board board, int snakeNumber) {
        this.board = board;
        this.length = 3;
        //Snake.numberOfSnakes++;
        this.direction = Directions.RIGHT;
        this.lastMove = Directions.RIGHT;
        this.statusSnake = StatusSnake.ALIVE;
        this.snakeNumber = snakeNumber;

        this.position = new Position[length];
        if(snakeNumber == 0) {
            for (int i = 0; i < length; i++) {
                this.position[i] = new Position(i, 0);
            }
        } else if(snakeNumber == 1) {
            for (int i = 0; i < length; i++) {
                this.position[i] = new Position(i, 10);
            }
        }

    }

    public void moveSnake() {
        Position[] newPositions;

        if (this.statusSnake == StatusSnake.DEAD) {
            return ;
        }
        if (!canMove()) {
            this.statusSnake = StatusSnake.DEAD;
            return ;
        }

        if (nexPositionIsFood()) {
            newPositions = new Position[length+1];
            for (int i = 0; i < length; i++) {
                newPositions[i] = position[i];
            }
            newPositions[length] = nextPosition();
            length++;
            this.position = newPositions;
            this.board.deleteFood(newPositions[length - 1]);
            this.board.createNewFood();
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
        if (direction == Directions.RIGHT) {
            newPosition = new Position(position[length - 1].getCoorX() + 1, position[length - 1].getCoorY());
        } else if (direction == Directions.LEFT) {
            newPosition = new Position(position[length - 1].getCoorX() - 1, position[length - 1].getCoorY());

        } else if (direction == Directions.UP) {
            newPosition = new Position(position[length - 1].getCoorX(), position[length - 1].getCoorY() - 1);
        } else {
            newPosition = new Position(position[length - 1].getCoorX(), position[length - 1].getCoorY() + 1);
        }
        return newPosition;
    }

    private boolean canMove() {
        return this.board.avaliableCell(nextPosition());
    }

    public void setDirection(Directions direction) {
        if (direction == this.direction) {
            return;
        }
        if ((direction == Directions.RIGHT && this.lastMove == Directions.LEFT) ||
                (direction == Directions.LEFT && this.lastMove == Directions.RIGHT) ||
                (direction == Directions.UP && this.lastMove == Directions.DOWN) ||
                (direction == Directions.DOWN && this.lastMove == Directions.UP)) {
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

    public boolean isAlive(){
        return this.statusSnake == StatusSnake.ALIVE;
    }
}
