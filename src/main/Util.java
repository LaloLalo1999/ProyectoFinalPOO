package main;

import java.util.List;

// Clase Util contiene una colección de funciones útiles para trabajar con redes neuronales.
public class Util {

    // Método sigmoid calcula la función sigmoidal de un número.
    // La función sigmoidal se utiliza frecuentemente en redes neuronales para introducir no linealidad en el modelo.
    // Esta función tiene una forma de 'S' y mapea cualquier número a un valor entre 0 y 1.
    public static double sigmoid(double in){
        return 1 / (1 + Math.exp(-in));
    }

    // Método sigmoidDeriv calcula la derivada de la función sigmoidal en un punto dado.
    // Esta función es útil durante el proceso de retropropagación en una red neuronal, donde se necesitan las derivadas de la función de activación.
    public static double sigmoidDeriv(double in){
        double sigmoid = Util.sigmoid(in);
        return sigmoid * (1 - in);
    }

    // Método meanSquareLoss calcula la pérdida cuadrática media entre dos listas de números.
    // Se asume que las listas 'correctAnswers' y 'predictedAnswers' tienen la misma longitud.
    // Esta función es útil para evaluar el rendimiento de un modelo de aprendizaje automático, comparando las salidas predichas con los valores reales.
    public static Double meanSquareLoss(List<Double> correctAnswers, List<Double> predictedAnswers){
        double sumSquare = 0;
        for (int i = 0; i < correctAnswers.size(); i++){
            double error = correctAnswers.get(i) - predictedAnswers.get(i);
            sumSquare += (error * error);
        }
        return sumSquare / (correctAnswers.size());
    }
}
