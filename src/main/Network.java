package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// La clase Network representa una red neuronal simple que consiste en varias neuronas.
public class Network {

    int epochs = 0;
    Double learnFactor = null;

    // Lista de neuronas en la red. Esta red consta de seis neuronas.
    List<Neuron> neurons = Arrays.asList(
            new Neuron(),/*0*/ new Neuron(),/*1*/ new Neuron(),/*2*/ // capas de entrada
            new Neuron(),/*3*/ new Neuron(),/*4*/ // capa oculta
            new Neuron()/*5*/); //capa de salida

    // Constructor Network inicializa la red con un número específico de epochs.
    public Network(int epochs) {
        this.epochs = epochs;
    }

    // Sobrecarga del constructor Network que también permite definir un factor de aprendizaje.
    public Network(int epochs, Double learnFactor) {
        this.epochs = epochs;
        this.learnFactor = learnFactor;
    }

    // Método predict toma dos entradas y realiza una pasada hacia adelante a través de la red,
    // devolviendo la salida de la última neurona.
    //              peso de la personas y la altura de la persona
    public Double predict(Integer input1, Integer input2) {
        return neurons.get(5).compute(
                neurons.get(4).compute(
                        neurons.get(2).compute(input1, input2),
                        neurons.get(1).compute(input1, input2)
                ),
                neurons.get(3).compute(
                        neurons.get(1).compute(input1, input2),
                        neurons.get(0).compute(input1, input2)
                )
        );
    }

    // El método train entrena la red neuronal utilizando el algoritmo de retropropagación.
    // Toma una lista de datos de entrada y las respuestas correctas correspondientes.
    // Durante cada época, la red ajusta los pesos y el bias de las neuronas para minimizar la pérdida cuadrática media.
    public void train(List<List<Integer>> data, List<Double> answers) {
        Double bestEpochLoss = null;
        for (int epoch = 0; epoch < epochs; epoch++) {
            // adaptar la neurona
            Neuron epochNeuron = neurons.get(epoch % 6);
            epochNeuron.mutate(this.learnFactor);

            // realizar predicciones para todos los datos de entrada
            List<Double> predictions = new ArrayList<Double>();
            for (int i = 0; i < data.size(); i++) {
                predictions.add(i, this.predict(data.get(i).get(0), data.get(i).get(1)));
            }

            // calcular la pérdida para esta época
            Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

            // imprimir la pérdida para cada 10 epoch
            if (epoch % 10 == 0)
                System.out.println(String.format("Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f", epoch, bestEpochLoss, thisEpochLoss));

            // guardar los mejores pesos y bias si la pérdida mejora, de lo contrario revertir a los valores antiguos
            if (bestEpochLoss == null) {
                bestEpochLoss = thisEpochLoss;
                epochNeuron.remember();
            } else {
                if (thisEpochLoss < bestEpochLoss) {
                    bestEpochLoss = thisEpochLoss;
                    epochNeuron.remember();
                } else {
                    epochNeuron.forget();
                }
            }
        }
    }
}
