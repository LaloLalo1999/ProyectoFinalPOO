// Importa las clases necesarias para leer archivos CSV y manejar excepciones
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    // Declara dos listas para almacenar los datos de entrada y salida
    private final List<List<Double>> inputData;
    private final List<Double> outputData;

    // Constructor que recibe la ruta del archivo CSV
    public DataLoader(String filePath) throws IOException {
        // Inicializa las listas de datos de entrada y salida
        inputData = new ArrayList<>();
        outputData = new ArrayList<>();

        // Crea un FileReader para leer el archivo CSV y un CSVReader para procesar los datos
        // El CSVReaderBuilder permite configurar opciones, en este caso, se omite la primera línea del archivo
        try (FileReader fileReader = new FileReader(filePath);
             CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build()) {

            // Lee cada línea del archivo CSV
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                // Convierte el género a un valor numérico (1.0 para "Male" y 0.0 para cualquier otro valor)
                double gender = "Male".equals(line[0]) ? 1.0 : 0.0;
                // Convierte las columnas de altura, peso e IMC a valores numéricos
                double height = Double.parseDouble(line[1]);
                double weight = Double.parseDouble(line[2]);
                double index = Double.parseDouble(line[3]);

                // Añade el género a la lista de datos de salida
                outputData.add(gender);
                // Crea una lista con los valores de altura, peso e IMC, y añade 1.0 al final
                List<Double> inputValues = new ArrayList<>(Arrays.asList(height, weight, index));

                // Para que se usa inputValues?  No se usa en el codigo

            }
        } catch (CsvValidationException e) {
            // Si hay un error al validar el archivo CSV, lanza una excepción en tiempo de ejecución
            throw new RuntimeException(e);
        }
    }

    // Método para entrenar y evaluar una red neuronal con los datos cargados
    public void trainNetwork(Network network, double learningRate, double testRatio) {
        // Mezcla y divide los datos en conjuntos de entrenamiento y prueba
        int dataSize = inputData.size();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < dataSize; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        int testSize = (int) (dataSize * testRatio);
        int trainSize = dataSize - testSize;

        // Entrena la red neuronal con los datos de entrenamiento
        for (int i = 0; i < trainSize; i++) {
            int index = indices.get(i);
            network.train(inputData.get(index), List.of(outputData.get(index)), learningRate);
        }

        // Evalúa la red neuronal con los datos de prueba
        int correct = 0;
        for (int i = trainSize; i < dataSize; i++) {
            int index = indices.get(i);
            double prediction = network.feedForward(inputData.get(index));
            double actual = outputData.get(index);
            // Si la predicción coincide con el valor real, incrementa el contador de aciertos
            if ((prediction >= 0.5 && actual == 1.0) || (prediction < 0.5 && actual == 0.0)) {
                correct++;
            }
        }

        // Calcula y muestra la precisión del modelo
        double accuracy = (double) correct / testSize;
        System.out.println("Accuracy: " + accuracy * 100 + "%");
    }
}
