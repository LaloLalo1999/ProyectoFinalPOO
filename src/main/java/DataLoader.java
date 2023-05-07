import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    public static void main(String[] args) {
        String csvFile = "/home/lalo/IdeaProjects/ProyectoFinalPOO/src/main/resources/SOCR-HeightWeight.csv";
        List<List<Double>> inputs = new ArrayList<>();
        List<List<Double>> targets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Double> inputRow = new ArrayList<>();
                List<Double> targetRow = new ArrayList<>();

                String[] values = line.split(",");

                // Preprocess input features and add them to inputRow
                // Preprocess target variable (gender) and add it to targetRow

                inputs.add(inputRow);
                targets.add(targetRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Shuffle and split the data
        Collections.shuffle(inputs);
        int splitIndex = (int) (inputs.size() * 0.8);
        List<List<Double>> trainInputs = inputs.subList(0, splitIndex);
        List<List<Double>> trainTargets = targets.subList(0, splitIndex);
        List<List<Double>> validationInputs = inputs.subList(splitIndex, inputs.size());
        List<List<Double>> validationTargets = targets.subList(splitIndex, targets.size());

        // Initialize the network
        Network network = new Network(trainInputs.get(0).size());

        // Train the network
        for (int i = 0; i < trainInputs.size(); i++) {
            network.train(trainInputs.get(i), trainTargets.get(i), 0.01);
        }

        // Evaluate the network

    }
}