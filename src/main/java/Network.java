import java.util.List;

public class Network {
    private final Layer inputLayer; // Capa de entrada de la red
    private final Layer hiddenLayer; // Capa oculta de la red
    private final Layer outputLayer; // Capa de salida de la red

    // Constructor que crea una red con una cantidad dada de características de entrada, neuronas ocultas y neuronas de salida
    public Network(int inputFeatures, int hiddenNeurons, int outputNeurons) {
        inputLayer = new Layer(inputFeatures, 0, false);
        hiddenLayer = new Layer(hiddenNeurons, inputFeatures, true);
        outputLayer = new Layer(outputNeurons, hiddenNeurons, true);
    }

    // Realiza la propagación hacia adelante (feedforward) a través de la red y devuelve el valor de salida de la red
    public double feedForward(List<Double> inputs) {
        List<Double> inputOutputs = inputLayer.calculateOutputs(inputs);
        List<Double> hiddenOutputs = hiddenLayer.calculateOutputs(inputOutputs);
        List<Double> outputOutputs = outputLayer.calculateOutputs(hiddenOutputs);
        return outputOutputs.get(0);
    }

    // Entrena la red utilizando un conjunto de entradas, valores objetivo y una tasa de aprendizaje
    public void train(List<Double> inputs, List<Double> targets, double learningRate) {
        // Feedforward
        List<Double> inputOutputs = inputLayer.calculateOutputs(inputs);
        List<Double> hiddenOutputs = hiddenLayer.calculateOutputs(inputOutputs);
        outputLayer.calculateOutputs(hiddenOutputs);

        // Backpropagation (propagación hacia atrás)
        outputLayer.calculateOutputDeltas(targets);
        hiddenLayer.calculateHiddenDeltas(outputLayer);

        // Actualización de los pesos
        outputLayer.updateWeights(hiddenOutputs, learningRate);
        hiddenLayer.updateWeights(inputOutputs, learningRate);
    }
}
