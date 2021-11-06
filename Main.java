import Core.SimManager;
import Shop.Checkout;
import Shop.Events.NewClient;
import Core.SimStats;
import Times.Priority;
import Times.TimeBetweenClients;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Checkout ch = new Checkout();
        Checkout ch2 = new Checkout();
        SimManager sim = SimManager.getInstance();
        new NewClient(sim.simTime() + TimeBetweenClients.produce(), Priority.produce(), ch, ch2);
        sim.setEndSimTime(100);
        sim.start();
        SimStats.show();
    }
}