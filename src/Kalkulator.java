import java.util.Scanner;
@FunctionalInterface
interface Proveri{
     boolean proveri(float a, float b);
}
public class Kalkulator{
    private float result;

    public Kalkulator() {
        this.result = 0;
    }

    public void dodadi(float num){
        this.result+=num;
    }
    public void odzemi(float num){
        this.result-=num;
    }
    public void pomnozi(float num){
        this.result*=num;
    }
    public void podeli(float num){
        this.result/=num;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }


    public static void main(String[] args) {
        Kalkulator kalkulator= new Kalkulator();
        Scanner in = new Scanner(System.in);
        while(true){

            String input= in.nextLine();
            String znak=input;
            float broj=0;
            if(input.split(" ").length>1){

                 znak=input.split(" ")[0];
                 broj= Float.parseFloat(input.split(" ")[1]);
            }

            if(input.split(" ").length>1||input.split(" ").length<2){

                if(znak.toLowerCase().startsWith("r")||znak.toLowerCase().startsWith("y")){
                    System.out.println(kalkulator.getResult());
                }
                if(znak.toLowerCase().startsWith("n")){
                    break;
                }
                if(znak.equals("+")){
                    kalkulator.dodadi(broj);
                    Proveri p= (a,b)->a==b;
                    

                }
                if(znak.equals("-")){
                    kalkulator.odzemi(broj);
                }
                if(znak.equals("*")){
                    kalkulator.pomnozi(broj);
                }
                if(znak.equals("/")){
                    kalkulator.podeli(broj);
                }
            }else{
                System.out.println("invalid input");
            }
        }
    }


}

