public class GridItem {
    private final GridItemKey position;
    private GridItemType type;
    private boolean isAccessible;

    public GridItem(int x, int y, GridItemType type) {
        this.position = new GridItemKey(x, y);
        this.type = type;
        isAccessible = false;
    }

    public GridItemType getType() {
        return type;
    }

    public boolean isAccessible() {
        return this.isAccessible;
    }

    public GridItemKey getKey() {
        return this.position;
    }

    public void setAccessible() {
        this.isAccessible = true;
    }

    public void setEmpty() {
        this.type = GridItemType.EMPTY;
    }
}
