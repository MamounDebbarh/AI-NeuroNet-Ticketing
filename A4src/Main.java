
import java.io.IOException;

public class Main {

    public static void main(final String args[]) throws IOException {
        switch (args[0]) {
            case "Bas":
                Network network = new Network();
                network.create();
                break;
            case "Int":
                IntUI ui = new IntUI();
                ui.process();
                break;
            case "Adv":
                Tester tester = new Tester();
                tester.testing();
                break;
             default:
                 System.out.println("usage: java Main <Bas|Int|Adv> ");
        }
    }
}
