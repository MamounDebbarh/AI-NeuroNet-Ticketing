import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.util.csv.CSVFormat;
import org.encog.util.simple.EncogUtility;
import org.encog.util.simple.TrainingSetUtil;
import java.io.File;
import java.io.IOException;

import static org.encog.persist.EncogDirectoryPersistence.saveObject;

public class Network {
    //train network
    public void train(BasicNetwork network, MLDataSet trainingSet) {
        final Backpropagation train = new Backpropagation(network, trainingSet);
        train.fixFlatSpot(false);
//        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);


        int epoch = 1;
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (train.getError() > 0.001);
        train.finishTraining();
    }

    //evaluate network
    public void evaluate(BasicNetwork network, MLDataSet trainingSet) {
        System.out.println();
        System.out.println("Evaluating Network");
        System.out.println("Neural Network Results:");
        for (MLDataPair pair : trainingSet) {
            final MLData output = network.compute(pair.getInput());
            System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + "," + pair.getInput().getData(2) +
                    "," + pair.getInput().getData(3) + "," + pair.getInput().getData(4) + "," + pair.getInput().getData(5) + "," +
                    pair.getInput().getData(6) + "," + pair.getInput().getData(7) + "," + pair.getInput().getData(8) + "   "
                    + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
        }
    }

    //Create network
    public void create() {
        System.out.println("\tCreating network.");
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
        System.out.println("\tTraining Network");
        train(network, trainingSet);

        System.out.println();
        System.out.println("\tTesting Network");
        evaluate(network, trainingSet);

        System.out.println();
        System.out.println("\tSaving Network to : " + "basicNetwork.eg");
        saveObject(new File("basicNetwork.eg"), network);

        Encog.getInstance().shutdown();

    }

    public void recreate(){
        System.out.println("\tCreating network.");
        Encoder encoder = new Encoder();
        final MLDataSet trainingSet = TrainingSetUtil.loadCSVTOMemory(
                CSVFormat.ENGLISH, "encoded.csv", true, 9, 1);
        final BasicNetwork network = EncogUtility.simpleFeedForward(9, 10,
                0, 1, true);

        System.out.println();
        System.out.println("\tTraining Network");
        train(network, trainingSet);

        System.out.println();
        System.out.println("\tTesting Network");
        evaluate(network, trainingSet);

        System.out.println();
        System.out.println("\tSaving Network to : " + "basicNetwork.eg");
        saveObject(new File("basicNetwork.eg"), network);

        Encog.getInstance().shutdown();
    }
}
