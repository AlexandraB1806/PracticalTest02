package ro.pub.cs.systems.eim.practicaltest02;

// PAS 1.4: Clasa pentru formatul datelor primite de la Server
public class WeatherForecastInformation {
    private final String temperature;
    private final String windSpeed;
    private final String condition;
    private final String pressure;
    private final String humidity;

    // Constructor
    public WeatherForecastInformation(String temperature, String windSpeed, String condition, String pressure, String humidity) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    // Getteri
    public String getTemperature() {
        return temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getCondition() {
        return condition;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    // toString() -> utila cand vreau ALL
    @Override
    public String toString() {
        return "WeatherForecastInformation{" +
                "temperature='" + temperature + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", condition='" + condition + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
