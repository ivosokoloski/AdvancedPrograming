import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


class Person{
    private int personID;
    private int numNutritive;

    public Person(int personID, int numNutritive) {
        this.personID = personID;
        this.numNutritive = numNutritive;
    }

    public int getPersonID() {
        return personID;
    }

    public int getNumNutritive() {
        return numNutritive;
    }

    @Override
    public String toString() {
        return "Person ID: "+this.personID+" (healthy meals: "+this.numNutritive+")";
    }
}
class HealthyMeals{
    private List<Person> people;

    public HealthyMeals() {
        this.people= new ArrayList<>();
    }

    public void evaluate(InputStream is, OutputStream os){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            line=bufferedReader.readLine();
            List<String> Nutritiveparts= new ArrayList<>(List.of(line.split("\\s+")));
            while ((line=bufferedReader.readLine())!=null){
                List<String> parts= new ArrayList<>(List.of(line.split("\\s+")));
                int numNutritive=0;
                for (String part : parts) {
                    if(Nutritiveparts.contains(part)){
                        numNutritive++;
                    }
                }
                people.add(new Person(Integer.parseInt(parts.get(0)),numNutritive));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        people.stream().sorted(Comparator.comparingInt(Person::getNumNutritive).thenComparing(Comparator.comparingInt(Person::getPersonID))).forEach(e-> {
            try {
                bufferedWriter.write(e.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}

public class HealthyMealstest {
    public static void main(String[] args) {
        HealthyMeals healthyMeals = new HealthyMeals();
        healthyMeals.evaluate(System.in, System.out);
    }
}
