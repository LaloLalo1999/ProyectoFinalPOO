// App.java
import java.util.List;

public class App {
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData("/home/lalo/IdeaProjects/ProyectoFinalPOO/src/main/resources/500_Person_Gender_Height_Weight_Index.csv");
        List<List<Double>> data = dataLoader.getData();
        List<Double> labels = dataLoader.getLabels();

        // Normalizar los datos aquí si es necesario

        int inputFeatures = data.get(0).size();
        int hiddenNeurons = 10;
        int outputNeurons = 1; // Asumiendo que solo queremos predecir el índice como un valor continuo
        Network network = new Network(inputFeatures, hiddenNeurons, outputNeurons);

        double learningRate = 0.05;
        int epochs = 10000;

        // Entrenar la red
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < data.size(); i++) {
                network.train(data.get(i), List.of(labels.get(i)), learningRate);
            }
        }

        // Evaluar la red y hacer predicciones aquí
        // Ejemplo:
        List<Double> inputs = List.of(180.0, 70.0, 4.0); // Altura, peso, indice de masa corporal
        double prediction = network.feedForward(inputs);
        System.out.println("Predicción: " + prediction);

        // Ejemplo 2:
        List<Double> inputs2 = List.of(160.0, 90.0, 2.0); // Altura, peso, indice de masa corporal
        double prediction2 = network.feedForward(inputs2);
        System.out.println("Predicción: " + prediction2);

        List<Double> inputs3 = List.of(150.0, 100.0, 3.0); // Altura, peso, indice de masa corporal
        double prediction3 = network.feedForward(inputs2);
        System.out.println("Predicción: " + prediction2);
    }
    // Metodo que convierta los valores de altura, peso e indice de masa corporal a valores entre 0 y 1, para que la red neuronal pueda trabajar con ellos

    