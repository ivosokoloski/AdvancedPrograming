import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface WeatherSub {
    void update(float temperature, float humidity, float pressure);

    void display();
}

class CurrentConditionsDisplay implements WeatherSub{

    float temperature;
    float humidity;

    public CurrentConditionsDisplay(WeatherDispatcher dispatcher) {
        dispatcher.register(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature=temperature;
        this.humidity=humidity;
        display();
    }

    @Override
    public void display() {
//        Temperature: 1.0F
//        Humidity: 2.0%
        System.out.printf("Temperature: %.1fF%n", this.temperature);
        System.out.printf("Humidity: %.1f%%%n", this.humidity);
    }
}
class ForecastDisplay implements WeatherSub{
    float previousPressure= 0;
    float currentPressure;

    public ForecastDisplay(WeatherDispatcher dispatcher) {
        dispatcher.register(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        previousPressure=currentPressure;
        currentPressure=pressure;
        display();
    }

    @Override
    public void display() {
        if(currentPressure>previousPressure){
            System.out.println("Forecast: Improving");
        }else if(currentPressure==previousPressure){
            System.out.println("Forecast: Same");
        }else{
            System.out.println("Forecast: Cooler");
        }
    }
}

class WeatherDispatcher{
    List<WeatherSub> subs;

    public WeatherDispatcher() {
        this.subs = new ArrayList<>();
    }

    public void remove(WeatherSub weatherSub) {
        subs.remove(weatherSub);
    }

    public void register(WeatherSub weatherSub) {
        subs.add(weatherSub);
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {

        subs.forEach(s->{
            s.update(temperature,humidity,pressure);
        });
        System.out.println();

    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}