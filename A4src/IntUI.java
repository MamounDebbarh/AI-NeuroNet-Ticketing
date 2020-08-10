import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.encog.persist.EncogDirectoryPersistence.loadObject;

public class IntUI {
    String[] ticket = {"Request ? (y/n)",
            "Incident ? (y/n)",
            "WebServices ? (y/n)",
            "Login ? (y/n)",
            "Wireless ? (y/n)",
            "Printing ? (y/n)",
            "IdCards ? (y/n)",
            "Staff ? (y/n)",
            "Students ? (y/n)",};


    public void process() throws IOException {
        Encoder encoder = new Encoder();
        boolean noExit = true;
        Scanner input = new Scanner(System.in);
        System.out.println("\t\tExit the ticketing with 'exit'");

        while (noExit) {
            org.encog.neural.networks.BasicNetwork loadFromFileNetwork = (org.encog.neural.networks.BasicNetwork) loadObject(new File("basicNetwork.eg"));
            MLData inputData = new BasicMLData(9);
            boolean noTicket = true;
            System.out.println("\tEnter New ticket with 'ticket'");
            for (int i = 0; i < ticket.length; i++) {
                System.out.println(ticket[i]);
                String in = input.next();
                if (in.equals("ticket")){
                    noTicket = false;
                    break;
                }else if (in.equals("exit")){
                    noExit = false;
                    break;
                }
                if (!in.equals("y") && !in.equals("n")) {
                    System.out.println("Please enter either y for YES or n for NO");
                    i--;
                    continue;
                }
                String dataIn;
                if (in.equals("y")) {
                    dataIn = "1";
                } else {
                    dataIn = "0";
                }

                inputData.setData(i, Double.parseDouble(dataIn));
                System.out.println(inputData.toString());
                System.out.println();

                if (i > 4) {
                    MLData output = loadFromFileNetwork.compute(inputData);
                    double netOut = output.getData(0);
                    double roundOut = Math.round(netOut * 4) / 4f;
                    String team = encoder.getTeam(roundOut);
                    System.out.println("Network Output: " + netOut);
                    System.out.println("Round: " + roundOut);
                    System.out.println("Probable Team: " + team);
                    System.out.println();
                }


            }
            MLData output = loadFromFileNetwork.compute(inputData);
            double netOut = output.getData(0);
            double roundOut = Math.round(netOut * 4) / 4f;
            String team = encoder.getTeam(roundOut);
            System.out.println("Network Output: " + netOut);
            System.out.println("Round: " + roundOut);
            System.out.println("Final Team: " + team);
            if (noExit&&noTicket){
                System.out.println("\tAre you satisfied? (enter n if you wish to enter the appropriate team)");
                String in = input.next();
                if (in.equals("n")){
                    System.out.println("teams: 1 for Emergencies ; 2 for Networking ; 3 for Credentials ; 4 for Datawarehouse ; 5 for Equipment (exit by entering any other key)");
                    String userTeam = input.next();
                    // encode ticket
                    switch (userTeam){
                        case "1": userTeam = "0";
                            break;
                        case "2": userTeam = "0.25";
                            break;
                        case "3": userTeam = "0.50";
                            break;
                        case "4": userTeam = "0.75";
                            break;
                        case "5": userTeam = "1";
                            break;
                        default:
                            System.out.println("\t\tYou have exited the service. Thank you");
                            System.exit(0);
                            break;

                    }
                    // write new ticket
                    String newTicket = inputData.getData(0) + "," + inputData.getData(1) + "," + inputData.getData(2) + "," +
                            inputData.getData(3) + "," +inputData.getData(4) + "," +inputData.getData(5) + "," +
                            inputData.getData(6) + "," +inputData.getData(7) + "," +inputData.getData(8) + "," + userTeam;
                    System.out.println("\t\tNew Ticket:");
                    System.out.println(newTicket);
                    encoder.writeTicket(newTicket);

                    //retrain network
                    Network network = new Network();
                    network.recreate();
                }
            }
        }
        System.out.println("\t\tYou have exited the service. Thank you");
    }
}
