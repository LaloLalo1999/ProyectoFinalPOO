package main;

//Clases csv file
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.trainAndPredict();
    }

    public void trainAndPredict() {

        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData("/home/lalo/IdeaProjects/ProyectoFinalPOO/src/main/500_Person_Gender_Height_Weight_Index.csv");
        List<List<Integer>> data = dataLoader.getData();
        List<Double> answers = dataLoader.getLabels();

        Random r = new Random();

        int a = r.nextInt(0, 450);
        List<List<Integer>> sampleData = data.subList(a, a + 50);// 50 random samples
        List<Double> sampleAnswers = answers.subList(a, a + 50);

//        List<List<Integer>> data = new ArrayList<List<Integer>>();
//        data.add(Arrays.asList(115, 66));
//        data.add(Arrays.asList(175, 78));
//        data.add(Arrays.asList(205, 72));
//        data.add(Arrays.asList(120, 67));
//        List<Double> answers = Arrays.asList(1.0, 0.0, 0.0, 1.0);

        Network network1000 = new Network(10000);
        network1000.train(data, answers);

        Network network10000 = new Network(100000);
        network10000.train(data, answers);

        //Mientras mas cerca de 0 es que tiende a ser hombre si es mas cerca de 1
//        System.out.println("");
        System.out.println(String.format("Male, 174, 90: network1000: %.10f | network10000: %.10f", network1000.predict(174,90), network10000.predict(174,90)));
        System.out.println(String.format("Female,169,103: network1000: %.10f | network10000: %.10f", network1000.predict(105, 67), network10000.predict(105, 67)));
//        System.out.println(String.format("Female, 120, 72: network1000: %.10f | network10000: %.10f", network1000.predict(120, 72), network10000.predict(120, 72)));
//        System.out.println(String.format("Female, 143, 67: network1000: %.10f | network10000: %.10f", network1000.predict(143, 67), network10000.predict(120, 72)));
//        System.out.println(String.format("Male, 130, 66: network1000: %.10f | network10000: %.10f", network1000.predict(130, 66), network10000.predict(130, 66)));
        
        //Crear el file
        FileWriter csvFileSaveData = new FileWriter("my_data.csv");
        //
        PrintWriter writer = new PrintWriter(csvFileSaveData);

        //Escribir en el archivo
        writer.print("Prediction,\n");
        writer.print(network1.predict(174,90));
        writer.print(","); 
        writer.println();

        // Cerrar archivo
        writer.close();
    }
}
