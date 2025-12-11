import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class ShapesApplication{
    public ShapesApplication() {
    }

    public int readCanvases (InputStream inputStream){
        int count=0;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts;
                parts= line.split("//s+");
                count+= parts.length-1;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}


public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);

    }
}