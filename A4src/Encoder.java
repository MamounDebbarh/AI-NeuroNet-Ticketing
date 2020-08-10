import java.io.*;

public class Encoder {
    public void encod() throws IOException {
        FileReader fr = new FileReader("tickets.csv");
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter("encoded.csv");
        String line;

        while((line = br.readLine()) != null)
        {
            line = line.replace("Yes","1");
            line = line.replace("No","0");

            line = line.replace("Emergencies","0");
            line = line.replace("Networking","0.25");
            line = line.replace("Credentials","0.50");
            line = line.replace("Datawarehouse","0.75");
            line = line.replace("Equipment","1");
            fw.write(line, 0, line.length());
            fw.write(System.lineSeparator());

        }
        fr.close();
        fw.close();
    }

    public String getTeam(double out){
        String team="";
        if (out == 0){
            team = "Emergencies";
        }else if (out == 0.25){
            team = "Networking";
        } else if(out == 0.50){
            team = "Credentials";
        } else if(out == 0.75){
            team = "Datawarehouse";
        } else if(out == 1){
            team = "Equipment";
        }
        return team;
    }

    public void writeTicket(String ticket) throws IOException {
        Writer output;
        output = new BufferedWriter(new FileWriter("encoded.csv", true));
        output.append(ticket).append("\n");
        output.close();
    }
}
