import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class App {
public static void main(String[] args) throws Exception {
        Network network = new Network();
        Double prediction = network.predict(115, 166);
        System.out.println(prediction);
    }
}

class Neuron{
    Random random = new Random();
    private Double bias = random.nextDouble(-1,1);
    public Double weight1 = random.nextDouble(-1,1);
    private Double weight2 = random.nextDouble(-1,1);

    public Double compute(double input1, double input2){
        double preActivation = (this.weight1 * input1) + (this.weight2 * input2) + this.bias;
        double output = Util.sigmoid(preActivation);
        return output;
    }
}

class Network {
    List<Neuron> neurons = Arrays.asList(
      new Neuron(), new Neuron(), new Neuron(),     // input nodes
      new Neuron(), new Neuron(),                   // hidden nodes
      new Neuron()                                   // output node
    );
    public Double predict(Integer input1, Integer input2){
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
}