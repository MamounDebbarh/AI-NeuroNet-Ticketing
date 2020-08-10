import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.csv.CSVFormat;
import org.encog.util.simple.EncogUtility;
import org.encog.util.simple.TrainingSetUtil;

import java.io.IOException;

public class Tester {

    public int testBprop(){
        //testing Bprop
        Network BProp = new Network();
        System.out.println("\tCreating Bprop network.");
        Encoder encoder = new Encoder();
        try {
            encoder.encod();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final MLDataSet trainingSet = TrainingSetUtil.loadCSVTOMemory(
                CSVFormat.ENGLISH, "encoded.csv", true, 9, 1);
        final BasicNetwork network = EncogUtility.simpleFeedForward(9, 10,
                0, 1, true);

        System.out.println();
        System.out.println("\tTraining Bprop Network");
        final Backpropagation train = new Backpropagation(network, trainingSet);
        train.fixFlatSpot(false);

        int epoch = 1;
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (train.getError() > 0.001);
        train.finishTraining();

        return epoch;
    }

    public int testRprop(){
        //testing Rprop
        System.out.println("\tCreating Rprop network.");
        Encoder encoder = new Encoder();
        try {
            encoder.encod();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final MLDataSet trainingSet = TrainingSetUtil.loadCSVTOMemory(
                CSVFormat.ENGLISH, "encoded.csv", true, 9, 1);
        final BasicNetwork network = EncogUtility.simpleFeedForward(9, 10,
                0, 1, true);

        System.out.println();
        System.out.println("\tTraining Rprop Network");
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 1;
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (train.getError() > 0.001);
        train.finishTraining();

        return epoch;
    }

    public void testing(){
        long startTime = System.nanoTime();
        int bprop = testBprop();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        long startTime2 = System.nanoTime();
        int rprop = testRprop();
        long endTime2 = System.nanoTime();

        long duration2 = (endTime2 - startTime2);

        System.out.println("epoch for Back Propagation: "+ bprop);
        System.out.println("time taken for Back Propagation: "+ duration);
        System.out.println("epoch for Resilient Propagation: "+ rprop);
        System.out.println("time taken for Resilient Propagation: "+ duration2);
        System.exit(0);

    }
}
