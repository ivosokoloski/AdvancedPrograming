import java.io.*;
import java.util.HashMap;
import java.util.Map;

// ===== STRATEGY INTERFACE =====
interface IDdv {
    float calculateTax(float amount);
}

// ===== CONCRETE STRATEGIES =====
class DdvA implements IDdv {
    @Override
    public float calculateTax(float amount) {
        return amount * 0.18f;
    }
}

class DdvB implements IDdv {
    @Override
    public float calculateTax(float amount) {
        return amount * 0.05f;
    }
}

class DdvV implements IDdv {
    @Override
    public float calculateTax(float amount) {
        return 0f;
    }
}

// ===== STRATEGY FACTORY =====
class DdvFactory {
    public static IDdv getStrategy(String type) {
        if (type.equals("A")) {
            return new DdvA();
        } else if (type.equals("B")) {
            return new DdvB();
        } else if (type.equals("V")) {
            return new DdvV();
        } else {
            throw new IllegalArgumentException("Unknown DDV type: " + type);
        }
    }
}

// ===== MAIN LOGIC CLASS =====
class MojDDV {

    Map<String, Integer> sum;
    Map<String, Float> tax;

    public MojDDV() {
        sum = new HashMap<>();
        tax = new HashMap<>();
    }

    void readRecords(InputStream inputStream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {

            try {
                String[] parts = line.split("\\s+");
                String id = parts[0];

                int totalSum = 0;
                float totalTax = 0;

                for (int i = 1; i < parts.length; i += 2) {
                    float amount = Float.parseFloat(parts[i]);
                    String type = parts[i + 1];

                    IDdv strategy = DdvFactory.getStrategy(type);

                    totalSum += amount;
                    totalTax += strategy.calculateTax(amount);
                }

                if (totalSum > 30000) {
                    throw new AmountNotAllowedException(
                            "Receipt with amount " + totalSum +
                                    " is not allowed to be scanned"
                    );
                }

                sum.put(id, totalSum);
                tax.put(id, totalTax);

            } catch (AmountNotAllowedException e) {
                // само порака, НЕ прекинува програмата
                System.out.println(e.getMessage());
            }
        }
    }

    void printTaxReturns(OutputStream outputStream) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        for (String id : sum.keySet()) {
            writer.write(id + " " + sum.get(id) + " " + tax.get(id));
            writer.newLine();
        }

        writer.flush();
    }
}

// ===== CUSTOM EXCEPTION =====
class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(String message) {
        super(message);
    }
}

public class MojDDVTest {

    public static void main(String[] args) throws IOException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}