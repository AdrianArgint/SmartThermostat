package com.adrianargint;

import java.util.ArrayList;

/**
 * Class for each 1-hour interval
 */
public class HourlyInterval {
    private ArrayList<Data> list;


    public HourlyInterval() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Data> getList() {
        return list;
    }
}
