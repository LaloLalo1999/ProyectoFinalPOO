import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
    private List<Double> weights;
    private Double output;
    private Double delta;

    public Neuron(double output) {
        this.output = output;
        this.weights = new ArrayList<>();
    }

    public Neuron(int num_weights, boolean bias) {
        Random r = new Random();
        this.weights = new ArrayList<>();
        for (int i = 0; i < num_weights; i++) {
            this.weights.add(r.nextDouble());
        }
        if (bias) {
            this.weights.add(1.0);
        }
    }

    public double calculateWeightedSum(List<Double> inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.size(); i++) {
            sum += inputs.get(i) * weights.get(i);
        }
        return sum;
    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public void calculateOutput(List<Double> inputs) {
        double weightedSum = calculateWeightedSum(inputs);
        output = sigmoid(weightedSum);
    }

    public void calculateOutputDelta(double target) {
        double error = target - output;
        this.setDelta(output * (1 - output) * error);
    }

    public void calculateHiddenDelta(double sumWeightedDeltas) {
        this.setDelta(output * (1 - output) * sumWeightedDeltas);
    }

    public void updateWeights(List<Double> inputs, double learningRate) {
        for (int i = 0; i < weights.size(); i++) {
            double newWeight = weights.get(i) + learningRate * inputs.get(i) * delta;
            weights.set(i, newWeight);
        }
    }

    // Getters and setters for weights, output, and delta
    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public Double getOutput() {
        return output;
    }

    public void setOutput(Double output) {
        this.output = output;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }
}