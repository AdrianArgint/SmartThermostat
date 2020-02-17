package com.adrianargint;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to create a House object
 */
public class House {
    private int roomNumber;
    private double globalTemperature;
    private double globalHumidity;
    private int refferingTime;
    private ArrayList<Room> rooms;

    /**
     * reads from "therm.in" the details about the house
     */
    public House() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(new File("therm.in")));
        String line;
        line = input.readLine();
        String[] word = line.split(" ");
        this.roomNumber = Integer.parseInt(word[0]);
        this.globalTemperature = Double.parseDouble(word[1]);
        if(word.length == 3) {
            this.globalHumidity = 0;
            this.refferingTime = Integer.parseInt(word[2]);
        }
        else{
            this.globalHumidity = Double.parseDouble(word[2]);
            this.refferingTime = Integer.parseInt(word[3]);
        }

        this.rooms = new ArrayList<>();
        input.close();
        setRooms();
    }

    private void setRooms() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(new File("therm.in")));
        String line = input.readLine(); // skips the first line
        for(int i = 0; i < this.roomNumber; i++){
            line = input.readLine();
            String[] words = line.split(" ");
            this.rooms.add(new Room(words[0], words[1], Integer.parseInt(words[2])));
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     *adds to device the "Data" object given as parameters
     * for temperature
     */
    public void addObserve(String deviceName, int time, double temperature) {
        if(time < (this.refferingTime - 25 * 60 * 60) || time > this.refferingTime)
        {
            return;
        }

        for (Room r : this.rooms){
            if(r.getDeviceTemperature().getDeviceName().equals(deviceName)){
                Device device = r.getDeviceTemperature();
                device.add(new Data(time, temperature), this.refferingTime);
            }
        }
    }

    /**
     *adds to device the "Data" object given as parameters
     * for humidity
     */
    public void addObserveH(String deviceName, int time, double temperature) {
        if(time < (this.refferingTime - 25 * 60 * 60) || time > this.refferingTime)
        {
//            System.out.println("Bad time!");
            return;
        }

        for (Room r : this.rooms){
            if(r.getDeviceHumidity().getDeviceName().equals(deviceName)){
                Device device = r.getDeviceHumidity();
                device.add(new Data(time, temperature), this.refferingTime);
            }
        }
    }

    /**
     *checks if it need to trigger heat
     */
    public void triggerHeat() throws IOException {
        FileWriter output = new FileWriter(new File("therm.out"), true);
        double areaSum = 0.00;
        if(this.globalHumidity != 0) {
            double humiditySum = 0;
            for (int i = 0; i < this.roomNumber; i++) {
                double max = this.rooms.get(i).maxHumidity();
                if (max != 0) {
                    humiditySum += max * this.rooms.get(i).getArea();
                    areaSum += this.rooms.get(i).getArea();
                }
            }
            if (humiditySum / areaSum > this.globalHumidity){
                output.write("NO\n");
                output.close();
                return;
            }
        }

        double tempSum = 0.00;
        for (int i = 0; i < this.roomNumber; i++) {
            double min = this.rooms.get(i).minTemperature();
            if(min != 0){
                tempSum += min * this.rooms.get(i).getArea();
                areaSum += this.rooms.get(i).getArea();
            }
        }
        if(this.globalTemperature > tempSum / areaSum){
            output.write("YES\n");
        }
        else{
            output.write("NO\n");
        }
        output.close();
    }

    public void changeTemperature(double temperature){
        this.globalTemperature = temperature;
    }

    /**
     * lists the temperatures of the room, whose name is given as parameter, sorted by intervals
     */
    public void list(String roomName, int inferiorLimit, int superiorLimit) throws IOException {
        FileWriter output = new FileWriter(new File("therm.out"), true);
        output.write(roomName);

        int noIntervals = (superiorLimit - inferiorLimit) / 3600;
        int superiorIntervalLimit = (this.refferingTime - superiorLimit) / 3600;
        Room myRoom = null;
        for(int i = 0; i < this.roomNumber; i++) {
            if (this.rooms.get(i).getName().equals(roomName)) {
                myRoom = this.rooms.get(i);
                break;
            }
        }

        if(myRoom == null){
            System.out.println("Room " + roomName + " not found!");
            output.close();
            return;
        }

        for(int i = 0; i < noIntervals; i++){

            ArrayList<Data> interval = myRoom.getDeviceTemperature().getList().get(superiorIntervalLimit + i).getList();
            Collections.sort(interval);
            for(Data data : interval)
                output.write(String.format(" %.2f", data.getValue()));
        }

        output.write("\n");
        output.close();

    }
}
