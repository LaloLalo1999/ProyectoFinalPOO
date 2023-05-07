import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neuron> neurons;
    public Layer(int numNeurons, int numWeights, boolean bias) {
        neurons = new ArrayList<>();
        for (int i = 0; i < numNeurons; i++) {
            neurons.add(new Neuron(numWeights, bias));
        }
    }
    public List<Double> calculateOutputs(List<Double> inputs) {
        List<Double> outputs = new ArrayList<>();
        for (Neuron neuron : neurons) {
            neuron.calculateOutput(inputs);
            outputs.add(neuron.getOutput());
        }
        return outputs;
    }
    public List<Neuron> getNeurons() {
        return neurons;
    }
    public void calculateOutputDeltas(List<Double> targets) {
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).calculateOutputDelta(targets.get(i));
        }
    }
    public void calculateHiddenDeltas(Layer outputLayer) {
        for (int i = 0; i < neurons.size(); i++) {
            double sumWeightedDeltas = 0;
            for (Neuron outputNeuron : outputLayer.getNeurons()) {
                sumWeightedDeltas += outputNeuron.getWeights().get(i) * outputNeuron.getDelta();
            }
            neurons.get(i).calculateHiddenDelta(sumWeightedDeltas);
        }
    }
    public void updateWeights(List<Double> inputs, double learningRate) {
        for (Neuron neuron : neurons) {
            neuron.updateWeights(inputs, learningRate);
        }
    }
}