import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Edge{
    int weight;
    String destination;

    public Edge(int weight, String destination){
        this.weight = weight;
        this.destination = destination;
    }
}

public class ImageGraph {

    public ImageGraph(){

    }

//    public void createArray() throws IOException {
//        // Reading the test File
//        String [] images = new String[]{"road", "boulder", "pothole", "explosive", "coyote", "tarred", "gold",
//                "start", "road_runner", "goal"};
//        char[] row;
//        BufferedReader reader = new BufferedReader(new FileReader("./TestInputs/sample_test_input_1.txt"));
//        String[] line = reader.readLine().split(" ");
//        int N = Integer.parseInt(line[0]);
//        int M = Integer.parseInt(line[1]);
//
//        Images[][] imageArray = new Images[N][M];
//
//        for(int i=0; i<N; i++){
//            row = reader.readLine().toCharArray();
//            for (int j=0; j<M; j++){
//                int col = Character.getNumericValue(row[j]);
//                String image = images[col];
//
////                imageArray[i][j] =
//            }
//        }
//    }
}
