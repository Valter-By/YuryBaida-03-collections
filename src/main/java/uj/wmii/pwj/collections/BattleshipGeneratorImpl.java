package uj.wmii.pwj.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BattleshipGeneratorImpl implements BattleshipGenerator {

    StringBuilder battleMap;
    Random rnd = new Random();
    int[] shipSizes = new int[] {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

    public BattleshipGeneratorImpl(){
        initBG();
    }

    private void initBG() {
        this.battleMap =  new StringBuilder();
        for (int i = 0; i < 10; i++) {
            battleMap.append("..........");
        }
    }

    @Override
    public String generateMap() {
        for (int k : shipSizes) {
            Set<Cell> ship = calcShip(k);
            coverShip(ship);
        }
        cleanWater();
        return battleMap.toString();
    }

    private int calcStringPosition(int row, int col) {
        return 10 * (row - 1) + col - 1;
    }

    public Set<Cell> calcShip(int nbrDesks) {
        int atp = 0;
        Set<Cell> currentShipDesks = new HashSet<>();
        while (atp < 1000 || currentShipDesks.size() != nbrDesks) {
            Cell startCell = new Cell();
            deleteShip(currentShipDesks);
            currentShipDesks.clear();
            int cnt = 0;  
            while (cnt == 0) {
                startCell = new Cell();
                if (battleMap.charAt(startCell.calcStringPosition()) == '.') {
                    cnt = 1;
                    currentShipDesks.add(startCell);
                    battleMap.setCharAt(startCell.calcStringPosition(), '#');
                }
            }
            Cell currentCell = startCell;

            while (cnt < nbrDesks && currentCell != null) {
                currentCell = addDesk(currentCell);
                if (currentCell != null && !currentShipDesks.contains(currentCell)) {
                    cnt++;
                    currentShipDesks.add(currentCell);
                    battleMap.setCharAt(currentCell.calcStringPosition(), '#');

                }
            }
            atp++;

        }
        return currentShipDesks;
    }

    public Cell addDesk(Cell currentCell) {
        List<Cell> avalableCells = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            int newRow = currentCell.row + dir.deltaRow();
            int newCol = currentCell.col + dir.deltaCol();
            if ((newRow > 0) && (newRow < 11) && (newCol > 0) && (newCol < 11) && (battleMap.charAt(calcStringPosition(newRow, newCol)) == '.')) {
                avalableCells.add(new Cell(newRow, newCol));
            }
        }
        int nbrWays = avalableCells.size();
        return nbrWays == 0 ? null : avalableCells.get(rnd.nextInt(nbrWays));
    }

    public void coverShip(Set<Cell> desks) {
        for (Cell point : desks) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int newRow = point.row + i;
                    int newCol = point.col + j;
                    int stringPos = calcStringPosition(newRow, newCol);
                    if ((newRow > 0) && (newRow < 11) && (newCol > 0) && (newCol < 11) && (battleMap.charAt(stringPos) == '.')) {
                        battleMap.setCharAt(stringPos, '-');;
                    }
                }
            }
        }
    }

    private void cleanWater() {
        for (int i = 0; i < battleMap.length(); i++) {
            if (battleMap.charAt(i) == '-') {
                battleMap.setCharAt(i, '.');
            }
        }
    }

    private void deleteShip(Set<Cell> desks) {
        for (Cell point : desks) {
            battleMap.setCharAt(point.calcStringPosition(), '.');
        }
    }
}
