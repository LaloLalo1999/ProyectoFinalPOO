import java.util.ArrayList;
import java.util.List;

public class Layer {
    private final List<Neuron> neurons; // Lista de neuronas en la capa

    // Constructor que crea una capa con un número dado de neuronas, cada una con un número dado de pesos y un flag para determinar si tienen sesgo (bias)
    public Layer(int numNeurons, int numWeights, boolean bias) {
        neurons = new ArrayList<>();
        for (int i = 0; i < numNeurons; i++) {
            neurons.add(new Neuron(numWeights, bias));
        }
    }

    // Calcula las salidas de todas las neuronas de la capa a partir de las entradas proporcionadas
    public List<Double> calculateOutputs(List<Double> inputs) {
        List<Double> outputs = new ArrayList<>();
        for (Neuron neuron : neurons) {
            neuron.calculateOutput(inputs);
            outputs.add(neuron.getOutput());
        }
        return outputs;
    }

    // Getter para la lista de neuronas en la capa
    public List<Neuron> getNeurons() {
        return neurons;
    }

    // Calcula los deltas de las neuronas de salida de la capa con respecto a los valores objetivo proporcionados
    public void calculateOutputDeltas(List<Double> targets) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).calculateOutputDelta(targets.get(i));
        }
    }

    // Calcula los deltas de las neuronas ocultas de la capa con respecto a la capa de salida proporcionada
    public void calculateHiddenDeltas(Layer outputLayer) {
        for (int i = 0; i < neurons.size(); i++) {
            double sumWeightedDeltas = 0;
            // Calcula la suma de los deltas ponderados de las neuronas conectadas en la capa de salida
            for (Neuron outputNeuron : outputLayer.getNeurons()) {
                sumWeightedDeltas += outputNeuron.getWeights().get(i) * outputNeuron.getDelta();
            }
            neurons.get(i).calculateHiddenDelta(sumWeightedDeltas);
        }
    }

    // Actualiza los pesos de todas las neuronas de la capa utilizando las entradas proporcionadas y la tasa de aprendizaje
    public void updateWeights(List<Double> inputs, double learningRate) {
        for (Neuron neuron : neurons) {
            neuron.updateWeights(inputs, learningRate);
        }
    }
}
