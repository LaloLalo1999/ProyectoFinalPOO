import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
    private List<Double> weights; // Lista de pesos sinápticos de la neurona
    private Double output; // Salida de la neurona
    private Double delta; // Delta utilizado para calcular el error y actualizar los pesos

    // Constructor que crea una neurona con una salida dada
    public Neuron(double output) {
        this.output = output;
        this.weights = new ArrayList<>();
    }

    // Constructor que crea una neurona con un número dado de pesos y un flag para determinar si tiene sesgo (bias)
    public Neuron(int num_weights, boolean bias) {
        Random r = new Random();
        this.weights = new ArrayList<>();
        if (bias) {
            num_weights--;
            this.weights.add(1.0); // Inicializa el sesgo, (es valor que se le agrega al producto de las entradas y los pesos para desplazar la función de activación)
        }
        for (int i = 0; i < num_weights; i++) {
            this.weights.add(r.nextDouble()); // Inicializa los pesos con valores aleatorios
        }
    }

    // Calcula la suma ponderada de las entradas y los pesos de la neurona
    public double calculateWeightedSum(List<Double> inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.size(); i++) {
            sum += inputs.get(i) * weights.get(i);
        }
        return sum;
    }

    // Función de activación sigmoide
    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    // Calcula la salida de la neurona aplicando la función sigmoid a la suma ponderada de las entradas y los pesos
    public void calculateOutput(List<Double> inputs) {
        double weightedSum = calculateWeightedSum(inputs);
        output = sigmoid(weightedSum);

    }

    // Calcula el delta de la neurona de salida con respecto al valor objetivo
    public void calculateOutputDelta(double target) {
        double error = target - output;
        this.setDelta(output * (1 - output) * error);
    }

    // Calcula el delta de la neurona oculta con respecto a la suma de los deltas ponderados de las neuronas conectadas
    public void calculateHiddenDelta(double sumWeightedDeltas) {
        this.setDelta(output * (1 - output) * sumWeightedDeltas);
    }

    // Actualiza los pesos de la neurona utilizando el delta, las entradas y la tasa de aprendizaje
    public void updateWeights(List<Double> inputs, double learningRate) {
        for (int i = 0; i < weights.size(); i++) {
            double newWeight = weights.get(i) + learningRate * inputs.get(i) * delta;
            weights.set(i, newWeight);
        }
    }

    // Métodos getter y setter para pesos, salida y delta
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
