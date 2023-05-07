import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    public static void main(String[] args) {
        String csvFile = "500_Person_Gender_Height_Weight_Index.csv";
        List<List<Double>> inputs = new ArrayList<>();
        List<List<Double>> targets = new ArrayList<>();

        // Calculate the mean and standard deviation for Height, Weight, and Index
        double[] mean = new double[3];
        double[] std = new double[3];
        int count = 0;



        // Read the data from the CSV file
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                List<Double> inputRow = new ArrayList<>();
                List<Double> targetRow = new ArrayList<>();

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
        // ...
    }

}
