/**
 * CSCI 4144 – Assignment 4
 * @author: Kareem Elghamriny, B000940361, March 27, 2026
 * @description: Uses Apriori for Association Rule Mining
 **/

public class Weather {

    private String outlook;
    private String temperature;
    private String humidity;
    private String windy;
    private String playTennis;

    public Weather(String outlook, String temperature, String humidity,String windy, String playTennis ) {
        this.outlook = outlook;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windy = windy;
        this.playTennis = playTennis;
    }

    public String getOutlook() {
        return outlook;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindy() {
        return windy;
    }

    public String getPlayTennis() {
        return playTennis;
    }

    public void setOutlook(String outlook) {
        this.outlook = outlook;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setWindy(String windy) {
        this.windy = windy;
    }

    public void setPlayTennis(String playTennis) {
        this.playTennis = playTennis;
    }

    public String[] toArray() {
        return new String[] {
                "Outlook=" + outlook, "Temperature=" + temperature, "Humidity=" + humidity,
                "Windy=" + windy, "PlayTennis=" + playTennis
        };
    }
    public String toString() {
    //   return outlook + " " + temperature + " " +  humidity + " " + windy + " " + playTennis;
        return "Hello!";
    }
}