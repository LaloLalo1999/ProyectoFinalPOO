// DataLoader.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private final List<List<Double>> data;
    private final List<Double> labels;

    public DataLoader() {
        data = new ArrayList<>();
        labels = new ArrayList<>();
    }

    public void loadData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (line == null) {
                    break;
                }
                String[] values = line.trim().split("\\s*,\\s*");
//                System.out.println(values[0] + values[1] + values[2] + values[3]);
                List<Double> features = new ArrayList<>();
                labels.add("Male".equals(values[0]) ? 1.0 : 0.0); // Género: Male = 1.0, Female = 0.0, es decir cambia los males y females por 1 y 0
                features.add(Double.valueOf(values[1])); // Altura
                features.add(Double.valueOf(values[2])); // Peso
                features.add(Double.valueOf(values[3])); // Índice de masa corporal
                data.add(features);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<Double>> getData() {
        return data;
    }

    public List<Double> getLabels() {
        return labels;
    }
}
