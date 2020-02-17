package com.adrianargint;

import java.util.*;

/**
 * Object device which stores data
 */
public class Device{
    private String deviceName;
    private ArrayList<HourlyInterval> list;

    /**
     * @param deviceName each device has a name so it is required to pass a name
     */
    public Device(String deviceName) {
        this.deviceName = deviceName;
        this.list = new ArrayList<>();
        for (int i = 0; i < 24; i++){
            list.add(new HourlyInterval());
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

    public ArrayList<HourlyInterval> getList() {
        return list;
    }

    /**
     * add data to device
     * @param data object of type Data(timestamp, value)
     * @param refferingTime present time
     */
    public void add(Data data, int refferingTime){
        int interval = (refferingTime - data.getTimeStamp()) / 3600;
        ArrayList<Data> list = this.list.get(interval).getList();
        for (Data myData : list){
            if(myData.getValue() == data.getValue()){
                return;
            }
        }
        list.add(data);
    }
}
