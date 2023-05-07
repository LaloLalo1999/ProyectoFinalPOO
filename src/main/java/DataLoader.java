import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    private static final String CSV_FILE = "src/main/resources/500_Person_Gender_Height_Weight_Index.csv";
    private static final double TRAIN_RATIO = 0.8;
    private static final int EPOCHS = 100;
    private static final double LEARNING_RATE = 0.01;

    private List<List<Double>> inputs;
    private List<List<Double>> targets;

    public DataLoader() {
        inputs = new ArrayList<>();
        targets = new ArrayList<>();
    }

    public void loadData() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            List<String[]> lines = reader.readAll();

            for (String[] line : lines) {
                double edad = Double.parseDouble(line[0]);
                double altura = Double.parseDouble(line[1]);
                double peso = Double.parseDouble(line[2]);
                double bmi = Double.parseDouble(line[3]);
                int genero = Integer.parseInt(line[4]); // 1 para hombre, 0 para mujer

                List<Double> inputRow = List.of(edad, altura, peso, bmi);
                List<Double> targetRow = List.of((double) genero);

                inputs.add(inputRow);
                targets.add(targetRow);
            }
        }

        shuffleData();
        splitAndTrain();
    }

    private void shuffleData() {
        int size = inputs.size();
        List<Integer> indexList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indexList.add(i);
        }

        Collections.shuffle(indexList);

        List<List<Double>> shuffledInputs = new ArrayList<>(size);
        List<List<Double>> shuffledTargets = new ArrayList<>(size);

        for (int i : indexList) {
            shuffledInputs.add(inputs.get(i));
            shuffledTargets.add(targets.get(i));
        }

        inputs = shuffledInputs;
        targets = shuffledTargets;
    }

    private void splitAndTrain() {
        int splitIndex = (int) (inputs.size() * TRAIN_RATIO);

        List<List<Double>> trainInputs = inputs.subList(0, splitIndex);
        List<List<Double>> trainTargets = targets.subList(0, splitIndex);

        List<List<Double>> testInputs = inputs.subList(splitIndex, inputs.size());
        List<List<Double>> testTargets = targets.subList(splitIndex, targets.size());

        // Inicializar la red neuronal con 4 características de entrada, 8 neuronas ocultas y 1 neurona de salida
        Network network = new Network(4, 8, 1);

        // Entrenar la red neuronal
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            for (int i = 0; i < trainInputs.size(); i++) {
                network.train(trainInputs.get(i), trainTargets.get(i), LEARNING_RATE);
            }
        }
        // Evaluar la red neuronal
        int correctPredictions = 0;
        for (int i = 0; i < testInputs.size(); i++) {
            List<Double> input = testInputs.get(i);
            double predictedOutput = network.feedForward(input);
            double actualOutput = testTargets.get(i).get(0);

            if (Math.round(predictedOutput) == actualOutput) {
                correctPredictions++;
            }
        }

        double accuracy = 100.0 * correctPredictions / testInputs.size();
        System.out.printf("Accuracy: %.2f%%\n", accuracy);
    }

    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();

        try {
            dataLoader.loadData();
        } catch (IOException | CsvException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }
}


