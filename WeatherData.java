/**
 * CSCI 4144 – Assignment 4
 * @author: Kareem Elghamriny, B000940361, March 27, 2026
 * @description: Uses Apriori for Association Rule Mining
 **/

import java.util.*;

public class WeatherData {
    private ArrayList<Weather> weatherList;

    public WeatherData() {
        weatherList = new ArrayList<Weather>();
    }

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(ArrayList<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public void add (Weather weather) {
        weatherList.add(weather);
    }


    /**
     * Loops through weather list to get the total occurances of attribute
     */
    public int count(ArrayList<String> weatherLog) {
        int count = 0;

        for (int i = 0; i < weatherList.size(); i++) {
            String[] weatherAttributes = weatherList.get(i).toArray();
            boolean containsAllAttributes = true;

            for (int j = 0; j < weatherLog.size(); j++) {
                boolean found = false;
                for (int a = 0; a < weatherAttributes.length; a++) {
                    if (weatherLog.get(j).equals(weatherAttributes[a])) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    containsAllAttributes = false;
                    break;
                }
            }
            if (containsAllAttributes) {
                count++;
            }
        }
        return count;
    }
}