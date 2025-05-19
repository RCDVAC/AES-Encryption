package model;

import java.util.Arrays;

public class State {
    public State() {
        table = new byte[rows][columns];
    }

    public State(byte[][] table) {
        if (table == null) {
            this.table = null;
        } else {
            this.table = Arrays.stream(table)
                    .map(row -> row == null ? null : Arrays.copyOf(row, row.length))
                    .toArray(byte[][]::new);
        }
    }

    private final byte[][] table;

    private final int rows = 4;

    private final int columns = 4;

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
