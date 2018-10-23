package com.bilygine.analyzer.analyze.result;

import java.util.*;

public class Result {

    
    /** All columns from each steps */
    private List<ResultColumn> columns = new ArrayList<>();

    /**
     * Add few columns to result
     * @param columns
     */
    public void addColumns(List<ResultColumn> columns) {
        for (ResultColumn column : columns) {
            this.addColumn(column);
        }
    }

    /**
     * Add a column to result
     * @param column
     */
    public void addColumn(ResultColumn column) {
        this.columns.add(column);
    }

    /**
     * @return Gets all columns.
     */
    public List<ResultColumn> getResultColumns() {
        return this.columns;
    }


    /**
     * Temp. method to display results
     */
    public void printResults() {
        int maxSize = this.columns.stream()
                .max(Comparator.comparing(ResultColumn::size))
                .get()
                .size();
        for (int i = 0; i < maxSize; i++)  {
            printLine(i);
        }
    }

    public void printLine(int index) {
        StringJoiner joiner = new StringJoiner("|");
        for (ResultColumn column : this.columns) {
            joiner.add((index >= column.values().size() ? "null" : column.values().get(index)).toString());
        }
        //
        System.out.println(joiner.toString());
    }
}
