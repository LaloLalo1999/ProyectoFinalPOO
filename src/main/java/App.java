import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        DataLoader dataLoader = new DataLoader("src/main/resources/500_Person_Gender_Height_Weight_Index.csv");
        Network network = new Network(3, 50, 1);
        dataLoader.trainNetwork(network, 0.01, 0.2);
    }
}
