import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * Created by olivier on 11/2/15.
 */
public class BattleShipModel extends Observable {
    private boolean[][] $grid;
    private int $numberOfTurns;
    private int $maxTurns;
    private ArrayList<Ship> $ships;

    public BattleShipModel() {
        this.$grid = new boolean[10][10]; //all cells have a default value of false
        this.$numberOfTurns = 1;
        this.$maxTurns = 100;
        this.$ships = new ArrayList<>(5);
        createShips();
    }

    public Ship getShip(int index) {
        return $ships.get(index);
    }

    public int getShipsLeft() {
        return $ships.size();
    }

    private void createShips() {
        Ship patrolboat = new Ship(ShipType.PATROLBOAT);
        createShipPosition(patrolboat);
        $ships.add(patrolboat);

        Ship destroyer = new Ship(ShipType.DESTROYER);
        createShipPosition(destroyer);
        $ships.add(destroyer);

        Ship sub = new Ship(ShipType.SUBMARINE);
        createShipPosition(sub);
        $ships.add(sub);

        Ship battleship = new Ship(ShipType.BATTLESHIP);
        createShipPosition(battleship);
        $ships.add(battleship);

        Ship carrier = new Ship(ShipType.AIRCRAFTCARRIER);
        createShipPosition(carrier);
        $ships.add(carrier);
    }

    private void createShipPosition(Ship ship) {
        boolean horizontal = getRandomNumber(2) == 0;
        boolean decrease = getRandomNumber(2) == 0;
        int row = getRandomNumber(10);
        int col = getRandomNumber(10);

        if (!$ships.isEmpty()) {
             while (!validPosition(row, col, ship.getSize(), horizontal, decrease)) {
                 horizontal = getRandomNumber(2) == 0;
                 decrease = getRandomNumber(2) == 0;

                 row = getRandomNumber(10);
                 col = getRandomNumber(10);
             }
             setPositions(ship, row, col, horizontal, decrease);
        } else {
            setPositions(ship, row, col, horizontal, decrease);
        }

    }

    private void setPositions(Ship ship, int row, int col, boolean horizontal, boolean decrease) {
        if (horizontal && decrease) {
            setLeft(ship, row, col);
        } else if (horizontal && !decrease) {
            setRight(ship, row, col);
        } else if (!horizontal && decrease) {
            setUp(ship, row, col);
        } else {
            setDown(ship, row, col);
        }
    }

    private void setLeft(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = col - length;
        for (int i = col; i > border ; --i) {
            $grid[row][i] = true;
            ship.setPosition(row, i);
        }
    }

    private void setRight(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = col + length;
        for (int i = col; i < border; ++i) {
            $grid[row][i] = true;
            ship.setPosition(row, i);
        }
    }

    private void setUp(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = row - length;
        for (int i = row; i > border; --i) {
            $grid[i][col] = true;
            ship.setPosition(i, col);
        }
    }

    private void setDown(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = row + length;
        for (int i = row; i < border; ++i) {
            $grid[i][col] = true;
            ship.setPosition(i, col);
        }
    }

    public String getType(int row, int col) {
        Ship ship = searchShip(row, col);
        String type = ship.getType();
        $ships.remove(ship);
        return type;
    }

    public boolean checkSunk(int row, int col) {
        Ship shipToCheck = searchShip(row,col);
        if (shipToCheck.checkSunk()) {

            return true;
        }
        return false;
    }

    public void setHitShip(int row, int col) {
        Ship hitShip = searchShip(row, col);
        hitShip.decreaseHit();

    }

    private Ship searchShip(int row, int col) {
        for (Ship ship : $ships) {
            if (ship.checkHit(row, col)) {
                return ship;
            }
        }
        return null;
    }


    private boolean validPosition(int row, int col, int shipLength, boolean horizontal, boolean decrease) {
        int border = $grid.length;
        if (horizontal && decrease) {
            if (col - shipLength < 0)
                return false;
        } else if (horizontal && !decrease) {
            if (col + shipLength >= border) {
                return false;
            }
        } else if (decrease && !horizontal) {
            if (row - shipLength < 0) {
                return false;
            }
        } else {
            if (row + shipLength >= border) {
                return false;
            }
        }

        return checkPositions(row, col, shipLength, horizontal, decrease);
    }

    private boolean checkPositions(int row, int col, int shipLength, boolean horizontal, boolean decrease) {
        if (horizontal && decrease) {
            return checkLeft(row, col, shipLength);
        } else if (horizontal && !decrease) {
            return checkRight(row, col, shipLength);
        } else if (!horizontal && decrease) {
            return checkUp(row, col, shipLength);
        } else
            return checkDown(row, col, shipLength);

    }

    private boolean checkLeft(int row, int col, int length) {
        int border = col - length;
        for (int i = col; i >= border; --i) {
            if ($grid[row][i]) {
                return false;
            }
        }

        return true;
    }

    private boolean checkRight(int row, int col, int length) {
        int border = col + length;
        for (int i = col; i < border; ++i) {
            if ($grid[row][i]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkUp(int row, int col, int length) {
        int border = row - length;
        for (int i = row; i >= border; --i) {
            if ($grid[i][col]) {
                return false;
            }
        }

        return true;
    }

    private boolean checkDown(int row, int col, int length) {
        int border = row + length;
        for (int i = row; i <  border; ++i) {
            if ($grid[i][col]) {
                return false;
            }
        }

        return true;
    }

    private int getRandomNumber(int max) {
        Random random = new Random();

        return random.nextInt(max);
    }


    public boolean checkShot(int row, int col) {
        return $grid[row][col];
    }

    public void increaseTurn() {
        $numberOfTurns++;

        BattleShipUpdate info = new BattleShipUpdate($maxTurns-$numberOfTurns + 1);
        setChanged();
        notifyObservers(info);
    }
}
