import java.util.List;

public class Network{
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    public Network(int inputFeatures){
        inputLayer = new Layer(inputFeatures, 0, false);
        hiddenLayer = new Layer(50, inputFeatures, true);
        outputLayer = new Layer(1, 50, true);
    }

    public double feedForward(List<Double> inputs){
        List<Double> inputOutputs = inputLayer.calculateOutputs(inputs);
        List<Double> hiddenOutputs = hiddenLayer.calculateOutputs(inputOutputs);
        List<Double> outputOutputs = outputLayer.calculateOutputs(hiddenOutputs);
        return outputOutputs.get(0);
    }

    public void train(List<Double> inputs, List<Double> targets, double learningRate) {
        // Feedforward
        List<Double> inputOutputs = inputLayer.calculateOutputs(inputs);
        List<Double> hiddenOutputs = hiddenLayer.calculateOutputs(inputOutputs);
        outputLayer.calculateOutputs(hiddenOutputs);

        // Backpropagation
        outputLayer.calculateOutputDeltas(targets);
        hiddenLayer.calculateHiddenDeltas(outputLayer);

        // Update weights
        outputLayer.updateWeights(hiddenOutputs, learningRate);
        hiddenLayer.updateWeights(inputOutputs, learningRate);
    }

}