import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Movie{
    private String title;
    private String genre;
    private int year;
    private double avgRating;

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
        return String.format("%s, %s, %d, %.2f",title,genre,year,avgRating);
    }
}
class MovieTheater{
    private ArrayList<Movie> movies;

    public MovieTheater() {
        this.movies = new ArrayList<>();
    }

    public void readMovies(InputStream is) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            int n= Integer.parseInt(bufferedReader.readLine());
            for(int i=0;i<n;i++){
                String title=bufferedReader.readLine();
                String genre=bufferedReader.readLine();
                int year= Integer.parseInt(bufferedReader.readLine());
                String[] parts=bufferedReader.readLine().split("\\s+");
                double avgRating=0.0;
                for (String part : parts) {
                    avgRating+=Double.parseDouble(part);
                }
                avgRating/= parts.length;
                movies.add(new Movie(title,genre,year,avgRating));
            }

        }
    }
    public void printByGenreAndTitle(){
        movies.stream().sorted(Comparator.comparing(Movie::getGenre).thenComparing(Comparator.comparing(Movie::getTitle))).forEach(e-> System.out.println(e.toString()));
    }
    public void printByYearAndTitle(){
        movies.stream().sorted(Comparator.comparingInt(Movie::getYear).thenComparing(Comparator.comparing(Movie::getTitle))).forEach(e-> System.out.println(e.toString()));
    }
    public void printByRatingAndTitle(){
        movies.stream().sorted(Comparator.comparingDouble(Movie::getAvgRating).reversed().thenComparing(Comparator.comparing(Movie::getTitle))).forEach(e-> System.out.println(e.toString()));
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