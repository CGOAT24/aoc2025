import java.util.HashMap;
import java.util.List;

public class Grid {
    private final HashMap<GridItemKey, GridItem> grid;
    private final int maxX;
    private final int maxY;

    public Grid(List<String> raw) {
        this.maxX = raw.getFirst().length();
        this.maxY = raw.size();
        this.grid = new HashMap<>();
        for(int i = 0; i < raw.size(); ++i) {
            for (int j = 0; j < raw.get(i).length(); ++j) {
                GridItemType type = raw.get(i).charAt(j) == '@' ? GridItemType.ROLL_OF_PAPER : GridItemType.EMPTY;
                GridItem item = new GridItem(j, i, type);
                grid.put(item.getKey(), item);
            }
        }
        defineAccessible();
    }

    public int countDirectlyAccessible() {
        long count = this.grid
                .values()
                .stream()
                .filter(i -> i.getType() == GridItemType.ROLL_OF_PAPER && i.isAccessible())
                .count();
        return Math.toIntExact(count);
    }

    public int countAccessibleRecursively() {
        int total = 0;
        int roundCount;

        do {
            roundCount = 0;
            for (GridItem item : this.grid.values()) {
                if (item.getType() == GridItemType.ROLL_OF_PAPER && item.isAccessible()) {
                    ++roundCount;
                    item.setEmpty();
                }
            }
            defineAccessible();
            total += roundCount;
        } while (roundCount != 0);
        return total;
    }

    public void print() {
        for (int i = 0; i < maxY; ++i) {
            for (int j = 0; j < maxX; ++j) {
                GridItem item = this.grid.get(new GridItemKey(j, i));
                String key = "?";
                if (item.isAccessible()) {
                    key = "x";
                } else if (item.getType() == GridItemType.ROLL_OF_PAPER) {
                    key = "@";
                } else if (item.getType() == GridItemType.EMPTY) {
                    key = ".";
                }
                System.out.print(key);
            }
            System.out.println();
        }
    }

    private boolean isAccessible(GridItemKey position, HashMap<GridItemKey, GridItem> grid) {
        int count = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int posX = position.x() + i;
                int posY = position.y() + j;

                if (posX < 0 || posX >= this.maxX || posY < 0 || posY >= this.maxY) {
                    continue;
                }
                if (grid.get(new GridItemKey(posX, posY)).getType() == GridItemType.ROLL_OF_PAPER) {
                    count++;
                }
                if(count >= 4) {
                    return false;
                }
            }
        }
        return true;
    }

    private void defineAccessible() {
        this.grid.replaceAll((key, item) -> {
            if (isAccessible(key, this.grid)) {
                item.setAccessible();
            }
            return item;
        });
    }
}
