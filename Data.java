package com.adrianargint;

/**
 * Class to store the information about time and temperature/humidity value
 */
public class Data implements Comparable<Data>{
    private final int timeStamp;
    private final double value;

    /**
     * @param timeStamp observation time
     * @param value value of temperature/humidity
     */
    public Data(int timeStamp, double value) {
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    /**
     * compare the temperature/humidity values
     */
    @Override
    public int compareTo(Data data) {
        if(this.value > data.getValue())
            return 1;

        if(this.value == data.getValue())
            return 0;

        return -1;
    }
}
