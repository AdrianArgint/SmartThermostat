package com.adrianargint;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Class for a room which has devices for temperature, humidity and has area stored.
 */

public class Room {
    private final String name;
    private final Device deviceTemperature;
    private final Device deviceHumidity;
    private final int area;

    public Room(String name, String deviceName, int area) {
        this.name = name;
        this.deviceTemperature = new Device(deviceName);
        this.deviceHumidity = new Device(deviceName);
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public Device getDeviceTemperature() {
        return deviceTemperature;
    }

    public Device getDeviceHumidity() {
        return deviceHumidity;
    }

    public int getArea() {
        return area;
    }

    /**
     *
     * @return the minimum temperature value stored in the most recent interval
     */
    public double minTemperature(){
        for (int i = 0; i < 24; i++) {
            ArrayList<Data> list = this.deviceTemperature.getList().get(i).getList();
            if (list.size() != 0) {
                Collections.sort(list);
                return list.get(0).getValue();
            }
        }
        return -1;
    }

    /**
     *
     * @return the maximum humidity value stored in the most recent interval
     */
    public double maxHumidity(){
        for (int i = 0; i < 24; i++) {
            ArrayList<Data> list = this.deviceHumidity.getList().get(i).getList();
            if (list.size() != 0) {
                Collections.sort(list);
                return list.get(list.size() - 1).getValue();
            }
        }
        return -1;
    }


}
