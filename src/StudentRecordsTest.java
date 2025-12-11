import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


class SporedNasoka implements Comparator<Students>{

    @Override
    public int compare(Students o1, Students o2) {
        return o1.getNasoka().compareTo(o2.getNasoka());
    }

}
class SporedProsek implements Comparator<Students>{

    @Override
    public int compare(Students o1, Students o2) {
        int prosek= Double.compare(o1.getAvg(),o2.getAvg());
        if(prosek!=0){
            return prosek;
        }else{

            return o1.getKod().compareTo(o2.getKod());
        }
    }

}

class Students{
    private String kod;
    private String nasoka;
    private Double avg;
    private int brojDesetki;

    public Students(String kod, String nasoka, Double avg, int brojDesetki) {
        this.kod = kod;
        this.nasoka = nasoka;
        this.avg = avg;
        this.brojDesetki = brojDesetki;
    }

    public String getKod() {
        return kod;
    }

    public String getNasoka() {
        return nasoka;
    }

    public Double getAvg() {
        return avg;
    }

    public int getBrojDesetki() {
        return brojDesetki;
    }

    @Override
    public String toString() {
       return String.format("%s %.2f", kod, avg);
    }
}
class StudentRecords{
    ArrayList<Students> zapisi;
    public StudentRecords() {
        this.zapisi= new ArrayList<>();
    }
    public int readRecords(InputStream inputStream){
        int num=0;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line= bufferedReader.readLine())!=null){
                num++;
                ArrayList<String> parts= new ArrayList<>(List.of(line.split("\\s+")));
                double avg=0;
                int brojDesetki=0;
                for(int i=2;i<parts.size();i++){
                    Double number= Double.parseDouble(parts.get(i));
                    if(number==10){
                        brojDesetki++;
                    }
                    avg+=number;
                }
                avg/=parts.size()-2;
                zapisi.add(new Students(parts.get(0),parts.get(1),avg,brojDesetki));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return num;
    }
    public void writeTable(OutputStream outputStream){
        BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream));
        zapisi.stream().collect(Collectors.groupingBy(Students::getNasoka,TreeMap::new,Collectors.toList())).forEach((nasoka,list)->{
            try {
                bufferedWriter.write(nasoka);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            list.stream().sorted(Comparator.comparing(Students::getAvg).reversed().thenComparing(Students::getKod)).forEach(s->{
                try {
                    bufferedWriter.write(s.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });

    }
    public void writeDistribution(OutputStream outputStream){
        BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream));

    }
}
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
//        studentRecords.writeDistribution(System.out);
    }
}

// your code here