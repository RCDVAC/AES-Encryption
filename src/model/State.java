package model;

public class State {

    private final byte[][] table;

    private final int rows = 4;

    private final int columns;

    public State(int size) {
        this.columns = size / 32;
        table = new byte[rows][columns];
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public byte[][] getTable() {
        return table;
    }

    public byte getValue(int row, int column) {
        return table[row][column];
    }

    public void setValue(int row, int column, byte value) {
        table[row][column] = value;
    }

}
