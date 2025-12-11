import java.io.*;
import java.text.Format;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Driver{
    private String driverName;
    private LocalTime bestTime;
    public Driver(String driverName, LocalTime bestTime) {
        this.driverName = driverName;
        this.bestTime = bestTime;
    }

    public String getDriverName() {
        return driverName;
    }

    public LocalTime getBestTime() {
        return bestTime;
    }
    private String formatBestTime(LocalTime time) {
        long minutes = time.getMinute();
        long seconds = time.minusMinutes(minutes).getSecond();
        long millis = time.minusMinutes(minutes).minusSeconds(seconds).getNano();

        return String.format("%d:%02d:%03d", minutes, seconds, millis);
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s",this.driverName,formatBestTime(this.bestTime));
    }
}
class F1Race {
    List<Driver> drivers;

    public F1Race() {
        this.drivers = new ArrayList<>();
    }
    public void readResults(InputStream inputStream){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<String> parts= new ArrayList<>(List.of(line.split("\\s+")));
                LocalTime bestTime=LocalTime.MAX;
                for(int i=1;i<parts.size();i++){
                    ArrayList<String> parsetime= new ArrayList<>(List.of(parts.get(i).split(":")));
                    LocalTime time= LocalTime.of(0,Integer.parseInt(parsetime.get(0)),Integer.parseInt(parsetime.get(1)),Integer.parseInt(parsetime.get(2)));
                    if(bestTime.isAfter(time)){
                        bestTime=time;
                    }
                }
                drivers.add(new Driver(parts.get(0),bestTime));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void printSorted(OutputStream outputStream){
        BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream));
        AtomicInteger i= new AtomicInteger();
        i.getAndIncrement();
        drivers.stream().sorted(Comparator.comparing(Driver::getBestTime)).forEach((e)-> {
            try {
                bufferedWriter.write(String.format("%d. ", i.getAndIncrement()));
                bufferedWriter.write(e.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}




public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

