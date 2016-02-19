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

    /**
     * Default constructor.
     * @pre TRUE
     * @post all member variables have been initialized
     */
    public BattleShipModel() {
        this.$grid = new boolean[10][10]; //all cells have a default value of false
        this.$numberOfTurns = 1;
        this.$maxTurns = 50;
        this.$ships = new ArrayList<>(5);
        createShips();
    }

    /**
     * Returns the ship on the given index from the list of ships.
     * @param index the index on which the requested ship is located
     * @return the requested ship
     * @pre the index is an integer, at least zero and less than the size of the list of ships
     */
    public Ship getShip(int index) {
        return $ships.get(index);
    }

    /**
     * Lets the sender of the request know how many ships are left.
     * @return the size of the list of ships
     */
    public int getShipsLeft() {
        return $ships.size();
    }

    /**
     * Creates all the required ships.
     */
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

    /**
     * Sets the positions for a given ship.
     * @param ship the ship which needs to get positions
     * @pre the ship is not null
     * @post the ship has been given its positions
     */
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

    /**
     * Sets the position of a part of a ship.
     * @param ship the ship of which a part will be positioned
     * @param row the row in which the part will be placed
     * @param col the column in which the part will be placed
     * @param horizontal true if the ship will be placed horizontally
     * @param decrease true if the ship will be placed from down to up
     */
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

    /**
     * Sets a part of the ship to the left of the grid.
     * @param ship the ship of which a part will be positioned
     * @param row row the row in which the part will be placed
     * @param col the column in which the part will be placed
     */
    private void setLeft(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = col - length;
        for (int i = col; i > border ; --i) {
            $grid[row][i] = true;
            ship.setPosition(row, i);
        }
    }

    /**
     * Sets a part of the ship to the left of the grid.
     * @param ship the ship of which a part will be positioned
     * @param row row the row in which the part will be placed
     * @param col the column in which the part will be placed
     */
    private void setRight(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = col + length;
        for (int i = col; i < border; ++i) {
            $grid[row][i] = true;
            ship.setPosition(row, i);
        }
    }

    /**
     * Sets a part of the ship to the left of the grid.
     * @param ship the ship of which a part will be positioned
     * @param row row the row in which the part will be placed
     * @param col the column in which the part will be placed
     */
    private void setUp(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = row - length;
        for (int i = row; i > border; --i) {
            $grid[i][col] = true;
            ship.setPosition(i, col);
        }
    }

    /**
     * Sets a part of the ship to the left of the grid.
     * @param ship the ship of which a part will be positioned
     * @param row row the row in which the part will be placed
     * @param col the column in which the part will be placed
     */
    private void setDown(Ship ship, int row, int col) {
        int length = ship.getSize();
        int border = row + length;
        for (int i = row; i < border; ++i) {
            $grid[i][col] = true;
            ship.setPosition(i, col);
        }
    }

    /**
     * Returns the type of the ship on a given position
     * @param row the row where the ship is situated
     * @param col the column where the ship is situated
     * @return the type of the ship
     * @pre the row and column are integers, both at least zero and less than the size of the grid
     * @post the type of the requested ship is returned
     */
    public String getType(int row, int col) {
        Ship ship = searchShip(row, col);
        String type = ship.getType();
        removeShip(ship);
        return type;
    }

    /**
     * Checks if a ship on a given position has been sunk.
     * @param row the row where the ship is/was situated
     * @param col the column where the ship is/was situated
     * @return true is the ship is no more hits left, false if not
     */
    public boolean checkSunk(int row, int col) {
        Ship shipToCheck = searchShip(row,col);

        if (shipToCheck != null) {
            return shipToCheck.checkSunk();
        }
        return false;
    }

    /**
     * Removes a ship from the list of ships.
     *
     * @param toBeRemoved the ship to be removed
     * @pre the ship that will be removed exists in the list of ships
     * @post the ship is removed from the list of ships
     */
    private void removeShip(Ship toBeRemoved) {
        this.$ships.remove(toBeRemoved);
    }
    /**
     * Sets a hit on a ship.
     * @param row the row where the ship is situated
     * @param col the column where the ship is situated
     */
    public void setHitShip(int row, int col) {
        Ship hitShip = searchShip(row, col);
        if (hitShip != null) {
            hitShip.decreaseHit();
        }

    }

    /**
     * Searches for a ship using a given row and column.
     * @param row the row in which a ship is located
     * @param col the column in which a ship is located
     * @return a ship
     */
    private Ship searchShip(int row, int col) {
        for (Ship ship : $ships) {
            if (ship.checkHit(row, col)) {
                return ship;
            }
        }
        return null;
    }


    /**
     * Checks if a ship can be placed in a valid position. Valid meaning that no ship may cross each other or go out
     * of bounds.
     * @param row the tow to be checked
     * @param col the column to be checked
     * @param shipLength the length of the ship
     * @param horizontal if the ship has a horizontal position (or not)
     * @param decrease if the ship goes from up to down (or not)
     * @return true if the ship can be placed on a valid position
     */
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

    /**
     * Checks if a ship crosses with other ships.
     * @param row the row to be checked
     * @param col the column to be checked
     * @param shipLength the length of the ship
     * @param horizontal if the ship has a horizontal position (or not)
     * @param decrease if the ship goes from top to bottom (or not)
     * @return true if the ship can be placed in this position
     */
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

    /**
     * Checks if a ship crosses with another ship on the left side of the grid.
     * @param row the row to be checked
     * @param col the column to be checked
     * @param length the length of the ship (a border value when subtracted from the column)
     * @return true if there are no collisions
     */
    private boolean checkLeft(int row, int col, int length) {
        int border = col - length;
        for (int i = col; i >= border; --i) {
            if ($grid[row][i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a ship crosses with another ship on the right side of the grid.
     * @param row the row to be checked
     * @param col the column to be checked
     * @param length the length of the ship (a border value when added to the column)
     * @return true if there are no collisions
     */
    private boolean checkRight(int row, int col, int length) {
        int border = col + length;
        for (int i = col; i < border; ++i) {
            if ($grid[row][i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a ship crosses with another ship on the upper side of the grid.
     * @param row the row to be checked
     * @param col the column to be checked
     * @param length the length of the ship (a border value when subtracted from the row)
     * @return true if there are no collisions
     */
    private boolean checkUp(int row, int col, int length) {
        int border = row - length;
        for (int i = row; i >= border; --i) {
            if ($grid[i][col]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a ship crosses with another ship on the lower side of the grid.
     * @param row the row to be checked
     * @param col the column to be checked
     * @param length the length of the ship (a border value when added to the row)
     * @return true if there are no collisions
     */
    private boolean checkDown(int row, int col, int length) {
        int border = row + length;
        for (int i = row; i <  border; ++i) {
            if ($grid[i][col]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a randomized number from 0 up to a maximum value (not included)
     * @param max a maximum integer value (not included in the range)
     * @return a randomized number between [0,max)
     * @pre the upper border is an integer larger than zero
     * @post an integer has been returned that is situated in the range [0,max)
     */
    private int getRandomNumber(int max) {
        Random random = new Random();

        return random.nextInt(max);
    }


    /**
     * Checks if a shot has hit a boat.
     * @param row the row to be checked
     * @param col the column to be checked
     * @return true if the value on the grid says true
     */
    public boolean checkShot(int row, int col) {
        return $grid[row][col];
    }

    /**
     * Increases the turns that the user has done in the game.
     * @pre the number of turns is a positive integer
     * @post the observers of the model are notified of the change
     */
    public void increaseTurn() {
        $numberOfTurns++;
        BattleShipUpdate info = new BattleShipUpdate($maxTurns-$numberOfTurns + 1);
        setChanged();
        notifyObservers(info);
    }
}
