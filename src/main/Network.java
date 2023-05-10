import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
    private int epochs = 0;
    private Double learningRate = null;

    //Esta red neuronal consta de 6 neuronas    
    List<Neuron> neurons = Arrays.asList(
            new Neuron(),/*0*/ new Neuron()/*1*/, 
            new Neuron()/*2*/, new Neuron()/*3*/, new Neuron()/*4*/,
            new Neuron()/*5*/);

    public Network(int epochs){
        setEpochs(epochs);
    }
    public Network(int epochs, Double learningRate) {
        setEpochs(epochs);
        setLearningRate(learningRate);
    }

    //            peso de la persona y la altura de la persona
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
    //[[height, weight]...]
    // Toma una lista de datos de entrada y las respuestas correctas correspondientes.
    public void train(List<List<Integer>> data, List<Double> answers){
        Double bestEpochLoss = null;
        for (int epoch = 0; epoch < getEpochs(); epoch++){
            // adapt neuron
            Neuron epochNeuron = neurons.get(epoch % 6);
            epochNeuron.mutate(getLearningRate());

            List<Double> predictions = new ArrayList<Double>();
            for (int i = 0; i < data.size(); i++){
                predictions.add(i, this.predict(data.get(i).get(0), data.get(i).get(1)));
            }
            Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

            if (epoch % 10 == 0) {
                System.out.println(String.format("Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f", epoch, bestEpochLoss, thisEpochLoss));
            }
            
            // guardar los mejores pesos y bias si la pérdida mejora, de lo contrario revertir a los valores antiguos
            if (bestEpochLoss == null){
                bestEpochLoss = thisEpochLoss;
                epochNeuron.remember();
            } else {
                if (thisEpochLoss < bestEpochLoss){
                    bestEpochLoss = thisEpochLoss;
                    epochNeuron.remember();
                } else {
                    epochNeuron.forget();
                }
            }
        }
    }

    //Setters y getters
    public void setEpochs(int epochs){
        this.epochs = epochs;
    }
    public int getEpochs() {
        return this.epochs;
    }

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }
    public Double getLearningRate(){
        return this.learningRate;
    }

}
