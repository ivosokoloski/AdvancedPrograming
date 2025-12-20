import java.io.*;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> cant= new ArrayList<>();
            TreeMap<String,List<String>> anagrams= new TreeMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                if(!cant.contains(line)){

                    if(!anagrams.containsKey(line)){
                        anagrams.put(line,new ArrayList<>());
                    }

                    String finalLine = line;
                    anagrams.forEach((key, value) -> {
                        boolean isA = true;
                        if(finalLine.length()==key.length()){
                            cant.add(finalLine);
                            for (int i = 0; i < finalLine.length(); i++) {
                                if (key.indexOf(finalLine.charAt(i)) < 0) {
                                    isA = false;
                                }
                            }

                            if(isA){
                                value.add(finalLine);

                            }
                        }

                    });

                }

            }

            anagrams.entrySet().stream().filter(a->a.getValue().size()>=5).forEach(a -> {
                for (String string : a.getValue()) {
                    System.out.print(string);
                    System.out.print(" ");
                }
                System.out.println();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
