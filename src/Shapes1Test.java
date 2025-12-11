import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class IrregularCanvasException extends Exception {
    public IrregularCanvasException(String message) {
        System.out.println(message);
    }
}

class Shapes{
    private String ID ;
    private double total_shapes ;
    private double total_circles ;
    private double total_squares ;
    private double min_area ;
    private double max_area ;
    private double average_area ;

    public Shapes(String ID, double total_shapes, double total_circles, double total_squares, double min_area, double max_area, double average_area) {
        this.ID = ID;
        this.total_shapes = total_shapes;
        this.total_circles = total_circles;
        this.total_squares = total_squares;
        this.min_area = min_area;
        this.max_area = max_area;
        this.average_area = average_area;
    }

    public String getID() {
        return ID;
    }

    public double getTotal_shapes() {
        return total_shapes;
    }

    public double getTotal_circles() {
        return total_circles;
    }

    public double getTotal_squares() {
        return total_squares;
    }

    public double getMin_area() {
        return min_area;
    }

    public double getMax_area() {
        return max_area;
    }

    public double getAverage_area() {
        return average_area;
    }

    @Override
    public String toString() {
        return String.format("%s %.0f %.0f %.0f %.2f %.2f %.2f%n",ID,total_shapes,total_circles,total_squares,max_area,min_area,average_area);
    }
}
class ShapesApplication1{
    ArrayList<Shapes> result;
    double maxArea;
    public ShapesApplication1(double maxArea) {
        result= new ArrayList<>();
        this.maxArea=maxArea;
    }

    public int readCanvases (InputStream inputStream){
        int count=0;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<String> parts= new ArrayList<>(List.of(line.split("\\s+")));
                String ID= parts.get(0) ;
                double total_shapes =0.0;
                double total_circles =0.0;
                double total_squares =0.0;
                double min_area =0.0;
                double max_area =0.0;
                double average_area =0.0;
                for(int i=1;i<parts.size();i+=2){

                    if(parts.get(i).equals("C")){
                        if((Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))*Math.PI)>=maxArea)){
                            System.out.printf("Canvas "+ID+" has a shape with area larger than %.2f%n",maxArea);
                            continue;

                        }else{
                            total_shapes++;
                            total_circles++;
                            average_area+=Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))*Math.PI);
                            if(max_area<(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))*Math.PI))){
                                max_area=(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))*Math.PI));
                            }
                            if(min_area>(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))*Math.PI))){
                                min_area=(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))*Math.PI));
                            }
                        }
                    }else{

                        if((Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1)))>=maxArea)){
                            System.out.printf("Canvas "+ID+" has a shape with area larger than %.2f%n",maxArea);
                            continue;
                        }else{
                            total_shapes++;
                            total_squares++;
                            average_area+=Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1)));
                            if(max_area<(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))))){
                                max_area=(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))));
                            }
                            if(min_area>(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))))){
                                min_area=(Integer.parseInt(parts.get(i+1))*(Integer.parseInt(parts.get(i+1))));
                            }
                        }
                    }
                }
                average_area/=total_shapes;
                result.add(new Shapes(ID,total_shapes,total_circles,total_squares,max_area,min_area,average_area));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public void printLargestCanvasTo (OutputStream outputStream){
        BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream));
        result.stream().sorted(Comparator.comparingDouble(Shapes::getAverage_area).reversed()).forEach(e-> {
            try {
                bufferedWriter.write(e.toString());
                bufferedWriter.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}


public class Shapes1Test {

    public static void main(String[] args) {

        ShapesApplication1 shapesApplication = new ShapesApplication1(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);


    }
}