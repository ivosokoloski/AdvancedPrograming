import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

class Task{
    String kategorija;
    String imenazadaca;
    String opis;
    String roknazadaca;
    int prioritet;

    public Task(String kategorija, String imenazadaca, String opis, String  roknazadaca , int prioritet) {
        this.kategorija = kategorija;
        this.imenazadaca = imenazadaca;
        this.opis = opis;
        this.roknazadaca = roknazadaca;
        this.prioritet = prioritet;
    }
    public Task(String kategorija, String imenazadaca, String opis) {
        this.kategorija = kategorija;
        this.imenazadaca = imenazadaca;
        this.opis = opis;
        this.roknazadaca = "";
        this.prioritet = 0;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public void setImenazadaca(String imenazadaca) {
        this.imenazadaca = imenazadaca;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setRoknazadaca(String roknazadaca) {
        this.roknazadaca = roknazadaca;
    }

    public void setPrioritet(int prioritet) {
        this.prioritet = prioritet;
    }

    public String getKategorija() {
        return kategorija;
    }

    public String getImenazadaca() {
        return imenazadaca;
    }

    public String getOpis() {
        return opis;
    }

    public String getRoknazadaca() {
        return roknazadaca;
    }

    public int getPrioritet() {
        return prioritet;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + kategorija + '\'' + ", name='" + imenazadaca + '\'' + ", description='" + opis + '\'' + ", deadline    ='" + roknazadaca + '\'' + ", priority='" + prioritet + '\'' + '}';
    }
}
class TaskManager{
    ArrayList<Task> tasks;
    public TaskManager() {
        this.tasks= new ArrayList<>();
    }
    public void readTasks (InputStream inputStream){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line=bufferedReader.readLine())!=null){
                ArrayList<String> parts= new ArrayList<>(List.of(line.split(",")));
                if(parts.size()==4){
                    Task task=new Task(parts.get(0),parts.get(1),parts.get(2));
                    if((parts.get(3).length()>2)){
                        task.setRoknazadaca(parts.get(3));
                        tasks.add(task);
                    }else{
                        task.setPrioritet(Integer.parseInt(parts.get(3)));
                        tasks.add(task);
                    }
                }else if(parts.size()==3){
                    tasks.add(new Task(parts.get(0),parts.get(1),parts.get(2)));
                }else{
                    tasks.add(new Task(parts.get(0),parts.get(1),parts.get(2),parts.get(3),(Integer.parseInt(parts.get(4)))));
                }

                if(LocalDate.parse(tasks.getLast().roknazadaca).isAfter(LocalDate.parse("2.6.2020"))){
                    tasks.remove(tasks.getLast());
                    throw new IllegalArgumentException();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory){
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        if(includeCategory==true){
            tasks.stream().collect(Collectors.groupingBy(Task::getKategorija, TreeMap::new,Collectors.toList())).forEach((kategorija,lista)->{
                try {
                    bufferedWriter.write(kategorija);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lista.forEach(e-> {
                    try {
                        bufferedWriter.write(e.toString());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            });
        }else{
            tasks.forEach(t-> {
                try {
                    bufferedWriter.write(t.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if(includePriority==true){
            tasks.stream().sorted(Comparator.comparingInt(Task::getPrioritet).reversed().thenComparing())
        }
    }
}


public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
