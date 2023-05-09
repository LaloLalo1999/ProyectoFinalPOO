package main;

import java.util.Random;

public class Neuron {
    // Un objeto Random es usado para generar números aleatorios.
    Random random = new Random();

    //Valores aleatorios entre -1 y 1

    private Double oldBias = random.nextDouble(-1, 1);
    private Double bias = random.nextDouble(-1, 1);

    private Double oldWeight1 = random.nextDouble(-1, 1);
    private Double weight1 = random.nextDouble(-1, 1);

    private Double oldWeight2 = random.nextDouble(-1, 1);
    private Double weight2 = random.nextDouble(-1, 1);

    // Aquí, se sobrescribe para mostrar los valores actuales y antiguos de bias y pesos.
    public String toString(){
        return String.format("oldBias: %.15f | bias: %.15f | oldWeight1: %.15f | weight1: %.15f | oldWeight2: %.15f | weight2: %.15f", this.oldBias, this.bias, this.oldWeight1, this.weight1, this.oldWeight2, this.weight2);
    }

    // El método mutate modifica aleatoriamente uno de los parámetros (bias o pesos) de la neurona.
    // Se multiplica un factor de cambio (un número aleatorio entre -1 y 1) por el factor de aprendizaje.
    // Si no se proporciona un factor de aprendizaje, el cambio se realiza solo con el número aleatorio.
    public void mutate(Double learnFactor){
        int propertyToChange = random.nextInt(0, 3); // Hacer pruebas con valor aleatorios para comparar con valores del data set.
        Double changeFactor = (learnFactor == null) ? random.nextDouble(-1, 1) : (learnFactor * random.nextDouble(-1, 1));
        if (propertyToChange == 0){
            this.bias += changeFactor;
        } else if (propertyToChange == 1){
            this.weight1 += changeFactor;
        } else {
            this.weight2 += changeFactor;
        }
    }

    // El método forget establece los valores actuales de bias y pesos a sus valores antiguos.
    public void forget(){
        bias = oldBias;
        weight1 = oldWeight1;
        weight2 = oldWeight2;
    }

    // El método remember actualiza los valores antiguos de bias y pesos a sus valores actuales.
    public void remember(){
        oldBias = bias;
        oldWeight1 = weight1;
        oldWeight2 = weight2;
    }

    // El método compute calcula la salida de la neurona.
    // La salida es la suma ponderada de las entradas y el bias, pasada a través de la función sigmoidal.
    public double compute(double input1, double input2){
//      this.input1 = input1;  this.input2 = input2;
        double preActivation = (this.weight1 * input1) + (this.weight2 * input2) + this.bias;
        double output = Util.sigmoid(preActivation);
        return output;
    }
}

