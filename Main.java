package com.adrianargint;
import java.io.*;
public class Main {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        House myHouse = new House();
        BufferedReader input = new BufferedReader(new FileReader(new File("therm.in")));
        FileWriter output = new FileWriter(new File("therm1.out"));
        output.close();

        //The first n lines were used in constructor of house, so skip them
        for(int i = 0; i <= myHouse.getRoomNumber(); i++) {
            String line = input.readLine();
        }


        String line;
        while((line = input.readLine()) != null ){
            String[] word = line.split(" ");
            switch (word[0]){
                case "OBSERVE":
                    myHouse.addObserve(word[1], Integer.parseInt(word[2]), Double.parseDouble(word[3]));
                    break;

                case "OBSERVEH":
                    myHouse.addObserveH(word[1], Integer.parseInt(word[2]), Double.parseDouble(word[3]));
                    break;

                case "TRIGGER":
                    myHouse.triggerHeat();
                    break;

                case "TEMPERATURE":
                    myHouse.changeTemperature(Double.parseDouble(word[1]));
                    break;

                case "LIST":
                    myHouse.list(word[1], Integer.parseInt(word[2]), Integer.parseInt(word[3]));
                    break;

                default:
                    break;
            }
        }

        input.close();
        output.close();
    }

}
