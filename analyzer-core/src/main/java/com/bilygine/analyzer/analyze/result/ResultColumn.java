package com.bilygine.analyzer.analyze.result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ResultColumn<T> {

    private String name;
    private List<T> values = new LinkedList<>();

    /**
     *
     * @param
     */
    public ResultColumn(String name) {
        this.name = name;
    }

    /**
     * @return name of column
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return type of column
     */
    public String getTypeName() {
        return this.getClass().getTypeName();
    }

    /**
     * add value in list
     * @param value
     */
    public void add(T value) {
        this.values.add(value);
    }

    /**
     * add value at index in list
     * @param index
     * @param value
     */
    public void add(int index, T value) {
        this.values.add(index, value);
    }

    /**
     * Gets all values
     * @return values
     */
    public List<T> values() {
        return this.values;
    }

    /**
     * Gets size
     */
    public int size() {
        return this.values.size();
    }
}
