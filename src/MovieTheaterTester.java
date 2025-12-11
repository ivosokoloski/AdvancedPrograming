import java.io.*;
import java.util.*;


class ByGenre implements Comparator<Movie>{

    public int compare(Movie first, Movie other) {
        return first.getGenre().compareTo(other.getGenre());
    }
}
class ByTitle implements Comparator<Movie>{

    public int compare(Movie first, Movie other) {
        return first.getTitle().compareTo(other.getTitle());
    }
}
class ByYear implements Comparator<Movie>{

    public int compare(Movie first, Movie other) {
        return Integer.compare(first.getYear(),other.getYear());
    }
}
class ByRating implements Comparator<Movie>{

    public int compare(Movie first, Movie other) {
        return Double.compare(first.getAvgRating(),other.getAvgRating());
    }
}
class Movie{
    String title;
    String genre;
    int year;
    double avgRating;

    public Movie(String title, String genre, int year, double avgRating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        return "%s, %s, %d, %s".formatted(this.title, this.genre, this.year, this.avgRating);
    }
}
class MovieTheater{
    ArrayList<Movie> movies;
    public void readMovies(InputStream is) throws IOException {
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is));
        String line;
        List<String> parts= new ArrayList<>();
        int n;
        try {
           n= Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int i=0;
        while (true) {
            try {
                if(i<=3){
                    if ((line=bufferedReader.readLine())!=null){
                        parts.add(line);
                        i++;
                    };
                    if(i==3){
                        movies.add(new Movie(parts.get(0), parts.get(1),Integer.parseInt(parts.get(2)),Double.parseDouble(parts.get(3)) ));
                    }
                }else{
                    i=0;
                    parts=new ArrayList<>();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void printByGenreAndTitle(){
        movies.stream().sorted(new ByGenre()).sorted(new ByTitle()).forEach(Movie::toString);
    }
    public void printByYearAndTitle(){
        movies.stream().sorted(new ByYear()).sorted(new ByTitle()).forEach(Movie::toString);
    }
    public void printByRatingAndTitle(){
        movies.stream().sorted(new ByRating()).sorted(new ByTitle()).forEach(Movie::toString);
    }

}


public class MovieTheaterTester {
    public static void main(String[] args) {
        MovieTheater mt = new MovieTheater();
        try {
            mt.readMovies(System.in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("SORTING BY RATING");
        mt.printByRatingAndTitle();
        System.out.println("\nSORTING BY GENRE");
        mt.printByGenreAndTitle();
        System.out.println("\nSORTING BY YEAR");
        mt.printByYearAndTitle();
    }
}