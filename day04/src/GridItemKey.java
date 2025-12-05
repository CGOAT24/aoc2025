public record GridItemKey(int x, int y) {
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GridItemKey(int x1, int y1))) {
            return false;
        }
        return this.x == x1 && this.y == y1;
    }
}
