// DataLoader.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private final List<List<Integer>> data;
    private final List<Double> labels;

    public DataLoader() {
        //Lista de listas porque en un arreglo se guardan los pesos y las alturas [[190,60],[]]...
        data = new ArrayList<List<Integer>>();
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
//                System.out.println(values[0] + values[1] + values[2]);
                List<Integer> features = new ArrayList<>();
                labels.add("Male".equals(values[0]) ? 0.0 : 1.0); // Género: Male = 0.0, Female = 1.0, es decir cambia los males y females por 0 y 1
                features.add(Integer.parseInt(values[1])); // Altura
                features.add(Integer.parseInt(values[2])); // Peso
                data.add(features);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<Integer>> getData() {
        return data;
    }

    public List<Double> getLabels() {
        return labels;
    }
}
