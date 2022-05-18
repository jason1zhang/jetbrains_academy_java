public enum CellState {
    ALIVE(1),
    DEAD(0);

    final int value;

    private CellState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
